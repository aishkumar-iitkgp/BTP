package com.vishal.giddu.btp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by giddu on 5/11/16.
 */

public class EditEntryFragment extends Fragment {

    EditText uniqueID, siteID, siteLocation, parameter;
    Button bSubmit;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_item, container, false);

        uniqueID = (EditText) view.findViewById(R.id.ef_et_unique_id);
        siteID = (EditText) view.findViewById(R.id.ef_et_site_id);
        siteLocation = (EditText) view.findViewById(R.id.ef_et_site_location);
        parameter = (EditText) view.findViewById(R.id.ef_et_parameter);
        bSubmit = (Button) view.findViewById(R.id.ef_b_submit);

        set_values();

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private void set_values() {
        bundle = this.getArguments();
        if (bundle != null) {
            uniqueID.setText(bundle.getString("unique_id"));
            siteID.setText(bundle.getString("site_id"));
            siteLocation.setText(bundle.getString("site_location"));
            parameter.setText(bundle.getString("parameter"));
        }
    }
}
