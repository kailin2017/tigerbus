package com.tigerbus.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tigerbus.base.annotation.FragmentInjects;
import com.tigerbus.base.annotation.FragmentView;
import com.tigerbus.base.delegate.MvpDelegateCallback;
import com.tigerbus.base.delegate.fragment.FragmentMvpDelegate;
import com.tigerbus.base.delegate.fragment.FragmentMvpDelegateImpl;
import com.tigerbus.base.delegate.fragment.FragmentMvpDelegateImplnon;

public abstract class MvpFragment<V extends MvpView, P extends MvpPresenter<V>>
        extends Fragment implements MvpView, MvpDelegateCallback<V, P> {

    protected FragmentMvpDelegate<V, P> mvpDelegate;
    protected P presenter;
    protected Context context;
    private boolean isMvpPatten = true;
    private int layoutId = 0;
    private FragmentView fragmentView;


    @NonNull
    public boolean isMVP() {
        return true;
    }


    public abstract P createPresenter();

    @NonNull
    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(@NonNull P presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean isRetainInstance() {
        return getRetainInstance();
    }

    @Override
    public boolean shouldInstanceBeRetained() {
        Activity activity = getActivity();
        boolean changingConfig = activity != null && activity.isChangingConfigurations();
        return getRetainInstance() && changingConfig;
    }

    @NonNull
    @Override
    public V getMvpView() {
        return (V) this;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        fragmentView = getClass().getAnnotation(FragmentView.class);
        if (fragmentView != null) {
            isMvpPatten = fragmentView.mvp();
            layoutId = fragmentView.layout();
        }
        getMvpDelegate().onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvpDelegate().onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view;
        if (fragmentView != null) {
            view = inflater.inflate(layoutId, container, false);
            FragmentInjects.inject(this, view);
        } else {
            view = super.onCreateView(inflater, container, savedInstanceState);
        }
        initView();
        return view;
    }

    protected void initView(){}

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMvpDelegate().onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMvpDelegate().onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        getMvpDelegate().onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getMvpDelegate().onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getMvpDelegate().onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getMvpDelegate().onDetach();
    }

    @NonNull
    protected FragmentMvpDelegate<V, P> getMvpDelegate() {
        synchronized (FragmentMvpDelegate.class) {
            if (mvpDelegate == null) {
                mvpDelegate = isMvpPatten ? new FragmentMvpDelegateImpl<>(this) : new FragmentMvpDelegateImplnon();
            }
            return mvpDelegate;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMvpDelegate().onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getMvpDelegate().onSaveInstanceState(outState);
    }
}
