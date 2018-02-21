package com.tigermvp.log;

public enum TlogType {
    debug("d"),
    error("e"),
    info("i"),
    verbose("v"),
    warn("w"),
    wtf("wtf");


    private String type;
    TlogType(String type) {
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
