package com.fanwe.baimei.model;

import java.util.List;

/**
 * 包名: com.fanwe.baimei.model
 * 描述: 房间列表实体类
 * 作者: Su
 * 创建时间: 2017/5/22 18:24
 **/
public class BMGameRoomListResponseModel
{

    /**
     * list : [{"room_id":"638687","sort_num":"200000000","group_id":"638687","user_id":"100986","city":"福州","title":"","cate_id":"0","live_in":"1","video_type":"2","create_type":"0","room_type":"3","watch_number":"24","head_image":"http://liveimage.fanwe.net/public/attachment/201611/100986/1478503120682.png?x-oss-process=image/resize,m_mfit,h_150,w_150","thumb_head_image":"http://liveimage.fanwe.net/public/attachment/201610/28/16/origin/thumb_1477613299100986.jpg?x-oss-process=image/resize,m_mfit,h_150,w_150","xpoint":"119.270364","ypoint":"26.072652","v_type":"0","v_icon":"http://image.qiankeep.com/public/attachment/201608/14/10/57afd1be32771.png","nick_name":"pro","user_level":"34","live_image":"http://liveimage.fanwe.net/public/attachment/201611/100986/1478503120682.png","is_live_pay":"0","live_pay_type":"0","live_fee":"0","user_create_time":"1472629415","today_create":0,"game_id":"4"}]
     * status : 1
     * error :
     */

    private int status;
    private String error;
    private List<BMGameRoomListModel> list;

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public List<BMGameRoomListModel> getList()
    {
        return list;
    }

    public void setList(List<BMGameRoomListModel> list)
    {
        this.list = list;
    }
}
