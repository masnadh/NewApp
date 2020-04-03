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
import id.masnadh.myapppeg.activities.DataActivity;
import id.masnadh.myapppeg.activities.LoginActivity;
import id.masnadh.myapppeg.activities.RiwayatPegPegActivity;
import id.masnadh.myapppeg.connections.AppController;
import id.masnadh.myapppeg.connections.Server;
import id.masnadh.myapppeg.models.JabatanModel;
import id.masnadh.myapppeg.models.PasutriModel;

public class TambahJabatanActivity extends AppCompatActivity {

    EditText etJab, etEse, etTmt, etStat;
    final String url = Server.URL+"insertJabatan.php";
    final String urlUp = Server.URL+"updateJabatan.php";
    final String urlDel = Server.URL+"deleteJabatan.php";
    Button btnbatalJab,btnsimpanJab;
    ProgressDialog pd;
    public final static String TAG_ID = "id";
    List<JabatanModel> mItems;
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
        setContentView(R.layout.activity_tambah_jabatan);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        Intent data = getIntent();
        final int update = data.getIntExtra("update",0);
        String intent_jab = data.getStringExtra("jabatan");
        String intent_ese = data.getStringExtra("eselon");
        String intent_tmt = data.getStringExtra("tmt_jabatan");
        String intent_stat = data.getStringExtra("status_jab");

        idpeg = (TextView) findViewById(R.id.id_peg);
        etJab = (EditText) findViewById(R.id.inp_jabatan);
        etEse = (EditText) findViewById(R.id.inp_eselon);
        etTmt = (EditText) findViewById(R.id.inp_tmt);
        etStat = (EditText) findViewById(R.id.inp_status_jab);
        etTmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        btnsimpanJab = (Button) findViewById(R.id.btn_simpan_jab);
        pd = new ProgressDialog(TambahJabatanActivity.this);

        if(update == 1)
        {
            btnsimpanJab.setText("Update Data");
            etJab.setText(intent_jab);
            //etNik.setVisibility(View.GONE);
            etEse.setText(intent_ese);
            etTmt.setText(intent_tmt);
            etStat.setText(intent_stat);
        }

        btnsimpanJab.setOnClickListener(new View.OnClickListener() {
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
        final String jabatan = this.etJab.getText().toString().trim();
        final String eselon = this.etEse.getText().toString().trim();
        final String tmtJab = this.etTmt.getText().toString().trim();
        final String statJab = this.etStat.getText().toString().trim();

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
                Toast.makeText(TambahJabatanActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("id_peg",idpeg.getText().toString());
                map.put("jabatan", jabatan);
                map.put("eselon", eselon);
                map.put("tmt_jabatan", tmtJab);
                map.put("status_jab", statJab);

                return map;

            }
        };

        AppController.getInstance().addToRequestQueue(updateReg);

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
                            Intent sukses = new Intent(TambahJabatanActivity.this, RiwayatPegPegActivity.class);
                            sukses.putExtra(TAG_ID, id);
                            finish();
                            startActivity(sukses);
                        }
                    }
                });

        AlertDialog berhasil = alert.create();
        berhasil.show();
    }

    private void simpanData() {
        final String ip = this.idpeg.getText().toString().trim();
        final String jabatan = this.etJab.getText().toString().trim();
        final String eselon = this.etEse.getText().toString().trim();
        final String tmtJab = this.etTmt.getText().toString().trim();
        final String statJab = this.etStat.getText().toString().trim();

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


                    Toast.makeText(TambahJabatanActivity.this, "pesan : " + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                Toast.makeText(TambahJabatanActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
            }

        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("id_peg",idpeg.getText().toString());
                map.put("jabatan", jabatan);
                map.put("eselon", eselon);
                map.put("tmt_jabatan", tmtJab);
                map.put("status_jab", statJab);


                return map;

            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);

    }

    private void tambahBerhasil() {
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
                            Intent sukses = new Intent(TambahJabatanActivity.this, RiwayatPegPegActivity.class);
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

    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                etTmt.setText(dateFormat.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
