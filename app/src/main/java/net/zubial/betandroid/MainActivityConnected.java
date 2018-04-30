package net.zubial.betandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.zubial.betandroid.activities.MspConfigBoardActivity;
import net.zubial.msprotocol.MspService;

public class MainActivityConnected extends Fragment {

    public MainActivityConnected() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main_connected, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppBarLayout appBar = view.getRootView().findViewById(R.id.app_bar);
        appBar.setExpanded(false);

        TextView txtConnectedTitle = view.findViewById(R.id.txtConnectedTitle);
        txtConnectedTitle.setText("Handshake...");

        MspService.getInstance().loadSystemData();

        Button cmdAccCalibration = view.findViewById(R.id.cmdAccCalibration);
        cmdAccCalibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MspService.getInstance().executeAccCalibration();
            }
        });

        TextView txtConfigurationBoard = view.findViewById(R.id.txtConfigurationBoard);
        txtConfigurationBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MspConfigBoardActivity.class));
            }
        });

    }
}