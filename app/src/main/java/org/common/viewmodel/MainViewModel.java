package org.common.viewmodel;

import android.content.Context;

import org.common.library.viewmodel.MyObservable;

/**
 * Created by peter on 3/1/2018.
 */

public class MainViewModel extends MyObservable {

    public MainViewModel(Context context) {
        super(context);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
