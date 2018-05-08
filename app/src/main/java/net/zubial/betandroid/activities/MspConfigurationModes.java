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
import android.widget.Button;
import android.widget.NumberPicker;

import net.zubial.betandroid.R;
import net.zubial.betandroid.components.MspModeRangeAdapter;
import net.zubial.betandroid.components.NoScrollListView;
import net.zubial.msprotocol.MspService;
import net.zubial.msprotocol.data.MspData;
import net.zubial.msprotocol.data.MspModeData;
import net.zubial.msprotocol.enums.MspMessageEventEnum;

import java.util.Map;

public class MspConfigurationModes extends Fragment {

    private static final String TAG = "MspConfiguration";

    private MspData mspData;

    // UI Composants
    private NoScrollListView listModesRange;
    private Button cmdAdd;

    // Msp Event
    private BroadcastReceiver onMspMessageReceived = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MspService.EVENT_MESSAGE_RECEIVED.equals(intent.getAction())) {
                MspMessageEventEnum mspEvent = (MspMessageEventEnum) intent.getSerializableExtra(MspService.EXTRA_EVENT);
                if (MspMessageEventEnum.EVENT_MSP_MODES_DATA.isEqual(mspEvent)) {

                    mspData = (MspData) intent.getSerializableExtra(MspService.EXTRA_DATA);
                    showData();
                }
            }
        }
    };

    public MspConfigurationModes() {
        // Default Ctr
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_msp_configuration_modes, container, false);
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

        listModesRange = view.findViewById(R.id.listModesRange);

        cmdAdd = view.findViewById(R.id.cmdAdd);
        cmdAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add Mode range");

                final NumberPicker input = new NumberPicker(getContext());

                final String[] values = new String[mspData.getMspModesData().getMspModeNames().size()];
                for (int j = 0; j < mspData.getMspModesData().getMspModeNames().size(); j++) {
                    values[j] = mspData.getMspModesData().getMspModeNames().get(j);
                }

                input.setMinValue(0);
                input.setMaxValue(values.length - 1);
                input.setDisplayedValues(values);
                input.setWrapSelectorWheel(true);
                input.setGravity(Gravity.START | Gravity.TOP);
                builder.setView(input);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String value = values[input.getValue()];

                        for (Map.Entry<Integer, String> item : mspData.getMspModesData().getMspModeNames().entrySet()) {
                            if (value.equals(item.getValue())) {
                                MspModeData newMode = new MspModeData();
                                newMode.setId(mspData.getMspModesData().getMspModeNames().size());
                                newMode.setIndex(mspData.getMspModesData().getMspModeIds().get(item.getKey()));
                                newMode.setAuxChannel(0);
                                newMode.setRangeStart(1200);
                                newMode.setRangeEnd(1800);
                                newMode.setEnable(true);

                                MspService.getInstance().setModeRange(newMode);
                                MspService.getInstance().loadModesData();

                                Snackbar.make(view, "Add Mode range", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                                break;
                            }
                        }
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
        MspService.getInstance().loadModesData();
    }

    private void showData() {
        if (mspData != null && mspData.getMspModesData().getMspModes() != null
                && getContext() != null) {
            MspModeRangeAdapter adapter = new MspModeRangeAdapter(getContext(), mspData.getMspModesData().getMspModes(), mspData);
            listModesRange.setAdapter(adapter);
        }
    }
}
