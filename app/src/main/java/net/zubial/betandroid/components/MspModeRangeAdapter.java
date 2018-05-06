package net.zubial.betandroid.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import net.zubial.betandroid.R;
import net.zubial.msprotocol.data.MspData;
import net.zubial.msprotocol.data.MspModeData;

import java.util.List;

public class MspModeRangeAdapter extends ArrayAdapter<MspModeData> {

    private MspData mspData;

    public MspModeRangeAdapter(Context context, List<MspModeData> values, MspData mspData) {
        super(context, 0, values);
        this.mspData = mspData;
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView,
                 @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.component_mode_range, parent, false);
        }

        ModeRangeViewHolder viewHolder = (ModeRangeViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ModeRangeViewHolder();
            viewHolder.modeName = convertView.findViewById(R.id.modeName);
            viewHolder.modeRange = convertView.findViewById(R.id.modeRange);
            viewHolder.switchMode = convertView.findViewById(R.id.switchMode);

            convertView.setTag(viewHolder);
        }

        MspModeData value = getItem(position);

        if (value != null) {
            if (value.getModeName() != null) {
                viewHolder.modeName.setText(value.getModeName() + "");
                viewHolder.modeRange.setText("Aux " + (value.getAuxChannel() + 1) + " : " + value.getRangeStart() + " - " + value.getRangeEnd());
            }
            viewHolder.switchMode.setChecked(value.getEnable());
            viewHolder.switchMode.setOnClickListener(new Switch.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Switch switchMode = v.findViewById(R.id.switchFeature);

                    if (switchMode != null && value.getEnable() != switchMode.isChecked()) {

                        Snackbar.make(v, "Update mode : " + value.getModeName(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                }
            });
        }
        return convertView;
    }

    private class ModeRangeViewHolder {
        public TextView modeName;
        public TextView modeRange;
        public Switch switchMode;
    }
}
