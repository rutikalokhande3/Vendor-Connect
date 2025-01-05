package com.rutu.tataconnect;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class AdapterGetAllCategoryDetails extends BaseAdapter {

    //baseadapter = multiple view load show
    //AdapterGetAllCategoryDetails show w=multiple collect show listview

    List<POJOGetAllCategory> pojoGetAllCategories;
    Activity activity;

    public AdapterGetAllCategoryDetails(List<POJOGetAllCategory> pojoGetAllCategories, Activity activity) {
        this.pojoGetAllCategories = pojoGetAllCategories;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
