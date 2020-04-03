package id.masnadh.myapppeg.tambahHapusData;

import androidx.appcompat.app.AppCompatActivity;

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
import id.masnadh.myapppeg.activities.DataActivity;
import id.masnadh.myapppeg.activities.LoginActivity;
import id.masnadh.myapppeg.activities.PendidikanActivity;
import id.masnadh.myapppeg.activities.TambahPendidikanActivity;
import id.masnadh.myapppeg.connections.AppController;
import id.masnadh.myapppeg.connections.Server;
import id.masnadh.myapppeg.fragments.PasutriFragment;
import id.masnadh.myapppeg.models.PasutriModel;
import id.masnadh.myapppeg.models.PendidikanModel;

public class TambahPasutriActivity extends AppCompatActivity {

    EditText etNik, etNama, etTmp, etTgl, etPend, etPek, etStatus;
    Button btnbatalPas,btnsimpanPas;
    ProgressDialog pd;
    public final static String TAG_ID = "id";
    List<PasutriModel> mItems;
    TextView idpeg;
    SharedPreferences sharedpreferences;
    Boolean session = false;
    final String TAG ="Edit";
    String id;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pasutri);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        Intent data = getIntent();
        final int update = data.getIntExtra("update",0);
        String intent_nik = data.getStringExtra("nik");
        String intent_nama = data.getStringExtra("nama");
        String intent_tmp = data.getStringExtra("tmp_lhr");
        String intent_tgl = data.getStringExtra("tgl_lhr");
        String intent_pen = data.getStringExtra("pendidikan");
        String intent_pek = data.getStringExtra("pekerjaan");
        String intent_stat_pas = data.getStringExtra("status_hub");

        idpeg = (TextView)findViewById(R.id.id_peg);
        etNik = (EditText) findViewById(R.id.inp_nik_pas);
        etNama = (EditText) findViewById(R.id.inp_nama_pas);
        etTmp = (EditText) findViewById(R.id.inp_tmp_pas);
        etTgl = (EditText) findViewById(R.id.inp_tgl_pas);
        etTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });


        etPend = (EditText) findViewById(R.id.inp_pend_pas);
        etPek = (EditText) findViewById(R.id.inp_pek_pas);
        etStatus = (EditText) findViewById(R.id.inp_status_pas);
        btnbatalPas = (Button) findViewById(R.id.btn_cancel_pend);
        btnsimpanPas = (Button) findViewById(R.id.btn_simpan_pend);
        pd = new ProgressDialog(TambahPasutriActivity.this);

        if(update == 1)
        {
            btnsimpanPas.setText("Update Data");
            etNik.setText(intent_nik);
            //etNik.setVisibility(View.GONE);
            etNama.setText(intent_nama);
            etTmp.setText(intent_tmp);
            etTgl.setText(intent_tgl);
            etPend.setText(intent_pen);
            etPek.setText(intent_pek);
            etStatus.setText(intent_stat_pas);
        }

        btnsimpanPas.setOnClickListener(new View.OnClickListener() {
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

        btnbatalPas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(TambahPasutriActivity.this, DataActivity.class);
                main.putExtra(TAG_ID, id);
                startActivity(main);
            }
        });

        String id_peg = getIntent().getStringExtra(TAG_ID);
        idpeg.setText(id_peg);
    }

    private void simpanData() {

        final String ip = this.idpeg.getText().toString().trim();
        final String nik = this.etNik.getText().toString().trim();
        final String nama = this.etNama.getText().toString().trim();
        final String tmp = this.etTmp.getText().toString().trim();
        final String tgl = this.etTgl.getText().toString().trim();
        final String stat = this.etStatus.getText().toString().trim();
        final String pend = this.etPend.getText().toString().trim();
        final String pek = this.etPek.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URL_INSERT_PAS, new Response.Listener<String>() {
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


                    Toast.makeText(TambahPasutriActivity.this, "pesan : " + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                Toast.makeText(TambahPasutriActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
            }

        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("id_peg",idpeg.getText().toString());
                map.put("nik", nik);
                map.put("nama", nama);
                map.put("tmp_lhr", tmp);
                map.put("tgl_lhr", tgl);
                map.put("pendidikan", pend);
                map.put("pekerjaan", pek);
                map.put("status_hub", stat);

                return map;

            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public void daftarBerhasil()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert
                .setMessage("Penambahan Data Suami/Istri Berhasil")
                .setCancelable(false)
                .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
                        session = sharedpreferences.getBoolean(LoginActivity.session_status, false);

                        id = sharedpreferences.getString(TAG_ID, id);

                        if(session) {
                            Intent sukses = new Intent(TambahPasutriActivity.this, DataActivity.class);
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

    private void updateData(){
        final String ip = this.idpeg.getText().toString().trim();
        final String nik = this.etNik.getText().toString().trim();
        final String nama = this.etNama.getText().toString().trim();
        final String tmp = this.etTmp.getText().toString().trim();
        final String tgl = this.etTgl.getText().toString().trim();
        final String stat = this.etStatus.getText().toString().trim();
        final String pend = this.etPend.getText().toString().trim();
        final String pek = this.etPek.getText().toString().trim();

        StringRequest updateReg = new StringRequest(Request.Method.POST, Server.URL_UPDATE_PAS, new Response.Listener<String>() {
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
                Toast.makeText(TambahPasutriActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("id_peg",idpeg.getText().toString());
                map.put("nik", nik);
                map.put("nama", nama);
                map.put("tmp_lhr", tmp);
                map.put("tgl_lhr", tgl);
                map.put("pendidikan", pend);
                map.put("pekerjaan", pek);
                map.put("status_hub", stat);

                return map;

            }
        };

        AppController.getInstance().addToRequestQueue(updateReg);
    }

    public void updaterBerhasil()
    {
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
                            Intent sukses = new Intent(TambahPasutriActivity.this, DataActivity.class);
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

    private void showDateDialog()
    {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                etTgl.setText(dateFormat.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

}
