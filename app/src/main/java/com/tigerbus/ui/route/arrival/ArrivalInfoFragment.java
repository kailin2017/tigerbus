package com.tigerbus.ui.route.arrival;

import android.app.Fragment;
import android.os.Bundle;

public final class ArrivalInfoFragment extends Fragment {

    public static ArrivalInfoFragment newInstance() {
        Bundle args = new Bundle();
        ArrivalInfoFragment fragment = new ArrivalInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
