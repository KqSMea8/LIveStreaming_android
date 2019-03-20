package com.fanwe.live.common;

import android.content.Context;
import android.text.TextUtils;

import com.fanwe.hybrid.app.App;
import com.fanwe.hybrid.constant.ApkConstant;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.http.AppHttpUtil;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.http.AppRequestParams;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.listener.SDResultCallback;
import com.fanwe.library.receiver.SDNetworkReceiver;
import com.fanwe.library.utils.IOUtil;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.live.R;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 域名管理类
 */
public class HostManager
{

    private static final int DURATION_RETRY = 5 * 1000;

    private static HostManager sInstance;

    private HostModel hostModel;
    private LinkedList<String> listTemp = new LinkedList<>();

    private boolean isFinding = false;
    private long nextRetryTime;

    private int hasPathUrlCount;

    private HostManager()
    {
        hostModel = HostModel.get();
    }

    public static HostManager getInstance()
    {
        if (sInstance == null)
        {
            synchronized (HostManager.class)
            {
                if (sInstance == null)
                {
                    sInstance = new HostManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获得接口地址
     *
     * @return
     */
    public String getApiUrl()
    {
        return hostModel.getApiUrl();
    }

    /**
     * 设置接口地址
     *
     * @param apiUrl
     */
    public void setApiUrl(String apiUrl)
    {
        hostModel.setApiUrl(apiUrl);
        hostModel.save();
    }

    /**
     * 保存接口返回的域名列表
     */
    public void saveActHost()
    {
        hostModel.saveActHost();
    }

    /**
     * 设置下次可以重试的时间
     *
     * @param duration 距离下次可以重试的间隔
     */
    private synchronized void setNextRetryTime(long duration)
    {
        if (duration <= 0)
        {
            duration = DURATION_RETRY;
        }
        nextRetryTime = System.currentTimeMillis() + duration;
    }

    /**
     * 设置是否正在查找中
     *
     * @param finding
     */
    private synchronized void setFinding(boolean finding)
    {
        isFinding = finding;
    }

    /**
     * 查找可用的域名
     */
    public synchronized void findAvailableHost()
    {
        if (isFinding)
        {
            LogUtil.i("isFinding");
            return;
        }
        if (System.currentTimeMillis() < nextRetryTime)
        {
            LogUtil.i("waiting next retry");
            return;
        }
        if (!SDNetworkReceiver.isNetworkConnected(App.getApplication()))
        {
            LogUtil.i("network is not connected");
            return;
        }

        setFinding(true);
        fillListTemp();
        findAvailableHost(new SDResultCallback<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                LogUtil.i("find success " + result);

                //设置并保存接口地址
                setApiUrl(result + ApkConstant.SERVER_URL_PATH_API);

                setFinding(false);
                hasPathUrlCount = 0;
                setNextRetryTime(DURATION_RETRY);
            }

            @Override
            public void onError(int code, String msg)
            {
                LogUtil.i("find error " + msg);

                setFinding(false);
                hasPathUrlCount = 0;
                setNextRetryTime(DURATION_RETRY);
            }
        });
        LogUtil.i("start find----------------");
    }

    /**
     * 把域名填充到临时列表
     */
    private void fillListTemp()
    {
        listTemp.clear();
        listTemp.addAll(hostModel.getList());
    }

    /**
     * 找到可用的域名
     *
     * @param callback
     */
    private void findAvailableHost(final SDResultCallback<String> callback)
    {
        if (!SDNetworkReceiver.isNetworkConnected(App.getApplication()))
        {
            callback.onError(-1, "no network");
            return;
        }
        if (listTemp.isEmpty())
        {
            callback.onError(-1, "listTemp is emtpy");
            return;
        }

        final String host = listTemp.pollFirst();
        LogUtil.i("trying host:" + host);
        boolean hasPath = false;
        try
        {
            URL url = new URL(host);
            String path = url.getPath();
            hasPath = !TextUtils.isEmpty(path);
        } catch (Exception e)
        {
            callback.onError(-1, String.valueOf(e));
        }

        AppRequestParams params = new AppRequestParams();
        if (hasPath)
        {
            // 打开新的地址获取域名
            hasPathUrlCount++;
            if (hasPathUrlCount > 1)
            {
                callback.onError(-1, "hasPathUrlCount overflow:" + hasPathUrlCount);
                return;
            }
            params.setUrl(host);
            AppHttpUtil.getInstance().get(params, new AppRequestCallback<InitActModel>()
            {
                @Override
                protected void onSuccess(SDResponse sdResponse)
                {
                    if (actModel != null)
                    {
                        if (hostModel.saveHost(actModel.getDomain_list()))
                        {
                            fillListTemp();
                            findAvailableHost(callback);
                        } else
                        {
                            findAvailableHost(callback);
                        }
                    } else
                    {
                        findAvailableHost(callback);
                    }
                }

                @Override
                protected void onError(SDResponse resp)
                {
                    super.onError(resp);
                    findAvailableHost(callback);
                }
            });
        } else
        {
            params.setUrl(host + ApkConstant.SERVER_URL_PATH_API);
            params.putCtl("app");
            params.putAct("init");
            AppHttpUtil.getInstance().post(params, new AppRequestCallback<InitActModel>()
            {
                @Override
                protected void onSuccess(SDResponse sdResponse)
                {
                    if (actModel != null)
                    {
                        callback.onSuccess(host);
                    } else
                    {
                        findAvailableHost(callback);
                    }
                }

                @Override
                protected void onError(SDResponse resp)
                {
                    super.onError(resp);
                    findAvailableHost(callback);
                }
            });
        }
    }

    private static class HostModel
    {
        private static final String FILE_NAME = "domains";

        private LinkedList<String> list = new LinkedList<>();
        private String apiUrl;
        private int versionCode;

        public void setVersionCode(int versionCode)
        {
            this.versionCode = versionCode;
        }

        public int getVersionCode()
        {
            return versionCode;
        }

        public LinkedList<String> getList()
        {
            return list;
        }

        public void setList(LinkedList<String> list)
        {
            this.list = list;
        }

        public String getApiUrl()
        {
            return apiUrl;
        }

        public void setApiUrl(String apiUrl)
        {
            this.apiUrl = apiUrl;
        }

        /**
         * 加载打包配置的域名
         */
        public void saveLocalHost()
        {
            String[] arrHost = App.getApplication().getResources().getStringArray(R.array.arr_host);
            if (arrHost != null && arrHost.length > 0)
            {
                if (saveHost(Arrays.asList(arrHost)))
                {
                    LogUtil.i("saveLocalHost success");
                }
            }
        }

        /**
         * 把接口返回的域名保存到本地
         */
        public void saveActHost()
        {
            InitActModel actModel = InitActModelDao.query();
            if (actModel != null)
            {
                if (saveHost(actModel.getDomain_list()))
                {
                    LogUtil.i("saveActHost success");
                }
            }
        }

        /**
         * 保存域名到本地
         *
         * @param listHost
         */
        public boolean saveHost(List<String> listHost)
        {
            if (listHost != null && !listHost.isEmpty())
            {
                this.list.clear();
                this.list.addAll(listHost);
                LogUtil.i("saveHost:" + this.list.toString());
                save();
                return true;
            } else
            {
                return false;
            }
        }

        public boolean isEmpty()
        {
            return this.list.isEmpty();
        }

        public static HostModel get()
        {
            try
            {
                boolean needSave = false;

                HostModel model = query();
                if (model == null)
                {
                    model = new HostModel();
                    needSave = true;
                }

                if (SDPackageUtil.getVersionCode() > model.getVersionCode())
                {
                    //app升级或者第一次安装
                    model.setApiUrl(ApkConstant.SERVER_URL_API);
                    model.setVersionCode(SDPackageUtil.getVersionCode());
                    model.saveLocalHost();
                    needSave = true;
                }

                if (model.isEmpty())
                {
                    model.saveLocalHost();
                    needSave = true;
                }

                if (needSave)
                {
                    model.save();
                }
                return model;
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 查找本地对象
         *
         * @return
         */
        private static HostModel query()
        {
            InputStream is = null;
            try
            {
                is = App.getApplication().openFileInput(FILE_NAME);
                String content = IOUtil.readStr(is);
                if (!TextUtils.isEmpty(content))
                {
                    HostModel model = SDJsonUtil.json2Object(content, HostModel.class);
                    LogUtil.i("query model success");
                    return model;
                } else
                {
                    LogUtil.i("query model empty");
                }
            } catch (Exception e)
            {
                e.printStackTrace();
                LogUtil.i("query model error:" + e.toString());
            } finally
            {
                IOUtil.closeQuietly(is);
            }
            return null;
        }

        /**
         * 保存对象到本地
         */
        private void save()
        {
            OutputStream os = null;
            try
            {
                String content = SDJsonUtil.object2Json(this);
                os = App.getApplication().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                IOUtil.writeStr(os, content);
                LogUtil.i("save model success");
            } catch (Exception e)
            {
                e.printStackTrace();
                LogUtil.i("save model error:" + e.toString());
            } finally
            {
                IOUtil.closeQuietly(os);
            }
        }
    }

}
