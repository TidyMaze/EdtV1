package com.example.edtv1;

import gestionBD.bean.ImageBean;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ListImageBeanAdapter extends BaseAdapter {
	
	private ArrayList<ImageBean> list;
	private Activity activity;
	private LayoutInflater inflater;

	public ListImageBeanAdapter(ArrayList<ImageBean> liste, Activity a) {
		this.list = liste;
        this.activity = a;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}



	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.grid_item, null);
 
        ImageView image =(ImageView)vi.findViewById(R.id.image_view);
        String imgPath = this.list.get(position).getImagePath();
        image.setImageURI(Utilities.stringToUri(imgPath));
        image.setScaleType(ScaleType.CENTER_CROP);
        return vi;
	}
	

	@Override
	public int getCount() {
		return this.list.size();
	}



	@Override
	public Object getItem(int position) {
		return this.list.get(position);
	}



	@Override
	public long getItemId(int position) {
		return position / 4;
	}
}
