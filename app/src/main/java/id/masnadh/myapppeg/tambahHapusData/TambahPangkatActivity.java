package id.masnadh.myapppeg.tambahHapusData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import id.masnadh.myapppeg.R;
import id.masnadh.myapppeg.activities.LoginActivity;
import id.masnadh.myapppeg.activities.RiwayatPegPegActivity;
import id.masnadh.myapppeg.connections.AppController;
import id.masnadh.myapppeg.connections.Server;
import id.masnadh.myapppeg.models.JabatanModel;
import id.masnadh.myapppeg.models.PangkatModel;

public class TambahPangkatActivity extends AppCompatActivity {

    EditText etPangkat, etGol, etJns_pangkat, etPejabat_sk, etNo_sk, etTgl_sk, etTmt_pangat, etStatus_pan;
    final String url = Server.URL+"insertPangkat.php";
    final String urlUp = Server.URL+"updatePangkat.php";
    final String urlDel = Server.URL+"deleteJabatan.php";
    Button btnbatalJab,btnsimpanPang;
    ProgressDialog pd;
    public final static String TAG_ID = "id";
    List<PangkatModel> mItems;
    TextView idpeg;
    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormat;
    SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pangkat);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        Intent data = getIntent();
        final int update = data.getIntExtra("update",0);
        String intent_pang = data.getStringExtra("pangkat");
        String intent_gol = data.getStringExtra("gol");
        String intent_jns = data.getStringExtra("jns_pangkat");
        String intent_tmt = data.getStringExtra("tmt_pangkat");
        String intent_pej = data.getStringExtra("pejabat_sk");
        String intent_sk = data.getStringExtra("no_sk");
        String intent_tgl = data.getStringExtra("tgl_sk");
        String intent_stat = data.getStringExtra("status_pan");

        idpeg = (TextView) findViewById(R.id.id_peg);
        etPangkat = (EditText) findViewById(R.id.inp_pangkat);
        etGol = (EditText) findViewById(R.id.inp_gol);
        etJns_pangkat = (EditText) findViewById(R.id.inp_jns_pang);
        etPejabat_sk = (EditText) findViewById(R.id.inp_pejabat);
        etTmt_pangat = (EditText) findViewById(R.id.inp_tmt_pang);
        etTmt_pangat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        etNo_sk = (EditText) findViewById(R.id.inp_no_sk);
        etStatus_pan = (EditText) findViewById(R.id.inp_stat_pang);
        etTgl_sk = (EditText) findViewById(R.id.inp_tgl_sk);
        etTgl_sk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog2();
            }
        });

        btnsimpanPang = (Button) findViewById(R.id.btn_simpan_jab);
        pd = new ProgressDialog(TambahPangkatActivity.this);

        if(update == 1)
        {
            btnsimpanPang.setText("Update Data");
            etPangkat.setText(intent_pang);
            //etNik.setVisibility(View.GONE);
            etGol.setText(intent_gol);
            etPejabat_sk.setText(intent_pej);
            etJns_pangkat.setText(intent_jns);
            etTgl_sk.setText(intent_tgl);
            etTmt_pangat.setText(intent_tmt);
            etStatus_pan.setText(intent_stat);
            etNo_sk.setText(intent_sk);
        }

        btnsimpanPang.setOnClickListener(new View.OnClickListener() {
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

        String id_peg = getIntent().getStringExtra(TAG_ID);
        idpeg.setText(id_peg);
    }

    private void updateData() {
        final String ip = this.idpeg.getText().toString().trim();
        final String pang = this.etPangkat.getText().toString().trim();
        final String gol = this.etGol.getText().toString().trim();
        final String jns = this.etJns_pangkat.getText().toString().trim();
        final String tmt = this.etTmt_pangat.getText().toString().trim();
        final String pej = this.etPejabat_sk.getText().toString().trim();
        final String noSk = this.etNo_sk.getText().toString().trim();
        final String tglSk = this.etTgl_sk.getText().toString().trim();
        final String statPang = this.etStatus_pan.getText().toString().trim();

        StringRequest updateReg = new StringRequest(Request.Method.POST, urlUp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int code = Integer.parseInt(jsonObject.getString("code"));
                    if (code == 1)
                    {
                        updateBerhasil();
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
                Toast.makeText(TambahPangkatActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("id_peg",idpeg.getText().toString());
                map.put("pangkat", pang);
                map.put("gol", gol);
                map.put("tmt_pangkat", tmt);
                map.put("status_pan", statPang);
                map.put("jns_pangkat", jns);
                map.put("no_sk", noSk);
                map.put("tgl_sk", tglSk);
                map.put("pejabat_sk", pej);

                return map;

            }
        };

        AppController.getInstance().addToRequestQueue(updateReg);
    }

    private void updateBerhasil() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert
                .setMessage("Update Berhasil")
                .setCancelable(false)
                .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
                        session = sharedpreferences.getBoolean(LoginActivity.session_status, false);

                        id = sharedpreferences.getString(TAG_ID, id);

                        if(session) {
                            Intent sukses = new Intent(TambahPangkatActivity.this, RiwayatPegPegActivity.class);
                            sukses.putExtra(TAG_ID, id);
                            finish();
                            startActivity(sukses);
                        }
                    }
                });

        AlertDialog berhasil = alert.create();
        berhasil.show();
    }

    private void updateGagal() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert
                .setMessage("Update Data Gagal")
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

    private void simpanData() {
        final String ip = this.idpeg.getText().toString().trim();
        final String pang = this.etPangkat.getText().toString().trim();
        final String gol = this.etGol.getText().toString().trim();
        final String jns = this.etJns_pangkat.getText().toString().trim();
        final String tmt = this.etTmt_pangat.getText().toString().trim();
        final String pej = this.etPejabat_sk.getText().toString().trim();
        final String noSk = this.etNo_sk.getText().toString().trim();
        final String tglSk = this.etTgl_sk.getText().toString().trim();
        final String statPang = this.etStatus_pan.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int code = Integer.parseInt(jsonObject.getString("code"));
                    if (code == 1)
                    {
                        tambahBerhasil();
                    }else if(code == 0)
                    {
                        tambahGagal();
                    }


                    Toast.makeText(TambahPangkatActivity.this, "pesan : " + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                Toast.makeText(TambahPangkatActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
            }

        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("id_peg",idpeg.getText().toString());
                map.put("pangkat", pang);
                map.put("gol", gol);
                map.put("tmt_pangkat", tmt);
                map.put("status_pan", statPang);
                map.put("jns_pangkat", jns);
                map.put("no_sk", noSk);
                map.put("tgl_sk", tglSk);
                map.put("pejabat_sk", pej);


                return map;

            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void tambahBerhasil() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert
                .setMessage("Tambah Data Berhasil")
                .setCancelable(false)
                .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
                        session = sharedpreferences.getBoolean(LoginActivity.session_status, false);

                        id = sharedpreferences.getString(TAG_ID, id);

                        if(session) {
                            Intent sukses = new Intent(TambahPangkatActivity.this, RiwayatPegPegActivity.class);
                            sukses.putExtra(TAG_ID, id);
                            startActivity(sukses);
                            finish();
                        }
                    }
                });

        AlertDialog berhasil = alert.create();
        berhasil.show();
    }

    private void tambahGagal() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert
                .setMessage("Tambah Data Gagal")
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

    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                etTmt_pangat.setText(dateFormat.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    private void showDateDialog2() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                etTgl_sk.setText(dateFormat.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
