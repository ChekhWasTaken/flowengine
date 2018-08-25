package com.github.chekhwastaken.flowengine;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;


public abstract class FlowHostActivity<Binding extends ViewDataBinding> extends AppCompatActivity {
    private Binding binding;
    private final Deque<Screen> screenStack = new ArrayDeque<>();
    private LinkedHashMap<Enum<?>, Screen> flow;

    protected final Binding getBinding() {
        return binding;
    }

    public final Screen currentScreen() {
        return screenStack.peek();
    }

    protected abstract LinkedHashMap<Enum<?>, Screen> createFlow();

    @LayoutRes
    protected abstract int getLayoutResId();

    protected void onActivityCreated(Bundle savedInstanceState) {
    }

    protected void onActivityDestroyed() {
    }

    @IdRes
    protected abstract int getFragmentContainerId();

    protected abstract void onFlowEnd(LinkedHashMap<Enum<?>, Screen> flow);

    /**
     * Add this annotation @Subscribe(threadMode = ThreadMode.MAIN)
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public abstract void parseAction(Action action);

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flow = createFlow();
        binding = DataBindingUtil.setContentView(this, getLayoutResId());
        Screen first = flow.entrySet().iterator().next().getValue();
        first.attachData(new FlowData(null, new Bundle()));
        screenStack.push(first);
        show(first, AnimationType.FROM_LEFT_TO_RIGHT);
        onActivityCreated(savedInstanceState);
    }

    @Override
    protected final void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
        onActivityStarted();
    }

    @SuppressWarnings("WeakerAccess")
    protected void onActivityStarted() {
    }

    @Override
    protected final void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        onActivityStopped();
    }

    @SuppressWarnings("WeakerAccess")
    protected void onActivityStopped() {
    }

    @Override
    protected final void onDestroy() {
        super.onDestroy();

        onActivityDestroyed();
    }

    protected final void next(@NonNull FlowData flowData) {
        Screen next = flow.get(flowData.getNextScreenKey());

        if (next == null) {
            onFlowEnd(flow);
            return;
        }

        next.attachData(flowData);

        Screen current = screenStack.peek();
        current.onPostShow(current.getArgs());

        screenStack.push(next);

        show(next, AnimationType.FROM_LEFT_TO_RIGHT);
    }

    @Override
    public void onBackPressed() {
        if (screenStack.size() > 1) {
            popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void popBackStack() {
        Screen current = screenStack.pop();
        current.onPostShow(current.getArgs());
        current.clearData();

        Screen previous = screenStack.peek();

        show(previous, AnimationType.FROM_RIGHT_TO_LEFT);
    }

    private void replaceFragment(String tag, Fragment fragment, AnimationType animationType) {
        if (isFinishing()) return;

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();

        fTrans.setCustomAnimations(animationType.enter(), animationType.exit());

        fTrans.replace(getFragmentContainerId(), fragment, tag);

        fTrans.commit();
    }

    private void show(@NonNull Screen screen, AnimationType animationType) {
        screen.onPreShow(screen.getArgs());

        Fragment screenFragment = screen.viewInstance();
        //noinspection ConstantConditions
        screenFragment.setArguments(screen.getArgs());


        replaceFragment(screen.title(), screenFragment, animationType);
    }
}
