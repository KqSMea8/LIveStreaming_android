package com.fanwe.hybrid.dao;

import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.live.common.HostManager;

public class InitActModelDao
{
    public static boolean insertOrUpdate(InitActModel model)
    {
        boolean result = JsonDbModelDao.getInstance().insertOrUpdate(model);
        HostManager.getInstance().saveActHost();
        return result;
    }

    public static InitActModel query()
    {
        return JsonDbModelDao.getInstance().query(InitActModel.class);
    }

    public static void delete()
    {
        JsonDbModelDao.getInstance().delete(InitActModel.class);
    }

}
