package com.newer.eshop.goods;

import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.newer.eshop.App;
import com.newer.eshop.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme;

public class GalleryImageAdapter extends BaseAdapter {

	private List<String> mList;
	private Context mContext;
	private int mGalleryItemBackground;

	public GalleryImageAdapter(Context context, List<String> list) {
		super();
		mList = list;
		mContext = context;

		TypedArray typedArray = mContext
				.obtainStyledAttributes(R.styleable.Gallery);
		mGalleryItemBackground = typedArray.getResourceId(
				R.styleable.Gallery_android_galleryItemBackground, 0);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final String path = mList.get(position);
		ViewHolder viewHolder = new ViewHolder();
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.gallery_child_item, parent, false);
			viewHolder.mImageView = (ImageView) convertView
					.findViewById(R.id.child_image);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		ImageLoader.getInstance().displayImage(App.SERVICE_IMAGES_URL + path + ".jpg",
				viewHolder.mImageView, App.initOptions());
		viewHolder.mImageView.setBackgroundResource(mGalleryItemBackground);
		return convertView;
	}

	public static class ViewHolder {
		public ImageView mImageView;
	}
}
