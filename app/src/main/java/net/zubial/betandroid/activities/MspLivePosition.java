package net.zubial.betandroid.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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

    private TextView txtRc01;
    private TextView txtRc02;
    private TextView txtRc03;
    private TextView txtRc04;
    private TextView txtRc05;
    private TextView txtRc06;

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
                if (MspService.getInstance().isRuning()) {
                    MspService.getInstance().pauseLive();
                    fab.setImageResource(R.drawable.ic_live_play);
                } else {
                    MspService.getInstance().resumeLive();
                    fab.setImageResource(R.drawable.ic_live_pause);
                }
            }
        });

        txtAccelerometer = view.findViewById(R.id.txtAccelerometer);
        txtGyroscope = view.findViewById(R.id.txtGyroscope);
        txtKinematics = view.findViewById(R.id.txtKinematics);

        txtRc01 = view.findViewById(R.id.txtRc01);
        txtRc02 = view.findViewById(R.id.txtRc02);
        txtRc03 = view.findViewById(R.id.txtRc03);
        txtRc04 = view.findViewById(R.id.txtRc04);
        txtRc05 = view.findViewById(R.id.txtRc05);
        txtRc06 = view.findViewById(R.id.txtRc06);

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

            if (!mspData.getMspLiveData().getMspLiveRc().isEmpty()) {
                if (txtRc01 != null) {
                    txtRc01.setText("Value : " + mspData.getMspLiveData().getMspLiveRc().get(0).getValue());
                }
                if (txtRc02 != null) {
                    txtRc02.setText("Value : " + mspData.getMspLiveData().getMspLiveRc().get(1).getValue());
                }
                if (txtRc03 != null) {
                    txtRc03.setText("Value : " + mspData.getMspLiveData().getMspLiveRc().get(2).getValue());
                }
                if (txtRc04 != null) {
                    txtRc04.setText("Value : " + mspData.getMspLiveData().getMspLiveRc().get(3).getValue());
                }
                if (txtRc05 != null) {
                    txtRc05.setText("Value : " + mspData.getMspLiveData().getMspLiveRc().get(4).getValue());
                }
                if (txtRc06 != null) {
                    txtRc06.setText("Value : " + mspData.getMspLiveData().getMspLiveRc().get(5).getValue());
                }
            }
        }
    }
}
