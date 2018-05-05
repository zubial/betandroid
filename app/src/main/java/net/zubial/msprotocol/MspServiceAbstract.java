package net.zubial.msprotocol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import net.zubial.msprotocol.data.MspData;
import net.zubial.msprotocol.enums.MspConnectorStateEnum;
import net.zubial.msprotocol.enums.MspDirectionEnum;
import net.zubial.msprotocol.enums.MspMessageEventEnum;
import net.zubial.msprotocol.enums.MspMessageTypeEnum;
import net.zubial.msprotocol.exceptions.MspBaseException;
import net.zubial.msprotocol.helpers.MspProtocolUtils;
import net.zubial.msprotocol.io.MspDecoder;
import net.zubial.msprotocol.io.MspEncoder;
import net.zubial.msprotocol.io.MspMapper;
import net.zubial.msprotocol.io.MspMessage;
import net.zubial.msprotocol.io.connector.MspBluetoothManager;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class MspServiceAbstract {

    // Connectors
    public static final String ACTION_CONNECT_BLUETOOTH = "net.zubial.msprotocol.action.CONNECT_BLUETOOTH";
    public static final String ACTION_DISCONNECT_BLUETOOTH = "net.zubial.msprotocol.action.DISCONNECT_BLUETOOTH";
    public static final String EXTRA_BLUETOOTH_ADDRESS = "net.zubial.msprotocol.extra.BLUETOOTH_ADDRESS";

    // Senders
    public static final String ACTION_SEND_COMMAND = "net.zubial.msprotocol.action.SEND_COMMAND";
    public static final String ACTION_SEND_MESSAGE = "net.zubial.msprotocol.action.SEND_MESSAGE";
    public static final String EXTRA_COMMAND = "net.zubial.msprotocol.extra.COMMAND";
    public static final String EXTRA_MULTI_COMMAND = "net.zubial.msprotocol.extra.MULTI_COMMAND";

    // Events
    public static final String EVENT_CONNECTED = "net.zubial.msprotocol.event.CONNECTED";
    public static final String EVENT_DISCONNECTED = "net.zubial.msprotocol.event.DISCONNECTED";
    public static final String EVENT_UNAVAILABLE = "net.zubial.msprotocol.event.UNAVAILABLE";
    public static final String EVENT_HANDSHAKE = "net.zubial.msprotocol.event.HANDSHAKE";
    public static final String EVENT_MESSAGE_RECEIVED = "net.zubial.msprotocol.event.MESSAGE_RECEIVED";
    public static final String EXTRA_EVENT = "net.zubial.msprotocol.extra.EVENT";
    public static final String EXTRA_DATA = "net.zubial.msprotocol.extra.DATA";
    public static final String EXTRA_MESSAGE = "net.zubial.msprotocol.extra.MESSAGE";
    public static final String EXTRA_CONNECTION_TYPE = "net.zubial.msprotocol.extra.CONNECTION_TYPE";
    protected static final String TAG = "MspService";

    // Local variables
    protected MspBluetoothManager bluetoothManager;
    protected ByteBuffer localInBuffer;
    protected MspData mspData;

    protected Context applicationContext;

    @SuppressLint("HandlerLeak")
    protected final Handler bluetoothHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == MspConnectorStateEnum.STATE_CONNECTING.getCode()) {
                Toast.makeText(applicationContext, "Bluetooth connecting", Toast.LENGTH_SHORT).show();

            } else if (msg.what == MspConnectorStateEnum.STATE_CONNECTED.getCode()) {
                broadcastConnectionEvent(EVENT_CONNECTED, "Bluetooth");
                Toast.makeText(applicationContext, "Bluetooth connected", Toast.LENGTH_SHORT).show();

                // Handshake
                ArrayList<MspMessageTypeEnum> listCommand = new ArrayList<>();
                listCommand.add(MspMessageTypeEnum.MSP_API_VERSION);
                listCommand.add(MspMessageTypeEnum.MSP_FC_VARIANT);
                listCommand.add(MspMessageTypeEnum.MSP_FC_VERSION);
                listCommand.add(MspMessageTypeEnum.MSP_BOARD_INFO);
                listCommand.add(MspMessageTypeEnum.MSP_BUILD_INFO);
                listCommand.add(MspMessageTypeEnum.MSP_NAME);

                sendMultiCommand(listCommand);

            } else if (msg.what == MspConnectorStateEnum.STATE_HANDSHAKE.getCode()) {
                broadcastConnectionEvent(EVENT_HANDSHAKE, "Bluetooth");
                Toast.makeText(applicationContext, "Handshake done", Toast.LENGTH_SHORT).show();

            } else if (msg.what == MspConnectorStateEnum.STATE_DISCONNECTED.getCode()) {
                broadcastConnectionEvent(EVENT_DISCONNECTED, "Bluetooth");
                Toast.makeText(applicationContext, "Bluetooth disconnected", Toast.LENGTH_SHORT).show();

            } else if (msg.what == MspConnectorStateEnum.STATE_UNAVAILABLE.getCode()) {
                broadcastConnectionEvent(EVENT_UNAVAILABLE, "Bluetooth");
                Toast.makeText(applicationContext, "Bluetooth unavailable", Toast.LENGTH_SHORT).show();

            } else if (msg.what == MspConnectorStateEnum.STATE_RECEIVING.getCode()) {
                ByteBuffer readBuffer = (ByteBuffer) msg.obj;

                if (readBuffer != null
                        && readBuffer.limit() > 0) {
                    readBuffer.position(0);

                    int localLenght = 0;
                    if (localInBuffer != null) {
                        localInBuffer.position(0);
                        localLenght = localInBuffer.limit();
                    }
                    ByteBuffer newBuffer = ByteBuffer.allocate(localLenght + readBuffer.limit());
                    if (localInBuffer != null) {
                        newBuffer.put(localInBuffer);
                    }
                    newBuffer.put(readBuffer);

                    localInBuffer = newBuffer;

                    try {
                        MspMessage inMessage = MspDecoder.decode(localInBuffer);
                        localInBuffer = ByteBuffer.wrap(inMessage.getNextMessage());

                        if (inMessage.isLoad()) {
                            List<MspMessageEventEnum> messageEvents = MspMapper.parseMessage(mspData, inMessage);

                            Log.d(TAG, "MSP Response : " + inMessage.getMessageType().name() + " " + MspProtocolUtils.toHexString(inMessage.getPayload()));

                            if (MspMessageTypeEnum.MSP_API_VERSION.equals(inMessage.getMessageType())) {
                                broadcastMessageEvent(EVENT_HANDSHAKE, MspMessageEventEnum.EVENT_MSP_SYSTEM_DATA, inMessage, mspData);

                            } else if (messageEvents != null && !messageEvents.isEmpty()) {
                                for (MspMessageEventEnum messageEvent : messageEvents) {
                                    broadcastMessageEvent(EVENT_MESSAGE_RECEIVED, messageEvent, inMessage, mspData);
                                }
                            }
                        }
                    } catch (MspBaseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    protected MspServiceAbstract(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    // Sender handles
    protected void sendCommand(MspMessageTypeEnum command) {
        try {
            byte[] message = MspEncoder.encode(MspDirectionEnum.MSP_OUTBOUND, command, null);

            Log.d(TAG, "MSP Request : " + command.name());

            if (isConnected()) {
                bluetoothManager.write(message);
            }

        } catch (MspBaseException e) {
            e.printStackTrace();
        }
    }

    protected void sendMultiCommand(ArrayList<MspMessageTypeEnum> multiCommand) {
        if (multiCommand != null
                && !multiCommand.isEmpty()) {
            for (MspMessageTypeEnum command : multiCommand) {
                sendCommand(command);
            }
        }
    }

    protected void sendMessage(MspMessageTypeEnum messageType, byte[] data) {
        try {
            byte[] message = MspEncoder.encode(MspDirectionEnum.MSP_OUTBOUND, messageType, data);

            Log.d(TAG, "MSP Request : " + messageType.name() + " " + MspProtocolUtils.toHexString(data));

            if (isConnected()) {
                bluetoothManager.write(message);
            }
        } catch (MspBaseException e) {
            e.printStackTrace();
        }
    }

    // Broadcast events
    protected void broadcastConnectionEvent(String event, String connectionType) {
        Intent intent = new Intent(event);
        intent.putExtra(EXTRA_CONNECTION_TYPE, connectionType);
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent);
    }

    protected void broadcastMessageEvent(String event, MspMessageEventEnum messageEvent, MspMessage message, MspData data) {
        Intent intent = new Intent(event);
        intent.putExtra(EXTRA_EVENT, messageEvent);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_DATA, data);
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent);
    }

    // Connector handles
    public void connectBluetooth(String address) {
        bluetoothManager = new MspBluetoothManager(bluetoothHandler);
        bluetoothManager.connect(address);

        mspData = new MspData();
    }

    public void disconnectBluetooth() {
        bluetoothManager.disconnect();
        mspData = new MspData();
        localInBuffer = null;
    }

    public Boolean isConnected() {
        return (bluetoothManager != null
                && bluetoothManager.isConnected());
    }
}
