package net.zubial.betandroid.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;

import net.zubial.betandroid.R;
import net.zubial.msprotocol.data.MspFeatureData;

import java.util.List;

public class MspFeatureSwitchAdapter extends ArrayAdapter<MspFeatureData> {

    public MspFeatureSwitchAdapter(Context context, List<MspFeatureData> features) {
        super(context, 0, features);
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView,
                 @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.component_feature_switch, parent, false);
        }

        FeaturesViewHolder viewHolder = (FeaturesViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new FeaturesViewHolder();
            viewHolder.switchFeature = (Switch) convertView.findViewById(R.id.switchFeature);
            convertView.setTag(viewHolder);
        }

        MspFeatureData feature = getItem(position);

        if (feature != null && feature.getFeature() != null) {
            viewHolder.switchFeature.setText(feature.getFeature().getLabel());
            viewHolder.switchFeature.setChecked(feature.isEnable());
        }
        return convertView;
    }

    private class FeaturesViewHolder {
        public Switch switchFeature;
    }
}
