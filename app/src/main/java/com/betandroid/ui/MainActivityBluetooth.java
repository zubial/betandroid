package com.betandroid.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.betandroid.msprotocol.MspService;
import com.betandroid.ui.components.MspBluetoothDeviceAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivityBluetooth extends Fragment {

    private static String TAG = "MainActivityBluetooth";

    public MainActivityBluetooth() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main_bluetooth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Button cmdBluetoothConfig = view.findViewById(R.id.cmdBluetoothConfig);
        cmdBluetoothConfig.setEnabled(mBluetoothAdapter != null);

        if (mBluetoothAdapter == null) {
            Log.d(TAG, "Device does't support Bluetooth");
            Snackbar.make(view, "Device does't support Bluetooth", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, new MainActivityHome());
            ft.commit();

            return;

        } else {
            Log.d(TAG, "Device support Bluetooth");
        }

        cmdBluetoothConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentOpenBluetoothSettings = new Intent();
                intentOpenBluetoothSettings.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivity(intentOpenBluetoothSettings);
            }
        });

        if (!mBluetoothAdapter.isEnabled()) {
            Snackbar.make(view, "Please enable your Bluetooth", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 3);

            return;
        }

        if (MspService.getInstance().isConnected()) {
            MspService.getInstance().disconnectBluetooth();
        }

        mBluetoothAdapter.startDiscovery();

        TextView txtBluetoothTitle = view.findViewById(R.id.txtBluetoothTitle);

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices != null && !pairedDevices.isEmpty()) {
            txtBluetoothTitle.setText("Paired Bluetooth devices");

            List<BluetoothDevice> deviceList = new ArrayList<>();

            for(BluetoothDevice bt : pairedDevices) {
                deviceList.add(bt);
                Log.d(TAG, bt.getName());
            }

            MspBluetoothDeviceAdapter adapter = new MspBluetoothDeviceAdapter(getContext(), deviceList);

            ListView listBluetoothDevice = view.findViewById(R.id.listBluetoothDevice);
            listBluetoothDevice.setAdapter(adapter);
            listBluetoothDevice.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapter, View v, int position, long id)
                {
                    BluetoothDevice device = (BluetoothDevice)adapter.getItemAtPosition(position);
                    MspService.getInstance().connectBluetooth(device.getAddress());
                }
            });

        } else {
            txtBluetoothTitle.setText("No paired Bluetooth device");

            Snackbar.make(view, "No paired Bluetooth device", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
