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

public class SDImageAdapter extends BaseAdapter {
    private Context mContext;

    public SDImageAdapter(Context c){
        mContext=c;
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


    // create a new ImageView for each item referenced by the Adapter
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

            R.drawable.starplus, R.drawable.zeetvsd,
            R.drawable.rishteysd, R.drawable.sonysd,
            R.drawable.lifeoksd, R.drawable.sonysabsd,
            R.drawable.moviesoksd, R.drawable.stargoldsd,
            R.drawable.zeecinemasd, R.drawable.sonymaxsd,
            R.drawable.xqsd, R.drawable.zindagisd,
            R.drawable.tvand, R.drawable.b4usd,
            R.drawable.zstudiosd, R.drawable.aajtaksd,
            R.drawable.abpnewssd, R.drawable.bigmagicsd,
            R.drawable.cnsd, R.drawable.discoverysd,
            R.drawable.disneysd, R.drawable.epicsd,
            R.drawable.fashiontvsd, R.drawable.hbosd,
            R.drawable.fnsd, R.drawable.homeshop18sd,
            R.drawable.colorsd, R.drawable.ptcchakdesd,
            R.drawable.ptcnewssd, R.drawable.sabtvsd,
            R.drawable.tencricketsd, R.drawable.vchannelsd,
            R.drawable.picsd,



    };
}
