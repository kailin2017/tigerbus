package com.tigerbus.ui.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tigerbus.ui.route.adapter.ArrivalRecyclerAdapter;

public final class PagerRecyclerObj {

    private RecyclerView recyclerView;
    private String pagerTitle;

    public PagerRecyclerObj(String pagerTitle, RecyclerView.Adapter adapter, Context context){
        setPagerTitle(pagerTitle);
        recyclerView = new RecyclerView(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    public RecyclerView getView() {
        return recyclerView;
    }

    public void setView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public String getPagerTitle() {
        return pagerTitle;
    }

    public void setPagerTitle(String pagerTitle) {
        this.pagerTitle = pagerTitle;
    }
}
