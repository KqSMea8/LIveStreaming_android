package com.fanwe.live.model;

import android.text.TextUtils;

import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDTypeParseUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 直播间观众列表实体
 */
public class App_viewerActModel extends BaseActModel {

    @Override
    public String toString() {
        return "App_viewerActModel{" +
                "list=" + list +
                ", list_fields=" + list_fields +
                ", list_data=" + list_data +
                ", time=" + time +
                ", count=" + count +
                ", has_next=" + has_next +
                ", watch_number=" + watch_number +
                ", short_url='" + short_url + '\'' +
                '}';
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static final String DEFAULT_IMAGE_SCHEME = "http";

    private static final String FIELD_USER_ID = "user_id"; //用户id字段
    private static final String FIELD_USER_LEVEL = "user_level"; //用户等级
    private static final String FIELD_HEAD_IMAGE = "head_image"; //头像链接
    private static final String FIELD_V_ICON = "v_icon"; //认证图标链接
    private static final String FIELD_USER_TICKET = "user_ticket";//观众票数
    private List<UserModel> list; //保存解析好的用户信息实体

    private List<String> list_fields; //字段列表
    private List<List<String>> list_data; //字段对应的数据列表

    private long time;
    private int count;
    private int has_next;
    private int watch_number; // 观看总人数
    private String short_url;

    //add
    private Map<String, Integer> mapFieldIndex = new HashMap<>();

    /**
     * 解析数据
     */
    public void parseData() {
        if (parseFields()) {
            this.list = parseUserData(list_data);
        }
    }

    /**
     * 解析字段，把字段在列表总的顺序位置保存下来
     *
     * @return
     */
    private boolean parseFields() {
        if (SDCollectionUtil.isEmpty(list_fields)) {
            return false;
        }

        for (int i = 0; i < list_fields.size(); i++) {
            String field = list_fields.get(i);
            if (FIELD_USER_ID.equals(field)) {
                mapFieldIndex.put(FIELD_USER_ID, i);
            } else if (FIELD_USER_LEVEL.equals(field)) {
                mapFieldIndex.put(FIELD_USER_LEVEL, i);
            } else if (FIELD_HEAD_IMAGE.equals(field)) {
                mapFieldIndex.put(FIELD_HEAD_IMAGE, i);
            } else if (FIELD_V_ICON.equals(field)) {
                mapFieldIndex.put(FIELD_V_ICON, i);
            } else if (FIELD_USER_TICKET.equals(field)) {
                mapFieldIndex.put(FIELD_USER_TICKET, i);
            }
        }
        if (mapFieldIndex.isEmpty()) {
            return false;
        }
        return true;
    }

    private List<UserModel> parseUserData(List<List<String>> listData) {
        if (SDCollectionUtil.isEmpty(listData)) {
            return null;
        }
        List<UserModel> listUser = new ArrayList<>();
        try {
            for (List<String> item : listData) {
                String userId = SDCollectionUtil.get(item, mapFieldIndex.get(FIELD_USER_ID));
                String userLevel = SDCollectionUtil.get(item, mapFieldIndex.get(FIELD_USER_LEVEL));
                String headImage = SDCollectionUtil.get(item, mapFieldIndex.get(FIELD_HEAD_IMAGE));
                String vIcon = SDCollectionUtil.get(item, mapFieldIndex.get(FIELD_V_ICON));
                String userTicket = SDCollectionUtil.get(item, mapFieldIndex.get(FIELD_USER_TICKET));
                if (!TextUtils.isEmpty(headImage) && !headImage.startsWith(DEFAULT_IMAGE_SCHEME)) {
                    headImage = short_url + headImage;
                }
                if (!TextUtils.isEmpty(vIcon) && !vIcon.startsWith(DEFAULT_IMAGE_SCHEME)) {
                    vIcon = short_url + vIcon;
                }

                UserModel model = new UserModel();
                model.setUser_id(userId);
                model.setUser_level(SDTypeParseUtil.getInt(userLevel));
                model.setHead_image(headImage);
                model.setV_icon(vIcon);
                model.setUser_ticket(userTicket);
                listUser.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listUser;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

    public List<List<String>> getList_data() {
        return list_data;
    }

    public void setList_data(List<List<String>> list_data) {
        this.list_data = list_data;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<String> getList_fields() {
        return list_fields;
    }

    public void setList_fields(List<String> list_fields) {
        this.list_fields = list_fields;
    }

    public List<UserModel> getList() {
        return list;
    }

    public void setList(List<UserModel> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getHas_next() {
        return has_next;
    }

    public void setHas_next(int has_next) {
        this.has_next = has_next;
    }

    public int getWatch_number() {
        return watch_number;
    }

    public void setWatch_number(int watch_number) {
        this.watch_number = watch_number;
    }

}
