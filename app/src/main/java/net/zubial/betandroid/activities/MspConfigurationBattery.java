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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.zubial.betandroid.R;
import net.zubial.betandroid.helpers.UiFormatter;
import net.zubial.betandroid.helpers.UiUtils;
import net.zubial.msprotocol.MspService;
import net.zubial.msprotocol.data.MspData;
import net.zubial.msprotocol.enums.MspMessageEventEnum;

public class MspConfigurationBattery extends Fragment {

    private static final String TAG = "MspConfiguration";

    private MspData mspData;

    // UI Composants
    private TextView txtLiveBatteryType;
    private TextView txtLiveBatteryVoltage;
    private ProgressBar barLiveBatteryUsage;

    private TextView txtConfigurationBatteryMaxCell;
    private TextView txtConfigurationBatteryMinCell;
    private TextView txtConfigurationBatteryWarningCell;

    // Msp Event
    private BroadcastReceiver onMspMessageReceived = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MspService.EVENT_MESSAGE_RECEIVED.equals(intent.getAction())) {
                MspMessageEventEnum mspEvent = (MspMessageEventEnum) intent.getSerializableExtra(MspService.EXTRA_EVENT);
                if (MspMessageEventEnum.EVENT_MSP_BATTERY_DATA.isEqual(mspEvent)) {

                    mspData = (MspData) intent.getSerializableExtra(MspService.EXTRA_DATA);
                    showData();
                }
            }
        }
    };

    public MspConfigurationBattery() {
        // Default Ctr
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_msp_configuration_battery, container, false);
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

        txtLiveBatteryType = view.findViewById(R.id.txtLiveBatteryType);
        txtLiveBatteryVoltage = view.findViewById(R.id.txtLiveBatteryVoltage);
        barLiveBatteryUsage = view.findViewById(R.id.barLiveBatteryUsage);

        txtConfigurationBatteryMaxCell = view.findViewById(R.id.txtConfigurationBatteryMaxCell);
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
                input.setGravity(Gravity.START | Gravity.TOP);
                input.setValue(mspData.getMspBatteryData().getVbatMaxCellVoltage() - 30);
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

        txtConfigurationBatteryMinCell = view.findViewById(R.id.txtConfigurationBatteryMinCell);
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
                input.setGravity(Gravity.START | Gravity.TOP);
                input.setValue(mspData.getMspBatteryData().getVbatMinCellVoltage() - 30);
                builder.setView(input);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        mspData.getMspBatteryData().setVbatMinCellVoltage(30 + input.getValue());

                        MspService.getInstance().setBatteryConfig(mspData.getMspBatteryData());
                        MspService.getInstance().loadBatteryData();

                        Snackbar.make(view, "Set Cell Voltage", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }).setNegativeButton("Cancel",
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

        txtConfigurationBatteryWarningCell = view.findViewById(R.id.txtConfigurationBatteryWarningCell);
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
                input.setGravity(Gravity.START | Gravity.TOP);
                input.setValue(mspData.getMspBatteryData().getVbatWarningCellVoltage() - 30);
                builder.setView(input);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        mspData.getMspBatteryData().setVbatWarningCellVoltage(30 + input.getValue());

                        MspService.getInstance().setBatteryConfig(mspData.getMspBatteryData());
                        MspService.getInstance().loadBatteryData();

                        Snackbar.make(view, "Set Cell Voltage", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }).setNegativeButton("Cancel",
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
        MspService.getInstance().loadBatteryData();
    }

    private void showData() {
        if (mspData != null) {

            if (txtLiveBatteryType != null
                    && txtLiveBatteryVoltage != null
                    && barLiveBatteryUsage != null) {
                if (mspData.getMspLiveData().getVoltage() != null
                        && mspData.getMspBatteryData().getVbatMaxCellVoltage() != null
                        && mspData.getMspLiveData().getVoltage() > 1) {

                    Integer batteryCells = UiUtils.getBatteryCells(mspData.getMspLiveData().getVoltage(), (mspData.getMspBatteryData().getVbatMaxCellVoltage() / 10.0));
                    String batteryType = batteryCells + "S Battery (max. " + UiFormatter.formatDecimal(batteryCells * (mspData.getMspBatteryData().getVbatMaxCellVoltage() / 10.0)) + "v)";

                    Integer batteryMax = batteryCells * mspData.getMspBatteryData().getVbatMaxCellVoltage();
                    Integer batteryMin = batteryCells * mspData.getMspBatteryData().getVbatMinCellVoltage();

                    Double batteryProgress = (mspData.getMspLiveData().getVoltage() * 10.0);
                    batteryProgress = batteryProgress - batteryMin;

                    Integer batteryPercent = UiUtils.getPercent(batteryMax - batteryMin, batteryProgress.intValue());

                    barLiveBatteryUsage.setProgress(batteryPercent);
                    barLiveBatteryUsage.setMax(100);

                    txtLiveBatteryType.setText(batteryType);

                    String batteryVoltage = mspData.getMspLiveData().getVoltage() + "v";
                    batteryVoltage += " (" + UiFormatter.formatDecimal(mspData.getMspLiveData().getVoltage() / batteryCells) + "v per cell)";
                    txtLiveBatteryVoltage.setText(batteryVoltage);

                } else {
                    txtLiveBatteryType.setText("Battery not connected.");
                    txtLiveBatteryVoltage.setText("");
                    barLiveBatteryUsage.setProgress(0);
                }
            }

            if (txtConfigurationBatteryMaxCell != null) {
                txtConfigurationBatteryMaxCell.setText(UiFormatter.formatVoltage(mspData.getMspBatteryData().getVbatMaxCellVoltage()));
            }

            if (txtConfigurationBatteryWarningCell != null) {
                txtConfigurationBatteryWarningCell.setText(UiFormatter.formatVoltage(mspData.getMspBatteryData().getVbatWarningCellVoltage()));
            }

            if (txtConfigurationBatteryMinCell != null) {
                txtConfigurationBatteryMinCell.setText(UiFormatter.formatVoltage(mspData.getMspBatteryData().getVbatMinCellVoltage()));
            }
        }
    }
}
