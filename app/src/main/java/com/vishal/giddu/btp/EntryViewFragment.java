package com.vishal.giddu.btp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by giddu on 5/11/16.
 */

public class EntryViewFragment extends Fragment {

    TextView uniqueID, siteID, siteLocation, parameter;
    Button bEdit;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_list_view, container, false);

        uniqueID = (TextView) view.findViewById(R.id.d_unique_id);
        siteID = (TextView) view.findViewById(R.id.d_site_id);
        siteLocation = (TextView) view.findViewById(R.id.d_site_location);
        parameter = (TextView) view.findViewById(R.id.d_parameter);
        bEdit = (Button) view.findViewById(R.id.b_edit);

        set_values();

        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditEntryFragment editEntryFragment = new EditEntryFragment();
                editEntryFragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, editEntryFragment);
                transaction.addToBackStack(null);
                transaction.commit();
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
