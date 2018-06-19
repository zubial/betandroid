package net.zubial.msprotocol.io.connector;

import android.support.annotation.NonNull;

import net.zubial.msprotocol.enums.MspConnectorStateEnum;

import java.util.List;

public interface IMspConnector {

    boolean connect(@NonNull String deviceAddress);

    void disconnect();

    void write(byte[] data);

    void startLive(List<byte[]> messages);

    void pauseLive();

    void resumeLive();

    Boolean isConnected();

    Boolean isRuning();

    MspConnectorStateEnum getCurrentState();
}
