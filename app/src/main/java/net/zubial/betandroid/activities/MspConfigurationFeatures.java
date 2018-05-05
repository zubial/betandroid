package net.zubial.betandroid.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.zubial.betandroid.R;
import net.zubial.betandroid.components.MspFeatureSwitchAdapter;
import net.zubial.betandroid.components.NoScrollListView;
import net.zubial.msprotocol.MspService;
import net.zubial.msprotocol.data.MspData;
import net.zubial.msprotocol.enums.MspMessageEventEnum;

public class MspConfigurationFeatures extends Fragment {

    private static final String TAG = "MspConfigBoard";

    private MspData mspData;

    // UI Composants
    private NoScrollListView listFeatures;

    // Msp Event
    private BroadcastReceiver onMspMessageReceived = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MspService.EVENT_MESSAGE_RECEIVED.equals(intent.getAction())) {

                MspMessageEventEnum mspEvent = (MspMessageEventEnum) intent.getSerializableExtra(MspService.EXTRA_EVENT);
                if (MspMessageEventEnum.EVENT_MSP_FEATURE_DATA.isEqual(mspEvent)) {
                    mspData = (MspData) intent.getSerializableExtra(MspService.EXTRA_DATA);
                    showData();
                }
            }
        }
    };

    public MspConfigurationFeatures() {
        // Default Ctr
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_msp_configuration_features, container, false);
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

        listFeatures = view.findViewById(R.id.listFeatures);

        IntentFilter onMspMessageReceivedFilter = new IntentFilter(MspService.EVENT_MESSAGE_RECEIVED);
        LocalBroadcastManager.getInstance(view.getContext()).registerReceiver(onMspMessageReceived, onMspMessageReceivedFilter);

        loadData();
    }

    private void loadData() {
        MspService.getInstance().loadSystemData();
        MspService.getInstance().loadFeaturesData();
        MspService.getInstance().loadBatteryData();
    }

    private void showData() {
        if (mspData != null && mspData.getMspFeaturesSelectable() != null
                && getContext() != null) {
            MspFeatureSwitchAdapter adapter = new MspFeatureSwitchAdapter(getContext(), mspData.getMspFeaturesSelectable(), mspData);
            listFeatures.setAdapter(adapter);
        }
    }
}
