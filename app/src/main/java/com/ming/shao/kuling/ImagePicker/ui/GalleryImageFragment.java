package com.ming.shao.kuling.ImagePicker.ui;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ming.shao.kuling.ImagePicker.GalleryAdapter;
import com.ming.shao.kuling.ImagePicker.model.BucketEntity;
import com.ming.shao.kuling.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smy on 2017/7/5 0005.
 */

public class GalleryImageFragment extends Fragment {
    private Activity activity;
//    private OnSwitchDirCallback callback;
    private static final String COLUMN_NAME_COUNT = "v_count";
    GridView gridView;
    GalleryAdapter adapter;


//    public interface OnSwitchDirCallback {
//        void onSwitchDir(String bucket_name);
//    }

    public static GalleryImageFragment newInstance() {
        return new GalleryImageFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
//        try {
//            //activity必须实现这个接口回调
//            callback = (OnSwitchDirCallback) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString() + "must implement OnSwitchDirCallback");
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        initView(view);
        initData(view);
        return view;
    }

    private void initView(View view) {
        gridView = (GridView) view.findViewById(R.id.gridView);
        adapter = new GalleryAdapter(getActivity());
        gridView.setAdapter(adapter);
    }

    private void initData(View view) {
        new PicExploreTask().execute();
    }

    class PicExploreTask extends AsyncTask<Void, Integer, List<BucketEntity>> {
        @Override
        protected List<BucketEntity> doInBackground(Void... params) {
            return getDirectory();
        }

        @Override
        protected void onPostExecute(List<BucketEntity> bucketEntities) {
            super.onPostExecute(bucketEntities);
            adapter.setData(bucketEntities);
        }


    }

    private List<BucketEntity> getDirectory() {
        List<BucketEntity> listFiles = new ArrayList<>();
        String[] mediaColumns = new String[]{
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                "COUNT(*) AS " + COLUMN_NAME_COUNT
        };
        // SELECT _data, COUNT(*) AS v_count  FROM video WHERE ( GROUP BY bucket_display_name)
        String selection = " 1=1 ) GROUP BY (" + MediaStore.Images.Media.BUCKET_DISPLAY_NAME;

        Cursor cursor = activity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mediaColumns, selection, null, null);
        assert cursor != null;
        while (cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            if (path.endsWith(".gif")) {
                continue;
            }

            String bucket_name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
            int count = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_COUNT));
            BucketEntity bucketEntity = new BucketEntity();
            bucketEntity.name = bucket_name;
            bucketEntity.count = count;
            bucketEntity.path = path;
            listFiles.add(bucketEntity);
        }
        cursor.close();
        return listFiles;
    }
}
