package com.github.chekhwastaken.testapp;

import android.os.Bundle;

import com.chekhwastaken.testapp.R;
import com.github.chekhwastaken.flowengine.Action;
import com.github.chekhwastaken.flowengine.FlowData;
import com.github.chekhwastaken.flowengine.FlowHostActivity;
import com.github.chekhwastaken.flowengine.Screen;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedHashMap;

public class ExampleHostActivity extends FlowHostActivity {


    @Override
    protected LinkedHashMap<Enum<?>, Screen> createFlow() {
        LinkedHashMap<Enum<?>, Screen> map = new LinkedHashMap<>();

        map.put(ScreenKeys.screen0, new Screen("Screen 0", Page0.class));
        map.put(ScreenKeys.screen1, new Screen("Screen 1", Page1.class));
        map.put(ScreenKeys.screen2, new Screen("Screen 2", Page2.class));

        return map;
    }

    @Override
    protected void onFlowEnd(LinkedHashMap flow) {

    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void parseAction(Action action) {
        switch (action.key) {
            case "show-next-screen":
                showNextScreen(action.payload);
                return;
        }
    }

    private void showNextScreen(Bundle payload) {
        /* process payload */

        next(new FlowData(ScreenKeys.screen2, null));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_flow_host;
    }

    @Override
    public int getFragmentContainerId() {
        return R.id.layout_content_fragment;
    }
}
