package net.zubial.betandroid.activities;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import net.zubial.betandroid.R;
import net.zubial.betandroid.helpers.UiFormatter;
import net.zubial.betandroid.helpers.UiUtils;
import net.zubial.msprotocol.MspService;
import net.zubial.msprotocol.data.MspData;
import net.zubial.msprotocol.enums.MspMessageEventEnum;

public class MspConfigurationSystem extends Fragment {

    private static final String TAG = "MspConfigBoard";

    private MspData mspData;

    // UI Composants
    private TextView txtConfigurationBoardName;
    private TextView txtConfigurationBoardIdentifier;
    private TextView txtConfigurationSensors;
    private TextView txtConfigurationSdcard;

    // Msp Event
    private BroadcastReceiver onMspMessageReceived = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MspService.EVENT_MESSAGE_RECEIVED.equals(intent.getAction())) {

                MspMessageEventEnum mspEvent = (MspMessageEventEnum) intent.getSerializableExtra(MspService.EXTRA_EVENT);
                if (MspMessageEventEnum.EVENT_MSP_SYSTEM_DATA.isEqual(mspEvent)) {

                    mspData = (MspData) intent.getSerializableExtra(MspService.EXTRA_DATA);
                    showData();
                }
            }
        }
    };

    public MspConfigurationSystem() {
        // Default Ctr
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_msp_configuration_system, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        FloatingActionButton fab = view.getRootView().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
                Snackbar.make(view, "Reload configuration", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        txtConfigurationBoardIdentifier = view.findViewById(R.id.txtConfigurationBoardIdentifier);
        txtConfigurationSensors = view.findViewById(R.id.txtConfigurationSensors);
        txtConfigurationSdcard = view.findViewById(R.id.txtConfigurationSdcard);

        txtConfigurationBoardName = view.findViewById(R.id.txtConfigurationBoardName);
        txtConfigurationBoardName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("BoardName");

                final EditText input = new EditText(getContext());

                input.setText(txtConfigurationBoardName.getText());
                input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                input.setSingleLine(true);
                input.setLines(1);
                input.setMaxLines(1);
                input.setGravity(Gravity.START | Gravity.TOP);
                builder.setView(input);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        MspService.getInstance().setBoardName(input.getText().toString());
                        MspService.getInstance().loadHandshake();

                        Snackbar.make(view, "Set Board name", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        IntentFilter onMspMessageReceivedFilter = new IntentFilter(MspService.EVENT_MESSAGE_RECEIVED);
        LocalBroadcastManager.getInstance(view.getContext()).registerReceiver(onMspMessageReceived, onMspMessageReceivedFilter);

        loadData();
    }

    private void loadData() {
        MspService.getInstance().loadHandshake();
        MspService.getInstance().loadSystemData();
        MspService.getInstance().loadFeaturesData();
        MspService.getInstance().loadBatteryData();
    }

    private void showData() {
        if (mspData != null) {
            if (txtConfigurationBoardIdentifier != null) {
                String boardIdentifier = mspData.getMspSystemData().getBoardIdentifier();
                if (mspData.getMspSystemData().getBoardFlightControllerIdentifier() != null) {
                    boardIdentifier += " - " + mspData.getMspSystemData().getBoardFlightControllerIdentifier().name();
                }
                boardIdentifier += " (" + mspData.getMspSystemData().getBoardApiVersion() + ")";

                txtConfigurationBoardIdentifier.setText(boardIdentifier);
            }

            if (txtConfigurationBoardName != null) {
                txtConfigurationBoardName.setText(mspData.getMspSystemData().getBoardName());
            }

            if (txtConfigurationSensors != null) {
                String configurationSensors = "";
                if (UiUtils.isTrue(mspData.getMspSystemData().getStatusHaveAccel())) {
                    configurationSensors += " - Accelerometer \n";
                }
                if (UiUtils.isTrue(mspData.getMspSystemData().getStatusHaveBaro())) {
                    configurationSensors += " - Barometer \n";
                }
                if (UiUtils.isTrue(mspData.getMspSystemData().getStatusHaveGps())) {
                    configurationSensors += " - GPS \n";
                }
                if (UiUtils.isTrue(mspData.getMspSystemData().getStatusHaveGyro())) {
                    configurationSensors += " - Gyrometer \n";
                }
                if (UiUtils.isTrue(mspData.getMspSystemData().getStatusHaveMag())) {
                    configurationSensors += " - Magnetometer \n";
                }
                if (UiUtils.isTrue(mspData.getMspSystemData().getStatusHaveSonar())) {
                    configurationSensors += " - Sonar \n";
                }
                txtConfigurationSensors.setText(configurationSensors);
            }

            if (txtConfigurationSdcard != null) {
                String configurationSdcard = "";
                if (UiUtils.isTrue(mspData.getMspSystemData().getSdcardSupported())) {
                    configurationSdcard += "SdCard supported";

                    if (UiUtils.isGtZero(mspData.getMspSystemData().getSdcardTotalSize())) {
                        configurationSdcard += "\nFree " + UiFormatter.formatByteSize(mspData.getMspSystemData().getSdcardFreeSize()) + " / " + UiFormatter.formatByteSize(mspData.getMspSystemData().getSdcardTotalSize());
                    }

                } else {
                    configurationSdcard += "SdCard not supported";
                }
                txtConfigurationSdcard.setText(configurationSdcard);
            }
        }
    }
}
