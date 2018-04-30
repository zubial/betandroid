package net.zubial.msprotocol.io.connector;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import net.zubial.msprotocol.enums.MspConnectorStateEnum;
import net.zubial.msprotocol.helpers.MspUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

public class MspBluetoothManager {

    private static final String TAG = "MspBluetoothManager";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final Handler serviceHandler;
    private MspConnectorStateEnum currentState;
    private Boolean isConnected;
    private ConnectedThread mConnectedThread;

    private BluetoothAdapter bluetoothAdapter = null;
    private BluetoothDevice bluetoothDevice = null;
    private BluetoothSocket bluetoothSocket = null;

    private OutputStream outStream = null;
    private InputStream inStream = null;

    public MspBluetoothManager(@NonNull Handler handler) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        currentState = MspConnectorStateEnum.STATE_NONE;
        isConnected = false;
        serviceHandler = handler;
    }

    private Boolean checkBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
        if (bluetoothAdapter != null
                && bluetoothAdapter.isEnabled()) {
            Log.d(TAG, "Adapter is up !");
            return true;
        }
        Log.d(TAG, "Adapter is down !");
        return false;
    }

    public MspConnectorStateEnum getCurrentState() {
        return currentState;
    }

    public Boolean isConnected() {
        return isConnected;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    private synchronized void handleState(MspConnectorStateEnum state) {
        if (currentState == state) {
            return;
        }

        Log.d(TAG, "State changed " + currentState.getLabel() + " -> " + state.getLabel());
        serviceHandler.obtainMessage(state.getCode(), currentState.getCode(), -1).sendToTarget();

        currentState = state;
    }

    public boolean connect(@NonNull String deviceAddress) {
        if (!checkBluetoothAdapter(bluetoothAdapter)) {
            isConnected = false;
            handleState(MspConnectorStateEnum.STATE_UNAVAILABLE);

            return false;
        }

        if (!isConnected) {

            handleState(MspConnectorStateEnum.STATE_CONNECTING);

            try {
                bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceAddress);
                bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
                bluetoothSocket.connect();

            } catch (IOException e) {
                Log.d(TAG, " Error connect socket.", e);

                try {
                    bluetoothSocket.close();
                } catch (IOException e1) {
                    Log.d(TAG, " Error close current socket.", e1);
                }

                isConnected = false;
                handleState(MspConnectorStateEnum.STATE_UNAVAILABLE);

                return isConnected;

            }

            if (bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
            }

            isConnected = true;

            handleState(MspConnectorStateEnum.STATE_CONNECTED);

            try {
                outStream = bluetoothSocket.getOutputStream();
                inStream = bluetoothSocket.getInputStream();

            } catch (IOException e) {
                Log.e(TAG, "Output stream creation failed.", e);

                isConnected = false;
                handleState(MspConnectorStateEnum.STATE_UNAVAILABLE);
            }

            mConnectedThread = new ConnectedThread(bluetoothSocket);
            mConnectedThread.start();

        }
        return isConnected;
    }

    public void disconnect() {
        if (mConnectedThread != null) {
            mConnectedThread.interrupt();
        }

        if (outStream != null) {
            try {
                outStream.close();
            } catch (IOException e) {
                Log.e(TAG, "Couldn't close output stream.", e);
            }
        }

        if (inStream != null) {
            try {
                inStream.close();
            } catch (IOException e) {
                Log.e(TAG, "Couldn't close output stream.", e);
            }
        }

        try {
            if (bluetoothSocket != null) {
                bluetoothSocket.close();
            }

            isConnected = false;
            handleState(MspConnectorStateEnum.STATE_DISCONNECTED);

        } catch (Exception e) {
            Log.e(TAG, "Couldn't close socket.", e);
        }

        handleState(MspConnectorStateEnum.STATE_NONE);
    }

    public void write(byte[] data) {
        if (isConnected) {
            handleState(MspConnectorStateEnum.STATE_WRITING);

            mConnectedThread.write(data);

            handleState(MspConnectorStateEnum.STATE_WAITING);
        }
    }

    // New thread for keep calm mainThread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                // Create I/O streams
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.d(TAG, "I/O exception", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] bytesBuffer = null;
            int bytesLenght;

            // Receive message
            while (true) {
                try {
                    byte[] localBuffer = new byte[256];
                    bytesLenght = mmInStream.read(localBuffer);

                    localBuffer = MspUtils.cleanByte(localBuffer, bytesLenght);

                    handleState(MspConnectorStateEnum.STATE_RECEIVING);
                    bytesBuffer = MspUtils.concat(bytesBuffer, localBuffer);

                    if (mmInStream.available() < 1) {
                        serviceHandler.obtainMessage(MspConnectorStateEnum.STATE_RECEIVING.getCode(), bytesLenght, -1, ByteBuffer.wrap(bytesBuffer)).sendToTarget();
                        bytesBuffer = null;
                        handleState(MspConnectorStateEnum.STATE_WAITING);
                    }
                } catch (IOException e) {
                    Log.d(TAG, "Read exception", e);
                    disconnect();
                    break;
                }
            }
        }

        // Write methods
        public void write(byte[] msgBuffer) {
            try {
                mmOutStream.write(msgBuffer);

            } catch (IOException e) {
                Log.d(TAG, "Write exception", e);
                disconnect();
            }
        }

        public void write(String input) {
            write(input.getBytes());
        }
    }
}
