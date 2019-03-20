package com.fanwe.live.dao;

import com.fanwe.hybrid.dao.JsonDbModelDao;
import com.fanwe.library.utils.SDObjectCache;
import com.fanwe.live.event.EUpdateUserInfo;
import com.fanwe.live.model.UserModel;
import com.sunday.eventbus.SDEventManager;

public class UserModelDao
{

    public static boolean insertOrUpdate(UserModel model)
    {
        boolean result = false;
        SDObjectCache.put(model);
        result = JsonDbModelDao.getInstance().insertOrUpdate(model, true);

        EUpdateUserInfo event = new EUpdateUserInfo();
        event.user = model;
        SDEventManager.post(event);

        return result;
    }

    public static UserModel query()
    {
        UserModel model = SDObjectCache.get(UserModel.class);
        if (model == null)
        {
            model = JsonDbModelDao.getInstance().query(UserModel.class, true);
            SDObjectCache.put(model);
        }

        return model;
    }

    public static void delete()
    {
        SDObjectCache.remove(UserModel.class);
        JsonDbModelDao.getInstance().delete(UserModel.class);
    }

    //extend

    /**
     * 支付秀豆
     *
     * @param diamonds
     * @return
     */
    public static UserModel payDiamonds(long diamonds)
    {
        UserModel model = query();
        if (model == null)
        {
            return null;
        }

        model.payDiamonds(diamonds);
        insertOrUpdate(model);
        return model;
    }

    /**
     * 支付游戏币
     *
     * @param coins
     * @return
     */
    public static UserModel payCoins(long coins)
    {
        UserModel model = query();
        if (model == null)
        {
            return null;
        }

        model.payCoins(coins);
        insertOrUpdate(model);
        return model;
    }

    /**
     * 更新秀豆
     *
     * @param diamonds
     * @return
     */
    public static UserModel updateDiamonds(long diamonds)
    {
        UserModel model = query();
        if (model == null)
        {
            return null;
        }

        model.setDiamonds(diamonds);
        insertOrUpdate(model);
        return model;
    }

    public static UserModel updateCoins(long coins)
    {
        UserModel model = query();
        if (model == null)
        {
            return null;
        }

        model.setCoin(coins);
        insertOrUpdate(model);
        return model;
    }

    public static UserModel updateDiamondsAndCoins(long diamonds, long coins)
    {
        UserModel model = query();
        if (model == null)
        {
            return null;
        }
        model.setDiamonds(diamonds);
        model.setCoin(coins);
        insertOrUpdate(model);
        return model;
    }

    /**
     * 秀豆是否够支付
     *
     * @param diamonds
     * @return
     */
    public static boolean canDiamondsPay(long diamonds)
    {
        UserModel model = query();
        return model != null && model.canDiamondsPay(diamonds);
    }

    /**
     * 游戏币是否够支付
     *
     * @param coins
     * @return
     */
    public static boolean canCoinsPay(long coins)
    {
        UserModel model = query();
        return model != null && model.canCoinsPay(coins);
    }

    /**
     * 更新newModel里面的等级到本地，只能更新大于当前等级的值
     *
     * @param newModel
     * @return
     */
    public static UserModel updateLevelUp(UserModel newModel)
    {
        UserModel model = query();
        if (model == null)
        {
            return null;
        }

        if (newModel == null)
        {
            return model;
        }

        String newId = newModel.getUser_id();
        if (newId == null)
        {
            return model;
        }

        if (!newId.equals(model.getUser_id()))
        {
            return model;
        }

        int newLevel = newModel.getUser_level();
        if (newLevel > model.getUser_level())
        {
            model.setUser_level(newLevel);
            insertOrUpdate(model);
        }
        return model;
    }

    /**
     * 获取用户id
     *
     * @return
     */
    public static String getUserId()
    {
        UserModel model = query();
        if (model == null)
        {
            return "";
        }
        return model.getUser_id();
    }

    /**
     * 支付游戏币
     *
     * @param value
     * @return
     */
    public static UserModel payGameCurrency(long value)
    {
        UserModel model = query();
        if (model == null)
        {
            return null;
        }

        model.payGameCurrency(value);
        insertOrUpdate(model);
        return model;
    }

    /**
     * 游戏币是否够支付
     *
     * @param value
     * @return
     */
    public static boolean canGameCurrencyPay(long value)
    {
        UserModel model = query();
        return model != null && model.canGameCurrencyPay(value);
    }

    /**
     * 更新游戏币
     *
     * @param value
     */
    public static UserModel updateGameCurrency(long value)
    {
        UserModel model = query();
        if (model == null)
        {
            return null;
        }

        model.updateGameCurrency(value);
        insertOrUpdate(model);
        return model;
    }

    /**
     * 获得游戏币余额
     *
     * @return
     */
    public static long getGameCurrency()
    {
        UserModel model = query();
        if (model == null)
        {
            return 0;
        }
        return model.getGameCurrency();
    }

}
