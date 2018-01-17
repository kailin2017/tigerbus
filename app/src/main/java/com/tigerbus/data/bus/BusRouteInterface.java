package com.tigerbus.data.bus;

import com.tigerbus.data.detail.NameType;

public interface BusRouteInterface {

    default String getRouteUID() {
        return null;
    }

    default NameType getRouteName() {
        return null;
    }

    default String getDirection() {
        return null;
    }

    default String getSubRouteUID() {
        return null;
    }

    default NameType getSubRouteName() {
        return null;
    }

}
