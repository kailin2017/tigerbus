package com.tigerbus.util;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import java.util.Map;

public final class DiffMapCallBack extends DiffUtil.Callback {

    private Map oldDatas, newDatas;

    public DiffMapCallBack(@NonNull Map oldDatas, @NonNull Map newDatas) {
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