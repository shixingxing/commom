package org.commom.library.viewmodel;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

/**
 * Created by peter on 3/1/2018.
 */

public class PickImageViewModel extends MyObservable {

    private static final int REQUEST_CODE_GET_IMAGE = 0x141;
    private static final int REQUEST_CODE_TAKE_PICTURE = 0x142;

    public ObservableField<String> imagePath;
    private String currentFilePath;

    public PickImageViewModel(Context context) {
        super(context);
        imagePath = new ObservableField<>();
    }

    @Override
    public void destroy() {
        super.destroy();

    }

    public void onPickClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_GET_IMAGE);
    }

    public void onTakeClick(View view) {
        Intent mIntent = new Intent();
        mIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        long date = Calendar.getInstance().getTimeInMillis();
        currentFilePath = Environment.getExternalStorageDirectory()
                + File.separator + "DCIM" + File.separator + "Camera" + File.separator + date + ".jpg";
        mIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(currentFilePath)));
        ((Activity) context).startActivityForResult(mIntent, REQUEST_CODE_TAKE_PICTURE);

    }

    @BindingAdapter({"imagePath"})
    public static void loadImage(ImageView view, String imagePath) {
        if (imagePath == null) {
            return;
        }
        Glide.with(view.getContext()).load(new File(imagePath)).into(view);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case REQUEST_CODE_GET_IMAGE:
                if (data == null)
                    return;

                currentFilePath = getRealFilePath((Activity) context, data.getData());
                imagePath.set(currentFilePath);
                break;
            case REQUEST_CODE_TAKE_PICTURE:
                File file = new File(currentFilePath);
                if (file.exists()) {
                    imagePath.set(currentFilePath);
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String getRealFilePath(Activity activity, final Uri uri) {
        if (null == uri) {
            return null;
        }

        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(activity, uri)) {

                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/"
                                + split[1];
                    }
                } else if (isDownloadsDocument(uri)) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(id));
                    return getDataColumn(activity, contentUri, null, null);
                } else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = MediaStore.MediaColumns._ID + "=?";
                    final String[] selectionArgs = new String[]{split[1]};
                    return getDataColumn(activity, contentUri, selection,
                            selectionArgs);
                } else {
                    return getDataColumn(activity, uri, null, null);
                }
            } else {
                return getDataColumn(activity, uri, null, null);
            }
        }
        return data;
    }

    private String getDataColumn(Context context, Uri uri,
                                 String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = MediaStore.MediaColumns.DATA;
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }
}
