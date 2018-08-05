package fsoft.training.movieapplication.data.preference;

import android.content.Context;
import android.content.SharedPreferences;

import fsoft.training.movieapplication.constant.Constants;

/**
 * Created by mac on 10/26/17.
 */

public class BaseSharedPreference {

    ////////////////////////////////////////////////////////////////////////////
    // instance fields
    ////////////////////////////////////////////////////////////////////////////
    private SharedPreferences mSharedPreferences;

    ////////////////////////////////////////////////////////////////////////////
    // public method
    ////////////////////////////////////////////////////////////////////////////
    public boolean restoringTypeLayout(String fileName, int mode, Context context) {
        mSharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(Constants.TYPE_LAYOUT_KEY, false);
    }

    public void saveDataTypeLayout(boolean typelayout, Context context) {
        mSharedPreferences = context.getSharedPreferences(Constants.FILE_NAME_TYPE_LAYOUT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(Constants.TYPE_LAYOUT_KEY, typelayout);
        editor.apply();
    }

    ////////////////////////////////////////////////////////////////////////////
    // private method
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // inner class
    ////////////////////////////////////////////////////////////////////////////

}
