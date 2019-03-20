package com.fanwe.hybrid.dialog;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.hybrid.app.App;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveCreateRoomActivity;
import com.fanwe.live.activity.LiveCreaterAgreementActivity;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.UserModel;
import com.fanwe.shortvideo.videorecord.TCVideoRecordActivity;


/**
 * Created by wxy on 2018/1/27.
 */

public class SelectLiveVideoPopupView extends BasePopupBottomView implements View.OnClickListener {

    private GridView mGridView;
    private ImageView btn_popu_cancel;
    private Context activity;

    public SelectLiveVideoPopupView(Activity activity) {
        super(activity);
        // TODO Auto-generated constructor stub
        View view = mInflater.inflate(R.layout.acatar_select_livevideo_popuoview, null);
        this.activity = activity;
        initPopupView(view);
        register(view);
    }

    private void register(View view) {
        btn_popu_cancel = (ImageView) view.findViewById(R.id.btn_popu_cancel);
        startPropertyAnim(btn_popu_cancel,1);
        btn_popu_cancel.setOnClickListener(this);

        mGridView = (GridView) view.findViewById(R.id.select_popu_gridview);
        Integer[] images = {R.drawable.tabbar_compose_shooting, R.drawable.tabbar_compose_camera};
        String[] titles = {"发起直播", "发小视频"};
        PictureAdapter adapter = new PictureAdapter(images,titles, activity);
        mGridView.setAnimation(AnimationUtils.loadAnimation(App.getApplication(), R.anim.gridview_item_anim));
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final UserModel userModel = UserModelDao.query();
                if (userModel.getIs_agree() == 1) {
                    if(position==0) {
                        Intent intent = new Intent(activity, LiveCreateRoomActivity.class);
                        activity.startActivity(intent);
                    }else {
                        Intent intent = new Intent(activity, TCVideoRecordActivity.class);
                        activity.startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(activity, LiveCreaterAgreementActivity.class);
                    activity.startActivity(intent);
                }
                popViewDismiss();

            }
        });
    }

    public void popViewDismiss() {
        startPropertyAnim(btn_popu_cancel,0);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dismiss();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    ObjectAnimator anim;

    // 动画实际执行
    private void startPropertyAnim(View v,int type) {
        // 第二个参数"rotation"表明要执行旋转
        // 0f -> 360f，从旋转360度，也可以是负值，负值即为逆时针旋转，正值是顺时针旋转。
        if(type==1){
            anim = ObjectAnimator.ofFloat(v, "rotation", 0f, 180f);
        }else{
            anim = ObjectAnimator.ofFloat(v, "rotation", 180f, 360f);
        }

        // 动画的持续时间，执行多久？
        anim.setDuration(500);

        // 正式开始启动执行动画
        anim.start();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_popu_cancel:
                popViewDismiss();
                break;

        }

    }

    private class PictureAdapter extends BaseAdapter {
        private Context context;

        private Integer[] pictures = new Integer[2];
        private String[] titles=new String[2];
        public PictureAdapter(Integer[] images,String[] titles, Context context) {
            super();
            this.context = context;
            this.pictures = images;
            this.titles=titles;
        }

        @Override
        public int getCount() {

            if (null != pictures) {
                return pictures.length;
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return pictures[position];
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;

            if (convertView == null) {

                viewHolder = new ViewHolder();
                // 获得容器
                convertView = LayoutInflater.from(this.context).inflate(R.layout.item_select_livevideo, null);

                // 初始化组件
                viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
                viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                // 给converHolder附加一个对象
                convertView.setTag(viewHolder);
            } else {
                // 取得converHolder附加的对象
                viewHolder = (ViewHolder) convertView.getTag();
            }

            // 给组件设置资源
            viewHolder.image.setImageResource(pictures[position]);
            viewHolder.title.setText(titles[position]);
            return convertView;
        }

        class ViewHolder {
            public TextView title;
            public ImageView image;
        }
    }
}
