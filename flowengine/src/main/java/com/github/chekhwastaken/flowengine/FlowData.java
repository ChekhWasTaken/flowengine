package com.github.chekhwastaken.flowengine;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public final class FlowData implements Parcelable {

    private final Bundle args;
    private final Enum<?> nextScreenKey;

    @SuppressWarnings("WeakerAccess")
    public FlowData(Enum nextScreenKey, Bundle args) {
        this.args = args;
        this.nextScreenKey = nextScreenKey;
    }

    @SuppressLint("ParcelClassLoader")
    @SuppressWarnings("WeakerAccess")
    protected FlowData(Parcel in) {
        args = in.readBundle();
        nextScreenKey = (Enum<?>) in.readSerializable();
    }

    @SuppressWarnings("WeakerAccess")
    public Bundle getArgs() {
        return args;
    }

    @SuppressWarnings("WeakerAccess")
    public Enum<?> getNextScreenKey() {
        return nextScreenKey;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(args);
        dest.writeSerializable(nextScreenKey);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FlowData> CREATOR = new Creator<FlowData>() {
        @Override
        public FlowData createFromParcel(Parcel in) {
            return new FlowData(in);
        }

        @Override
        public FlowData[] newArray(int size) {
            return new FlowData[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlowData that = (FlowData) o;

        return args.equals(that.args) && nextScreenKey.equals(that.nextScreenKey);
    }

    @Override
    public int hashCode() {
        int result = args.hashCode();
        result = 31 * result + nextScreenKey.hashCode();
        return result;
    }
}
