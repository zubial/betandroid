package net.zubial.betandroid.components;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.zubial.betandroid.R;

import java.util.List;

public class MspBluetoothDeviceAdapter extends ArrayAdapter<BluetoothDevice> {

    public MspBluetoothDeviceAdapter(Context context, List<BluetoothDevice> bluetoothDevices) {
        super(context, 0, bluetoothDevices);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.component_bluetooth_device, parent, false);
        }

        DeviceViewHolder viewHolder = (DeviceViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new DeviceViewHolder();
            viewHolder.deviceName = (TextView) convertView.findViewById(R.id.deviceName);
            viewHolder.deviceAddress = (TextView) convertView.findViewById(R.id.deviceAddress);
            convertView.setTag(viewHolder);
        }

        BluetoothDevice device = getItem(position);

        viewHolder.deviceName.setText(device.getName() + " (" + device.getType() + ")");
        viewHolder.deviceAddress.setText(device.getAddress());

        return convertView;
    }

    private class DeviceViewHolder {
        public TextView deviceName;
        public TextView deviceAddress;
    }
}
