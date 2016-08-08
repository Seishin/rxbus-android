package com.apsoft.rxbus.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.apsoft.rxbus.RxBus;
import com.apsoft.rxbus.annotations.Event;
import com.apsoft.rxbus.sample.events.SampleEvent;
import com.apsoft.rxbus.sample.events.SampleEventTwo;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new TestFragment(), TestFragment.class.getCanonicalName())
                .addToBackStack(TestFragment.class.getCanonicalName())
                .commit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RxBus.getInstance().send(new SampleEvent("Hello, world!"));
                getSupportFragmentManager().popBackStackImmediate();
                RxBus.getInstance().send(new SampleEventTwo("Hey, there!"));
            }
        }, 1500);
    }

    @Event(SampleEvent.class)
    public void testMethod(SampleEvent event) {
        Log.e(TAG, event.getText());
    }

    @Event(SampleEventTwo.class)
    public void onSampleEventTwoReceived(SampleEventTwo event) {
        Log.e(TAG, event.getText());
    }

    @Override
    protected void onResume() {
        super.onResume();
        RxBus.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        RxBus.getInstance().unregister(this);
    }
}
