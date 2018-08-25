package com.github.chekhwastaken.flowengine;

import android.support.annotation.AnimRes;

import com.chekhwastaken.flowengine.R;

public enum AnimationType {
    FROM_LEFT_TO_RIGHT(R.anim.slide_in_right, R.anim.slide_left),
    FROM_RIGHT_TO_LEFT(R.anim.slide_out_right, R.anim.slide_out_right);

    @AnimRes
    private final int enter;

    @AnimRes
    private final int exit;

    AnimationType(@AnimRes int enter, @AnimRes int exit) {
        this.enter = enter;
        this.exit = exit;
    }

    @AnimRes
    public int enter() {
        return enter;
    }

    @AnimRes
    public int exit() {
        return exit;
    }
}