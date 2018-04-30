package net.zubial.betandroid;

import org.junit.Test;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MspTest {
    @Test
    public void useMspInt() {
/*
        try {

            MspBuffer encoder = new MspBuffer();
            encoder.newWriter(3);

            encoder.writeInt8(110);
            encoder.writeInt8(2);
            encoder.writeInt8(3);


            encoder.encode(MspDirectionEnum.MSP_INCOMING, MspMessageTypeEnum.MSP_API_VERSION);

            byte[] message = encoder.getMessage();
            System.out.println("MSP request : " + new String(message, 0, message.length));

            MspSystemData data = new MspSystemData();
            data = MspMapper.parseMessage(data, message);

            System.out.println("MSP response : Version = " + data.getMspProtocolVersion() + " MultiType = " + data.getBoardApiVersion());

            assertTrue(true);
        } catch (MspBaseException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        */
    }

    @Test
    public void useMspString() {
/*
        try {
            MspBuffer encoder = new MspBuffer();
            encoder.newWriter(7);

            encoder.writeString( "TEST");
            encoder.writeInt16(3);
            encoder.writeInt8(1);

            encoder.encode(MspDirectionEnum.MSP_INCOMING, MspMessageTypeEnum.MSP_BOARD_INFO);

            byte[] message = encoder.getMessage();
            System.out.println("MSP request : " + new String(message, 0, message.length));

            MspSystemData data = new MspSystemData();
            data = MspMapper.parseMessage(data, message);

            System.out.println("MSP response : BoardIdentifier = " + data.getBoardIdentifier() + "  -  " + data.getBoardType());

            assertTrue(true);
        } catch (MspBaseException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        */
    }

    @Test
    public void useMspService() {
/*
        try {
            MspService2 service = MspService2.getInstance();
            service.loadSystemData();

            assertTrue(true);

        } catch (MspBaseException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        */
    }
}
