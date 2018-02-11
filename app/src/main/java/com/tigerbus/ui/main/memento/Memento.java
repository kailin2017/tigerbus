package com.tigerbus.ui.main.memento;

/**
 * Created by Kailin on 2018/2/11.
 */

public final class Memento {

    private int ItemId;

    public Memento(int itemId) {
        ItemId = itemId;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }
}
