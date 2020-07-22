package id.masnadh.myapppeg.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.masnadh.myapppeg.MainActivity;
import id.masnadh.myapppeg.R;
import id.masnadh.myapppeg.adapters.PendidikanAdapter;
import id.masnadh.myapppeg.connections.AppController;
import id.masnadh.myapppeg.connections.Server;
import id.masnadh.myapppeg.fragments.HomeFragment;
import id.masnadh.myapppeg.models.PendidikanModel;
import id.masnadh.myapppeg.newActivities.SekolahActivity;

public class PendidikanActivity extends AppCompatActivity  {

    public static final String EXTRA_MENU = "extra_menu_pendidikan";

    String id;
    RecyclerView mRecyclerView;
    Button btnInsert, btnDelete;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    List<PendidikanModel> mItems;
    ProgressDialog pd;
    public final static String TAG_ID = "id";

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendidikan);

        Intent intent = getIntent();

//        Parcelable[] dashboard = intent.getParcelableArrayExtra(EXTRA_MENU);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvPend);
        btnInsert = (Button) findViewById(R.id.btn_insertPend);
        btnDelete = (Button) findViewById(R.id.btn_deletePend);
        mItems = new ArrayList<>();

        mManager = new LinearLayoutManager(PendidikanActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
        mAdapter = new PendidikanAdapter(mItems, PendidikanActivity.this);
        mRecyclerView.setAdapter(mAdapter);

        toolbar = (Toolbar) findViewById(R.id.PendToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        getSupportActionBar().setTitle("Pendidikan");

        loadJson();
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id = getIntent().getStringExtra(TAG_ID);
                Intent intent = new Intent(PendidikanActivity.this,TambahPendidikanActivity.class);
                intent.putExtra(TAG_ID,Id);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = getIntent().getStringExtra(TAG_ID);
                Intent hapus = new Intent(PendidikanActivity.this,DeleteActivity.class);
                hapus.putExtra(TAG_ID,Id);
                startActivity(hapus);
            }
        });

    }

    private void loadJson() {

//        pd.setMessage("Mengambil Data");
//        pd.setCancelable(false);
//        pd.show();

//        pd = new ProgressDialog(PendidikanActivity.this);
//        pd.setMessage("Proses Pengambilan Data, Mohon Tunggu...");
//        pd.show();

        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, Server.URL_PEND,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // pd.cancel();
                        Log.d("volley","response : " + response.toString());
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                PendidikanModel pm = new PendidikanModel();
                                int extraId = Integer.parseInt(getIntent().getStringExtra(TAG_ID));
                                int id = data.getInt("id_peg");

                                if ( extraId == id) {
                                    pm.setJenjang(data.getString("tingkat"));
//                                    pm.setJenjang(Integer.toString().extraId);
                                    pm.setNamaSek(data.getString("nama_sekolah"));
                                    pm.setProdi(data.getString("jurusan"));
                                    pm.setLulus(data.getString("thn_lulus"));
                                    mItems.add(pm);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        pd.cancel();
                        Log.d("volley", "error : " + error.getMessage());
                    }

                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();

                return parameters;
            }
        };

        AppController.getInstance().addToRequestQueue(reqData);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        super.onBackPressed();
    }


    //    public void onBackPressed()
//    {
//        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//
////        Intent intent = new Intent(PendidikanActivity.this, MainActivity.class);
////        startActivity(intent);
////        finish();
//    }

//    public static final String EXTRA_MENU = "extra_menu_pendidikan";
//
//    Toolbar toolbar;
//    FloatingActionButton fab;
//    ListView list;
//    SwipeRefreshLayout swipe;
//    int success;
//    AlertDialog.Builder dialog;
//    View dialogView;
//    LayoutInflater inflater;
//
//        String id;
//    RecyclerView mRecyclerView;
//    Button btnInsert, btnDelete;
//    PendidikanAdapter adapter;
//    RecyclerView recyclerView;
//    RecyclerView.Adapter mAdapter;
//    RecyclerView.LayoutManager mManager;
//        List<PendidikanModel> mItems;
//    List itemList = new ArrayList();
//    ProgressDialog pd;
//
////    EditText txt_id, txt_nama, txt_tingkat, txt_jurusan, txt_lulus;
////    String namaSek, tingkat, jurusan, id, lulus;
//
//    public final static String TAG_ID = "id";
//
//    private static final String TAG = PendidikanActivity.class.getSimpleName();
//
//    private static String url_select = Server.URL + "pendidikan.php";
//    private static String url_insert = Server.URL + "insertPendBaru.php";
//    private static String url_edit = Server.URL + "editPendBaru.php";
//    private static String url_update = Server.URL + "updatePendBaru.php";
//    private static String url_delete = Server.URL + "deletePendBaru.php";
//
//    //private static final String TAG = PendidikanActivity.class.getSimpleName();
//
//    public static final String TAG_NAMA = "nama_sekolah";
//    public static final String TAG_TINGKAT = "tingkat";
//    public static final String TAG_JURUSAN = "jurusan";
//    public static final String TAG_LULUS = "thn_lulus";
//
//    private static final String TAG_SUCCESS = "success";
//    private static final String TAG_MESSAGE = "message";
//
//    String tag_json_obj = "json_obj_req";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pendidikan);
//
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        fab = (FloatingActionButton) findViewById(R.id.fab_add);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(PendidikanActivity.this, TambahPendidikanActivity.class);
//                startActivity(intent);
//            }
//        });
//
////        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
////        list = (ListView) findViewById(R.id.list);
//
////        adapter = new PendidikanAdapter(PendidikanActivity.this, itemList);
////        list.setAdapter(adapter);
//
////        swipe.setOnRefreshListener(this);
////
////        swipe.post(new Runnable() {
////            @Override
////            public void run() {
////                swipe.setRefreshing(true);
////                itemList.clear();
////                adapter.notifyDataSetChanged();
////                callVolley();
////            }
////        });
////
////
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                DialogForm("", "", "", "", "SIMPAN");
////            }
////        });
////
////
////        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
////            @Override
////            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, final long id) {
////
////                final String idx = itemList.get(position).toString();
////
////                final CharSequence[] dialogItem = {"Edit", "Delete"};
////                dialog = new AlertDialog.Builder(PendidikanActivity.this);
////                dialog.setCancelable(true);
////                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialog, int which) {
////                        switch (which) {
////                            case 0:
////                                edit(idx);
////                                break;
////                            case 1:
////                                delete(idx);
////                                break;
////                        }
////                    }
////                }).show();
////                return false;
////            }
////        });
//
//        Intent intent = getIntent();
//
//        Parcelable[] dashboard = intent.getParcelableArrayExtra(EXTRA_MENU);
//
//        mRecyclerView = (RecyclerView) findViewById(R.id.rvPend);
////        btnInsert = (Button) findViewById(R.id.btn_insertPend);
////        btnDelete = (Button) findViewById(R.id.btn_deletePend);
//        mItems = new ArrayList<>();
//
//        mManager = new LinearLayoutManager(PendidikanActivity.this, LinearLayoutManager.VERTICAL, false);
//        mRecyclerView.setLayoutManager(mManager);
//        mAdapter = new PendidikanAdapter(mItems, PendidikanActivity.this);
//        mRecyclerView.setAdapter(mAdapter);
//
//        toolbar = (Toolbar) findViewById(R.id.PendToolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
//        getSupportActionBar().setTitle("Pendidikan");
//
//        loadJson();
//
////        btnInsert.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                String Id = getIntent().getStringExtra(TAG_ID);
////                Intent intent = new Intent(PendidikanActivity.this,TambahPendidikanActivity.class);
////                intent.putExtra(TAG_ID,Id);
////                startActivity(intent);
////            }
////        });
////
////        btnDelete.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                String Id = getIntent().getStringExtra(TAG_ID);
////                Intent hapus = new Intent(PendidikanActivity.this,DeleteActivity.class);
////                hapus.putExtra(TAG_ID,Id);
////                startActivity(hapus);
////            }
////        });
//
//    }
//
////    @Override
////    public void onRefresh() {
////        itemList.clear();
////        adapter.notifyDataSetChanged();
////        callVolley();
////
////    }
//
//
////    private void DialogForm(String id, String namaSek, String tingkat, String jurusan, String lulus) {
////        dialog = new AlertDialog.Builder(PendidikanActivity.this);
////        inflater = getLayoutInflater();
////        dialogView = inflater.inflate(R.layout.activity_tambah_pendidikan, null);
////        dialog.setView(dialogView);
////        dialog.setCancelable(true);
////        dialog.setIcon(R.drawable.ic_account_circle_black_24dp);
////        dialog.setTitle("Pendidikan");
////    }
////
////    private void kosong() {
////        txt_nama.setText(null);
////        txt_tingkat.setText(null);
////        txt_jurusan.setText(null);
////        txt_lulus.setText(null);
////    }
////
////
////    private void DialogForm(String idx, String namaSekx, String jurusanx, String tingkatx, String lulusx, String button) {
////        dialog = new AlertDialog.Builder(PendidikanActivity.this);
////        inflater = getLayoutInflater();
////        dialogView = inflater.inflate(R.layout.activity_tambah_pendidikan, null);
////        dialog.setView(dialogView);
////        dialog.setCancelable(true);
////        dialog.setIcon(R.drawable.ic_account_circle_black_24dp);
////        dialog.setTitle("Kontak");
////
////        txt_nama = (EditText) dialogView.findViewById(R.id.inp_nama_sekolah);
////        txt_jurusan = (EditText) dialogView.findViewById(R.id.inp_prodi);
////        txt_tingkat = (EditText) dialogView.findViewById(R.id.inp_jenjang);
////        txt_lulus = (EditText) dialogView.findViewById(R.id.inp_lulus);
////
////        if (!namaSek.isEmpty()) {
////            //txt_id.setText(idx);
////            txt_nama.setText(namaSekx);
////            txt_jurusan.setText(jurusanx);
////            txt_tingkat.setText(tingkatx);
////            txt_lulus.setText(lulusx);
////        } else {
////            kosong();
////        }
////
////        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {
////
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////                id = txt_id.getText().toString();
////                namaSek = txt_nama.getText().toString();
////                tingkat = txt_tingkat.getText().toString();
////                jurusan = txt_jurusan.getText().toString();
////                lulus = txt_lulus.getText().toString();
////                simpan_update();
////                dialog.dismiss();
////            }
////        });
////
////        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
////
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////                dialog.dismiss();
////                kosong();
////            }
////        });
////
////        dialog.show();
////    }
////
////
////    private void callVolley() {
////        itemList.clear();
////        adapter.notifyDataSetChanged();
////        swipe.setRefreshing(true);
////
////        // membuat request JSON
////        JsonArrayRequest jArr = new JsonArrayRequest(url_select, new Response.Listener<JSONArray>() {
////            @Override
////            public void onResponse(JSONArray response) {
////                Log.d(TAG, response.toString());
////
////                // Parsing json
////                for (int i = 0; i < response.length(); i++) {
////                    try {
////                        JSONObject obj = response.getJSONObject(i);
////
////                        PendidikanModel item = new PendidikanModel();
////
////                        item.setId(obj.getString(TAG_ID));
////                        item.setNamaSek(obj.getString(TAG_NAMA));
////                        item.setJenjang(obj.getString(TAG_TINGKAT));
////                        item.setProdi(obj.getString(TAG_JURUSAN));
////                        item.setLulus(obj.getString(TAG_LULUS));
////
////                        // menambah item ke array
////                        itemList.add(item);
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                }
////
////                // notifikasi adanya perubahan data pada adapter
////                adapter.notifyDataSetChanged();
////
////                swipe.setRefreshing(false);
////            }
////        }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////                VolleyLog.d(TAG, "Error: " + error.getMessage());
////                swipe.setRefreshing(false);
////            }
////        });
////
////
////            AppController.getInstance().addToRequestQueue(jArr);
////        }
////
////        private void simpan_update() {
////            String url;
////
////            if (id.isEmpty()) {
////                url = url_insert;
////            } else {
////                url = url_update;
////            }
////
////            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
////                @Override
////                public void onResponse(String response) {
////                    Log.d(TAG, "Response: " + response.toString());
////
////                    try {
////                        JSONObject jObj = new JSONObject(response);
////                        success = jObj.getInt(TAG_SUCCESS);
////
////                        if (success == 1) {
////                            Log.d("Add/Update", jObj.toString());
////
////                            callVolley();
////                            kosong();
////
////                            Toast.makeText(PendidikanActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
////                            adapter.notifyDataSetChanged();
////
////                        } else {
////                            Toast.makeText(PendidikanActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
////                        }
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                }
////            }, new Response.ErrorListener() {
////                @Override
////                public void onErrorResponse(VolleyError error) {
////                    Log.e(TAG, "Error: " + error.getMessage());
////                    Toast.makeText(PendidikanActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
////                }
////            }) {
////
////                @Override
////                protected Map getParams() {
////                    // Posting parameters ke post url
////                    Map params = new HashMap();
////
////                    if (id.isEmpty()) {
////                        params.put("nama_sekolah", namaSek);
////                        params.put("tingkat", tingkat);
////                        params.put("jurusan", jurusan);
////                        params.put("thn_lulus", lulus);
////                    } else {
////                        params.put("id_peg", id);
////                        params.put("nama_sekolah", namaSek);
////                        params.put("tingkat", tingkat);
////                        params.put("jurusan", jurusan);
////                        params.put("thn_lulus", lulus);
////                    }
////
////                    return params;
////                }
////
////            };
////
////            AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
////        }
////
////
////
////    private void edit(final String idx) {
////        StringRequest strReq = new StringRequest(Request.Method.POST, url_edit, new Response.Listener<String>() {
////            @Override
////            public void onResponse(String response) {
////                Log.d(TAG, "Response: " + response.toString());
////                try {
////                    JSONObject jObj = new JSONObject(response);
////                    success = jObj.getInt(TAG_SUCCESS);
////
////                    if (success == 1) {
////                        Log.d("get edit data", jObj.toString());
////                        String id = jObj.getString(TAG_ID);
////                        String namaSek = jObj.getString(TAG_NAMA);
////                        String tingkat = jObj.getString(TAG_TINGKAT);
////                        String jurusan = jObj.getString(TAG_JURUSAN);
////                        String lulus = jObj.getString(TAG_LULUS);
////
////                        DialogForm(id, namaSek, tingkat, jurusan, lulus, "UPDATE");
////
////                        adapter.notifyDataSetChanged();
////                    } else {
////                        Toast.makeText(PendidikanActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
////                    }
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
////            }
////        }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////                Log.e(TAG, "Error: " + error.getMessage());
////                Toast.makeText(PendidikanActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
////            }
////        }) {
////
////            @Override
////            protected Map getParams() {
////                // Posting parameters ke post url
////                Map params = new HashMap();
////                params.put("id_peg", idx);
////
////                return params;
////            }
////
////        };
////
////        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
////    }
////
////    // fungsi untuk menghapus
////    private void delete(final String idx) {
////        StringRequest strReq = new StringRequest(Request.Method.POST, url_delete, new Response.Listener<String>() {
////            @Override
////            public void onResponse(String response) {
////                Log.d(TAG, "Response: " + response.toString());
////                try {
////                    JSONObject jObj = new JSONObject(response);
////                    success = jObj.getInt(TAG_SUCCESS);
////
////                    if (success == 1) {
////                        Log.d("delete", jObj.toString());
////
////                        callVolley();
////
////                        Toast.makeText(PendidikanActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
////
////                        adapter.notifyDataSetChanged();
////                    } else {
////                        Toast.makeText(PendidikanActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
////                    }
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
////            }
////        }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////                Log.e(TAG, "Error: " + error.getMessage());
////                Toast.makeText(PendidikanActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
////            }
////        }) {
////
////            @Override
////            protected Map getParams() {
////                // Posting parameters ke post url
////                Map params = new HashMap();
////                params.put("id_peg", idx);
////
////                return params;
////            }
////
////        };
////
////        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
////    }
//
//    private void loadJson() {
////        pd.setMessage("Mengambil Data");
////        pd.setCancelable(false);
////        pd.show();
//
////        pd = new ProgressDialog(PendidikanActivity.this);
////        pd.setMessage("Proses Pengambilan Data, Mohon Tunggu...");
////        pd.show();
//
////        itemList.clear();
////        adapter = new PendidikanAdapter(itemList, getApplicationContext());
////        recyclerView.setAdapter(mAdapter);
//
////        recyclerView.setItemAnimator(new DefaultItemAnimator());
////        pd.setMessage("Loading...");
////        pd.show();
//
//        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, Server.URL_PEND,null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                       // pd.cancel();
//                        Log.d("volley","response : " + response.toString());
//                        for(int i = 0 ; i < response.length(); i++)
//                        {
//                            try {
//                                JSONObject data = response.getJSONObject(i);
//                                PendidikanModel pm = new PendidikanModel();
//                                int extraId = Integer.parseInt(getIntent().getStringExtra(TAG_ID));
//                                int id = data.getInt("id_peg");
//
//                                if ( extraId == id) {
//                                    pm.setJenjang(data.getString("tingkat"));
//                                    pm.setNamaSek(data.getString("nama_sekolah"));
//                                    pm.setProdi(data.getString("jurusan"));
//                                    pm.setLulus(data.getString("thn_lulus"));
//                                    mItems.add(pm);
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        mAdapter.notifyDataSetChanged();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                        pd.cancel();
//                        Log.d("volley", "error : " + error.getMessage());
//                    }
//
//                }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> parameters = new HashMap<String, String>();
//
//                return parameters;
//            }
//        };
//
//        AppController.getInstance().addToRequestQueue(reqData);
//    }
//
//
//
//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(PendidikanActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();
////        finish();
//////        startActivity(new Intent(getApplicationContext(), MainActivity.class));
////        startActivity(getIntent());
//////        super.onBackPressed();
//    }
////
////
////            public void onBackPressed()
////    {
////        startActivity(new Intent(getApplicationContext(), MainActivity.class));
////
//////        Intent intent = new Intent(PendidikanActivity.this, MainActivity.class);
//////        startActivity(intent);
//////        finish();
////    }

    }

