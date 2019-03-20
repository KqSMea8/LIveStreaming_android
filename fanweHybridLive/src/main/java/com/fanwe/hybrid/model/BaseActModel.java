package com.fanwe.hybrid.model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * @author yhz
 * @create time 2014-9-16 类说明 基类Model
 */
@SuppressWarnings("serial")
public class BaseActModel implements Serializable
{
    protected String act;
    protected String act_2;
    protected String gq_name;
    protected int response_code = -999;
    protected String show_err;
    protected int user_login_status = -999; // 用户登录状态，0-未登录，1-已登录
    protected String info;
    protected int status; // 接口返回状态，1-成功，其他值失败，或者其他业务
    protected long expiry_after; // 接口的json数据过期时间点，如果大于0，则要缓存这个json(单位秒)
    protected String error; // 接口返回的提示信息
    private long init_version;

    //add

    /**
     * 缓存是否过期
     *
     * @return
     */
    public boolean isExpired()
    {
        return System.currentTimeMillis() > (expiry_after * 1000);
    }

    public long getInit_version()
    {
        return init_version;
    }

    public void setInit_version(long init_version)
    {
        this.init_version = init_version;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public long getExpiry_after()
    {
        return expiry_after;
    }

    public void setExpiry_after(long expiry_after)
    {
        this.expiry_after = expiry_after;
    }

    public String getGq_name()
    {
        return gq_name;
    }

    public void setGq_name(String gq_name)
    {
        if (!TextUtils.isEmpty(gq_name))
        {
            this.gq_name = gq_name;
        } else
        {
            this.gq_name = "股权众筹";
        }
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }

    public int getUser_login_status()
    {
        return user_login_status;
    }

    public void setUser_login_status(int user_login_status)
    {
        this.user_login_status = user_login_status;
    }

    public String getShow_err()
    {
        return show_err;
    }

    public void setShow_err(String show_err)
    {
        this.show_err = show_err;
    }

    public int getResponse_code()
    {
        return response_code;
    }

    public void setResponse_code(int response_code)
    {
        this.response_code = response_code;
    }

    public String getAct()
    {
        return act;
    }

    public void setAct(String act)
    {
        this.act = act;
    }

    public String getAct_2()
    {
        return act_2;
    }

    public void setAct_2(String act_2)
    {
        this.act_2 = act_2;
    }

    public boolean isOk()
    {
        return this.status == 1;
    }

//    status 错误码
//    10001 查询的业务数据不存在
//    10002 操作的业务动作失败
//    10003 分润失败，需做日志处理或重新发起请求
//    10004 订单支付失败
//    10005 接口不存在
//    10006 接口下的方法不存在
//    10007 服务端未登陆
//    10008 商品不存在
//    10009 主播不存在
//    10010 竞拍商品不存在
//    10011 竞拍人不存在
//    10012 下单单失败
//    10013 提交保证金失败
//    10014 竞拍失败
//    10015 添加收货地址失败
//    10016 删除收货地址失败
//    10017 姓名为空
//    10018 手机号码为空
//    10019 手机号码格式错误
//    10020 编辑收货地址失败
//    10021 消息类型为空
//    10022 消息推送失败
//    10023 消息删除失败
//    10024 设置默认收货地址失败
//    10025 创建竞拍失败
//    10026 编辑竞拍失败
//    10027 关闭竞拍失败
//    10028 确认完成虚拟竞拍失败
//    10029 确认竞拍退款失败
//    10030 申诉竞拍失败
//    10031 确认约会失败
//    10032 撤销失败
//    10033 推送会员为空
//    10034 区域数据错误
//    10035 收货地址为空
//    10036 竞拍已结束
//    10037 订单号错误
//    10038 名称不能为空
//    10039 描述不能为空
//    10040 时间不能为空
//    10041 地点不能为空
//    10042 联系人不能为空
//    10043 请输入正确的联系电话
//    10044 竞拍价格不能为0
//    10045 每次加价幅度不能为
//    10046 竞拍时长不能为0
//    10047 每次竞拍延时不能为0
//    10048 最大延时不能为0
//    10049 存在未完成的竞拍，创建竞拍失败
//    10050 已提交过保证金
//    10051 禁止发起竞拍，创建竞拍失败
//    10052 未提交保证金
}
