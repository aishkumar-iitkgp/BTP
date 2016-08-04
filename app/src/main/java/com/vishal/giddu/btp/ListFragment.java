package com.vishal.giddu.btp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by giddu on 3/8/16.
 */

public class ListFragment extends android.app.ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ListItem weather_data[] = new ListItem[]
                {
                        new ListItem("ID 1", "03"),
                        new ListItem("ID 1", "03"),
                        new ListItem("ID 1", "03"),
                        new ListItem("ID 1", "03"),
                        new ListItem("ID 1", "03")
                };

        CustomAdapter adapter = new CustomAdapter(getActivity(),
                R.layout.list_item, weather_data);

        setListAdapter(adapter);

    }


    private class ListItem {

        public String siteID;
        public String dateTime;

        public ListItem(String siteID, String dateTime) {
            this.siteID = siteID;
            this.dateTime = dateTime;
        }

    }

    private class CustomAdapter extends ArrayAdapter<ListItem> {

        Context context;
        int layoutResourceId;
        ListItem data[] = null;

        public CustomAdapter(Context context, int layoutResourceId, ListItem[] data) {
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

            ListItem listItem = data[position];
            holder.tvSiteID.setText(listItem.siteID);
            holder.tvDateTime.setText(listItem.dateTime);

            return row;
        }

        //somehow make it static
        class ListItemHolder {
            TextView tvSiteID;
            TextView tvDateTime;
        }

    }
}
