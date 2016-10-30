package com.amatorlee.rdemo;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by AmatorLee on 2016/10/29.
 * 基类Adaper
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder>{


    private static final int ITEM_HEAD = 1000;
    private static final int ITEM_FOOT = 2000;
    private static final int ITEM_NOMAL = 3000;

    private View mHeadView;
    private View mFootView;
    private List<T> mDatas;
    private LayoutInflater mInflater;


    /**
     * 点击回调
     */
    private OnItemClickListener mListener;

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }


    /**
     * 构造方法
     * @param context
     * @param mDatas
     */
    public BaseRecyclerAdapter(Context context, List<T> mDatas) {
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    /**
     * headView
     * @param headView
     */
    public void addHeadView(View headView){
        this.mHeadView = headView;
        notifyItemInserted(0);
    }
    public View getHeadView(){
        if (mHeadView != null){
            return mHeadView;
        }
        return null;
    }

    /**
     * FootView
     * @param footView
     */
    public void addFootView(View footView){
        this.mFootView = footView;
        notifyItemInserted(getItemCount() - 1);
    }
    public View getFootView(){
        if (mFootView != null){
            return mFootView;
        }
        return null;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeadView != null && viewType == ITEM_HEAD){
            //如果为headView则itemView为headView
            return new BaseRecyclerViewHolder(mHeadView);
        }else if (mFootView != null && viewType == ITEM_FOOT){
            //如果为footView则itemView为footView
            return new BaseRecyclerViewHolder(mFootView);
        }
        //否则则为自己的View
        return new BaseRecyclerViewHolder(mInflater.inflate(getLayoutID(),parent,false));
    }
    /*此方法为解析自己的view提供一个layoutId*/
    public abstract int getLayoutID();


    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, final int position) {
        /*判断是否有headView，如果0被占用则position-1*/
        final int pos = getLayoutPos(holder);

        if (getItemViewType(position) == ITEM_HEAD)return;/*交予自己处理headView*/
        if (getItemViewType(position) == ITEM_FOOT)return;/*处理footView*/
        /*点击监听*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onItemClick(v,pos);
                }
            }
        });
        /*自己处理数据的方法*/
        onBindData(holder,pos);
    }
    /*判断是否有headView，如果0被占用则position-1*/
    private int getLayoutPos(BaseRecyclerViewHolder holder){
        return mHeadView == null?holder.getLayoutPosition():holder.getLayoutPosition() - 1;
    }

    /*自己处理数据的方法*/
    public abstract void onBindData(BaseRecyclerViewHolder holder, int position);

    @Override
    public int getItemCount() {
        if (mHeadView == null && mFootView != null){
            //如果footView则count+1
            return mDatas.size() + 1;
        }else if (mHeadView != null && mFootView == null){
            //如果有headView则+1
            return mDatas.size()+ 1;
        }else if(mFootView != null && mHeadView != null){
            //假如都有则+2
            return mDatas.size() + 2;
        }
        /*否则不处理*/
        return mDatas.size();
    }

    /***
     * 此方法判断itemType的类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeadView == null && mFootView == null)return ITEM_NOMAL;
        if (position == 0) return ITEM_HEAD;
        if (position == getItemCount() -1) return ITEM_FOOT;
        return ITEM_NOMAL;

    }

    /**
     *回调监听
     */
    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager){
            ((GridLayoutManager) manager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == ITEM_HEAD || getItemViewType(position) == ITEM_FOOT){
                        return ((GridLayoutManager) manager).getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(BaseRecyclerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder.getItemViewType() == ITEM_HEAD ||holder.getItemViewType() == ITEM_FOOT){
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams){
                StaggeredGridLayoutManager.LayoutParams sl = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                sl.setFullSpan(true);
            }
        }
    }
}
