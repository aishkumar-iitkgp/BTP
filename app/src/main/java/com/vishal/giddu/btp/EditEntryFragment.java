package com.vishal.giddu.btp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by giddu on 5/11/16.
 */

public class EditEntryFragment extends Fragment {

    EditText etUniqueID, etSiteID, etSiteLocation, etColour, etOdour, etTemp, etPH, etEC, etDO, etNO2NO3, etBOD, etTotalColiforms, etFaecalColiforms;
    Button bSubmit;
    Bundle bundle;

    DatabaseManager myDatabaseManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_item, container, false);

        myDatabaseManager = new DatabaseManager(getActivity());

        etUniqueID = (EditText) view.findViewById(R.id.unique_id);
        etUniqueID.setEnabled(false);

        etSiteID = (EditText) view.findViewById(R.id.site_id);
        etSiteLocation = (EditText) view.findViewById(R.id.site_location);
        etColour = (EditText) view.findViewById(R.id.colour);
        etOdour = (EditText) view.findViewById(R.id.odour);
        etTemp = (EditText) view.findViewById(R.id.temp);
        etPH = (EditText) view.findViewById(R.id.ph);
        etEC = (EditText) view.findViewById(R.id.ec);
        etDO = (EditText) view.findViewById(R.id.p_do);
        etNO2NO3 = (EditText) view.findViewById(R.id.no2no3);
        etBOD = (EditText) view.findViewById(R.id.bod);
        etTotalColiforms = (EditText) view.findViewById(R.id.total_coliforms);
        etFaecalColiforms = (EditText) view.findViewById(R.id.faecal_coliforms);

        bSubmit = (Button) view.findViewById(R.id.ef_b_submit);

        set_values();

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDatabaseManager.open();
                myDatabaseManager.updateEntry(etUniqueID.getText().toString(),
                        etSiteID.getText().toString(),
                        etSiteLocation.getText().toString(),
                        etColour.getText().toString(),
                        etOdour.getText().toString(),
                        etTemp.getText().toString(),
                        etPH.getText().toString(),
                        etEC.getText().toString(),
                        etDO.getText().toString(),
                        etNO2NO3.getText().toString(),
                        etBOD.getText().toString(),
                        etTotalColiforms.getText().toString(),
                        etFaecalColiforms.getText().toString());
                myDatabaseManager.close();
                Toast.makeText(getActivity(), "Entry Updated", Toast.LENGTH_SHORT).show();
                getActivity().getFragmentManager().popBackStack();
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

            etUniqueID.setText(rowElement.getUniqueID());
            etSiteID.setText(rowElement.getSiteID());
            etSiteLocation.setText(rowElement.getSiteLocation());
            etColour.setText(rowElement.getColour());
            etOdour.setText(rowElement.getOdour());
            etTemp.setText(rowElement.getTemp());
            etPH.setText(rowElement.getPh());
            etEC.setText(rowElement.getEc());
            etDO.setText(rowElement.getP_do());
            etNO2NO3.setText(rowElement.getNo2no3());
            etBOD.setText(rowElement.getBod());
            etTotalColiforms.setText(rowElement.getTotal_coliforms());
            etFaecalColiforms.setText(rowElement.getFaecal_coliforms());
        }
    }
}
