package com.betandroid.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.betandroid.msprotocol.MspService;
import com.betandroid.ui.MainActivity;
import com.betandroid.ui.R;

public class MspConfigBoardActivity extends AppCompatActivity {

    private static final String TAG = "MspConfigBoard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msp_config_board);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        IntentFilter onMspDisconnectedFilter = new IntentFilter(MspService.EVENT_DISCONNECTED);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(onMspDisconnected, onMspDisconnectedFilter);

        gotoContent();
    }

    private BroadcastReceiver onMspDisconnected = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,intent.getAction());

            if(MspService.EVENT_DISCONNECTED.equals(intent.getAction())) {
                startActivity(new Intent(context, MainActivity.class));
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_msp_config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_config_board:
                gotoConfigBoard();
                return true;

            case R.id.action_config_battery:
                gotoConfigBattery();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void gotoContent() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, new MspConfigBoardContent());
        ft.commitAllowingStateLoss();
    }

    private void gotoConfigBoard() {

    }

    private void gotoConfigBattery() {

    }
}
