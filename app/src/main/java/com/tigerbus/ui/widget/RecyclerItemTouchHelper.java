package com.tigerbus.ui.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public final class RecyclerItemTouchHelper extends ItemTouchHelper.Callback {

    private final ItemTouchHelperCallback helperCallback;

    public RecyclerItemTouchHelper(ItemTouchHelperCallback helperCallback) {
        this.helperCallback = helperCallback;
    }

    /**
     * 设置滑动类型标记
     *
     * @param recyclerView
     * @param viewHolder
     * @return 返回一个整数类型的标识，用于判断Item那种移动行为是允许的
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //START  右向左 END左向右 LEFT  向左 RIGHT向右  UP向上
        //如果某个值传0，表示不触发该操作，次数设置支持上下拖拽，支持向右滑动
        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.START);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return super.isLongPressDragEnabled();
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        helperCallback.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        helperCallback.onItemDelete(viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
    }

    public interface ItemTouchHelperCallback {
        default void onItemDelete(int positon){}

        default void onMove(int fromPosition, int toPosition){}
    }
}
