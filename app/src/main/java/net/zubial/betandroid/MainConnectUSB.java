package net.zubial.betandroid;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Map;

public class MainConnectUSB extends Fragment {

    private static String TAG = "Main";

    // UI Components
    private TextView txtUsbTitle;
    private ListView listUsbDevice;

    public MainConnectUSB() {
        // Default Ctr
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main_usb, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtUsbTitle = view.findViewById(R.id.txtUsbTitle);

        PackageManager myPackageManager = view.getContext().getPackageManager();
        Boolean USBHostSupported = myPackageManager.hasSystemFeature(PackageManager.FEATURE_USB_HOST);
        if (!USBHostSupported) {
            Log.d(TAG, "Device does't support Usb");
            Snackbar.make(view, "Device does't support Usb", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            if (getFragmentManager() != null) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, new MainHome());
                ft.commitAllowingStateLoss();
            }
            return;
        }

        UsbManager mUsbManager = (UsbManager) view.getContext()
                .getSystemService(Context.USB_SERVICE);

        Map<String, UsbDevice> devices = mUsbManager.getDeviceList();
        if (devices != null
                && devices.size() > 0) {
            Log.d(TAG, "Found " + devices.size() + " devices");

            txtUsbTitle.setText("Select a device");

            for (Map.Entry<String, UsbDevice> entry : devices.entrySet()) {
                Log.d(TAG, entry.getKey() + "/ getDeviceName : " + entry.getValue().getDeviceName());
                Log.d(TAG, entry.getKey() + "/ getDeviceId : " + entry.getValue().getDeviceId());
                Log.d(TAG, entry.getKey() + "/ getProductId : " + entry.getValue().getProductId());
                Log.d(TAG, entry.getKey() + "/ getDeviceProtocol : " + entry.getValue().getDeviceProtocol());
            }

            Snackbar.make(view, "Found " + devices.size() + " device(s)", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            txtUsbTitle.setText("Plug a device");
        }
    }
}
