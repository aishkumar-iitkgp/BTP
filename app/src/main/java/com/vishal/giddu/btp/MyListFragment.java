package com.vishal.giddu.btp;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent i = new Intent(getActivity(), CreateEntryActivity.class);
//                startActivity(i);

                CreateEntryFragment createEntryFragment = new CreateEntryFragment();

                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_container, createEntryFragment);
                transaction.addToBackStack(null);

                transaction.commit();


            }
        });

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

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

//        dialogView.show();
//
//        uniqueID.setText(rowElements[position].getUniqueID());
//        siteID.setText(rowElements[position].getSiteID());
//        siteLocation.setText(rowElements[position].getSiteLocation());
//        parameter.setText(rowElements[position].getParamter());

        EntryViewFragment entryViewFragment = new EntryViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("unique_id", rowElements[position].getUniqueID());

        entryViewFragment.setArguments(bundle);

        FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, entryViewFragment);
        transaction.addToBackStack(null);

        transaction.commit();
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
                holder.tvSiteLocation = (TextView) row.findViewById(R.id.site_location);
                holder.tvDateTime = (TextView) row.findViewById(R.id.date_time);

                row.setTag(holder);
            } else {
                holder = (ListItemHolder) row.getTag();
            }

            RowElement listItem = data[position];
            holder.tvSiteID.setText("Site ID: " + listItem.getSiteID());
            String unqiueID = listItem.getUniqueID();
            String dateTime = "Date: " + unqiueID.substring(6, 8) + "/"
                    + unqiueID.substring(4, 6) + "/" + unqiueID.substring(0, 4)
                    + " Time: " + unqiueID.substring(8, 10) + ":"
                    + unqiueID.substring(10, 12) + ":" + unqiueID.substring(12);
            holder.tvDateTime.setText(dateTime);
            holder.tvSiteLocation.setText("Site Location: " + listItem.getSiteLocation());

            return row;
        }

        //somehow make it static
        class ListItemHolder {
            TextView tvSiteID;
            TextView tvSiteLocation;
            TextView tvDateTime;
        }

    }
}
