package com.fanwe.games.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.games.model.GameBankerModel;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.live.R;

import java.util.List;

/**
 * Created by shibx on 2017/2/24.
 * 玩家申请上庄列表
 */

public class GameBankerListAdapter extends SDSimpleAdapter<GameBankerModel> {

    private String unit;

    public GameBankerListAdapter(List listModel, Activity activity, String unit) {
        super(listModel, activity);
        getSelectManager().setMode(SDSelectManager.Mode.SINGLE_MUST_ONE_SELECTED);
        this.unit = unit;
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_banker_list;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, GameBankerModel model) {

        TextView tv_banker_nickname = get(R.id.tv_banker_nickname, convertView);
        TextView tv_coins = get(R.id.tv_coins, convertView);
        ImageView iv_check = get(R.id.iv_check, convertView);
        tv_banker_nickname.setText(model.getBanker_name());
        tv_coins.setText(model.getCoin() + unit);
        if (model.isSelected())
        {
            iv_check.setImageResource(R.drawable.ic_me_following);
            tv_banker_nickname.setTextColor(SDResourcesUtil.getColor(R.color.main_color));
            tv_coins.setTextColor(SDResourcesUtil.getColor(R.color.main_color));
        } else
        {
            iv_check.setImageResource(R.drawable.ic_live_pop_choose_off);
            tv_banker_nickname.setTextColor(SDResourcesUtil.getColor(R.color.text_content_deep));
            tv_coins.setTextColor(SDResourcesUtil.getColor(R.color.text_content_deep));
        }

    }
}
