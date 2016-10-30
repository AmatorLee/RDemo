package com.amatorlee.rdemo;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

/**
 * Created by AmatorLee on 2016/10/29.
 * 目的Adapter，处理自己的相关逻辑
 */

public class TestAdapter extends BaseRecyclerAdapter<String> {

    private List<String> mDatas;
    private LayoutInflater mInflater;


    public TestAdapter(Context context, List<String> mDatas) {
        super(context, mDatas);
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    @Override
    public int getLayoutID() {
        return R.layout.recycler_test;
    }


    @Override
    public void onBindData(BaseRecyclerViewHolder holder, int position) {
        holder.setText(R.id.recycler_textView,mDatas.get(position));
    }
}
