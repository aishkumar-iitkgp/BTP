package com.vishal.giddu.btp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by giddu on 3/8/16.
 */

public class MyListFragment extends ListFragment {

    DatabaseManager myDatabaseManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
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

        RowElement[] rowElements = myDatabaseManager.getData();

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
}
