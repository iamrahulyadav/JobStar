package com.papercutlabs.jobstar.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.papercutlabs.jobstar.R;
import com.papercutlabs.jobstar.model.FileDetails;
import com.papercutlabs.jobstar.network.MultipartPostCallback;
import com.papercutlabs.jobstar.network.MultipartPostRequest;
import com.papercutlabs.jobstar.network.Url;
import com.papercutlabs.jobstar.util.FilePath;
import com.papercutlabs.jobstar.util.Util;
import com.papercutlabs.jobstar.view.CustomTextView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by ritwik on 23-04-2018.
 */

public class ProfileActivity extends AppCompatActivity implements MultipartPostCallback {

    private Context mContext;
    private static final int GALLERY_INTENT_CODE = 987;
    private FileDetails uploadedFile;
    private CustomTextView version;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mContext = ProfileActivity.this;
        getSupportActionBar().setTitle("Profile");
        version = (CustomTextView) findViewById(R.id.version);
        version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUploadResumeClick(view);
            }
        });
    }

    public void onPostJobClick(View view) {
        Intent intent = new Intent(ProfileActivity.this, ActivityPostJob.class);
        startActivity(intent);


    }

    public void onSearchMenuClick(View view) {
        Intent intent = new Intent(mContext, ActivitySearchJob.class);
        startActivity(intent);
        finish();
    }

    public void onNotificationMenuClick(View view) {
        Intent intent = new Intent(mContext, ActivityNotification.class);
        startActivity(intent);
        finish();
    }

    public void onProfileMenuClick(View view) {

    }

    public void onMediaMenuClick(View view) {
        Intent intent = new Intent(mContext, ActivityBlogVideo.class);
        startActivity(intent);

    }

    //Upload a new cv
    public void onUploadResumeClick(View view) {
        System.out.println(Build.VERSION.SDK_INT);
        Intent galleryintent = new Intent();
        if (Build.VERSION.SDK_INT < 19) {
            galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            galleryintent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            galleryintent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        galleryintent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        galleryintent.setType("*/*");
        startActivityForResult(galleryintent, GALLERY_INTENT_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GALLERY_INTENT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri selectedFileUri = data.getData();
                    String selectedFilePath = FilePath.getPath(this, selectedFileUri);
                    Uri uri = data.getData();
                    int actionType = Util.checkFileExtention(mContext, uri);
                    Log.e(" ", "uri" + uri);
                    String uriString = uri.toString();
                    File myFile = new File(uriString);

                    Log.e(" ", "uri" + uri);
                    String displayName = null;
                    FileDetails file = null;
                    // int sizeIndex = 0;
                    try {
                        InputStream input = mContext.getContentResolver().openInputStream(uri);
                        if (uriString.startsWith("content://")) {
                            Cursor cursor = null;
                            try {
                                cursor = getContentResolver().query(uri, null, null, null, null);
                                if (cursor != null && cursor.moveToFirst()) {
                                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                    // sizeIndex =
                                    // cursor.getColumnIndex(OpenableColumns.SIZE);
                                }
                            } finally {
                                cursor.close();
                            }
                        } else if (uriString.startsWith("file://")) {
                            displayName = myFile.getName();
                        }

                        file = new FileDetails();
                        file.setFileName(displayName);
                        file.setFilePath(uriString);
                        file.setSelectedFilePath(selectedFilePath);
                        // file.setFileSize(sizeIndex);
                        file.setInputStream(input);
                        file.setActionType(actionType);

                    } catch (FileNotFoundException e) {

                        e.printStackTrace();
                    }
                    Log.e("Display Name: ", "displayName=--" + displayName);

                    /*if (onFileChooseCallBack != null) {

                        System.out.println("Get on file choose callback");
                        onFileChooseCallBack.onFileChoose(file);
                    }*/

                    //Multipart POst Upload file
                    uploadFile(file);
                }
                break;
        }
    }

    private void uploadFile(FileDetails file) {

        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("user_id", "" + Util.fetchUserClass(mContext).getUserId());
        requestMap.put("cover_letter", "test cover letter text");
        requestMap.put("mobile", "7044742773");
        uploadedFile = file;
        MultipartPostRequest request = new MultipartPostRequest(mContext, Url.UPDATE_PROFILE_URL,
                requestMap, file, "uploaded_file");
        request.mListener = this;
        request.execute();
    }

    @Override
    public void onMultipartPost(String response) {
        Log.e("Response", "" + response);
        String responseStatus = "";
        try {
            JSONObject requestJsonObject = new JSONObject(response);
            responseStatus = requestJsonObject.optString("code").trim();

            if (responseStatus != null && responseStatus.trim().equalsIgnoreCase("200")) {

                Toast.makeText(mContext, "CV posted succesfully.", Toast.LENGTH_SHORT).show();
               /* UserClass userClass = Util.fetchUserClass(mContext);
                Log.e("Filename", "Oh Lord: file name: " + uploadedFile.getFileName());
                userClass.uplodedCVUrl = getBaseImageURl(userClass.uplodedCVUrl) + "/" + uploadedFile.getFileName();
                Util.saveUserClass(mContext, userClass);
                Log.e("FInalURL", "Final URL: " + userClass.uplodedCVUrl);*/

            } else if (responseStatus != null && responseStatus.trim().equalsIgnoreCase("400")) {
                Util.showMessageWithOk(ProfileActivity.this, "" + requestJsonObject.optString("msg"));
            } else {

                Toast.makeText(mContext, "Error occured while uploading cv.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

            Toast.makeText(mContext, "Error occured while uploading cv.", Toast.LENGTH_SHORT).show();
        }
    }
}
