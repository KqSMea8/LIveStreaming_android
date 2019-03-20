package com.fanwe.live.appview;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.customview.SDSlidingPlayView;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.live.R;

import java.util.List;

public class SDSlidingPlayViewHandler<T>
{

    private BindDataListener<T> bindDataListener;

    public void setBindDataListener(BindDataListener<T> bindDataListener)
    {
        this.bindDataListener = bindDataListener;
    }

    public SDPagerAdapter<List<T>> bindData(SDSlidingPlayView view, List<T> listModel, final int colNumber, final int pageSize, Activity activity)
    {
        SDPagerAdapter<List<T>> pagerAdapter = null;
        List<List<T>> listPage = SDCollectionUtil.splitList(listModel, pageSize);
        if (view != null && activity != null)
        {
            pagerAdapter = new SDPagerAdapter<List<T>>(listPage, activity)
            {
                @Override
                public View getView(ViewGroup container, int position)
                {
                    View view = inflate(R.layout.item_pager_grid_linear, container);

                    SDGridLinearLayout grid = (SDGridLinearLayout) view.findViewById(R.id.ll_content);
                    grid.setColNumber(colNumber);
                    grid.setAdapter(new SDSimpleAdapter<T>(getData(position), getActivity())
                    {

                        @Override
                        public int getLayoutId(int position, View convertView, ViewGroup parent)
                        {
                            if (bindDataListener != null)
                            {
                                return bindDataListener.getLayoutId(position, convertView, parent);
                            } else
                            {
                                return 0;
                            }
                        }

                        @Override
                        public void bindData(int position, View convertView, ViewGroup parent, T model)
                        {
                            if (bindDataListener != null)
                            {
                                bindDataListener.bindData(position, convertView, parent, model);
                            }
                        }
                    });
                    return view;
                }
            };
            view.setAdapter(pagerAdapter);
        }
        return pagerAdapter;
    }

    public interface BindDataListener<T>
    {
        void bindData(int position, View convertView, ViewGroup parent, T model);

        int getLayoutId(int position, View convertView, ViewGroup parent);
    }
}
