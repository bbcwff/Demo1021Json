package example.demo1021json;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import example.demo1021json.entity.NewsEase;

/**
 * Created by Administrator on 2016/10/21.
 */

public class NewsAsyncTask extends AsyncTask<String,Void,List<NewsEase>> {

    @Override
    protected List<NewsEase> doInBackground(String... params) {
        String str=params[0];
        List<NewsEase> newsEaseList=new ArrayList<NewsEase>();
        try {
            URL url=new URL(str);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            if(con.getResponseCode()==200){
                InputStream is=con.getInputStream();
                ByteArrayOutputStream byos=new ByteArrayOutputStream();
                byte[] b=new byte[1024];
                int len=0;
                while ((len=is.read(b))!=-1){
                    byos.write(b,0,len);
                }

                String result=new String(byos.toByteArray());
                //利用Gson的jar包，处理获得的json字符串
                Gson gson=new Gson();
                JSONObject jsonObject=new JSONObject(result);
                Log.d("TAG1", "doInBackground: "+result);
                JSONArray jsonArray=jsonObject.getJSONArray("T1348647909107");
                for(int i=0;i<jsonArray.length();i++){
                    //参数分别为json对象的属性名，类
                    NewsEase newsEase=gson.fromJson(jsonArray.get(i).toString(),NewsEase.class);
                    Log.d("--------", newsEase.toString());
                    newsEaseList.add(newsEase);
                }
            }
        }catch (Exception e){

        }
        return newsEaseList;
    }

    //执行结束后，将数据发给activity，用借口来实现监听
    @Override
    protected void onPostExecute(List<NewsEase> newsEases) {
        if (listener!=null){
            listener.sendNewsList(newsEases);
        }
    }

    //内部接口实现向UI界面发送集合
    private LoadNewsListener listener;

    public void setListener(LoadNewsListener listener) {
        this.listener = listener;
    }

    public interface  LoadNewsListener{
        void sendNewsList(List<NewsEase> newsEases);
    }
}
