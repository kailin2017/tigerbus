package com.tigerbus.ui.main.memento;

import java.util.ArrayList;

public final class CareTaker {

    private ArrayList<Memento> mementos = new ArrayList<>();

    public void add(Memento state) {
        mementos.add(state);
    }

    public Memento get(int index) {
        return mementos.get(index);
    }

    public void removeLast() {
        if (getMementosSize() > 0)
            mementos.remove(getMementosSize() - 1);
    }

    public Memento popLast() {
        removeLast();
        return getMementosSize() > 0 ? mementos.get(mementos.size() - 1) : null;
    }

    private int getMementosSize() {
        return mementos.size();
    }
}
