package net.zubial.betandroid;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.zubial.msprotocol.MspService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private BroadcastReceiver onMspConnected = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            gotoConnected();
        }
    };

    private BroadcastReceiver onMspDisconnect = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            gotoHome();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MspService.getInstance(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
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

        IntentFilter onMspConnectedFilter = new IntentFilter(MspService.EVENT_HANDSHAKE);
        LocalBroadcastManager.getInstance(this).registerReceiver(onMspConnected, onMspConnectedFilter);

        IntentFilter onMspDisconnectFilter = new IntentFilter(MspService.EVENT_DISCONNECTED);
        LocalBroadcastManager.getInstance(this).registerReceiver(onMspDisconnect, onMspDisconnectFilter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
