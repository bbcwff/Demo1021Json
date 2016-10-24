package example.demo1021json.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import example.demo1021json.R;
import example.demo1021json.base.BaseHolder;
import example.demo1021json.base.MyBaseAdapter;
import example.demo1021json.entity.NewsEase;
import example.demo1021json.utils.ImageLoader;

/**
 * Created by Administrator on 2016/10/21.
 */

public class NewsAdapter extends MyBaseAdapter<NewsEase> {
    private ImageLoader mLoader;

    public NewsAdapter() {
        mLoader = new ImageLoader();
    }

    @Override
    public BaseHolder<NewsEase> getHolder(Context context) {
        return new NewsHolder(context);
    }

    public class NewsHolder extends BaseHolder<NewsEase> {

        @BindView(R.id.tv_1)
        TextView mTv1;
        @BindView(R.id.iv_1)
        ImageView mIv1;

        public NewsHolder(Context context) {
            super(context);
        }

        @Override
        public View initView() {
            View view = View.inflate(context, R.layout.layout_item_news, null);
            return view;
        }

        @Override
        public void setData(NewsEase newsEase) {
            mTv1.setText(newsEase.getTitle());
//            mLoader.setImageUrl(mIv1, newsEase.getImgsrc());
        }
    }

}
