package com.fanwe.live.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.games.model.PluginModel;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.MemoryModel;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.live.view.CornerTextView;

import java.util.List;
import java.util.Random;

/**
 * 主播直播插件适配器
 *
 * @author luodong
 * @version 创建时间：2016-11-24
 */
public class LiveTagAdapter extends SDSimpleAdapter<MemoryModel.TagListBean> {
    public LiveTagAdapter(List<MemoryModel.TagListBean> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_tag;
    }
    //随机产生颜色RGB
    public int generateRandomColorCode() {
        Random random = new Random();
        return Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }
    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final MemoryModel.TagListBean model) {
        CornerTextView cornerTextView = get(R.id.item, convertView);
        if (model.getIs_selected() == 1) {
            if(model.getRgb()==0){
                int color_rgb=generateRandomColorCode();
                cornerTextView.setfilColor(color_rgb);
                model.setRgb(color_rgb);
                cornerTextView.setTextColor(Color.WHITE);
            }
        } else {
            cornerTextView.setTextColor(Color.BLACK);
            cornerTextView.setfilColor(getActivity().getResources().getColor(R.color.text_content));
        }
        cornerTextView.setText(model.getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagClickListener.TagClick(model);
            }
        });
    }
    TagClickListener tagClickListener;

    public TagClickListener getTagClickListener() {
        return tagClickListener;
    }

    public void setTagClickListener(TagClickListener tagClickListener) {
        this.tagClickListener = tagClickListener;
    }

    public interface TagClickListener{
        void TagClick(MemoryModel.TagListBean model);
    }
}
