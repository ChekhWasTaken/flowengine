package com.github.chekhwastaken.testapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chekhwastaken.testapp.R;
import com.chekhwastaken.testapp.databinding.Page2Binding;
import com.github.chekhwastaken.flowengine.Action;
import com.github.chekhwastaken.flowengine.FlowItemFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Page2 extends FlowItemFragment<Page2Binding> {
    @Override
    protected int getLayoutResId() {
        return R.layout.page2;
    }

    @Override
    protected void onViewCreated(Bundle args) {

        getBinding().text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Page 0", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void parseAction(Action action) {

    }


}
