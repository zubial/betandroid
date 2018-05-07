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
import android.widget.TextView;

import net.zubial.betandroid.R;
import net.zubial.msprotocol.MspService;
import net.zubial.msprotocol.data.MspData;
import net.zubial.msprotocol.enums.MspMessageEventEnum;

public class MspLivePosition extends Fragment {

    private static final String TAG = "MspLive";

    private MspData mspData;

    // UI Composants
    private FloatingActionButton fab;

    private TextView txtAccelerometer;
    private TextView txtGyroscope;
    private TextView txtKinematics;

    public MspLivePosition() {
        // Default Ctr
    }

    // Msp Event
    private BroadcastReceiver onMspMessageReceived = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MspService.EVENT_MESSAGE_RECEIVED.equals(intent.getAction())) {

                MspMessageEventEnum mspEvent = (MspMessageEventEnum) intent.getSerializableExtra(MspService.EXTRA_EVENT);
                if (MspMessageEventEnum.EVENT_MSP_LIVE_DATA.isEqual(mspEvent)) {

                    mspData = (MspData) intent.getSerializableExtra(MspService.EXTRA_DATA);
                    showData();
                }
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_msp_live_position, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        fab = view.getRootView().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
                Snackbar.make(view, "Restart", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        txtAccelerometer = view.findViewById(R.id.txtAccelerometer);
        txtGyroscope = view.findViewById(R.id.txtGyroscope);
        txtKinematics = view.findViewById(R.id.txtKinematics);

        IntentFilter onMspMessageReceivedFilter = new IntentFilter(MspService.EVENT_MESSAGE_RECEIVED);
        LocalBroadcastManager.getInstance(view.getContext()).registerReceiver(onMspMessageReceived, onMspMessageReceivedFilter);

        loadData();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        MspService.getInstance().pauseLive();
    }

    private void loadData() {
        MspService.getInstance().startLiveData();
    }

    private void showData() {
        if (mspData != null
                && mspData.getMspLiveData() != null) {

            if (txtAccelerometer != null) {
                String accelerometer = " X : " + mspData.getMspLiveData().getAccelerometerX();
                accelerometer += " \n Y : " + mspData.getMspLiveData().getAccelerometerY();
                accelerometer += " \n Z : " + mspData.getMspLiveData().getAccelerometerZ();
                txtAccelerometer.setText(accelerometer);
            }

            if (txtGyroscope != null) {
                String gyroscope = " X : " + mspData.getMspLiveData().getGyroscopeX();
                gyroscope += " \n Y : " + mspData.getMspLiveData().getGyroscopeY();
                gyroscope += " \n Z : " + mspData.getMspLiveData().getGyroscopeZ();
                txtGyroscope.setText(gyroscope);
            }

            if (txtKinematics != null) {
                String kinematics = " X : " + mspData.getMspLiveData().getKinematicsX();
                kinematics += " \n Y : " + mspData.getMspLiveData().getKinematicsY();
                kinematics += " \n Z : " + mspData.getMspLiveData().getKinematicsZ();
                txtKinematics.setText(kinematics);
            }
        }
    }
}
