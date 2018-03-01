package org.common.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import org.commom.library.activity.BaseActivity;
import org.commom.library.view.PickImageActivity;
import org.common.R;
import org.common.databinding.ActivityMainBinding;
import org.common.qrcode.CaptureActivity;
import org.common.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        mainViewModel = new MainViewModel(this);
        //set list
        String[] list = getResources().getStringArray(R.array.main_activity_list);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        binding.mainActivityList.setAdapter(adapter);
        binding.mainActivityList.setOnItemClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewModel.destroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PickImageActivity.class);
                startActivity(intent);
                break;
            case 1:
                break;
            case 2:
                Intent intentQr = new Intent();
                intentQr.setClass(MainActivity.this, CaptureActivity.class);
                startActivity(intentQr);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
