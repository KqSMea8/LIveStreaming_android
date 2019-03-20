package com.fanwe.hybrid.db;

import org.xutils.DbManager;
import org.xutils.x;
import org.xutils.db.table.TableEntity;

/**
 * xutils数据库管理类
 * 
 * @author Administrator
 * @date 2016-4-6 上午9:13:30
 */
public class DbManagerX
{

	public static DbManager getDb()
	{
		return x.getDb(configDefault);
	}

	/** 默认数据库配置 */
	private static final DbManager.DaoConfig configDefault = new DbManager.DaoConfig().setDbName("fanwe.db").setDbVersion(1)
			.setAllowTransaction(true).setDbOpenListener(new org.xutils.DbManager.DbOpenListener()
			{
				@Override
				public void onDbOpened(org.xutils.DbManager db)
				{

				}
			}).setTableCreateListener(new org.xutils.DbManager.TableCreateListener()
			{
				@Override
				public void onTableCreated(org.xutils.DbManager db, TableEntity<?> table)
				{

				}
			}).setDbUpgradeListener(new org.xutils.DbManager.DbUpgradeListener()
			{
				@Override
				public void onUpgrade(org.xutils.DbManager db, int oldVersion, int newVersion)
				{

				}
			});
}
