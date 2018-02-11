package com.tigerbus.ui.main.memento;

/**
 * Created by Kailin on 2018/2/11.
 */

public final class Originator {

    private int itemId;

    public Originator(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void load(Memento memento){
        setItemId(memento.getItemId());
    }

    public Memento save(){
        return new Memento(getItemId());
    }
}
