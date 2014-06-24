package com.example.edtv1;

import gestionBD.DAO.ImagesBDD;
import gestionBD.bean.BeanAvecImage;
import gestionBD.bean.ImageBean;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class ListImageTextViewAdapter extends BaseAdapter implements Filterable {

	private ArrayList<? extends BeanAvecImage> listActivites;
	private ArrayList<? extends BeanAvecImage> utilises;

	private Activity activity;
	private LayoutInflater inflater;

	public ListImageTextViewAdapter(ArrayList<? extends BeanAvecImage> liste, Activity a) {
		this.listActivites = liste;
		utilises = liste;
		this.activity = a;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.row_item, null);

		ImageView image = (ImageView) vi.findViewById(R.id.list_image);

		ImagesBDD bddImage = new ImagesBDD(activity);
		bddImage.open();
		ImageBean imageBean = bddImage.getImageWithId(utilises.get(position).getIdImage());
		bddImage.close();

		String imgPath = imageBean.getImagePath();
		image.setImageURI(Utilities.stringToUri(imgPath));
		image.setScaleType(ScaleType.CENTER_CROP);

		TextView title = (TextView) vi.findViewById(R.id.title);
		String str = this.utilises.get(position).toString();
		title.setText(str);

		return vi;
	}

	@Override
	public int getCount() {
		return this.utilises.size();
	}

	@Override
	public Object getItem(int position) {
		return this.utilises.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public Filter getFilter() {
		return new Filter() {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				final FilterResults oReturn = new FilterResults();
				final ArrayList<BeanAvecImage> results = new ArrayList<BeanAvecImage>();

				if (constraint != null) {
					if (listActivites.size() > 0) {
						for (final BeanAvecImage a : listActivites) {
							if (a.getName().toLowerCase(Locale.getDefault()).contains(constraint.toString()))
								results.add(a);
						}
					}
					oReturn.values = results;
				}
				return oReturn;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				utilises = (ArrayList<BeanAvecImage>) results.values;
				notifyDataSetChanged();
			}
		};
	}

}
