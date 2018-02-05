package com.tigerbus.ui.route.arrival;

import android.os.Bundle;
import android.widget.TextView;

import com.tigerbus.R;
import com.tigerbus.base.BaseFragment;
import com.tigerbus.base.MvpPresenter;
import com.tigerbus.base.annotation.FragmentView;
import com.tigerbus.base.annotation.ViewInject;
import com.tigerbus.data.bus.BusRoute;

@FragmentView(mvp = false, layout = R.layout.route_info_fragment)
public final class ArrivalInfoFragment extends BaseFragment {

    private final static String ARG_BUSROUTE = "ARG_BUSROUTE";

    @ViewInject(R.id.station_start)
    private TextView startionStart;
    @ViewInject(R.id.station_end)
    private TextView startionEnd;

    public static ArrivalInfoFragment newInstance(BusRoute busRoute) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_BUSROUTE, busRoute);
        ArrivalInfoFragment fragment = new ArrivalInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public MvpPresenter createPresenter() {
        return null;
    }
}
