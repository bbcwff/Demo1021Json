package example.demo1021json.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import example.demo1021json.app.MyApplication;

/**
 * Created by Administrator on 2016/10/23.
 */

public class ImageLoader {
    private final LruCache<String, Bitmap> imageCache;
    private final String path;

    public ImageLoader() {
        imageCache=new LruCache<String,Bitmap>(5*1024*1024){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        //自定义application类要到清单注册
        path= Environment.getDataDirectory()+"/data/"+ MyApplication.getApp().getPackageName()+"/imageCache/";
        //目录初始化
        File file=new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    public void setImageUrl(ImageView ivImage,String imgsrc){
        Bitmap bitmap=null;
        //从内存获取bitmap
        bitmap= getBitmapFromMemory(imgsrc);
        if (bitmap!=null){
            ivImage.setImageBitmap(bitmap);
            return;
        }else{
            //从磁盘文件中获取
            bitmap=getBitmapFromDisk(imgsrc);
            if (bitmap!=null){
                ivImage.setImageBitmap(bitmap);
                //如果从文件中获取到了，就将其加入内存
                saveBitmapToMemory(imgsrc,bitmap);
                return;
            }
        }
        //1内存也没有，存储空间也没有，只能去网络获取
        //开线程，访问网络，得到流，解析出图片，设置
        LoadImgTask task=new LoadImgTask(ivImage);
        task.execute();
    }

    //将bitmap保存到内存
    private void saveBitmapToMemory(String imgsrc, Bitmap bitmap) {
        String key=getKeyFromImgsrc(imgsrc);
        imageCache.put(key,bitmap);
    }

    //获得缓存的键
    private String getKeyFromImgsrc(String imgsrc) {
        return imgsrc.substring(imgsrc.lastIndexOf("/")+1);
    }

    private Bitmap getBitmapFromDisk(String imgsrc) {
        String fileName=getKeyFromImgsrc(imgsrc);
        return BitmapFactory.decodeFile(path+fileName);
    }

    private Bitmap getBitmapFromMemory(String imgsrc) {
        String key=getKeyFromImgsrc(imgsrc);
        return imageCache.get(key);
    }

    private class LoadImgTask extends AsyncTask<String,Void,Bitmap>{
        private ImageView ivImage;
        private String imgsrc;

        public LoadImgTask(ImageView ivImage) {
            this.ivImage = ivImage;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            imgsrc=params[0];
            Bitmap bitmap=null;
            try {
                URL url=new URL(imgsrc);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                    bitmap=BitmapFactory.decodeStream(urlConnection.getInputStream());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap!=null){
                ivImage.setImageBitmap(bitmap);
                //先保存到文件中
                //再保存到内存中
                saveBitmapToDisk(imgsrc,bitmap);
            }
        }
    }

    private void saveBitmapToDisk(String imgsrc, Bitmap bitmap) {
        String fileName=getKeyFromImgsrc(imgsrc);
        File file=new File(fileName);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream fos=null;
        try {
            fos= new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
    }

}
