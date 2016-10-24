package example.demo1021json.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class MyBaseAdapter<T> extends BaseAdapter {

	protected List<T> list;
	
	
	public MyBaseAdapter() {//setter��������
		super();
		// TODO Auto-generated constructor stub
		list=new ArrayList<T>();
	}

	public MyBaseAdapter(List<T> list) {//��
		super();
		this.list = list;
	}
//����޲εĹ��췽��������list��ֵ
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	//���
	public void addData(T t){
		list.add(t);
	}
	public void addDataList(List<T> l){
		if (l==null) {
			return;
		}
		list.addAll(l);
	}
	
	//ɾ��
	public void removeData(T t){
		list.remove(t);
	}
	public void removeDataList(List<T> l){
		if (l==null) {
			return;
		}
		list.removeAll(l);
	}
	//���
	public void clear(){
		list.clear();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list==null?0:list.size();
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return list==null?null:list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		BaseHolder<T> holder=null;
		if (convertView==null) {
			holder=getHolder(parent.getContext());
			convertView=holder.getmRootView();
		}else {
			holder=(BaseHolder<T>) convertView.getTag();
		}
		holder.setData(getItem(position));
		return convertView;
	}

	public abstract BaseHolder<T> getHolder(Context context);
}
