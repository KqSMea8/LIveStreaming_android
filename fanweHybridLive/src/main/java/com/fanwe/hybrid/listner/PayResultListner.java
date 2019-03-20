package com.fanwe.hybrid.listner;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-3-17 下午3:44:21 类说明 支付SDK返回结果接口回调类
 */
public interface PayResultListner
{
	/** 支付成功 */
	void onSuccess();

	/** 支付中 */
	void onDealing();

	/** 支付失败 */
	void onFail();

	/** 取消支付 */
	void onCancel();

	/** 网络原因 */
	void onNetWork();

	/** 其他原因 */
	void onOther();
}
