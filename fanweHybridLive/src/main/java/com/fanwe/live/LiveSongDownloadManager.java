package com.fanwe.live;

import android.text.TextUtils;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.span.utils.MD5Util;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.event.ELiveSongDownload;
import com.fanwe.live.model.LiveSongModel;

import org.xutils.common.Callback;
import org.xutils.common.util.FileUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class LiveSongDownloadManager
{
    private static int MAX_TASK = 5;
    private List<LiveSongModel> mDownList;
    private static LiveSongDownloadManager sManager;
    private File mCacheDir, mCacheLrcDir;
    private List<LiveSongModel> mTaskList;

    public LiveSongDownloadManager()
    {
        mDownList = new ArrayList<LiveSongModel>();
        mTaskList = new ArrayList<LiveSongModel>();
        initCacheDir();
    }

    protected void initCacheDir()
    {
        mCacheDir = FileUtil.getCacheDir("song");
        mCacheLrcDir = FileUtil.getCacheDir("lrc");
    }

    public static LiveSongDownloadManager getInstance()
    {
        if (sManager == null)
        {
            sManager = new LiveSongDownloadManager();
        }
        return sManager;
    }

    public void addTask(LiveSongModel model)
    {
        if (model == null || TextUtils.isEmpty(model.getAudio_id()))
        {
            return;
        }
        statusDownloading(model, 0);
        if (!isExistTask(model))
        {
            mDownList.add(model);
        }

        startTask();
    }

    protected boolean isExistTask(LiveSongModel model)
    {
        for (int i = 0; i < mDownList.size(); i++)
        {
            if (mDownList.get(i).getAudio_id() == model.getAudio_id())
            {
                return true;
            }
        }
        for (int i = 0; i < mTaskList.size(); i++)
        {
            if (mTaskList.get(i).getAudio_id() == model.getAudio_id())
            {
                return true;
            }
        }
        return false;
    }

    protected void startTask()
    {
        if (mCacheDir == null || SDCollectionUtil.isEmpty(mDownList))
        {
            return;
        }

        if (mTaskList.size() >= MAX_TASK)
        {
            return;
        }

        final LiveSongModel item = mDownList.remove(0);
        mTaskList.add(item);
        startDownload(item);
    }

    private void startDownload(final LiveSongModel item)
    {
        String path = getSongFilePath(item);

        try
        {
            File xx = new File(path);
            if (xx.exists())
            {
                downloadSongOK(item);
                return;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        String downloadUrl = item.getAudio_link();
//		downloadUrl = "https://codeload.github.com/hongyangAndroid/Android-ProgressBarWidthNumber/zip/master";
        RequestParams params = new RequestParams(downloadUrl);
        params.setSaveFilePath(path);
        params.setAutoRename(false);
        params.setAutoResume(false);

        x.http().get(params, new Callback.ProgressCallback<File>()
        {

            @Override
            public void onCancelled(CancelledException arg0)
            {
            }

            @Override
            public void onError(Throwable arg0, boolean arg1)
            {
                statusDownloadErr(item);
            }

            @Override
            public void onFinished()
            {
                mTaskList.remove(item);
                startTask();
            }

            @Override
            public void onSuccess(File file)
            {
                if (file != null)
                {
                    downloadSongOK(item);
                } else
                {
                    statusDownloadErr(item);
                }
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading)
            {
                int progress = (int) ((current * 100) / (total));
                statusDownloading(item, progress);
            }

            @Override
            public void onStarted()
            {
                statusDownloading(item, 0);
            }

            @Override
            public void onWaiting()
            {
            }

        });
    }

    protected void downloadSongOK(LiveSongModel item)
    {
        if (item == null)
        {
            return;
        }
        statusDownloading(item, 100);
        addUserSong(item);
        saveLrc(item);
//		if (!TextUtils.isEmpty(item.getLrc_link())) {
//			downloadLrc(item);
//		}else {
//			
//		}
    }

    protected void saveLrc(LiveSongModel model)
    {
        if (TextUtils.isEmpty(model.getLrc_content()))
        {
            return;
        }
        String path = getLrcFilePath(model);
        if (TextUtils.isEmpty(path))
        {
            return;
        }
        File xxFile = new File(path);
        if (xxFile.exists())
        {
            return;
        }
        FileOutputStream fout = null;
        try
        {
            fout = new FileOutputStream(path);
            byte[] bytes = model.getLrc_content().getBytes();
            fout.write(bytes);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (fout != null)
            {
                try
                {
                    fout.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

    }

    public void downloadLrc(final LiveSongModel item)
    {
        String path = getLrcFilePath(item);

        try
        {
            File xx = new File(path);
            if (xx.exists())
            {
//				xx.delete();
                return;
            }
        } catch (Exception e)
        {

        }
        RequestParams params = new RequestParams(item.getLrc_link());
        params.setSaveFilePath(path);
        params.setAutoRename(false);
        params.setAutoResume(false);

        x.http().get(params, new Callback.ProgressCallback<File>()
        {

            @Override
            public void onSuccess(File result)
            {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback)
            {
            }

            @Override
            public void onCancelled(CancelledException cex)
            {

            }

            @Override
            public void onFinished()
            {

            }

            @Override
            public void onWaiting()
            {

            }

            @Override
            public void onStarted()
            {
            }

            @Override
            public void onLoading(long total, long current,
                                  boolean isDownloading)
            {


            }

        });
    }

    public void addUserSongSilent(LiveSongModel model)
    {
        CommonInterface.requestMusic_add_music(
                model.getAudio_id(),
                model.getAudio_name(),
                model.getAudio_link(),
                model.getLrc_link(),
                model.getLrc_content(),
                model.getArtist_name(),
                model.getTime_len(), new AppRequestCallback<BaseActModel>()
                {

                    @Override
                    protected void onSuccess(SDResponse resp)
                    {

                    }
                });
    }

    protected void addUserSong(final LiveSongModel model)
    {
        if (model.isCached())
        {
            statusDownloadSucc(model);
            return;
        }
        CommonInterface.requestMusic_add_music(
                model.getAudio_id(),
                model.getAudio_name(),
                model.getAudio_link(),
                model.getLrc_link(),
                model.getLrc_content(),
                model.getArtist_name(),
                model.getTime_len(), new AppRequestCallback<BaseActModel>()
                {

                    @Override
                    protected void onSuccess(SDResponse resp)
                    {
                        if (actModel.isOk())
                        {
                            statusDownloadSucc(model);
                        } else
                        {
                            statusDownloadErr(model);
                        }
                    }
                });
    }

    public String getSongFilePath(LiveSongModel model)
    {
        if (model == null || TextUtils.isEmpty(model.getAudio_id()))
        {
            return null;
        }
        String url = model.getAudio_link();
        if (TextUtils.isEmpty(url))
        {
            return null;
        }
        String ext = ".mp3";
        if (url.contains("."))
        {
            if (url.contains("?"))
            {
                url = url.substring(0, url.lastIndexOf("?"));
            }
            ext = url.substring(url.lastIndexOf("."));
        }

        String path = mCacheDir.getAbsolutePath() + File.separator + MD5Util.MD5("FFFFFF" + model.getAudio_id()) + ext;

        return path;
    }

    public String getLrcFilePath(LiveSongModel model)
    {
        if (model == null || TextUtils.isEmpty(model.getAudio_id()))
        {
            return null;
        }
        return mCacheLrcDir.getAbsolutePath() + File.separator + MD5Util.MD5("FFFFFF" + model.getAudio_id());
    }

    public boolean isFileCached(LiveSongModel model)
    {
        if (model == null || TextUtils.isEmpty(model.getAudio_id()))
        {
            return false;
        }
        String pathString = getSongFilePath(model);
        if (TextUtils.isEmpty(pathString))
        {
            return false;
        }
        File file = new File(pathString);
        return file.exists();
    }

    protected void statusDownloading(LiveSongModel model, int progress)
    {
        model.setStatus(1);
        model.setProgress(progress);
        EventBus.getDefault().post(new ELiveSongDownload(model));
    }

    protected void statusDownloadErr(LiveSongModel model)
    {
        model.setStatus(-1);
        model.setProgress(0);
        EventBus.getDefault().post(new ELiveSongDownload(model));
    }

    protected void statusDownloadSucc(LiveSongModel model)
    {
        model.setStatus(2);
        model.setProgress(100);
        model.setCached(model.isMusicExist());
        EventBus.getDefault().post(new ELiveSongDownload(model));

    }
}
