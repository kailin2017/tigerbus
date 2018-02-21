package com.tigermvp.delegate;

import android.os.Bundle;

public interface MvpDelegate {

    void onCreate(Bundle saved);

    void onPause();

    void onResume();

    void onStart();

    void onStop();

    void onDestroy();
}
