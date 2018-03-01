package org.commom.library.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.commom.library.R;
import org.commom.library.activity.BaseActivity;
import org.commom.library.databinding.PickImageActivityBinding;
import org.commom.library.viewmodel.PickImageViewModel;

/**
 * Created by peter on 3/1/2018.
 */

public class PickImageActivity extends BaseActivity {

    private PickImageViewModel viewModel;
    private PickImageActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.pick_image_activity);
        viewModel = new PickImageViewModel(this);
        binding.setViewModel(viewModel);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        viewModel.onActivityResult(requestCode, resultCode, data);
    }
}
