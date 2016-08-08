package com.apsoft.rxbus.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apsoft.rxbus.RxBus;
import com.apsoft.rxbus.annotations.Event;
import com.apsoft.rxbus.sample.events.SampleEvent;
import com.apsoft.rxbus.sample.events.SampleEventTwo;

public class TestFragment extends Fragment {

    private String TAG = TestFragment.class.getCanonicalName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Event(SampleEvent.class)
    public void onSampleEventReceived(SampleEvent event) {
        Log.e(TAG, event.getText());
    }

    @Event(SampleEventTwo.class)
    public void onSampleEventTwoReceived(SampleEventTwo event) {
        Log.e(TAG, event.getText());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        RxBus.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        RxBus.getInstance().unregister(this);
        super.onDestroy();
    }
}
