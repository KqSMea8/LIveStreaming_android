package com.fanwe.live.dialog;

import android.app.Activity;

import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveMainActivity;

/**
 * 升级
 *
 * Created by luodong on 2016/10/20.
 */
public class LevelUpgradeDialog extends LevelBaseDialog
{

    public LevelUpgradeDialog(Activity activity) {
        super(activity);
        initSetView();
    }

    private void initSetView() {
        setImageResource(R.drawable.ic_level_upgrade);
        setTitle("恭喜您升到10级！");
        setContent("");
    }

    public static void check(Activity activity)
    {
        if (activity == null)
        {
            return;
        }
        InitActModel model = InitActModelDao.query();
        if(model!=null) {
            if (model.getOpen_upgrade_prompt() == 1) {//是否开启每日登录升级提示
                if (model.getNew_level() != 0) {//新的等级，大于0为新的等级，等于0未没有升级
                    LevelUpgradeDialog levelUpgradeDialog = new LevelUpgradeDialog(activity);
                    levelUpgradeDialog.setTitle("恭喜您升到" + model.getNew_level() + "级！");
                    levelUpgradeDialog.setContent("");
                    levelUpgradeDialog.show();
                }
            }
        }
    }

}
