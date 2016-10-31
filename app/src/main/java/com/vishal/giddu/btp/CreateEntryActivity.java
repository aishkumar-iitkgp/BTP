package com.vishal.giddu.btp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateEntryActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUniqueID, etSiteID, etSiteLocation, etParamter;
    Button submit;

    DatabaseManager entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //for database
        entry = new DatabaseManager(CreateEntryActivity.this);
        //setting up views
        setUpViews();
    }

    private void setUpViews() {
        etUniqueID = (EditText) findViewById(R.id.unique_id);
        setUniqueID();

        etSiteID = (EditText) findViewById(R.id.site_id);
        etSiteLocation = (EditText) findViewById(R.id.site_location);
        etParamter = (EditText) findViewById(R.id.paramter);

        submit = (Button) findViewById(R.id.bSubmit);
        submit.setOnClickListener(CreateEntryActivity.this);
    }

    private void setUniqueID() {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        String id = myFormat.format(calendar.getTime());
        etUniqueID.setText(id);
        etUniqueID.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        //since only one button no need to check
        entry.open();

        entry.createEntry(etUniqueID.getText().toString(),
                etSiteID.getText().toString(),
                etSiteLocation.getText().toString(),
                etParamter.getText().toString());

        entry.close();

        finish();
    }
}
