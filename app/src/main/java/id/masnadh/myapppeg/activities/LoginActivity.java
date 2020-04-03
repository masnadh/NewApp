package id.masnadh.myapppeg.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import id.masnadh.myapppeg.MainActivity;
import id.masnadh.myapppeg.R;
import id.masnadh.myapppeg.connections.AppController;
import id.masnadh.myapppeg.connections.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    Button  btnLogin;
    EditText txt_username, txt_password;
    Intent intent;

    int success;
    ConnectivityManager conMgr;

    private String url = Server.URL + "masuk.php";

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_LEVEL = "level";
    public final static String TAG_USERNAME = "username";
    public final static String TAG_ID = "id";

    String tag_json_obj = "json_obj_req";

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id, username, level, nama;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

     @Override
     protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            TextView isiForm = (TextView) findViewById(R.id.link_register);

             isiForm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent isiForm = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(isiForm);
                }
             });

         conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
         {
             if (conMgr.getActiveNetworkInfo() != null
                     && conMgr.getActiveNetworkInfo().isAvailable()
                     && conMgr.getActiveNetworkInfo().isConnected()) {
             } else {
                 Toast.makeText(getApplicationContext(), "No Internet Connection",
                         Toast.LENGTH_LONG).show();
             }
         }

         btnLogin = (Button) findViewById(R.id.btn_login);
         txt_username = (EditText) findViewById(R.id.edt_username);
         txt_password = (EditText) findViewById(R.id.edt_pass_log);

         // Cek session login jika TRUE maka langsung buka MainActivity
         sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
         session = sharedpreferences.getBoolean(session_status, false);
         id = sharedpreferences.getString(TAG_ID, null);
//        username = sharedpreferences.getString(TAG_USERNAME, null);
         // nama = sharedpreferences.getString(TAG_NAMA, null);

         if (session) {
             Intent intent = new Intent(LoginActivity.this, MainActivity.class);
             intent.putExtra(TAG_ID, id);
             intent.putExtra(TAG_USERNAME, username);
             intent.putExtra(TAG_LEVEL, level);
             finish();
             startActivity(intent);
         }

         btnLogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String username = txt_username.getText().toString();
                 String password = txt_password.getText().toString();

                 // mengecek kolom yang kosong
                 if (username.trim().length() > 0 && password.trim().length() > 0) {
                     if (conMgr.getActiveNetworkInfo() != null
                             && conMgr.getActiveNetworkInfo().isAvailable()
                             && conMgr.getActiveNetworkInfo().isConnected()) {
                         checkLogin(username, password);
                     } else {
                         Toast.makeText(getApplicationContext() ,"No Internet Connection", Toast.LENGTH_LONG).show();
                     }
                 } else {
                     // Prompt user to enter credentials
                     Toast.makeText(getApplicationContext() ,"Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                 }
             }
         });

     }

    private void checkLogin(final String username, final String password) {

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    if (success == 1){
                        String username = jObj.getString(TAG_USERNAME);
                        String id = jObj.getString(TAG_ID);
                        String level = jObj.getString(TAG_LEVEL);

                        Log.e("Successfully Login!", jObj.toString());

                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        // menyimpan login ke session
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(TAG_ID, id);
                        editor.putString(TAG_USERNAME, username);
                        editor.putString(TAG_LEVEL, level);
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra(TAG_ID, id);
                        intent.putExtra(TAG_USERNAME, username);
                        intent.putExtra(TAG_LEVEL, level);
                        // finish();
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Login Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();

                        hideDialog();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
        //AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
