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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import net.zubial.betandroid.R;
import net.zubial.betandroid.helpers.MspFieldUtils;
import net.zubial.msprotocol.MspService;
import net.zubial.msprotocol.data.MspData;
import net.zubial.msprotocol.enums.MspFeatureEnum;
import net.zubial.msprotocol.io.MspMessage;

import java.util.Map;

public class MspConfigBoardContent extends Fragment {

    private static final String TAG = "MspConfigBoard";

    private MspData mspData;
    private BroadcastReceiver onMspMessageReceived = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, intent.getAction());

            if (MspService.EVENT_MESSAGE_RECEIVED.equals(intent.getAction())) {
                MspMessage message = (MspMessage) intent.getSerializableExtra(MspService.EXTRA_MESSAGE);
                mspData = (MspData) intent.getSerializableExtra(MspService.EXTRA_DATA);
                showData(mspData);

            }
        }
    };

    public MspConfigBoardContent() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_msp_config_board, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        FloatingActionButton fab = (FloatingActionButton) view.getRootView().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
                Snackbar.make(view, "Reload configuration", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView txtConfigurationBoardName = view.findViewById(R.id.txtConfigurationBoardName);
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
                input.setGravity(Gravity.LEFT | Gravity.TOP);
                builder.setView(input);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        MspService.getInstance().setBoardName(input.getText().toString());
                        MspService.getInstance().loadSystemData();

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

        TextView txtConfigurationBatteryMaxCell = view.findViewById(R.id.txtConfigurationBatteryMaxCell);
        txtConfigurationBatteryMaxCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Max cell voltage");

                final NumberPicker input = new NumberPicker(getContext());
                final String[] values = {"3.0",
                        "3.1",
                        "3.2",
                        "3.3",
                        "3.4",
                        "3.5",
                        "3.6",
                        "3.7",
                        "3.8",
                        "3.9",
                        "4.0",
                        "4.1",
                        "4.2",
                        "4.3"};
                input.setMinValue(0);
                input.setMaxValue(values.length - 1);
                input.setDisplayedValues(values);
                input.setWrapSelectorWheel(true);
                input.setGravity(Gravity.LEFT | Gravity.TOP);
                builder.setView(input);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        mspData.getMspBatteryData().setVbatMaxCellVoltage(30 + input.getValue());

                        MspService.getInstance().setBatteryConfig(mspData.getMspBatteryData());
                        MspService.getInstance().loadBatteryData();

                        Snackbar.make(view, "Set Cell Voltage", Snackbar.LENGTH_LONG)
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

        TextView txtConfigurationBatteryMinCell = view.findViewById(R.id.txtConfigurationBatteryMinCell);
        txtConfigurationBatteryMinCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Min cell voltage");

                final NumberPicker input = new NumberPicker(getContext());
                final String[] values = {"3.0",
                        "3.1",
                        "3.2",
                        "3.3",
                        "3.4",
                        "3.5",
                        "3.6",
                        "3.7",
                        "3.8",
                        "3.9",
                        "4.0",
                        "4.1",
                        "4.2",
                        "4.3"};
                input.setMinValue(0);
                input.setMaxValue(values.length - 1);
                input.setDisplayedValues(values);
                input.setWrapSelectorWheel(true);
                input.setGravity(Gravity.LEFT | Gravity.TOP);
                builder.setView(input);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        mspData.getMspBatteryData().setVbatMinCellVoltage(30 + input.getValue());

                        MspService.getInstance().setBatteryConfig(mspData.getMspBatteryData());
                        MspService.getInstance().loadBatteryData();

                        Snackbar.make(view, "Set Cell Voltage", Snackbar.LENGTH_LONG)
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

        TextView txtConfigurationBatteryWarningCell = view.findViewById(R.id.txtConfigurationBatteryWarningCell);
        txtConfigurationBatteryWarningCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Min cell voltage");

                final NumberPicker input = new NumberPicker(getContext());
                final String[] values = {"3.0",
                        "3.1",
                        "3.2",
                        "3.3",
                        "3.4",
                        "3.5",
                        "3.6",
                        "3.7",
                        "3.8",
                        "3.9",
                        "4.0",
                        "4.1",
                        "4.2",
                        "4.3"};
                input.setMinValue(0);
                input.setMaxValue(values.length - 1);
                input.setDisplayedValues(values);
                input.setWrapSelectorWheel(true);
                input.setGravity(Gravity.LEFT | Gravity.TOP);
                builder.setView(input);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        mspData.getMspBatteryData().setVbatWarningCellVoltage(30 + input.getValue());

                        MspService.getInstance().setBatteryConfig(mspData.getMspBatteryData());
                        MspService.getInstance().loadBatteryData();

                        Snackbar.make(view, "Set Cell Voltage", Snackbar.LENGTH_LONG)
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
        MspService.getInstance().loadSystemData();
        MspService.getInstance().loadBatteryData();
    }

    private void showData(MspData mspData) {
        if (getView() != null) {
            TextView txtConfigurationBoardIdentifier = getView().findViewById(R.id.txtConfigurationBoardIdentifier);
            if (txtConfigurationBoardIdentifier != null) {
                String boardIdentifier = mspData.getMspSystemData().getBoardIdentifier();
                if (mspData.getMspSystemData().getBoardFlightControllerIdentifier() != null) {
                    boardIdentifier += " - " + mspData.getMspSystemData().getBoardFlightControllerIdentifier().name();
                }
                boardIdentifier += " (" + mspData.getMspSystemData().getBoardApiVersion() + ")";

                txtConfigurationBoardIdentifier.setText(boardIdentifier);
            }


            TextView txtConfigurationBoardName = getView().findViewById(R.id.txtConfigurationBoardName);
            if (txtConfigurationBoardName != null) {
                txtConfigurationBoardName.setText(mspData.getMspSystemData().getBoardName());
            }

            TextView txtConfigurationSensors = getView().findViewById(R.id.txtConfigurationSensors);
            if (txtConfigurationSensors != null) {
                String configurationSensors = "";
                if (MspFieldUtils.isTrue(mspData.getMspSystemData().getStatusHaveAccel())) {
                    configurationSensors += " - Accelerometer \n";
                }
                if (MspFieldUtils.isTrue(mspData.getMspSystemData().getStatusHaveBaro())) {
                    configurationSensors += " - Barometer \n";
                }
                if (MspFieldUtils.isTrue(mspData.getMspSystemData().getStatusHaveGps())) {
                    configurationSensors += " - GPS \n";
                }
                if (MspFieldUtils.isTrue(mspData.getMspSystemData().getStatusHaveGyro())) {
                    configurationSensors += " - Gyrometer \n";
                }
                if (MspFieldUtils.isTrue(mspData.getMspSystemData().getStatusHaveMag())) {
                    configurationSensors += " - Magnetometer \n";
                }
                if (MspFieldUtils.isTrue(mspData.getMspSystemData().getStatusHaveSonar())) {
                    configurationSensors += " - Sonar \n";
                }
                txtConfigurationSensors.setText(configurationSensors);
            }

            TextView txtConfigurationSdcard = getView().findViewById(R.id.txtConfigurationSdcard);
            if (txtConfigurationSdcard != null) {
                String configurationSdcard = "";
                if (MspFieldUtils.isTrue(mspData.getMspSystemData().getSdcardSupported())) {
                    configurationSdcard += "SdCard supported";

                    if (MspFieldUtils.isGtZero(mspData.getMspSystemData().getSdcardTotalSize())) {
                        configurationSdcard += "\nFree " + MspFieldUtils.formatByteSize(mspData.getMspSystemData().getSdcardFreeSize()) + " / " + MspFieldUtils.formatByteSize(mspData.getMspSystemData().getSdcardTotalSize());
                    }

                } else {
                    configurationSdcard += "SdCard not supported";
                }
                txtConfigurationSdcard.setText(configurationSdcard);
            }

            TextView txtConfigurationBatteryMaxCell = getView().findViewById(R.id.txtConfigurationBatteryMaxCell);
            if (txtConfigurationBatteryMaxCell != null) {
                txtConfigurationBatteryMaxCell.setText(MspFieldUtils.formatVoltage(mspData.getMspBatteryData().getVbatMaxCellVoltage()));
            }

            TextView txtConfigurationBatteryWarningCell = getView().findViewById(R.id.txtConfigurationBatteryWarningCell);
            if (txtConfigurationBatteryWarningCell != null) {
                txtConfigurationBatteryWarningCell.setText(MspFieldUtils.formatVoltage(mspData.getMspBatteryData().getVbatWarningCellVoltage()));
            }

            TextView txtConfigurationBatteryMinCell = getView().findViewById(R.id.txtConfigurationBatteryMinCell);
            if (txtConfigurationBatteryMinCell != null) {
                txtConfigurationBatteryMinCell.setText(MspFieldUtils.formatVoltage(mspData.getMspBatteryData().getVbatMinCellVoltage()));
            }

            TextView txtConfigurationFeatures = getView().findViewById(R.id.txtConfigurationFeatures);
            if (txtConfigurationFeatures != null) {
                String features = "";
                if (mspData.getMspSystemData().getFeatures() != null
                        && !mspData.getMspSystemData().getFeatures().isEmpty()) {
                    for (Map.Entry<MspFeatureEnum, Boolean> entry : mspData.getMspSystemData().getFeatures().entrySet()) {
                        features += "Feature : " + entry.getKey() + " Value : " + entry.getValue() + "\n";
                    }
                }
                txtConfigurationFeatures.setText(features);
            }
        }
    }


}
