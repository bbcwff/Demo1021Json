package example.demo1021json;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.demo1021json.adapter.NewsAdapter;
import example.demo1021json.entity.NewsEase;

public class MainActivity extends AppCompatActivity implements NewsAsyncTask.LoadNewsListener {

    @BindView(R.id.lv_main)
    ListView mLvMain;
    private String str = "http://c.m.163.com/nc/article/list/T1348647909107/0-20.html";
    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdapter = new NewsAdapter();
        mLvMain.setAdapter(mAdapter);

        getDataByUrl(str);
    }

    private void getDataByUrl(String url) {
        NewsAsyncTask task = new NewsAsyncTask();
        task.setListener(this);
        task.execute(url);
    }


    @Override
    public void sendNewsList(List<NewsEase> newsEases) {
       Toast.makeText(this, "共获得"+newsEases.size()+"个分类", Toast.LENGTH_SHORT).show();
        mAdapter.setList(newsEases);
        mAdapter.notifyDataSetChanged();
    }
}
