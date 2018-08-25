package com.github.chekhwastaken.testapp;

import android.os.Bundle;
import android.view.View;

import com.chekhwastaken.testapp.R;
import com.chekhwastaken.testapp.databinding.Page1Binding;
import com.github.chekhwastaken.flowengine.Action;
import com.github.chekhwastaken.flowengine.FlowItemFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Page1 extends FlowItemFragment<Page1Binding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.page1;
    }

    @Override
    protected void onViewCreated(Bundle args) {
        getBinding().text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Action("show-next-screen", null));
            }
        });
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void parseAction(Action action) {

    }
}
