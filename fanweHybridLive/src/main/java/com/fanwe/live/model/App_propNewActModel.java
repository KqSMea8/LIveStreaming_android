package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * 礼物列表接口实体
 */
public class App_propNewActModel extends BaseActModel
{

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 3
         * name : 手绘
         * data : [{"id":"38","name":"全省","score":"100","diamonds":"100","icon":"https://osshttps.fanwe.net/public/attachment/201712/29/10/5a45ad3915547.jpg","pc_icon":"https://osshttps.fanwe.net/public/attachment/201712/29/10/5a45ad4655f7e.jpg","pc_gif":"https://osshttps.fanwe.net/public/attachment/201712/29/10/5a45ad4ecd55b.jpg","ticket":20,"is_much":"0","sort":"22","is_red_envelope":"0","is_animated":"0","anim_type":"","gif_gift_show_style":"0","g_id":"3","is_award":"0","is_heat":"0","red_envelope_type":"1","score_fromat":"+100经验值"}]
         */

        private String g_id;
        private String name;
        private List<LiveGiftModel> data;


        public List<LiveGiftModel> getData() {
            return data;
        }

        public void setData(List<LiveGiftModel> data) {
            this.data = data;
        }

        public String getG_id() {
            return g_id;
        }

        public void setG_id(String g_id) {
            this.g_id = g_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
