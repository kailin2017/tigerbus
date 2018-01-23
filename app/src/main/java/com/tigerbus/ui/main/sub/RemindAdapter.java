package com.tigerbus.ui.main.sub;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public final class RemindAdapter extends RecyclerView.Adapter<RemindAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    final class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
