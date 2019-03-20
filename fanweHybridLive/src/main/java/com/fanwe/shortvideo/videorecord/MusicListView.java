package com.fanwe.shortvideo.videorecord;

import android.content.Context;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.shortvideo.model.MusicDownloadModel;
import com.fanwe.shortvideo.model.MusicListModel;

import org.xutils.common.Callback;
import org.xutils.common.util.FileUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Link on 2016/9/12.
 */
public class MusicListView extends ListView {

    private MusicListAdapter adapter;

    @Override
    public MusicListAdapter getAdapter() {
        return adapter;
    }

    public MusicListView(Context context) {
        super(context);
        init(context);
    }

    public MusicListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.setChoiceMode(CHOICE_MODE_SINGLE);

    }

    public void setupList(LayoutInflater inflater) {
        adapter = new MusicListAdapter(inflater, new ArrayList<TCAudioControl.MediaEntity>());
        setAdapter(adapter);
    }

    static public class ViewHolder {
        ImageView selected;
        TextView name;
        TextView duration;
        TextView tv_download;
    }

}


class MusicListAdapter extends BaseAdapter {
    private Context mContext;
    List<TCAudioControl.MediaEntity> mData = null;
    private LayoutInflater mInflater;
    private ItemSelectListener mItemSelectListener;


    MusicListAdapter(LayoutInflater inflater, List<TCAudioControl.MediaEntity> list) {
        mInflater = inflater;
        mData = list;
    }

    public void setData(List<TCAudioControl.MediaEntity> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MusicListView.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.audio_ctrl_music_item, null);
            holder = new MusicListView.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.xml_music_item_name);
            holder.duration = (TextView) convertView.findViewById(R.id.xml_music_item_duration);
            holder.tv_download = (TextView) convertView.findViewById(R.id.tv_download);
//            holder.selected = (ImageView) convertView.findViewById(R.id.music_item_selected);
            convertView.setTag(holder);
        } else {
            holder = (MusicListView.ViewHolder) convertView.getTag();
        }
        holder.name.setText(mData.get(position).title);
        holder.duration.setText(mData.get(position).singer);
        holder.tv_download.setText(mData.get(position).isDownLoad == 1 ? "选择" : "下载");
//        holder.selected.setVisibility(mData.get(position).state == 1 ? View.VISIBLE : View.GONE);
        holder.tv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TCAudioControl.MediaEntity entity=mData.get(position);
                if (entity.isDownLoad == 1) {
                    mItemSelectListener.OnItemSelected(position,entity.title,entity.path);
                } else {
                    requestData(position);
                }
            }
        });
        return convertView;
    }
    public void setOnItemSelecetListener(ItemSelectListener listener) {
        mItemSelectListener = listener;
    }

    public interface ItemSelectListener {
        void OnItemSelected(int position,String title,String path);
    }
    /**
     * 是否正在下载中
     */
    private static boolean isDownloading;

    /**
     * 开始下载
     */
    private void startDownload(MusicDownloadModel.SongModel songModel) {
        isDownloading = true;

        File dir = null;
        String dirName = x.app().getPackageName();
        if (FileUtil.existsSdcard()) {
            dir = new File(Environment.getExternalStorageDirectory(), dirName);
        } else {
            dir = new File(Environment.getDataDirectory(), dirName);
        }
        String path = dir.getAbsolutePath() + File.separator + songModel.songName + ".mp3";
        RequestParams params = new RequestParams(songModel.songLink);
        params.setSaveFilePath(path);
        params.setAutoRename(false);
        params.setAutoResume(false);

        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onError(Throwable arg0, boolean arg1) {
                isDownloading = false;
                SDToast.showToast("下载失败");
            }

            @Override
            public void onCancelled(CancelledException e) {
                isDownloading = false;
            }

            @Override
            public void onFinished() {
                isDownloading = false;
            }

            @Override
            public void onSuccess(File file) {
                isDownloading = false;
                SDToast.showToast("下载完成");
                notifyDataSetChanged();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                int progress = (int) ((current * 100) / (total));
            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
            }
        });
    }

    private void requestData(final int position) {
        CommonInterface.requestDownLoadMusicPath(mData.get(position).id, new AppRequestCallback<MusicDownloadModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel != null && actModel.data != null) {
                    if (actModel.data.songList != null && actModel.data.songList.size() != 0) {
                        MusicDownloadModel.SongModel model=actModel.data.songList.get(0);
                        startDownload(model);
                        mData.get(position).path=model.songLink;
                        mData.get(position).isDownLoad=1;
                    }
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
            }
        });
    }

}
