package com.betandroid.msprotocol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.betandroid.msprotocol.data.MspData;
import com.betandroid.msprotocol.io.MspMapper;
import com.betandroid.msprotocol.enums.MspConnectorStateEnum;
import com.betandroid.msprotocol.enums.MspDirectionEnum;
import com.betandroid.msprotocol.enums.MspMessageTypeEnum;
import com.betandroid.msprotocol.exceptions.MspBaseException;
import com.betandroid.msprotocol.helpers.MspUtils;
import com.betandroid.msprotocol.io.connector.MspBluetoothManager;
import com.betandroid.msprotocol.io.MspDecoder;
import com.betandroid.msprotocol.io.MspMessage;
import com.betandroid.msprotocol.io.MspEncoder;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class MspServiceAbstract {

    protected static final String TAG = "MspService";

    // Connectors
    public static final String ACTION_CONNECT_BLUETOOTH = "com.betandroid.msp.action.CONNECT_BLUETOOTH";
    public static final String ACTION_DISCONNECT_BLUETOOTH = "com.betandroid.msp.action.DISCONNECT_BLUETOOTH";

    public static final String EXTRA_BLUETOOTH_ADDRESS = "com.betandroid.msp.extra.BLUETOOTH_ADDRESS";

    // Senders
    public static final String ACTION_SEND_COMMAND = "com.betandroid.msp.action.SEND_COMMAND";
    public static final String ACTION_SEND_MESSAGE = "com.betandroid.msp.action.SEND_MESSAGE";

    public static final String EXTRA_COMMAND = "com.betandroid.msp.extra.COMMAND";
    public static final String EXTRA_MULTI_COMMAND = "com.betandroid.msp.extra.MULTI_COMMAND";

    // Events
    public static final String EVENT_CONNECTED = "com.betandroid.msp.event.CONNECTED";
    public static final String EVENT_DISCONNECTED = "com.betandroid.msp.event.DISCONNECTED";
    public static final String EVENT_UNAVAILABLE = "com.betandroid.msp.event.UNAVAILABLE";

    public static final String EVENT_MESSAGE_RECEIVED = "com.betandroid.msp.event.MESSAGE_RECEIVED";

    public static final String EXTRA_DATA = "com.betandroid.msp.extra.DATA";
    public static final String EXTRA_MESSAGE = "com.betandroid.msp.extra.MESSAGE";
    public static final String EXTRA_CONNECTION_TYPE = "com.betandroid.msp.extra.CONNECTION_TYPE";

    // Local variables
    protected MspBluetoothManager bluetoothManager;
    protected ByteBuffer localInBuffer;
    protected MspData mspData;

    protected Context applicationContext;

    protected MspServiceAbstract(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    // Sender handles
    protected void sendCommand(MspMessageTypeEnum command) {
        try {
            byte[] message = MspEncoder.encode(MspDirectionEnum.MSP_OUTBOUND, MspMessageTypeEnum.findByValue(command.getValue()), null);

            Log.d(TAG,"MSP request : " + MspUtils.toHexString(message));

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

            Log.d(TAG,"MSP request : " + MspUtils.toHexString(message));

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

    protected void broadcastMessageEvent(String event, MspMessage message, MspData data) {
        Intent intent = new Intent(event);
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
    }

    public Boolean isConnected() {
        return (bluetoothManager != null
                && bluetoothManager.isConnected());
    }

    @SuppressLint("HandlerLeak")
    protected final Handler bluetoothHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == MspConnectorStateEnum.STATE_CONNECTING.getCode()) {
                Toast.makeText(applicationContext, "Bluetooth connecting", Toast.LENGTH_SHORT).show();

            } else if (msg.what == MspConnectorStateEnum.STATE_CONNECTED.getCode()) {
                broadcastConnectionEvent(EVENT_CONNECTED, "Bluetooth");
                Toast.makeText(applicationContext, "Bluetooth connected",Toast.LENGTH_SHORT).show();

            } else if (msg.what == MspConnectorStateEnum.STATE_DISCONNECTED.getCode()) {
                broadcastConnectionEvent(EVENT_DISCONNECTED, "Bluetooth");
                Toast.makeText(applicationContext, "Bluetooth disconnected",Toast.LENGTH_SHORT).show();

            } else if (msg.what == MspConnectorStateEnum.STATE_UNAVAILABLE.getCode()) {
                broadcastConnectionEvent(EVENT_UNAVAILABLE, "Bluetooth");
                Toast.makeText(applicationContext, "Bluetooth unavailable",Toast.LENGTH_SHORT).show();

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
                            MspMapper.parseMessage(mspData, inMessage);

                            Log.d(TAG, "MSP response : " + MspUtils.toHexString(inMessage.getPayload()));

                            broadcastMessageEvent(EVENT_MESSAGE_RECEIVED, inMessage, mspData);
                        }
                    } catch (MspBaseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };
}
