package id.masnadh.myapppeg.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static id.masnadh.myapppeg.activities.LoginActivity.my_shared_preferences;
import static id.masnadh.myapppeg.activities.LoginActivity.session_status;

import de.hdodenhof.circleimageview.CircleImageView;
import id.masnadh.myapppeg.R;
import id.masnadh.myapppeg.connections.AppController;
import id.masnadh.myapppeg.connections.Server;

public class ProfileFragment extends Fragment {

    String id;
    SharedPreferences sharedpreferences;
    //public final String deluser = Server.URL+"de.php";
    public final static String TAG = "Profile";
    public static final String TAG_ID = "id";
    public final static String TAG_IDU = "idu";
    public static final String TAG_LEVEL = "level";
    public static final String TAG_USERNAME = "username";

    public  static final int RequestPermissionCode  = 1 ;
    Button btnFoto;
    ImageView fotoProfile;
    Boolean session = false;
    final String ambilfoto = Server.URL+"pegawai.php";
    final String gantifoto = Server.URL+"gantifoto.php";
    final String uploadFoto = Server.URL+"upload.php";

    private String Document_img1="";
    Bitmap bitmap;
    ProgressDialog progressDialog;
    int PICK_IMAGE_REQUEST = 111;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container,false);

//        sharedpreferences = getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
//        session = sharedpreferences.getBoolean(session_status, false);
//        //levelU = sharedpreferences.getString(TAG_LEVEL, null);
//        id = sharedpreferences.getString(TAG_ID, null);

        fotoProfile = (CircleImageView)view.findViewById(R.id.profile_image);
        fotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = { "Ambil Foto", "Pilih Dari Gallery","Batal" };
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileFragment.this.getActivity());
                builder.setTitle("Ganti Foto!");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Ambil Foto"))
                        {
                            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 100);

                            btnFoto.setEnabled(true);

                        }
                        else if (options[item].equals("Pilih Dari Gallery"))
                        {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_PICK);
                            startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);

                            btnFoto.setEnabled(true);
                        }
                        else if (options[item].equals("Batal")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
//        fotoProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pilihgambar();
//            }
//        });





        btnFoto = view.findViewById(R.id.btnPhoto);
        btnFoto.setEnabled(false);
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(ProfileFragment.this.getActivity());
                progressDialog.setMessage("Proses Simpan, Mohon Tunggu...");
                progressDialog.show();

                //converting image to base64 string
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                RequestQueue requestQ = Volley.newRequestQueue(getActivity().getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, gantifoto, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject dataObj = new JSONObject(response);
                            progressDialog.dismiss();


                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error: " + error.getMessage());
                        Toast.makeText(ProfileFragment.this.getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map = new HashMap<>();
                        map.put("id_peg", getActivity().getIntent().getStringExtra(TAG_ID));
                        map.put("foto",imageString);
                        return map;
                    }
                };

                AppController.getInstance().addToRequestQueue(stringRequest);
                RequestQueue requestQueue = Volley.newRequestQueue(ProfileFragment.this.getActivity());
                requestQueue.add(stringRequest);
            }
        });

        progressDialog = new ProgressDialog(ProfileFragment.this.getActivity());
        progressDialog.setMessage("Proses Pengambilan Data, Mohon Tunggu...");
        progressDialog.show();
       RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, ambilfoto, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray dataArray= new JSONArray(response);
                    progressDialog.dismiss();
                    for (int i =0; i<dataArray.length(); i++) {

                        JSONObject obj = dataArray.getJSONObject(i);
                        int extraId = Integer.parseInt(getActivity().getIntent().getStringExtra(TAG_ID));

                        int id = obj.getInt("id_peg");
                        if (extraId == id) {
                            String fotobase64 = obj.getString("foto");
                            byte[] decodedString = Base64.decode(fotobase64, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                            if (extraId== id ) {

                                if (fotobase64.isEmpty()) {

                                    Picasso.with(getActivity().getApplication()).load("http://smknprigen.sch.id/bkk/image/default.png").into(fotoProfile);
                                } else if (fotobase64.equals("null")) {

                                    Picasso.with(getActivity().getApplication()).load("http://smknprigen.sch.id/bkk/image/default.png").into(fotoProfile);
                                } else {

                                    fotoProfile.setImageBitmap(decodedByte);
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(ProfileFragment.this.getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            //adding parameters to send
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();

                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(ProfileFragment.this.getActivity());
        rQueue.add(request);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                //Setting image to ImageView
                fotoProfile.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
