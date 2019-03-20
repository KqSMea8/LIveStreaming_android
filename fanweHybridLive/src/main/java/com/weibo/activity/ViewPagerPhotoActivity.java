package com.weibo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/24 0024.
 */

public class ViewPagerPhotoActivity extends BaseActivity {
    private ViewPager vp;
    private List<String> listtext = new ArrayList<>();
    private List<String> listimage = new ArrayList<>();
    private MyAdapter adapter;
    private ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_photo_pager);
        listimage.addAll(getIntent().getStringArrayListExtra("photos"));
        vp = (ViewPager) findViewById(R.id.viewpager);
        iv_back=find(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new MyAdapter(this);
        for(int i=0;i<listimage.size();i++){
            listtext.add(i+1+" / "+listimage.size());
        }
        vp.setAdapter(adapter);
    }
    public

    class MyAdapter extends PagerAdapter {

        private Context context;
        private LayoutInflater layoutInflater;

        public MyAdapter(Context context) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return listimage.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = layoutInflater.inflate(R.layout.item_photo_pager, null);
            TextView tv_page = (TextView) view.findViewById(R.id.tv_num);
            ImageView imageView= (ImageView) view.findViewById(R.id.photoview);
            GlideUtil.load(listimage.get(position)).into(imageView);
            tv_page.setText(listtext.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}