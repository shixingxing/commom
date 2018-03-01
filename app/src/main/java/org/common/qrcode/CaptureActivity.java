package org.common.qrcode;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import org.common.R;

public class CaptureActivity extends com.google.zxing.client.android.CaptureActivity  {


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_capture);

    }


    @Override
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        super.handleDecode(rawResult, barcode, scaleFactor);

        String result = rawResult.getText().trim();

        Toast.makeText(this,result,Toast.LENGTH_LONG).show();

        sendReplyMessage(R.id.restart_preview, null, 1000);
    }


}
