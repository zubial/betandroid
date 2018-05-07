package net.zubial.betandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.zubial.betandroid.activities.MspConfigurationActivity;
import net.zubial.betandroid.activities.MspLiveActivity;
import net.zubial.msprotocol.MspService;
import net.zubial.msprotocol.data.MspData;
import net.zubial.msprotocol.enums.MspMessageEventEnum;
import net.zubial.msprotocol.enums.MspMessageTypeEnum;
import net.zubial.msprotocol.io.MspMessage;

public class MainConnected extends Fragment {

    private static final String TAG = "Main";

    private MspData mspData;

    // UI Components
    private TextView txtConnectedTitle;
    private TextView txtBoardName;
    private TextView txtBoardIdentifier;
    // Msp Event
    private BroadcastReceiver onMspMessageReceived = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MspService.EVENT_MESSAGE_RECEIVED.equals(intent.getAction())) {

                MspMessage mspMessage = (MspMessage) intent.getSerializableExtra(MspService.EXTRA_MESSAGE);
                if (mspMessage != null
                        && mspMessage.getMessageType().isEqual(MspMessageTypeEnum.MSP_ACC_CALIBRATION)) {
                    Toast.makeText(getContext(), "Calibration done", Toast.LENGTH_SHORT).show();
                }

                MspMessageEventEnum mspEvent = (MspMessageEventEnum) intent.getSerializableExtra(MspService.EXTRA_EVENT);
                if (MspMessageEventEnum.EVENT_MSP_SYSTEM_DATA.isEqual(mspEvent)) {
                    mspData = (MspData) intent.getSerializableExtra(MspService.EXTRA_DATA);
                    showData();
                }
            }
        }
    };

    public MainConnected() {
        // Default Ctr
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main_connected, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppBarLayout appBar = view.getRootView().findViewById(R.id.app_bar);
        appBar.setExpanded(false);

        txtConnectedTitle = view.findViewById(R.id.txtConnectedTitle);
        txtConnectedTitle.setText("Handshake...");

        txtBoardName = view.findViewById(R.id.txtBoardName);
        txtBoardIdentifier = view.findViewById(R.id.txtBoardIdentifier);

        Button cmdAccCalibration = view.findViewById(R.id.cmdAccCalibration);
        cmdAccCalibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MspService.getInstance().executeAccCalibration();
            }
        });

        Button cmdDisconnect = view.findViewById(R.id.cmdDisconnect);
        cmdDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MspService.getInstance().disconnectBluetooth();
            }
        });

        Button cmdConfiguration = view.findViewById(R.id.cmdConfiguration);
        cmdConfiguration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MspConfigurationActivity.class));
            }
        });

        Button cmdLive = view.findViewById(R.id.cmdLive);
        cmdLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MspLiveActivity.class));
            }
        });

        IntentFilter onMspMessageReceivedFilter = new IntentFilter(MspService.EVENT_MESSAGE_RECEIVED);
        LocalBroadcastManager.getInstance(view.getContext()).registerReceiver(onMspMessageReceived, onMspMessageReceivedFilter);

        loadData();
    }

    private void loadData() {
        MspService.getInstance().loadSystemData();
    }

    private void showData() {
        if (mspData != null) {
            if (txtConnectedTitle != null) {
                txtConnectedTitle.setText("Connected");
            }

            if (txtBoardName != null) {
                txtBoardName.setText(mspData.getMspSystemData().getBoardName());
            }

            if (txtBoardIdentifier != null) {
                String boardIdentifier = mspData.getMspSystemData().getBoardIdentifier();
                if (mspData.getMspSystemData().getBoardFlightControllerIdentifier() != null) {
                    boardIdentifier += " - " + mspData.getMspSystemData().getBoardFlightControllerIdentifier().name();
                }
                boardIdentifier += " (" + mspData.getMspSystemData().getBoardApiVersion() + ")";

                txtBoardIdentifier.setText(boardIdentifier);
            }
        }
    }
}