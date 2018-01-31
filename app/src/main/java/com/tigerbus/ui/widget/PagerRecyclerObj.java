package com.tigerbus.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public final class PagerRecyclerObj {

    private RecyclerView recyclerView;
    private String pagerTitle;

    public PagerRecyclerObj(@NonNull String pagerTitle, @NonNull RecyclerView.Adapter adapter, @NonNull Context context) {
        init(pagerTitle, adapter, context, null);
    }

    public PagerRecyclerObj(@NonNull String pagerTitle, @NonNull RecyclerView.Adapter adapter,
                            @NonNull Context context, RecyclerItemTouchHelper recyclerItemTouchHelper) {
        init(pagerTitle, adapter, context, recyclerItemTouchHelper);
    }

    private void init(@NonNull String pagerTitle, @NonNull RecyclerView.Adapter adapter,
                      @NonNull Context context, RecyclerItemTouchHelper recyclerItemTouchHelper) {
        setPagerTitle(pagerTitle);
        recyclerView = new RecyclerView(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        if (recyclerItemTouchHelper != null) {
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(recyclerItemTouchHelper);
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }
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
