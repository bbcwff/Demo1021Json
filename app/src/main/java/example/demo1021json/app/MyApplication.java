package example.demo1021json.app;

import android.app.Application;

/**
 * Created by Administrator on 2016/10/23.
 */

public class MyApplication extends Application {
    private static MyApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
    }

    public static MyApplication getApp() {
        return app;
    }
}
