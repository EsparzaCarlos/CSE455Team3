package com.example.caballero.cse455_fall17;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Carlini on 11/8/2017.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    String[] groupnames = {"What is the point of this app?", "What is the point of the FAQ?",
                            "Help I have found a bug in the app what can I do?","Why am I still reading this?"};
    String[][] childnames = { {"The point is to get an A in CSE 455! Duh.", "Maybe learn something too..."},
                            {"Hopefully to help you get some answers you are looking for."},
                            {"Do not panic this app is still in beta and there are (hopefully not) lots of bugs " +
                                    "but you can help by reporting them to the team in the log tab"},
                            {"Most likely you have NOTHING better to do :D"}};

    Context context;

    public ExpandableListViewAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return groupnames.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childnames[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupnames[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childnames[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        TextView txtView = new TextView(context);
        txtView.setText(groupnames[groupPosition]);
        txtView.setPadding(100,50,0,0);
        txtView.setTextSize(25);
        txtView.setTextColor(Color.BLACK);
        return txtView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        final TextView txtView = new TextView(context);
        txtView.setText(childnames[groupPosition][childPosition]);
        txtView.setPadding(100,50,0,0);
        txtView.setTextSize(22);
        txtView.setTextColor(Color.BLACK);

        txtView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(context, txtView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return txtView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
