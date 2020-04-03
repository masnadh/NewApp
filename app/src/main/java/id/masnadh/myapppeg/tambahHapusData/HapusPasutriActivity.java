package id.masnadh.myapppeg.tambahHapusData;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.masnadh.myapppeg.R;
import id.masnadh.myapppeg.activities.DataActivity;
import id.masnadh.myapppeg.activities.DeleteActivity;
import id.masnadh.myapppeg.activities.LoginActivity;
import id.masnadh.myapppeg.activities.PendidikanActivity;
import id.masnadh.myapppeg.connections.AppController;
import id.masnadh.myapppeg.connections.Server;

public class HapusPasutriActivity extends AppCompatActivity {

    EditText deleteNik ;
    Button btnDelete;
    ProgressDialog pd;
    SharedPreferences sharedpreferences;
    Boolean session = false;
    public final static String TAG_ID = "id";
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hapus_pasutri);

        deleteNik = (EditText) findViewById(R.id.del_param);
        btnDelete = (Button) findViewById(R.id.btn_delete_pas);
        pd = new ProgressDialog(HapusPasutriActivity.this);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });


    }

    private void deleteData() {
        StringRequest delReq = new StringRequest(Request.Method.POST, Server.URL_DEL_PAS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // pd.cancel();
                Log.d("volley", "response : " + response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int code = Integer.parseInt(jsonObject.getString("code"));
                    if (code == 1)
                    {
                        hapusBerhasil();
                    }else if(code == 0)
                    {
                        hapusGagal();
                    }

                    Toast.makeText(HapusPasutriActivity.this, "pesan : " + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //             startActivity(new Intent(DeleteActivity.this, PendidikanActivity.class));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                Log.d("volley", "error : " + error.getMessage());
                Toast.makeText(HapusPasutriActivity.this, "pesan : gagal menghapus data", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("nik", deleteNik.getText().toString());
                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(delReq);
    }

    private void hapusBerhasil() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert
                .setMessage("Hapus Data Suami / Istri Berhasil")
                .setCancelable(false)
                .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
                        session = sharedpreferences.getBoolean(LoginActivity.session_status, false);

                        id = sharedpreferences.getString(TAG_ID, id);

                        if(session) {
                            Intent sukses = new Intent(HapusPasutriActivity.this, DataActivity.class);
                            sukses.putExtra(TAG_ID, id);
                            finish();
                            startActivity(sukses);
                        }
                    }
                });

        AlertDialog berhasil = alert.create();
        berhasil.show();
    }

    private void hapusGagal() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert
                .setMessage("Penambahan Data Gagal")
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
