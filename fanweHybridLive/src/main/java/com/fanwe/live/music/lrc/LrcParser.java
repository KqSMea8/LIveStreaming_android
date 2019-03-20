package com.fanwe.live.music.lrc;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ldh on 2016/6/16.
 */
public class LrcParser {
    public static final String TAG = "LRC";
    //存放临时时间
    private long currentTime = 0 ;
    //存放临时歌词
    private String currentContent=null;
    //用于保存时间点和歌词之间的对应关系
    //private Map<Long,String>maps =new HashMap<Long,String>();
    private Map<Long,String> maps=new TreeMap<Long,String>();

    private LrcInfo lrcinfo;

    public static LrcInfo parser(String path)throws Exception{
        String value = getFromPath(path);
        return getLrcInfo(value);

    }

    public static List<LrcRow> parseLrcRows(String path) {
    	if (TextUtils.isEmpty(path)) {
			return null;
		}
        try {
            LrcInfo lrcInfo = parser(path);
            return lrcInfo.getRows();
        }catch (Exception e){

        }
        return null;
    }
    
    public static List<LrcRow> parseRowsByLrc(String lrc) {
		LrcInfo lrcInfo = getLrcInfo(lrc);
		if (lrcInfo != null) {
			return lrcInfo.getRows();
		}
		return null;
	}

    protected static String getFromPath(String path){
        try {
            File f= new File(path);
            InputStream ins = new FileInputStream(f);
            InputStreamReader inputReader = new InputStreamReader(ins);
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String result="";
            while((line = bufReader.readLine()) != null){
                if(line.trim().equals(""))
                    continue;
                result += line + "\r\n";
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    protected static LrcInfo getLrcInfo(String rawLrc) {
        Log.d(TAG,"getLrcRows by rawString");
        if(rawLrc == null || rawLrc.length() == 0){
            Log.e(TAG,"getLrcRows rawLrc null or empty");
            return null;
        }
        StringReader reader = new StringReader(rawLrc);
        BufferedReader br = new BufferedReader(reader);
        LrcInfo lrcInfo = new LrcInfo();
        String line = null;
        List<LrcRow> rows = new ArrayList<LrcRow>();
        try{
            //循环地读取歌词的每一行
            do{
                line = br.readLine();
                /**
                 一行歌词只有一个时间的  例如：徐佳莹   《我好想你》
                 [01:15.33]我好想你 好想你

                 一行歌词有多个时间的  例如：草蜢 《失恋战线联盟》
                 [02:34.14][01:07.00]当你我不小心又想起她
                 [02:45.69][02:42.20][02:37.69][01:10.60]就在记忆里画一个叉
                 **/
                Log.d(TAG,"lrc raw line: " + line);
                if (TextUtils.isEmpty(line)){
                    continue;
                }
                //获取歌曲名信息
                if(line.startsWith("[ti:")){
                    String title =line.substring(4,line.length()-1);
                    Log.i("","title-->"+title);
                    lrcInfo.setTitle(title);
                }
                //取得歌手信息
                else if(line.startsWith("[ar:")){
                    String artist = line.substring(4, line.length()-1);
                    Log.i("","artist-->"+artist);
                    lrcInfo.setArtist(artist);
                }
                //取得专辑信息
                else if(line.startsWith("[al:")){
                    String album =line.substring(4, line.length()-1);
                    Log.i("","album-->"+album);
                    lrcInfo.setAlbum(album);
                }
                //取得歌词制作者
                else if(line.startsWith("[by:")){
                    String bysomebody=line.substring(4, line.length()-1);
                    Log.i("","by-->"+bysomebody);
                    lrcInfo.setBySomeBody(bysomebody);
                }else {
                    List<LrcRow> lrcRows = LrcRow.createRows(line);
                    if(lrcRows != null && lrcRows.size() > 0){
                        for(LrcRow row : lrcRows){
                            rows.add(row);
                        }
                    }
                }

            }while(line != null);

            if( rows.size() > 0 ){
                // 根据歌词行的时间排序
                Collections.sort(rows);
                if(rows!=null&&rows.size()>0){

                    for (int i=0; i<rows.size(); i++){
                        LrcRow row = rows.get(i);
                        Log.d(TAG, "lrcRow:" + row.toString());
                        if (i == rows.size() - 1){
                            row.endTime = row.time+1;
                        }else {
                            row.endTime = rows.get(i+1).time - 1;
                        }
                    }
                }
            }
            lrcInfo.setRows(rows);
        }catch(Exception e){
            Log.e(TAG,"parse exceptioned:" + e.getMessage());
            return null;
        }finally{
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            reader.close();
        }
        return lrcInfo;
    }


    /**
     * 利用正则表达式解析每行具体语句
     * 并将解析完的信息保存到LrcInfo对象中
     * @param line
     */
    private void parserLine(String line) {
        //获取歌曲名信息
        if(line.startsWith("[ti:")){
            String title =line.substring(4,line.length()-1);
            lrcinfo.setTitle(title);
        }
        //取得歌手信息
        else if(line.startsWith("[ar:")){
            String artist = line.substring(4, line.length()-1);
            lrcinfo.setArtist(artist);
        }
        //取得专辑信息
        else if(line.startsWith("[al:")){
            String album =line.substring(4, line.length()-1);
            lrcinfo.setAlbum(album);
        }
        //取得歌词制作者
        else if(line.startsWith("[by:")){
            String bysomebody=line.substring(4, line.length()-1);
            Log.i("","by-->"+bysomebody);
            lrcinfo.setBySomeBody(bysomebody);
        }
        //通过正则表达式取得每句歌词信息
        else{
            //设置正则表达式
            String reg ="\\[(\\d{1,2}:\\d{1,2}\\.\\d{1,2})\\]|\\[(\\d{1,2}:\\d{1,2})\\]";
            Pattern pattern = Pattern.compile(reg);


            Matcher matcher=pattern.matcher(line);
            //如果存在匹配项则执行如下操作
            while(matcher.find()){
                //得到匹配的内容
                String msg=matcher.group();
                //得到这个匹配项开始的索引
                int start = matcher.start();
                //得到这个匹配项结束的索引
                int end = matcher.end();
                //得到这个匹配项中的数组
                int groupCount = matcher.groupCount();
                for(int index =0;index<groupCount;index++){
                    String timeStr = matcher.group(index);
                    Log.i("","time["+index+"]="+timeStr);
                    if(index==0){
                        //将第二组中的内容设置为当前的一个时间点
                        currentTime=str2Long(timeStr.substring(1, timeStr.length()-1));
                    }
                }
                //得到时间点后的内容
                String[] content = pattern.split(line);
                //for(int index =0; index<content.length; index++){
//                Log.i("","content="+content[content.length-1]);
                //if(index==content.length-1){
                //将内容设置魏当前内容
                currentContent = content[content.length-1];
                //}
                //}
                //设置时间点和内容的映射
                maps.put(currentTime, currentContent);
//                Log.i("","currentTime--->"+currentTime+"   currentContent--->"+currentContent);
                //遍历map
            }
        }
    }
    private long str2Long(String timeStr){
        //将时间格式为xx:xx.xx，返回的long要求以毫秒为单位
        Log.i("","timeStr="+timeStr);
        String[] s = timeStr.split("\\:");
        int min = Integer.parseInt(s[0]);
        int sec=0;
        int mill=0;
        if(s[1].contains(".")){
            String[] ss=s[1].split("\\.");
            sec =Integer.parseInt(ss[0]);
            mill=Integer.parseInt(ss[1]);
            Log.i("","s[0]="+s[0]+"s[1]"+s[1]+"ss[0]="+ss[0]+"ss[1]="+ss[1]);
        }else{
            sec=Integer.parseInt(s[1]);
            Log.i("","s[0]="+s[0]+"s[1]"+s[1]);
        }
        return min*60*1000+sec*1000+mill*10;
    }
}
