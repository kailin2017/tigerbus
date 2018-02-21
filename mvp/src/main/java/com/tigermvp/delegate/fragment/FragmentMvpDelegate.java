package com.tigermvp.delegate.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tigermvp.MvpPresenter;
import com.tigermvp.MvpView;
import com.tigermvp.delegate.MvpDelegate;

public interface FragmentMvpDelegate<V extends MvpView, P extends MvpPresenter<V>>extends MvpDelegate {

    void onAttach(Context context);

    void onActivityCreated(Bundle savedInstanceState);

    void onViewCreated(View view, @Nullable Bundle savedInstanceState);

    void onDestroyView();

    void onDetach();

    void onSaveInstanceState(Bundle outState);
}
