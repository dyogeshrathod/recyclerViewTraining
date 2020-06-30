package com.coolapps.yo.recyclerviewtraining;

import java.util.Objects;

public class ContactModel {
    private String mName;
    private int mNumber;
    private int mImageRes;

    public ContactModel(String name, int number, int imageRes) {
        mName = name;
        mNumber = number;
        mImageRes = imageRes;
    }

    public String getName() {
        return mName;
    }

    public int getNumber() {
        return mNumber;
    }

    public int getImageRes() {
        return mImageRes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactModel)) return false;
        ContactModel that = (ContactModel) o;
        return mNumber == that.mNumber &&
                mImageRes == that.mImageRes &&
                mName.equals(that.mName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mName, mNumber, mImageRes);
    }
}
