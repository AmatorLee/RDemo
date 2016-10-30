package com.amatorlee.rdemo;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by AmatorLee on 2016/10/29.
 * 基类Viewholder
 */

public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder{

    private SparseArray<View> mViews;/*传说中装在view性能好的容器，key为int型，value为View*/
    public View itemView;

    /**
     * 构造方法
     * @param itemView
     */
    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
        this.itemView = itemView;
    }

    /**
     * 利用SpaseArray和泛型省略多次findViewbyid()
     * @param viewId id
     * @param <T> 类型
     * @return
     */
    public <T extends View>T getView(int viewId){
        View view = mViews.get(viewId);
        if (view == null){
            view = itemView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    /**
     * 给外部调用的设置文本的方法
     * @param viewId
     * @param msg
     */
    public void setText(int viewId,String msg){
        TextView textView = getView(viewId);
        textView.setText(msg);
    }

    /**
     * 给外部调用的设置图片资源的方法
     * @param viewID
     * @param resID
     */
    public void setImgRes(int viewID,int resID){
        ImageView imageView = getView(viewID);
        imageView.setImageResource(resID);
    }
}
