package net.zubial.betandroid.components;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.zubial.betandroid.R;

import java.util.List;

public class UsbDeviceAdapter extends ArrayAdapter<UsbDevice> {

    public UsbDeviceAdapter(Context context, List<UsbDevice> devices) {
        super(context, 0, devices);
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.component_usb_device, parent, false);
        }

        DeviceViewHolder viewHolder = (DeviceViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new DeviceViewHolder();
            viewHolder.deviceName = convertView.findViewById(R.id.deviceName);
            viewHolder.deviceId = convertView.findViewById(R.id.deviceId);
            convertView.setTag(viewHolder);
        }

        UsbDevice device = getItem(position);

        viewHolder.deviceName.setText(device.getDeviceName() + " (" + device.getDeviceProtocol() + ")");
        viewHolder.deviceId.setText(device.getDeviceId());

        return convertView;
    }

    private class DeviceViewHolder {
        public TextView deviceName;
        public TextView deviceId;
    }
}
