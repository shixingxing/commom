package org.common.library.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;

/**
 * Created by peter on 2016/3/27.
 */
public class MyObservable extends BaseObservable implements ViewModel {

    protected Context context;

    public MyObservable(Context context) {
        this.context = context;
    }


    @Override
    public void destroy() {
        context = null;
    }
}
