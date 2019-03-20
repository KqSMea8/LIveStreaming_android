package com.fanwe.live.model.custommsg;

import com.fanwe.live.LiveConstant;
import com.fanwe.live.gif.GifConfigModel;
import com.fanwe.live.model.UserOpenShouhu;

import java.util.List;

/**
 * 大型礼物通知消息
 */
public class CustomMsgOpenShouhu extends CustomMsg
{

    public CustomMsgOpenShouhu()
    {
        super();
        setType(LiveConstant.CustomMsgType.MSG_LIVE_OPEN_SHOUHU);
    }
        private String id;
        private String name;
        private int is_animated;
        private String anim_type;
        private List<GifConfigModel> anim_cfg;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public int getIs_animated() {
            return is_animated;
        }

        public void setIs_animated(int is_animated) {
            this.is_animated = is_animated;
        }

        public String getAnim_type() {
            return anim_type;
        }

        public void setAnim_type(String anim_type) {
            this.anim_type = anim_type;
        }


        public List<GifConfigModel> getAnim_cfg() {
            return anim_cfg;
        }

        public void setAnim_cfg(List<GifConfigModel> anim_cfg) {
            this.anim_cfg = anim_cfg;
        }
}
