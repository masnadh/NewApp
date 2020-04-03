package id.masnadh.myapppeg.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import id.masnadh.myapppeg.R;
import id.masnadh.myapppeg.connections.AppController;
import id.masnadh.myapppeg.connections.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText nip, nama, username, password, cPassword;
    Button btnReg;
    ProgressBar loading;
    private static String URL_REGIST = Server.URL + "insert.php";
    private String TAG = "tag";
    private String TAG_SUCCESSS = "success";
    private String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loading = findViewById(R.id.loading_reg);
        nip = findViewById(R.id.edt_nip);
        nama = findViewById(R.id.edt_name);
        username = findViewById(R.id.edt_user);
        password = findViewById(R.id.edt_pwd);
        //cPassword = findViewById(R.id.edt_pwd_con);
        btnReg = findViewById(R.id.btn_reg);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regist();
            }
        });
    }

    private void regist(){
//        loading.setVisibility(View.VISIBLE);
//        btnReg.setVisibility(View.GONE);

        final String nip = this.nip.getText().toString().trim();
        final String nama = this.nama.getText().toString().trim();
        final String username = this.username.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                      //  String success = jsonObject.getString("success");

                        int code = Integer.parseInt(jsonObject.getString("code"));
                        if (code == 1)
                        {
                            daftarBerhasil();
                        }else if(code == 0)
                        {
                            daftarGAgal();
                        }

                        Toast.makeText(RegisterActivity.this, jsonObject.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

//                        if (success.equals("1")){
//                            Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
//                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "Register Error" + e.toString(), Toast.LENGTH_SHORT).show();
//                        loading.setVisibility(View.GONE);
//                        btnReg.setVisibility(View.VISIBLE);

                    }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(RegisterActivity.this, "Register Error" + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error: " + error.getMessage());
                        Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
//                        loading.setVisibility(View.GONE);
//                        btnReg.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nip", nip);
                params.put("nama", nama);
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public void daftarBerhasil()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert
                .setMessage("Pendaftaran Berhasil")
                .setCancelable(false)
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                        finish();
                        startActivity(login);

                    }
                });

        AlertDialog berhasil = alert.create();
        berhasil.show();
    }
    public void daftarGAgal()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert
                .setMessage("Pendaftaran Gagal")
                .setCancelable(false)
                .setNegativeButton("Ulangi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        recreate();
                    }
                });

        AlertDialog berhasil = alert.create();
        berhasil.show();
    }
}
