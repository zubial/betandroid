package com.betandroid.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.betandroid.msprotocol.MspService;
import com.betandroid.msprotocol.data.MspData;
import com.betandroid.msprotocol.enums.MspMessageTypeEnum;
import com.betandroid.msprotocol.io.MspMessage;
import com.betandroid.ui.activities.MspConfigBoardActivity;
import com.betandroid.ui.views.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MspService mspService = MspService.getInstance(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, new MainActivityHome());
        ft.commitAllowingStateLoss();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoBluetooth();
            }
        });

        IntentFilter onMspConnectedFilter = new IntentFilter(MspService.EVENT_CONNECTED);
        LocalBroadcastManager.getInstance(this).registerReceiver(onMspConnected, onMspConnectedFilter);

        IntentFilter onMspMessageReceivedFilter = new IntentFilter(MspService.EVENT_MESSAGE_RECEIVED);
        LocalBroadcastManager.getInstance(this).registerReceiver(onMspMessageReceived, onMspMessageReceivedFilter);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (MspService.getInstance().isConnected()) {
            gotoConnected();
        } else {
            gotoHome();
        }
    }

    private BroadcastReceiver onMspConnected = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,intent.getAction());

            if(MspService.EVENT_CONNECTED.equals(intent.getAction())) {
                gotoConnected();
            }
        }
    };

    private BroadcastReceiver onMspMessageReceived = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,intent.getAction());

            if(MspService.EVENT_MESSAGE_RECEIVED.equals(intent.getAction())) {
                MspMessage message = (MspMessage)intent.getSerializableExtra(MspService.EXTRA_MESSAGE);

                if (message != null
                        && message.getMessageType().isEqual(MspMessageTypeEnum.MSP_ACC_CALIBRATION)) {
                    Toast.makeText(getApplicationContext(), "Calibration done",Toast.LENGTH_SHORT).show();
                }

                showModel((MspData)intent.getSerializableExtra(MspService.EXTRA_DATA));
            }
        }
    };

    private void gotoHome() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, new MainActivityHome());
        ft.commitAllowingStateLoss();
    }

    private void gotoBluetooth() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, new MainActivityBluetooth());
        ft.commitAllowingStateLoss();
    }

    private void gotoConnected() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, new MainActivityConnected());
        ft.commitAllowingStateLoss();
    }

    private void showModel(MspData mspData) {

        TextView txtConnectedTitle = findViewById(R.id.txtConnectedTitle);
        if (txtConnectedTitle != null) {
            txtConnectedTitle.setText("Connected");
        }

        TextView txtBoardName = findViewById(R.id.txtBoardName);
        if (txtBoardName != null) {
            txtBoardName.setText(mspData.getMspSystemData().getBoardName());
        }

        TextView txtBoardIdentifier = findViewById(R.id.txtBoardIdentifier);
        if (txtBoardIdentifier != null) {
            String boardIdentifier = mspData.getMspSystemData().getBoardIdentifier();
            if (mspData.getMspSystemData().getBoardFlightControllerIdentifier() != null) {
                boardIdentifier += " - " + mspData.getMspSystemData().getBoardFlightControllerIdentifier().name();
            }
            boardIdentifier += " (" + mspData.getMspSystemData().getBoardApiVersion() + ")";

            txtBoardIdentifier.setText(boardIdentifier);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_msp_configuration:
                startActivity(new Intent(getApplicationContext(), MspConfigBoardActivity.class));

                return true;

            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));

                return true;

            case R.id.action_help:
                // Go Help
                Intent goHelp = new Intent();
                goHelp.setAction(Intent.ACTION_VIEW);
                goHelp.setData(Uri.parse("http://www.example.com"));
                startActivity(goHelp);

                return true;

            case R.id.action_disconnect:
                // Disconnect
                MspService.getInstance().disconnectBluetooth();

                gotoHome();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
