package com.wiacek.martyna.esnpwapp.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.NavigationDrawer;
import com.wiacek.martyna.esnpwapp.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChangePictureFragment extends Fragment {

    @InjectView(R.id.profilePic) ImageView profilePic;
    @InjectView(R.id.takePicButton)Button takePic;
    @InjectView(R.id.choosePicButton)Button choosePic;
    @InjectView(R.id.uploadPicButton) Button uploadPic;
    ProgressDialog prgDialog;
    String encodedString;
    String imgPath, fileName;
    Bitmap bitmap;
    private static int RESULT_LOAD_IMG = 1;
    private static int RESULT_CAPTURE_IMG = 2;
    private Uri fileUri;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.custom_preference_picture_dialog,container, false);
        ButterKnife.inject(this, view);

        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setCancelable(false);

        Picasso.with(getActivity().getApplicationContext()).load(ServerUrl.BASE_URL + new SessionManager(getActivity().getApplicationContext()).getValueOfProfileImage()).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(profilePic);

        choosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImagefromGallery();
            }
        });

        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        return view;
    }

    public void loadImagefromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    public void takePicture() {
        if (getActivity().getApplicationContext().getPackageManager().
                hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, RESULT_CAPTURE_IMG);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Camera not supported!", Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && null != data) {
            if (requestCode == RESULT_LOAD_IMG ) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();

                profilePic.setImageBitmap(BitmapFactory
                        .decodeFile(imgPath));
                fileName = "user_profile_id_" + new SessionManager(getActivity().getApplicationContext()).getValueOfUserId() + ".jpg";
            } else if (requestCode == RESULT_CAPTURE_IMG) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();
                profilePic.setImageBitmap(BitmapFactory
                        .decodeFile(imgPath));
                fileName = "user_profile_id_" + new SessionManager(getActivity().getApplicationContext()).getValueOfUserId() + ".jpg";
            }
        } else {
        }
    }

    public void uploadImage() {
        if (imgPath != null && !imgPath.isEmpty()) {
            encodeImagetoString();
        } else {
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    "You must select image from gallery or take a new picture before you can upload it!",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void encodeImagetoString() {

        new AsyncTask<Void, Void, String>() {
            protected void onPreExecute() {
            };

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                options.outHeight = 200;
                options.outWidth = 200;
                bitmap = BitmapFactory.decodeFile(imgPath,
                        options);
                bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byte_arr = stream.toByteArray();
                encodedString = Base64.encodeToString(byte_arr, 0);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                prgDialog.setMessage("Calling Upload");
                triggerImageUpload();
            }
        }.execute(null, null, null);
    }

    public void triggerImageUpload() {
        makeHTTPCall();
    }

    public void makeHTTPCall() {
        prgDialog.setMessage("Uploading image on server");
        prgDialog.show();
        RequestQueue rq = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = ServerUrl.BASE_URL + "uploadImage.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    prgDialog.dismiss();
                    new SessionManager(getActivity().getApplicationContext()).setValueOfProfileImage("include/user_profile_pics/"+fileName);
                    Toast.makeText(getActivity().getBaseContext(),
                            "The image was successfully uploaded!", Toast.LENGTH_SHORT)
                            .show();

                    Picasso.with(getActivity().getBaseContext()).load(ServerUrl.BASE_URL + new SessionManager(getActivity().getApplicationContext()).getValueOfProfileImage()).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(profilePic);//
                } catch (Exception e) {
                    prgDialog.dismiss();
                    Toast.makeText(getActivity().getBaseContext(),
                            "Error while loadin data!",
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prgDialog.dismiss();
                Toast.makeText(getActivity().getBaseContext(),
                        "Cannot connect to server", Toast.LENGTH_LONG)
                        .show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", new SessionManager(getActivity().getApplicationContext()).getValueOfUserId());
                params.put("image", encodedString);
                params.put("filename", fileName);
                return params;
            }
        };
        rq.add(stringRequest);
    }
}