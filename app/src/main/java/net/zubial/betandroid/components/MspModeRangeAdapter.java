package net.zubial.betandroid.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import net.zubial.betandroid.R;
import net.zubial.betandroid.helpers.UiUtils;
import net.zubial.msprotocol.MspService;
import net.zubial.msprotocol.data.MspData;
import net.zubial.msprotocol.data.MspModeData;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.List;

public class MspModeRangeAdapter extends ArrayAdapter<MspModeData> {

    private MspData mspData;

    private static final String TAG = "MspModeRangeAdapter";

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
            viewHolder.modeChannel = convertView.findViewById(R.id.modeChannel);
            viewHolder.switchMode = convertView.findViewById(R.id.switchMode);
            viewHolder.seekBarRange = convertView.findViewById(R.id.seekBarRange);

            convertView.setTag(viewHolder);
        }

        MspModeData value = getItem(position);

        if (value != null) {
            if (value.getModeName() != null) {
                viewHolder.modeName.setText(value.getModeName() + "");
                viewHolder.modeChannel.setText("Aux " + (value.getAuxChannel() + 1));
            }

            viewHolder.seekBarRange.setRangeValues(900, 2100);
            viewHolder.seekBarRange.setSelectedMinValue(value.getRangeStart());
            viewHolder.seekBarRange.setSelectedMaxValue(value.getRangeEnd());
            viewHolder.seekBarRange.setTextAboveThumbsColorResource(android.R.color.black);
            viewHolder.seekBarRange.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {

                    Double minResult = UiUtils.quarterRound(((Integer) minValue).doubleValue() / 100) * 100;
                    Double maxResult = UiUtils.quarterRound(((Integer) maxValue).doubleValue() / 100) * 100;

                    value.setRangeStart(minResult.intValue());
                    value.setRangeEnd(maxResult.intValue());

                    Log.d(TAG, "Aux " + (value.getAuxChannel() + 1) + " : " + minResult.intValue() + " - " + maxResult.intValue());

                    Snackbar.make(bar.getRootView(), "Update mode : " + value.getModeName(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    MspService.getInstance().setModeRange(value);
                    MspService.getInstance().loadModesData();
                }
            });

            viewHolder.switchMode.setChecked(value.getEnable());
            viewHolder.switchMode.setOnClickListener(new Switch.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Switch switchMode = v.findViewById(R.id.switchFeature);

                    if (switchMode != null && !switchMode.isChecked()) {

                        value.setRangeStart(900);
                        value.setRangeEnd(900);
                        value.setEnable(false);

                        Snackbar.make(v, "Remove mode : " + value.getModeName(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        MspService.getInstance().setModeRange(value);
                        MspService.getInstance().loadModesData();
                    }
                }
            });
        }
        return convertView;
    }

    private class ModeRangeViewHolder {
        public TextView modeName;
        public TextView modeChannel;
        public Switch switchMode;
        public RangeSeekBar seekBarRange;
    }
}
