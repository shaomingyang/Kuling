package com.ming.shao.kuling.ImagePicker;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ming.shao.kuling.ImagePicker.model.BucketEntity;
import com.ming.shao.kuling.R;
import com.ming.shao.kuling.base.ProjectAdapter;
import com.ming.shao.kuling.base.ProjectApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smy on 2017/7/5 0005.
 */

public class GalleryAdapter extends ProjectAdapter {
    List<BucketEntity> entityList = new ArrayList<>();
    private int width, height;

    public GalleryAdapter(Context context) {
        super(context);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        width = (dm.widthPixels - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3 * 8, context.getResources().getDisplayMetrics())) / 2;
        height = (int) (width * 1.0);
    }

    public void setData(List<BucketEntity> entityList) {
        this.entityList = entityList;
    }

    @Override
    public int getCount() {
        return null == entityList ? 0 : entityList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.item_gallery_layout, null);
        }
        ImageView pic = get(convertView, R.id.pic);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        pic.setLayoutParams(params);

        TextView name = get(convertView, R.id.name);
        TextView count = get(convertView, R.id.count);
        BucketEntity item = entityList.get(position);
        Glide.with(ProjectApplication.getApplication())
                .load(new File(item.path))
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .crossFade()
                .into(pic);

        String bucket_name = item.name;
        if (bucket_name.toLowerCase().equals("camera")) {
            name.setText("我的照片");
        } else if (bucket_name.toLowerCase().equals("screenshots")) {
            name.setText("截图");
        } else {
            name.setText(bucket_name);
        }
        count.setText(String.format(context.getString(R.string.yo_count), item.count));
        return convertView;
    }

}
