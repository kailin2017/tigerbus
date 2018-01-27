package com.tigerbus.util;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import java.util.List;

public final class DiffListCallBack extends DiffUtil.Callback {

    private List oldDatas, newDatas;

    public DiffListCallBack(@NonNull List oldDatas, @NonNull List newDatas) {
        this.oldDatas = oldDatas;
        this.newDatas = newDatas;
    }

    @Override
    public int getOldListSize() {
        return oldDatas.size();
    }

    @Override
    public int getNewListSize() {
        return newDatas.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }
}