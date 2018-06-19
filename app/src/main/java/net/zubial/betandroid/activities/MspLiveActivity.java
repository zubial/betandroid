package net.zubial.betandroid.activities;

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

import net.zubial.betandroid.MainActivity;
import net.zubial.betandroid.R;
import net.zubial.msprotocol.MspService;

public class MspLiveActivity extends AppCompatActivity {

    private static final String TAG = "MspLive";

    private BroadcastReceiver onMspDisconnected = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, intent.getAction());

            if (MspService.EVENT_DISCONNECTED.equals(intent.getAction())) {
                startActivity(new Intent(context, MainActivity.class));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msp_live);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        IntentFilter onMspDisconnectedFilter = new IntentFilter(MspService.EVENT_DISCONNECTED);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(onMspDisconnected, onMspDisconnectedFilter);

        gotoLivePosition();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_msp_live, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_help:
                // Go Help
                Intent goHelp = new Intent();
                goHelp.setAction(Intent.ACTION_VIEW);
                goHelp.setData(Uri.parse("https://github.com/zubial/betandroid/wiki"));
                startActivity(goHelp);

                return true;

            case R.id.action_disconnect:
                // Disconnect
                MspService.getInstance().disconnectBluetooth();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                return true;

            case R.id.action_live_position:
                gotoLivePosition();
                return true;

            case R.id.action_live_rc:
                gotoLiveRc();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void gotoLivePosition() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, new MspLivePosition());
        ft.commitAllowingStateLoss();
    }

    private void gotoLiveRc() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, new MspLiveRc());
        ft.commitAllowingStateLoss();
    }
}
