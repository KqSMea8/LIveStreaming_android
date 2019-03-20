package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

public class App_get_videoActModel extends BaseActModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public String begin_time="0";

    private int room_id; // 直播间id
    private String user_id; // 主播id
    private String nick_name;
    private String group_id; // 聊天组id
    private String cont_url; // 贡献榜地址
    private int live_in; // 0-结束；1-正在直播；2-创建中；3-回放
    private int viewer_num; // 观众数量
    private int has_lianmai; // 1-显示连麦
    private int online_status = -1; // 1-主播在线；0-主播离开
    private int sort_num = -1; // 当前观众在观众列表中的排序
    private String guard_num;
    private int heat_rank;

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    @Override
    public String toString() {
        return "App_get_videoActModel{" +
                "begin_time='" + begin_time + '\'' +
                ", room_id=" + room_id +
                ", user_id='" + user_id + '\'' +
                ", group_id='" + group_id + '\'' +
                ", cont_url='" + cont_url + '\'' +
                ", live_in=" + live_in +
                '}';
    }

    public int getHeat_rank() {
        return heat_rank;
    }

    public void setHeat_rank(int heat_rank) {
        this.heat_rank = heat_rank;
    }

    public String getGuard_num() {
        return guard_num;
    }

    public void setGuard_num(String guard_num) {
        this.guard_num = guard_num;
    }

    public String getBegin_time() {
        return begin_time;
    }


    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    private String title; // 话题
    private RoomUserModel podcast;
    private RoomShareModel share; //分享信息

    private RandomPodcastModel podcast_previous;
    private RandomPodcastModel podcast_next;

    private int is_private; // 1-私密直播
    private String private_share;
    private String share_type; // 分享类型
    private String play_url; // 拉流地址
    private String push_rtmp; // 推流地址
    private String play_rtmp; // 拉流地址
    private String play_mp4;
    private String play_flv;
    private String play_hls;
    private int sdk_type; // 0-腾讯云sdk；1-金山sdk
    private String push_url; // 主播的推流地址
    private int has_video_control; // 当live_in=3时，回放是否显示视频控制操作

    private int has_focus; // status-2的时候返回是否已关注
    private int show_num; // status-2的时候返回观看人数
    private int is_del_vod; // 1-回放视频已删除

    private int pai_id;//当前直播间存在的竞拍ID
    private int join_room_prompt = -1; //0-不显示“XX 来了”；1-显示
    private String video_title;

    //按场 按时 参数=========
    private int is_live_pay;//直播是否收费 0指不收费，1指收费
    private int live_pay_type;//直播收费类型 0按时，1按场
    private int live_fee;//直播费用
    private int is_pay_over;//是否付费过 1 付费过 0未付费
    private String preview_play_url;//预览视频地址
    private int countdown;//预览时间
    private int is_only_play_voice;//是否只播放声音
    //按场 按时 参数=========

    private int create_type; // 0-手机 1-pc
    private App_viewerActModel viewer;

    //百媚=================================start
    private int is_push;// 1 是 0 否 房间是否在推流
    private int is_bm;//1 是 0 否 是否百媚模式
    private String private_key;
    //百媚=================================end
    int v_identity;//0游客  1  会员  2管理

    public int getV_identity() {
        return v_identity;
    }

    public void setV_identity(int v_identity) {
        this.v_identity = v_identity;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public App_viewerActModel getViewer() {
        return viewer;
    }

    public void setViewer(App_viewerActModel viewer) {
        this.viewer = viewer;
    }

    public int getCreate_type() {
        return create_type;
    }

    public void setCreate_type(int create_type) {
        this.create_type = create_type;
    }

    private UserJoinGuard guard;
    private UserJoinMount mount;
    public UserJoinGuard getGuard() {
        return guard;
    }

    public UserJoinMount getMount() {
        return mount;
    }

    public void setMount(UserJoinMount mount) {
        this.mount = mount;
    }

    public void setGuard(UserJoinGuard guard) {
        this.guard = guard;
    }

    //add
    public boolean canJoinRoom() {
        if (getIs_live_pay() == 1) {
            if (getIs_pay_over() == 0) {
                return false;
            }
        }
        return true;
    }

    public int getIs_only_play_voice() {
        return is_only_play_voice;
    }

    public void setIs_only_play_voice(int is_only_play_voice) {
        this.is_only_play_voice = is_only_play_voice;
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public String getPreview_play_url() {
        return preview_play_url;
    }

    public void setPreview_play_url(String preview_play_url) {
        this.preview_play_url = preview_play_url;
    }

    public int getLive_fee() {
        return live_fee;
    }

    public void setLive_fee(int live_fee) {
        this.live_fee = live_fee;
    }

    public int getIs_pay_over() {
        return is_pay_over;
    }

    public void setIs_pay_over(int is_pay_over) {
        this.is_pay_over = is_pay_over;
    }

    public int getLive_pay_type() {
        return live_pay_type;
    }

    public void setLive_pay_type(int live_pay_type) {
        this.live_pay_type = live_pay_type;
    }

    public int getJoin_room_prompt() {
        return join_room_prompt;
    }

    public void setJoin_room_prompt(int join_room_prompt) {
        this.join_room_prompt = join_room_prompt;
    }

    public int getIs_live_pay() {
        return is_live_pay;
    }

    public void setIs_live_pay(int is_live_pay) {
        this.is_live_pay = is_live_pay;
    }

    private int game_log_id;//直播间游戏轮数，0：未开始游戏 非0：当前游戏轮数

    public int getGame_log_id() {
        return game_log_id;
    }

    public void setGame_log_id(int game_log_id) {
        this.game_log_id = game_log_id;
    }

    public int getSort_num() {
        return sort_num;
    }

    public void setSort_num(int sort_num) {
        this.sort_num = sort_num;
    }

    public String getPush_rtmp() {
        return push_rtmp;
    }

    public void setPush_rtmp(String push_rtmp) {
        this.push_rtmp = push_rtmp;
    }

    public String getPlay_rtmp() {
        return play_rtmp;
    }

    public void setPlay_rtmp(String play_rtmp) {
        this.play_rtmp = play_rtmp;
    }

    public String getPlay_mp4() {
        return play_mp4;
    }

    public void setPlay_mp4(String play_mp4) {
        this.play_mp4 = play_mp4;
    }

    public String getPlay_flv() {
        return play_flv;
    }

    public void setPlay_flv(String play_flv) {
        this.play_flv = play_flv;
    }

    public String getPlay_hls() {
        return play_hls;
    }

    public void setPlay_hls(String play_hls) {
        this.play_hls = play_hls;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideoStoped() {
        return status == 2;
    }

    public int getHas_video_control() {
        return has_video_control;
    }

    public void setHas_video_control(int has_video_control) {
        this.has_video_control = has_video_control;
    }

    public String getPlay_url() {
        return play_url;
    }

    public void setPlay_url(String play_url) {
        this.play_url = play_url;
    }

    public String getPush_url() {
        return push_url;
    }

    public void setPush_url(String push_url) {
        this.push_url = push_url;
    }

    public int getIs_del_vod() {
        return is_del_vod;
    }

    public void setIs_del_vod(int is_del_vod) {
        this.is_del_vod = is_del_vod;
    }


    public RandomPodcastModel getPodcast_previous() {
        return podcast_previous;
    }

    public void setPodcast_previous(RandomPodcastModel podcast_previous) {
        this.podcast_previous = podcast_previous;
    }

    public int getHas_focus() {
        return has_focus;
    }

    public void setHas_focus(int has_focus) {
        this.has_focus = has_focus;
    }

    public int getShow_num() {
        return show_num;
    }

    public void setShow_num(int show_num) {
        this.show_num = show_num;
    }

    public RandomPodcastModel getPodcast_next() {
        return podcast_next;
    }

    public void setPodcast_next(RandomPodcastModel podcast_next) {
        this.podcast_next = podcast_next;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getCont_url() {
        return cont_url;
    }

    public void setCont_url(String cont_url) {
        this.cont_url = cont_url;
    }

    public int getLive_in() {
        return live_in;
    }

    public void setLive_in(int live_in) {
        this.live_in = live_in;
    }

    public RoomUserModel getPodcast() {
        return podcast;
    }

    public void setPodcast(RoomUserModel podcast) {
        this.podcast = podcast;
    }

    public RoomShareModel getShare() {
        return share;
    }

    public void setShare(RoomShareModel share) {
        this.share = share;
    }

    public int getViewer_num() {
        return viewer_num;
    }

    public void setViewer_num(int viewer_num) {
        this.viewer_num = viewer_num;
    }

    public int getHas_lianmai() {
        if (is_bm == 1) {
            //如果是百媚房间，根据是否推流展示连麦按钮
            return is_push;
        } else {
            return has_lianmai;
        }
    }

    public void setHas_lianmai(int has_lianmai) {
        this.has_lianmai = has_lianmai;
    }

    public int getOnline_status() {
        return online_status;
    }

    public void setOnline_status(int online_status) {
        this.online_status = online_status;
    }

    public String getPrivate_share() {
        return private_share;
    }

    public void setPrivate_share(String private_share) {
        this.private_share = private_share;
    }

    public String getShare_type() {
        return share_type;
    }

    public void setShare_type(String share_type) {
        this.share_type = share_type;
    }

    public int getIs_private() {
        return is_private;
    }

    public void setIs_private(int is_private) {
        this.is_private = is_private;
    }

    public int getPai_id() {
        return pai_id;
    }

    public void setPai_id(int pai_id) {
        this.pai_id = pai_id;
    }

    public int getSdk_type() {
        return sdk_type;
    }

    public void setSdk_type(int sdk_type) {
        this.sdk_type = sdk_type;
    }

    public int getIs_push() {
        return is_push;
    }

    public void setIs_push(int is_push) {
        this.is_push = is_push;
    }

    public int getIs_bm() {
        return is_bm;
    }

    public void setIs_bm(int is_bm) {
        this.is_bm = is_bm;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

}
