package com.vishal.giddu.btp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by giddu on 3/8/16.
 */

public class MyListFragment extends ListFragment {

    DatabaseManager myDatabaseManager;

    //for accessing data, need to change, pick data from table
    RowElement[] rowElements;

    //for dialog
    Dialog dialogView;
    TextView uniqueID, siteID, siteLocation, parameter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        //setting dialog
        dialogView = new Dialog(getActivity());

        dialogView.setContentView(R.layout.dialog_list_view);

        uniqueID = (TextView) dialogView.findViewById(R.id.d_unique_id);
        siteID = (TextView) dialogView.findViewById(R.id.d_site_id);
        siteLocation = (TextView) dialogView.findViewById(R.id.d_site_location);
        parameter = (TextView) dialogView.findViewById(R.id.d_parameter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        myDatabaseManager = new DatabaseManager(getActivity());

        updateList();
    }

    private void updateList() {
        myDatabaseManager.open();

        rowElements = myDatabaseManager.getData();

        myDatabaseManager.close();

        CustomAdapter adapter = new CustomAdapter(getActivity(),
                R.layout.list_item, rowElements);

        setListAdapter(adapter);
    }

    private class CustomAdapter extends ArrayAdapter<RowElement> {

        Context context;
        int layoutResourceId;
        RowElement data[] = null;

        public CustomAdapter(Context context, int layoutResourceId, RowElement[] data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ListItemHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new ListItemHolder();
                holder.tvSiteID = (TextView) row.findViewById(R.id.site_id);
                holder.tvDateTime = (TextView) row.findViewById(R.id.date_time);

                row.setTag(holder);
            } else {
                holder = (ListItemHolder) row.getTag();
            }

            RowElement listItem = data[position];
            holder.tvSiteID.setText(listItem.getSiteID());
            holder.tvDateTime.setText(listItem.getSiteLocation());

            return row;
        }

        //somehow make it static
        class ListItemHolder {
            TextView tvSiteID;
            TextView tvDateTime;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        dialogView.show();

        uniqueID.setText(rowElements[position].getUniqueID());
        siteID.setText(rowElements[position].getSiteID());
        siteLocation.setText(rowElements[position].getSiteLocation());
        parameter.setText(rowElements[position].getParamter());
    }
}
