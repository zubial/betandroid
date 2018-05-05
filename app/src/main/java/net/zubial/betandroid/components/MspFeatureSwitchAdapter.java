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

import net.zubial.betandroid.R;
import net.zubial.msprotocol.MspService;
import net.zubial.msprotocol.data.MspData;
import net.zubial.msprotocol.data.MspFeatureData;
import net.zubial.msprotocol.helpers.MspProtocolUtils;

import java.util.List;

public class MspFeatureSwitchAdapter extends ArrayAdapter<MspFeatureData> {

    private MspData mspData;

    public MspFeatureSwitchAdapter(Context context, List<MspFeatureData> features, MspData mspData) {
        super(context, 0, features);
        this.mspData = mspData;
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
            viewHolder.switchFeature = convertView.findViewById(R.id.switchFeature);
            convertView.setTag(viewHolder);
        }

        MspFeatureData feature = getItem(position);

        if (feature != null && feature.getFeature() != null) {
            viewHolder.switchFeature.setText(feature.getFeature().getLabel());
            viewHolder.switchFeature.setChecked(feature.isEnable());
            viewHolder.switchFeature.setOnClickListener(new Switch.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Switch switchFeature = v.findViewById(R.id.switchFeature);

                    if (switchFeature != null && feature.isEnable() != switchFeature.isChecked()) {
                        int featureMask = mspData.getMspFeaturesMask();

                        if (switchFeature.isChecked()) {
                            featureMask = MspProtocolUtils.bitSetEnable(featureMask, feature.getFeature().getCode());
                        } else {
                            featureMask = MspProtocolUtils.bitSetDisable(featureMask, feature.getFeature().getCode());
                        }

                        MspService.getInstance().setFeatures(featureMask);

                        Snackbar.make(v, "Update feature : " + switchFeature.getText(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        MspService.getInstance().loadFeaturesData();
                    }
                }
            });
        }
        return convertView;
    }

    private class FeaturesViewHolder {
        public Switch switchFeature;
    }
}
