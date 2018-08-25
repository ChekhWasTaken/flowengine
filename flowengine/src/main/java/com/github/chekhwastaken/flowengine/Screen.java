package com.github.chekhwastaken.flowengine;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.util.Locale;

@SuppressWarnings("SameParameterValue")
public class Screen implements Parcelable {

    @SuppressWarnings("WeakerAccess")
    protected String title;
    private final Class<? extends FlowItemFragment> fragment;
    private FlowData data;

    public Screen(@NonNull String title, @NonNull Class<? extends FlowItemFragment> fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    @SuppressWarnings("WeakerAccess")
    public final String title() {
        return title;
    }

    @SuppressWarnings("WeakerAccess")
    public void onPreShow(Bundle args) {
    }

    @SuppressWarnings("WeakerAccess")
    public void onPostShow(Bundle args) {
    }

    @SuppressWarnings("WeakerAccess")
    public final void attachData(FlowData flowResult) {
        this.data = flowResult;
    }

    @SuppressWarnings("WeakerAccess")
    public final Fragment viewInstance() {
        try {
            return fragment.newInstance();
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("WeakerAccess")
    public final Bundle getArgs() {
        return data.getArgs();
    }

    @SuppressWarnings("WeakerAccess")
    public final void clearData() {
        if (data.getArgs() != null) data.getArgs().clear();
        data = null;
    }

    @Override
    public final String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\n{");
        s.append(String.format(Locale.ENGLISH, "\n\tname=%s", title()));
        s.append(String.format(Locale.ENGLISH, "\n\tview=%s", fragment.getSimpleName()));
        if (data.getArgs() != null)
            for (String key : data.getArgs().keySet()) {
                Object value = data.getArgs().get(key);
                s.append(String.format(Locale.ENGLISH, "\n\t%s : %s", key, value == null ? null : value.toString()));
            }
        s.append("\n}");

        return s.toString();
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Screen)) return false;

        Screen rh = (Screen) obj;

        return rh == this || title.equals(rh.title) && fragment.equals(rh.fragment) && data.equals(rh.data);

    }

    @Override
    public final int hashCode() {
        return 34 | title.hashCode() | fragment.hashCode() | data.hashCode();
    }

    @Override
    public final int describeContents() {
        return 0;
    }

    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeSerializable(this.fragment);
        dest.writeParcelable(this.data, flags);
    }

    @SuppressWarnings("WeakerAccess")
    protected Screen(Parcel in) {
        this.title = in.readString();
        //noinspection unchecked
        this.fragment = (Class<? extends FlowItemFragment>) in.readSerializable();
        this.data = in.readParcelable(FlowData.class.getClassLoader());
    }

    public static final Creator<Screen> CREATOR = new Creator<Screen>() {
        @Override
        public Screen createFromParcel(Parcel source) {
            return new Screen(source);
        }

        @Override
        public Screen[] newArray(int size) {
            return new Screen[size];
        }
    };
}
