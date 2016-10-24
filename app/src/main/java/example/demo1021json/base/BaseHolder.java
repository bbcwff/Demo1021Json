package example.demo1021json.base;

import android.content.Context;
import android.view.View;

public abstract class BaseHolder<T> {

	private View mRootView;
	protected Context context;
	public BaseHolder(Context context) {
		super();
		// TODO Auto-generated constructor stub
		this.context=context;
		mRootView=initView();
		mRootView.setTag(this);
	}


	public View getmRootView() {
		return mRootView;
	}
	
	//��ʼ������
	public abstract View initView();
	//��������
	public abstract void setData(T t);
}
