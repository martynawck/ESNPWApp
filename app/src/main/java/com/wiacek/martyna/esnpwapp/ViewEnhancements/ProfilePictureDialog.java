package com.wiacek.martyna.esnpwapp.ViewEnhancements;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.DialogPreference;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by Martyna on 2015-05-14.
 */
class ProfilePictureDialog extends DialogPreference {
    private static final int RESULT_LOAD_IMAGE = 1;


    private ProgressDialog prgDialog;
    private String encodedString;
    private String imgPath;
    private Bitmap bitmap;

    public ProfilePictureDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.custom_preference_picture_dialog);
        setPersistent(true);
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        builder.setTitle("Change your picture");
        super.onPrepareDialogBuilder(builder);

    }

    @Override
    public void onClick(DialogInterface dialog, int which){

        if(which == DialogInterface.BUTTON_POSITIVE) {
            Log.d("POS","POSITIVE");

                Toast.makeText(getContext(), "Value updated!", Toast.LENGTH_LONG).show();
                setTitle("Password: changed");

        }
    }

    @Override
    public void onBindDialogView(View view){

        ImageView picture = (ImageView) view.findViewById(R.id.profilePic);
        Picasso.with(getContext()).load(ServerUrl.BASE_URL + new SessionManager(getContext()).getValueOfProfileImage()).into(picture);
        Button choosePic = (Button) view.findViewById(R.id.choosePicButton);

        choosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImagefromGallery();
            }
        });
        super.onBindDialogView(view);
    }

    void loadImagefromGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        Log.d("Intent",photoPickerIntent.toString());
        getDialog().getOwnerActivity().startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
        //startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data, View view) {
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContext().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) view.findViewById(R.id.profilePic);
                // Set the Image in ImageView
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgPath));
                // Get the Image's file name
                String fileNameSegments[] = imgPath.split("/");
                String fileName = fileNameSegments[fileNameSegments.length - 1];
                // Put file name in Async Http Post Param which will used in Php web app
               // params.put("filename", fileName);

            } else {
                Toast.makeText(getContext(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    // When Upload button is clicked
    public void uploadImage(View v) {
        // When Image is selected from Gallery
        if (imgPath != null && !imgPath.isEmpty()) {
            prgDialog.setMessage("Converting Image to Binary Data");
            prgDialog.show();
            // Convert image to String using Base64
            encodeImagetoString();
            // When Image is not selected from Gallery
        } else {
            Toast.makeText(
                    getContext(),
                    "You must select image from gallery before you try to upload",
                    Toast.LENGTH_LONG).show();
        }
    }

    // AsyncTask - To convert Image to String
    void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                bitmap = BitmapFactory.decodeFile(imgPath,
                        options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, 0);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                prgDialog.setMessage("Calling Upload");
            }
        }.execute(null, null, null);
    }
}
