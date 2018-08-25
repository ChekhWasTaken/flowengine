package com.github.chekhwastaken.flowengine;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class Action {
    @SuppressWarnings("WeakerAccess")
    public final String key;
    @SuppressWarnings("WeakerAccess")
    public final Bundle payload;

    public Action(@NonNull String key, @Nullable Bundle payload) {
        this.key = key;
        this.payload = payload;
    }
}
