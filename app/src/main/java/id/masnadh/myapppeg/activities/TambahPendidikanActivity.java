package id.masnadh.myapppeg.activities;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.masnadh.myapppeg.R;
import id.masnadh.myapppeg.connections.AppController;
import id.masnadh.myapppeg.connections.Server;
import id.masnadh.myapppeg.models.PendidikanModel;

public class TambahPendidikanActivity extends AppCompatActivity {

    EditText etJenjang, etNamaSek, etProdi, etLulus;
    Button btnbatal,btnsimpan;
    ProgressDialog pd;
    public final static String TAG_ID = "id";
    List<PendidikanModel> mItems;
    TextView idpeg;
    SharedPreferences sharedpreferences;
    Boolean session = false;
    final String TAG ="Edit";

    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pendidikan);

 //       editData();

        /*get data from intent update*/
        Intent data = getIntent();
        final int update = data.getIntExtra("update",0);
        String intent_jenjang = data.getStringExtra("tingkat");
        String intent_nama = data.getStringExtra("nama_sekolah");
        String intent_jurusan = data.getStringExtra("jurusan");
        String intent_lulus = data.getStringExtra("thn_lulus");
        /*end get data from intent*/

        idpeg = (TextView)findViewById(R.id.id_peg);
        etJenjang = (EditText) findViewById(R.id.inp_jenjang);
        etNamaSek = (EditText) findViewById(R.id.inp_nama_sekolah);
        etProdi = (EditText) findViewById(R.id.inp_prodi);
        etLulus = (EditText) findViewById(R.id.inp_lulus);
        btnbatal = (Button) findViewById(R.id.btn_cancel_pend);
        btnsimpan = (Button) findViewById(R.id.btn_simpan_pend);
        pd = new ProgressDialog(TambahPendidikanActivity.this);

        /*kondisi update / insert*/
        if(update == 1)
        {
            btnsimpan.setText("Update Data");
            etJenjang.setText(intent_jenjang);
            etJenjang.setVisibility(View.GONE);
            etNamaSek.setText(intent_nama);
            etProdi.setText(intent_jurusan);
            etLulus.setText(intent_lulus);


        }

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(update == 1)
                {
                    updateData();
                }else {
                    simpanData();
                }
            }
        });

        btnbatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(TambahPendidikanActivity.this, PendidikanActivity.class);
                startActivity(main);
            }
        });

        String id_peg = getIntent().getStringExtra(TAG_ID);
        idpeg.setText(id_peg);

    }

    private void updateData(){
        final String ip = this.idpeg.getText().toString().trim();
        final String tingkat = this.etJenjang.getText().toString().trim();
        final String nama = this.etNamaSek.getText().toString().trim();
        final String jurusan = this.etProdi.getText().toString().trim();
        final String lulus = this.etLulus.getText().toString().trim();

        StringRequest updateReg = new StringRequest(Request.Method.POST, Server.URL_UPDATE_PEND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int code = Integer.parseInt(jsonObject.getString("code"));
                    if (code == 1)
                    {
                        updaterBerhasil();
                    }else if(code == 0)
                    {
                        updateGagal();
                    }


                   // Toast.makeText(TambahPendidikanActivity.this, "pesan : " + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                Toast.makeText(TambahPendidikanActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
            }



        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("id_peg",idpeg.getText().toString());
                map.put("tingkat", tingkat);
                map.put("nama_sekolah", nama);
                map.put("jurusan", jurusan);
                map.put("thn_lulus", lulus);

                return map;

            }
        };

        AppController.getInstance().addToRequestQueue(updateReg);
    }

//    private void editData() {
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequests =
//                new StringRequest(Request.Method.GET, Server.URL_INSERT_PEND, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONArray dataArray= new JSONArray(response);
//
//                            for (int i =0; i<dataArray.length(); i++)
//                            {
//
//
//                                JSONObject obj = dataArray.getJSONObject(i);
//                                int extraId = Integer.parseInt(getIntent().getStringExtra(TAG_ID));
//                                int id = obj.getInt("id_peg");
//                                String id_peg = obj.getString("id_peg");
//                                String tingkat = obj.getString("tingkat");
//                                String nama = obj.getString("nama_sekolah");
//                                String prodi = obj.getString("jurusan");
//                                String lulus = obj.getString("thn_lulus");
////////
//                        if (extraId == id) {
//
//                            idpeg.setText(id_peg);
//                            etJenjang.setText(tingkat);
//                            etNamaSek.setText(nama);
//                            etProdi.setText(prodi);
//                            etLulus.setText(lulus);
//////                            pm.setJenjang(jsonObject.getString("tingkat"));
//////                            pm.setNamaSek(jsonObject.getString("nama_sekolah"));
//////                            pm.setProdi(jsonObject.getString("jurusan"));
//////                            pm.setLulus(jsonObject.getString("thn_lulus"));
//////                            mItems.add(pm);
//                        }
//                            }
//                            Log.d(TAG, "onResponse:" + response);
//                        }  catch(
//                                JSONException e)
//
//                        {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        etNamaSek.setText(error.getLocalizedMessage());
//                    }
//                });
//        requestQueue.add(stringRequests);
//        requestQueue.getCache().clear();
//
//    }

    private void simpanData() {

        final String ip = this.idpeg.getText().toString().trim();
        final String tingkat = this.etJenjang.getText().toString().trim();
        final String nama = this.etNamaSek.getText().toString().trim();
        final String jurusan = this.etProdi.getText().toString().trim();
        final String lulus = this.etLulus.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URL_INSERT_PEND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        int code = Integer.parseInt(jsonObject.getString("code"));
                        if (code == 1)
                        {
                            daftarBerhasil();
                        }else if(code == 0)
                        {
                            daftarGAgal();
                        }


                        Toast.makeText(TambahPendidikanActivity.this, "pesan : " + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                Toast.makeText(TambahPendidikanActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
            }



        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("id_peg",idpeg.getText().toString());
                map.put("tingkat", tingkat);
                map.put("nama_sekolah", nama);
                map.put("jurusan", jurusan);
                map.put("thn_lulus", lulus);

                return map;

            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public void daftarBerhasil()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert
                .setMessage("Penambahan Data Pendidikan Berhasil")
                .setCancelable(false)
                .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
                        session = sharedpreferences.getBoolean(LoginActivity.session_status, false);

                        id = sharedpreferences.getString(TAG_ID, id);

                        if(session) {
                            Intent sukses = new Intent(TambahPendidikanActivity.this, PendidikanActivity.class);
                            sukses.putExtra(TAG_ID, id);
                            finish();
                            startActivity(sukses);
                        }


                    }
                });

        AlertDialog berhasil = alert.create();
        berhasil.show();
    }
    public void daftarGAgal()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert
                .setMessage("Penambahan Data Pendidikan Gagal")
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

    public void updaterBerhasil()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert
                .setMessage("Update Data Pendidikan Berhasil")
                .setCancelable(false)
                .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
                        session = sharedpreferences.getBoolean(LoginActivity.session_status, false);

                        id = sharedpreferences.getString(TAG_ID, id);

                        if(session) {
                            Intent sukses = new Intent(TambahPendidikanActivity.this, PendidikanActivity.class);
                            sukses.putExtra(TAG_ID, id);
                            finish();
                            startActivity(sukses);
                        }


                    }
                });

        AlertDialog berhasil = alert.create();
        berhasil.show();
    }
    public void updateGagal()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert
                .setMessage("Update Data Pendidikan Gagal")
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
