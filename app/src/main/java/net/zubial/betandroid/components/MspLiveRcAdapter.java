package net.zubial.betandroid.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.zubial.betandroid.R;
import net.zubial.msprotocol.data.MspData;
import net.zubial.msprotocol.data.MspLiveRcData;

import java.util.List;

public class MspLiveRcAdapter extends ArrayAdapter<MspLiveRcData> {

    private static final String TAG = "MspLiveRcAdapter";
    private MspData mspData;

    public MspLiveRcAdapter(Context context, List<MspLiveRcData> values, MspData mspData) {
        super(context, 0, values);
        this.mspData = mspData;
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView,
                 @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.component_live_rc, parent, false);
        }

        RcViewHolder viewHolder = (RcViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new RcViewHolder();
            viewHolder.rcChannel = convertView.findViewById(R.id.rcChannel);
            viewHolder.rcBar = convertView.findViewById(R.id.rcBar);
            viewHolder.rcValue = convertView.findViewById(R.id.rcValue);
            convertView.setTag(viewHolder);
        }

        MspLiveRcData value = getItem(position);

        if (value != null
                && value.getId() != null
                && value.getChannel() != null) {
            viewHolder.rcChannel.setText(value.getChannel().getLabel());
            viewHolder.rcBar.setProgress(value.getValue() - 800);
            viewHolder.rcValue.setText(value.getValue() + "");
        }

        return convertView;
    }

    private class RcViewHolder {
        public TextView rcChannel;
        public ProgressBar rcBar;
        public TextView rcValue;
    }
}
