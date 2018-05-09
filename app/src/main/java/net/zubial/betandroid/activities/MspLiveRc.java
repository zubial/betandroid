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

import net.zubial.betandroid.R;
import net.zubial.betandroid.components.MspLiveRcAdapter;
import net.zubial.betandroid.components.NoScrollListView;
import net.zubial.msprotocol.MspService;
import net.zubial.msprotocol.data.MspData;
import net.zubial.msprotocol.enums.MspMessageEventEnum;

public class MspLiveRc extends Fragment {

    private static final String TAG = "MspLive";

    private MspData mspData;

    // UI Composants
    private FloatingActionButton fab;
    private NoScrollListView listLiveRc;
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

    public MspLiveRc() {
        // Default Ctr
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_msp_live_rc, container, false);
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

        listLiveRc = view.findViewById(R.id.listLiveRc);

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
                && mspData.getMspLiveData() != null
                && mspData.getMspLiveData().getMspLiveRc() != null) {

            MspLiveRcAdapter adapter = new MspLiveRcAdapter(getContext(), mspData.getMspLiveData().getMspLiveRc(), mspData);
            listLiveRc.setAdapter(adapter);
        }
    }
}
