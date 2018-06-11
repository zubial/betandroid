package net.zubial.msprotocol.io.connector;

import android.support.annotation.NonNull;

import net.zubial.msprotocol.enums.MspConnectorStateEnum;

import java.util.List;

public interface IMspConnector {

    MspConnectorStateEnum getCurrentState();

    Boolean isConnected();

    boolean connect(@NonNull String deviceAddress);

    void disconnect();

    void write(byte[] data);

    Boolean isRuning();

    void startLive(List<byte[]> messages);

    void pauseLive();

    void resumeLive();
}
