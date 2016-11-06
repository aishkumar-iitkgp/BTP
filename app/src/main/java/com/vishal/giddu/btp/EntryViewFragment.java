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

    TextView uniqueID, siteID, siteLocation, colour, odour, temp, ph, ec, p_do, no2no3, bod, totalColiforms, faecalColiforms;
    Button bEdit;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_list_view, container, false);

        uniqueID = (TextView) view.findViewById(R.id.d_unique_id);
        siteID = (TextView) view.findViewById(R.id.d_site_id);
        siteLocation = (TextView) view.findViewById(R.id.d_site_location);
        colour = (TextView) view.findViewById(R.id.colour);
        odour = (TextView) view.findViewById(R.id.odour);
        temp = (TextView) view.findViewById(R.id.temp);
        ph = (TextView) view.findViewById(R.id.ph);
        ec = (TextView) view.findViewById(R.id.ec);
        p_do = (TextView) view.findViewById(R.id.p_do);
        no2no3 = (TextView) view.findViewById(R.id.no2no3);
        bod = (TextView) view.findViewById(R.id.bod);
        totalColiforms = (TextView) view.findViewById(R.id.total_coliforms);
        faecalColiforms = (TextView) view.findViewById(R.id.faecal_coliforms);

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

            DatabaseManager databaseManager = new DatabaseManager(getActivity());
            databaseManager.open();
            RowElement rowElement = databaseManager.getEntry(bundle.getString("unique_id"));
            databaseManager.close();

            uniqueID.setText(rowElement.getUniqueID());
            siteID.setText(rowElement.getSiteID());
            siteLocation.setText(rowElement.getSiteLocation());
            colour.setText(rowElement.getColour());
            odour.setText(rowElement.getOdour());
            temp.setText(rowElement.getTemp());
            ph.setText(rowElement.getPh());
            ec.setText(rowElement.getEc());
            p_do.setText(rowElement.getP_do());
            no2no3.setText(rowElement.getNo2no3());
            bod.setText(rowElement.getBod());
            totalColiforms.setText(rowElement.getTotal_coliforms());
            faecalColiforms.setText(rowElement.getFaecal_coliforms());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        set_values();
        //getActivity().getFragmentManager().popBackStack();
    }
}
