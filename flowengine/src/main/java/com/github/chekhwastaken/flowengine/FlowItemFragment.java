package com.github.chekhwastaken.flowengine;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

public abstract class FlowItemFragment<Binding extends ViewDataBinding> extends Fragment {
    private FlowHostActivity hostActivity;
    private Binding binding;

    public FlowItemFragment() {
    }

    protected final boolean has(@SuppressWarnings("SameParameterValue") String key) {
        return getArguments().containsKey(key);
    }

    protected final Binding getBinding() {
        return binding;
    }

    protected final Drawable getDrawable(@DrawableRes int drawableResId) {
        return ContextCompat.getDrawable(getContext(), drawableResId);
    }

    protected final int getColor(@ColorRes int colorResId) {
        return ContextCompat.getColor(getContext(), colorResId);
    }

    protected final FlowHostActivity getHostActivity() {
        return hostActivity;
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract void onViewCreated(Bundle args);

    /**
     * Add this annotation @Subscribe(threadMode = ThreadMode.MAIN)
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public abstract void parseAction(Action action);

    @Override
    public final void onAttach(Context context) {
        super.onAttach(context);

        if (!(context instanceof FlowHostActivity)) {
            String message = String.format(Locale.ENGLISH, "%s should be hosted by FlowHostActivity and not %s", getClass().getSimpleName(), context.getClass().getSimpleName());
            throw new RuntimeException(message);
        }

        hostActivity = (FlowHostActivity) context;
    }

    @Override
    public final void onDetach() {
        super.onDetach();

        hostActivity = null;
    }

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);

        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
        return binding.getRoot();
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreated(getArguments());
    }

    @Override
    public final void onDestroyView() {
        super.onDestroyView();

        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }
}
