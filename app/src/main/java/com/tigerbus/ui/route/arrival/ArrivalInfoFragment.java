package com.tigerbus.ui.route.arrival;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.tigerbus.R;
import com.tigerbus.app.BaseFragment;
import com.tigermvp.MvpPresenter;
import com.tigermvp.annotation.FragmentView;
import com.tigermvp.annotation.ViewInject;
import com.tigerbus.data.bus.BusRoute;

@FragmentView(mvp = false, layout = R.layout.route_info_fragment)
public final class ArrivalInfoFragment extends BaseFragment {

    private final static String ARG_BUSROUTE = "ARG_BUSROUTE";

    @ViewInject(R.id.station_start)
    private TextView startionStart;
    @ViewInject(R.id.station_end)
    private TextView startionEnd;

    public static ArrivalInfoFragment newInstance(@NonNull BusRoute busRoute) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_BUSROUTE, busRoute);
        ArrivalInfoFragment fragment = new ArrivalInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView() {
        BusRoute busRoute = getArguments().getParcelable(ARG_BUSROUTE);
        startionStart.setText(stringFormat(R.string.route_info_startstation,
                busRoute.getDepartureStopNameZh(), busRoute.getDepartureStopNameEn()));
        startionEnd.setText(stringFormat(R.string.route_info_endstation,
                busRoute.getDestinationStopNameZh(), busRoute.getDestinationStopNameEn()));
    }

    private String stringFormat(int format, Object... obj) {
        return String.format(getString(format), obj);
    }


    @Override
    public MvpPresenter createPresenter() {
        return null;
    }
}
