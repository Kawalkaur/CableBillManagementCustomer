package com.kawal.cablebillmanagement;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by kawaldeep on 5/24/2017.
 */

public class HDImageAdapter extends BaseAdapter {
    private Context mContext;

    public  HDImageAdapter(Context c){
        mContext = c;
    }
    @Override
    public int getCount() {
        return mThumbIds.length;
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
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.strplhd,  R.drawable.colorshd,
            R.drawable.zeetvhd, R.drawable.bbchd,
            R.drawable.stargoldhd, R.drawable.cnhd,
            R.drawable.dichd, R.drawable.foxhd,
            R.drawable.disneyhd, R.drawable.hbohd,
            R.drawable.hdsony, R.drawable.nbchd,
            R.drawable.ngchd, R.drawable.sonyespnhd,
            R.drawable.sonymaxhd, R.drawable.starworldhd,
            R.drawable.tenhd, R.drawable.tlchd,
            R.drawable.zeecinemahd, R.drawable.zeeprihd,
            R.drawable.starsportssd,

    };
}
