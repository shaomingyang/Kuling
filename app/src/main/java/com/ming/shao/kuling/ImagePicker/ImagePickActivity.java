package com.ming.shao.kuling.ImagePicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.ming.shao.kuling.ImagePicker.ui.GalleryImageFragment;
import com.ming.shao.kuling.R;

/**
 * Created by smy on 2017/7/5 0005.
 */

public class ImagePickActivity extends FragmentActivity  {
    private static final String EXTRA_IS_MULTIPLE = "extra_is_multiple";
    private boolean isMultiplePick = true;

    public static Intent getCallingIntent(Context context, boolean multiplePick) {
        Intent intent = new Intent(context, ImagePickActivity.class);
        intent.putExtra(EXTRA_IS_MULTIPLE, multiplePick);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pick);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isMultiplePick = bundle.getBoolean(EXTRA_IS_MULTIPLE, true);
        }
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, GalleryImageFragment.newInstance()).commit();

        } else {
            // 回退栈里包含 PicPreviewFragment时 TitleBar标题为"预览"
            if (getSupportFragmentManager().getBackStackEntryCount() == 2) {

            }
        }
    }

//    @Override
//    public void onSwitchDir(String bucket_name) {
//        addFragment(GalleryImageFragment.newInstance(bucket_name, isMultiplePick));
//    }
//    private void addFragment(Fragment fragment) {
//        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
//        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        trans.add(R.id.container, fragment);
//        trans.addToBackStack(null);
//        try {
//            trans.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
