package org.common.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.commom.library.fragment.BaseFragment;
import org.common.App;
import org.common.R;
import org.common.http.ApiService;
import org.common.model.BaseInfo;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by peter on 2016/3/28.
 */
public class BaseInfoFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_info, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getBaseInfo("1111");
    }

    private void getBaseInfo(String token) {

        App app = (App) getActivity().getApplication();
        ApiService service = app.getApiService();
        service.getBaseInfo(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(app.defaultSubscribeScheduler()).subscribe(new Action1<BaseInfo>() {
            @Override
            public void call(BaseInfo baseInfo) {

            }

        });
    }
}
