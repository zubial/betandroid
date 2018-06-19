package net.zubial.msprotocol.io.connector;

import android.support.annotation.NonNull;

import net.zubial.msprotocol.enums.MspConnectorStateEnum;

import java.util.List;

public class MspUSBConnector implements IMspConnector {

    @Override
    public boolean connect(@NonNull String deviceAddress) {
        return false;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public void write(byte[] data) {

    }

    @Override
    public void startLive(List<byte[]> messages) {

    }

    @Override
    public void pauseLive() {

    }

    @Override
    public void resumeLive() {

    }

    @Override
    public Boolean isConnected() {
        return null;
    }

    @Override
    public Boolean isRuning() {
        return null;
    }

    @Override
    public MspConnectorStateEnum getCurrentState() {
        return null;
    }
}
