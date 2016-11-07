package com.vishal.giddu.btp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by giddu on 6/11/16.
 */

public class CreateEntryFragment extends Fragment {

    EditText etUniqueID, etSiteID, etSiteLocation, etColour, etOdour, etTemp, etPH, etEC, etDO, etNO2NO3, etBOD, etTotalColiforms, etFaecalColiforms;
    Button submit;

    DatabaseManager entry;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_entry, container, false);
        entry = new DatabaseManager(getActivity());
        setUpViews(view);
        return view;
    }

    private void setUpViews(View view) {
        etUniqueID = (EditText) view.findViewById(R.id.unique_id);
        setUniqueID();

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

        submit = (Button) view.findViewById(R.id.bSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEntry();
            }
        });
    }


    private void setUniqueID() {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        String id = myFormat.format(calendar.getTime());
        etUniqueID.setText(id);
        etUniqueID.setEnabled(false);
    }


    private void createEntry() {
        entry.open();

        entry.createEntry(etUniqueID.getText().toString(),
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

        entry.close();

        getActivity().getFragmentManager().popBackStack();

    }
}
