package com.tigerbus.base.delegate.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tigerbus.base.MvpPresenter;
import com.tigerbus.base.MvpView;
import com.tigerbus.base.delegate.MvpDelegate;

public interface FragmentMvpDelegate<V extends MvpView, P extends MvpPresenter<V>>extends MvpDelegate {

    void onAttach(Context context);

    void onActivityCreated(Bundle savedInstanceState);

    void onViewCreated(View view, @Nullable Bundle savedInstanceState);

    void onDestroyView();

    void onDetach();

    void onSaveInstanceState(Bundle outState);
}
