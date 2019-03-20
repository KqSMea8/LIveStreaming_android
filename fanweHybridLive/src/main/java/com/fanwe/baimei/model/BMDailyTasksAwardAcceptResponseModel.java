package com.fanwe.baimei.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * 包名: com.fanwe.baimei.model
 * 描述: 领取每日任务奖励返回数据实体类
 * 作者: Su
 * 创建时间: 2017/5/31 14:53
 **/
public class BMDailyTasksAwardAcceptResponseModel extends BaseActModel
{

    /**
     * mission : {"image":"http://liveimage.fanwe.net/public/images/watch_live.png","title":"在线10秒钟","desc":"奖励20💎","money":20,"time":0,"max_times":4,"left_times":3,"current":0,"target":0,"progress":100,"type":0}
     * diamonds : 996
     * coin : 10
     * is_mission : 1
     */

    private MissionBean mission;
    private String diamonds;
    private String coin;
    private int is_mission;

    public MissionBean getMission()
    {
        return mission;
    }

    public void setMission(MissionBean mission)
    {
        this.mission = mission;
    }

    public String getDiamonds()
    {
        return diamonds;
    }

    public void setDiamonds(String diamonds)
    {
        this.diamonds = diamonds;
    }

    public String getCoin()
    {
        return coin;
    }

    public void setCoin(String coin)
    {
        this.coin = coin;
    }

    public int getIs_mission()
    {
        return is_mission;
    }

    public void setIs_mission(int is_mission)
    {
        this.is_mission = is_mission;
    }

    public static class MissionBean
    {
        /**
         * image : http://liveimage.fanwe.net/public/images/watch_live.png
         * title : 在线10秒钟
         * desc : 奖励20💎
         * money : 20
         * time : 0
         * max_times : 4
         * left_times : 3
         * current : 0
         * target : 0
         * progress : 100
         * type : 0
         */

        private String image;
        private String title;
        private String desc;
        private int money;
        private int time;
        private int max_times;
        private int left_times;
        private int current;
        private int target;
        private int progress;
        private int type;

        public String getImage()
        {
            return image;
        }

        public void setImage(String image)
        {
            this.image = image;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String getDesc()
        {
            return desc;
        }

        public void setDesc(String desc)
        {
            this.desc = desc;
        }

        public int getMoney()
        {
            return money;
        }

        public void setMoney(int money)
        {
            this.money = money;
        }

        public int getTime()
        {
            return time;
        }

        public void setTime(int time)
        {
            this.time = time;
        }

        public int getMax_times()
        {
            return max_times;
        }

        public void setMax_times(int max_times)
        {
            this.max_times = max_times;
        }

        public int getLeft_times()
        {
            return left_times;
        }

        public void setLeft_times(int left_times)
        {
            this.left_times = left_times;
        }

        public int getCurrent()
        {
            return current;
        }

        public void setCurrent(int current)
        {
            this.current = current;
        }

        public int getTarget()
        {
            return target;
        }

        public void setTarget(int target)
        {
            this.target = target;
        }

        public int getProgress()
        {
            return progress;
        }

        public void setProgress(int progress)
        {
            this.progress = progress;
        }

        public int getType()
        {
            return type;
        }

        public void setType(int type)
        {
            this.type = type;
        }
    }
}
