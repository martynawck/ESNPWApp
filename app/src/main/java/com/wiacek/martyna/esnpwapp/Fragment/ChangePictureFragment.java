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

public class ChangePictureFragment extends Fragment {

    ImageView profilePic;
    Button takePic;
    Button choosePic;
    Button uploadPic;
    ProgressDialog prgDialog;
    String encodedString;
  //  RequestParams params = new RequestParams();
    String imgPath, fileName;
    Bitmap bitmap;
    private static int RESULT_LOAD_IMG = 1;
    private static int RESULT_CAPTURE_IMG = 2;
    private Uri fileUri;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";


    public ChangePictureFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.custom_preference_picture_dialog,container, false);

        profilePic = (ImageView) view.findViewById(R.id.profilePic);
        takePic = (Button) view.findViewById(R.id.takePicButton);
        choosePic = (Button) view.findViewById(R.id.choosePicButton);
        uploadPic = (Button) view.findViewById(R.id.uploadPicButton);
        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setCancelable(false);

        Picasso.with(getActivity().getApplicationContext()).load(ServerUrl.BASE_URL + new SessionManager(getActivity().getApplicationContext()).getValueOfProfileImage()).memoryPolicy(MemoryPolicy.NO_CACHE).into(profilePic);
      //  ivIcon=(ImageView)view.findViewById(R.id.frag2_icon);
       // tvItemName=(TextView)view.findViewById(R.id.frag2_text);

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


//        tvItemName.setText(getArguments().getString(ITEM_NAME));
  //      ivIcon.setImageDrawable(view.getResources().getDrawable(
    //            getArguments().getInt(IMAGE_RESOURCE_ID)));
        return view;
    }

    public void loadImagefromGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    public void takePicture() {
        // Create intent to Open Image applications like Gallery, Google Photos
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
        Log.d("q","1");
        if (resultCode == Activity.RESULT_OK && null != data) {
            Log.d("q","2");
            if (requestCode == RESULT_LOAD_IMG ) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();
                //ImageView imgView = (ImageView) findViewById(R.id.imgView);
                // Set the Image in ImageView
                //   Picasso.with(getActivity().getApplicationContext()).load(ServerUrl.BASE_URL + new SessionManager(getActivity().getApplicationContext()).getValueOfProfileImage()).into(profilePic);

                profilePic.setImageBitmap(BitmapFactory
                        .decodeFile(imgPath));
                // Get the Image's file name
                String fileNameSegments[] = imgPath.split("/");
                //     fileName = fileNameSegments[fileNameSegments.length - 1];
                fileName = "user_profile_id_" + new SessionManager(getActivity().getApplicationContext()).getValueOfUserId() + ".jpg";
                Log.d("FN", fileName);
                Log.d("IMG_PATH", imgPath);
            } else if (requestCode == RESULT_CAPTURE_IMG) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();
                profilePic.setImageBitmap(BitmapFactory
                        .decodeFile(imgPath));
                fileName = "user_profile_id_" + new SessionManager(getActivity().getApplicationContext()).getValueOfUserId() + ".jpg";


            }
          //  uploadImage();
        } else {
            Log.d("q","3");
            Log.d("FN","NO DATA");
        }
    }

    public void uploadImage() {
        // When Image is selected from Gallery
        if (imgPath != null && !imgPath.isEmpty()) {
           // prgDialog.setMessage("Converting Image to Binary Data");
            //prgDialog.show();
            // Convert image to String using Base64
            encodeImagetoString();
            // When Image is not selected from Gallery
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
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, 0);
                Log.d("enc_str",encodedString);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                prgDialog.setMessage("Calling Upload");
                // Put converted Image string into Async Http Post param
             //   params.put("image", encodedString);
                // Trigger Image upload
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
        Log.d("URL", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Log.e("RESPONSE", response);
                 //   JSONObject json = new JSONObject(response);
                    prgDialog.dismiss();
                    new SessionManager(getActivity().getApplicationContext()).setValueOfProfileImage("include/user_profile_pics/"+fileName);
              //      Intent intent = getActivity().getIntent();
               //     intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                //    getActivity().finish();
                //    startActivity(intent);
                    Toast.makeText(getActivity().getBaseContext(),
                            "The image was successfully uploaded!", Toast.LENGTH_SHORT)
                            .show();

                    Picasso.with(getActivity().getBaseContext()).load(ServerUrl.BASE_URL + new SessionManager(getActivity().getApplicationContext()).getValueOfProfileImage()).memoryPolicy(MemoryPolicy.NO_CACHE).into(profilePic);//
                    // invalidate(ServerUrl.BASE_URL + new SessionManager(getActivity().getApplicationContext()).getValueOfProfileImage());


                } catch (Exception e) {
                    prgDialog.dismiss();
                    Log.d("JSON Exception", e.toString());
                    Toast.makeText(getActivity().getBaseContext(),
                            "Error while loadin data!",
                            Toast.LENGTH_LONG).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prgDialog.dismiss();
                Log.d("ERROR", "Error [" + error + "]");
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