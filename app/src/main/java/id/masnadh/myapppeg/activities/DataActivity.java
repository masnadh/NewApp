package id.masnadh.myapppeg.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.google.android.material.tabs.TabLayout;

import id.masnadh.myapppeg.MainActivity;
import id.masnadh.myapppeg.R;
import id.masnadh.myapppeg.connections.Server;
import id.masnadh.myapppeg.fragments.HomeFragment;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataActivity extends AppCompatActivity  {

    TextView namaUser, ttlUser, kodeKelas, jurusan;
    String idu,id;
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public final static String TAG = "DataActivity";
    public final static String TAG_ID = "id";
    PagerAdapter pagerAdapter;
    Button btnFoto;
    ImageView fotoPeg;
    Boolean session = false;
    public static final String TAG_USERNAME = "username";

    final String ambilfoto = Server.URL+"pegawai.php";

    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    ArrayList <HashMap<String, String>> data_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
//        btnrefresh = (Button) findViewById(R.id.btnrefreshData);
//
//        btnrefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recreate();
//            }
//        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.profileToolbar);
        setSupportActionBar(toolbar);

        ambilfoto();

        fotoPeg = (ImageView) findViewById(R.id.fotoProfile1);
//        progressDialog = new ProgressDialog(DataActivity.this);
//        progressDialog.setMessage("Proses Pengambilan Data, Mohon Tunggu...");
//        progressDialog.show();

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(LoginActivity.session_status, false);
        idu = sharedpreferences.getString(TAG_ID, idu);
        id = sharedpreferences.getString(TAG_ID, id);
//        levelU = sharedpreferences.getString(TAG_LEVEL, null);


        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });

        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        getSupportActionBar().setTitle("Data Pegawai");

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        //Memanggil dan Memasukan Value pada Class PagerAdapter(FragmentManager dan JumlahTab)
        PagerAdapter pagerAdapter = new id.masnadh.myapppeg.adapters.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        //Memasang Adapter pada ViewPager
        viewPager.setAdapter(pagerAdapter);

        /*
         Menambahkan Listener yang akan dipanggil kapan pun halaman berubah atau
         bergulir secara bertahap, sehingga posisi tab tetap singkron
         */
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Callback Interface dipanggil saat status pilihan tab berubah.
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Dipanggil ketika tab memasuki state/keadaan yang dipilih.
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Dipanggil saat tab keluar dari keadaan yang dipilih.
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Dipanggil ketika tab yang sudah dipilih, dipilih lagi oleh user.
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.logout:{

//                AlertDialog.Builder alert = new AlertDialog.Builder(this);
//                alert
//                        .setMessage("Apa Anda  Yakin Ingin Keluar")
//                        .setCancelable(false)
//                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        })

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginActivity.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();

                Intent intent = new Intent(DataActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);

                //Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;
            }

        }

        return super.onOptionsItemSelected(item);

        //    //final String url = "http://152746201341.ip-dynamic.com/ptk/api/pegawai.php";
//    private static final String TAG = DataActivity.class.getSimpleName();
//    private String url_select = "http://152746201341.ip-dynamic.com/ptk/api/pegawai.php";
//
//    public final static String TAG_IDU = "idu";
//
//    //private Context mContext;
//
////    private RecyclerView recyclerView;
////    private DashboardAdapter adapter;
////    private ArrayList<dashboard> dashboards = new ArrayList<>();
////    private String[] menuName;
////    private TypedArray menuPhoto;
//
//    ImageView ivPhoto;
//    TextView tvNama, tvNip, tvTempat, tvLahir, tvAgama, tvJenis, tvGolDa, tvStatus, tvAlamat, tvTelp, tvEmail;
////    String id;
////    SharedPreferences sharedPreferences;
//
//    RecyclerView recyclerView;
//
//    ArrayList<Pegawai> mResults;
//    PegawaiAdapter adapter;
//
//    RequestQueue requestQueue;
//    StringRequest stringRequest;
//
//    RecyclerView mRecyclerview;
//    RecyclerView.Adapter mAdapter;
//    RecyclerView.LayoutManager mManager;
//
//    ProgressDialog progressDialog;
//
//    RelativeLayout rlTop;
//    AppBarLayout appBar;
//    CollapsingToolbarLayout ctLayout;
//    Toolbar toolbar;
//    CardView cardView;
//
//    boolean ExpandedActionBar = true;
//
//    List<Pegawai> mItems = new ArrayList<Pegawai>();
//
//    public static final String TAG_ID = "id_peg";
//    public static final String TAG_NAMA = "nama";
//    public static final String TAG_TLP = "telepon";
//    public static final String TAG_EMAIL = "email";
//
//    public static final String EXTRA_MENU = "extra_menu_data";
//
//    String tag_json_obj = "json_obj_req";
//
////    public final static String TAG_IDU = "idu";
////    public final static String TAG="Profile";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_data);
//
//        rlTop = findViewById(R.id.data_rlTop);
//        appBar = findViewById(R.id.data_appBar);
//        ctLayout = findViewById(R.id.data_cToolbar);
//        toolbar = findViewById(R.id.data_toolbar);
//
//        this.setSupportActionBar(toolbar);
//        ctLayout.setTitle("");
//
////        adapter = new PegawaiAdapter(DataActivity.this, mItems);
////        list.setAdapter(adapter);
//
////        recyclerView = (RecyclerView) findViewById(R.id.rvData);
//        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mItems = new ArrayList<>();
////
////
////        mResults = new ArrayList<>();
//
//        ctLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white));
//        ctLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.colorPrimary));
//
//        appBar.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (Math.abs(verticalOffset) > 200){
//                    ExpandedActionBar = false;
//                    ctLayout.setTitle("Data Pegawai");
//                    rlTop.setVisibility(View.GONE);
//                    invalidateOptionsMenu();
//                } else {
//                    ExpandedActionBar = true;
//                    ctLayout.setTitle("");
//                    rlTop.setVisibility(View.VISIBLE);
//                    invalidateOptionsMenu();
//                }
//            }
//        });
//
////        ArrayList<dashboard> dashboards = this.getIntent().getParcelableArrayListExtra(EXTRA_MENU);
//
//       // loadJson();
////        Intent intent = getIntent();
////
////        Parcelable[] dashboard = intent.getParcelableArrayExtra(EXTRA_MENU);
//
//        tvNama = findViewById(R.id.detail_nama);
//        tvNip = findViewById(R.id.detail_nip);
//        tvTempat = findViewById(R.id.detail_tempat_lahir);
//        tvLahir = findViewById(R.id.detail_tanggal_lahir);
//        tvAgama = findViewById(R.id.detail_agama);
//        tvJenis = findViewById(R.id.detail_jk);
//        tvGolDa = findViewById(R.id.detail_golDarah);
//        tvStatus = findViewById(R.id.detail_nikah);
//        tvAlamat = findViewById(R.id.detail_alamat);
//        tvTelp = findViewById(R.id.detail_telp);
//        tvEmail = findViewById(R.id.detail_email);
//        ivPhoto = findViewById(R.id.ivPhoto);
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_select, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try {
//                    JSONArray dataArray = new JSONArray(response);
//
//                    for (int i = 0; i < dataArray.length(); i++) {
//
//                        JSONObject object = dataArray.getJSONObject(i);
//                        Pegawai mPegawai = new Pegawai();
//                        mPegawai.setNama(object.getString("nama"));
//                        mPegawai.setNip(object.getString("nip"));
//                        mPegawai.setTempatLahir(object.getString("tempat_lhr"));
//                        mPegawai.setTangalLahir(object.getString("tgl_lhr"));
//                        mItems.add(mPegawai);
//                    }
//
//                    Log.d(TAG, "onResponse:" + response);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                tvNama.setText(error.getLocalizedMessage());
//            }
//        });
//
//
//
//        requestQueue.add(stringRequest);
////        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
//
//        //parseJson();
//
////        requestQueue = Volley.newRequestQueue(DataActivity.this);
////
////        list_data = new ArrayList<HashMap<String, String>>();
////
////        stringRequest = new StringRequest(Request.Method.GET, url_select, new Response.Listener<String>() {
////            @Override
////            public void onResponse(String response) {
////                try {
////                    JSONObject jsonObject = new JSONObject(response);
////                    JSONArray jsonArray = jsonObject.getJSONArray("pegawai");
////
////                    for (int i = 0; i < jsonArray.length(); i++) {
////                        JSONObject object = jsonArray.getJSONObject(i);
////                        HashMap<String, String> map = new HashMap<String, String>();
////                        map.put("id_peg", object.getString("id_peg"));
////                        map.put("nama", object.getString("nama"));
////                        map.put("nip", object.getString("nip"));
////
////                        list_data.add(map);
////                    }
////
////                    Glide.with(getApplicationContext())
////                            .load(url_select + list_data.get(0).get("foto"))
////                            .centerCrop()
////                            .placeholder(R.mipmap.ic_launcher)
////                            .into(ivPhoto);
////
////                    tvNama.setText(list_data.get(0).get("nama"));
////
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
////            }
////        }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////                Toast.makeText(DataActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
////            }
////        });
////
////        requestQueue.add(stringRequest);
//
//        //RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());
////        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
////            @Override
////            public void onResponse(String response) {
////                try {
////                    JSONArray dataArray = new JSONArray(response);
////
////                    for (int i = 0; i < dataArray.length(); i++) {
////                        JSONObject object = dataArray.getJSONObject(i);
////                        int id = object.getInt("id_peg");
////                        String nama = object.getString("nama");
////                        String nip = object.getString("nip");
////                        String tempatLahir = object.getString("tempat_lhr");
////
////
////
//////                        if (extraId == id){
//////                            tvNama.setText(nama);
//////                            tvNip.setText(nip);
//////                            tvTempat.setText(tempatLahir);
//////                        }
////                    }
////
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
////            }
////        }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////                tvNama.setText(error.getLocalizedMessage());
////            }
////        });
////
////        requestQueue.add(stringRequest);
////        AppController.getInstance().addToRequestQueue(stringRequest);
//    }
//
////    private void parseJson() {
////
//////        progressDialog.setMessage("Mengambil Data");
//////        progressDialog.setCancelable(false);
//////        progressDialog.show();
////
////        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url_select, null,
////                new Response.Listener<JSONArray>() {
////                    @Override
////                    public void onResponse(JSONArray response) {
////                        progressDialog.cancel();
////                        Log.d("volley", "response : " + response.toString());
////                        for (int i = 0; i < response.length(); i++) {
////
////                            try {
////                                JSONObject data = response.getJSONObject(i);
////                                pegawai mPegawai = new pegawai();
////                                mPegawai.setNama(data.getString("nama"));
////                                mPegawai.setNip(data.getString("nip"));
////                                mPegawai.setTempatLahir(data.getString("tempat_lhr"));
////                                mPegawai.setTanggalLahir(data.getString("tanggal_lhr"));
////                                mItems.add(mPegawai);
////
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
////                            mAdapter.notifyDataSetChanged();
////                        }
////                    }
////                }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////
////                progressDialog.cancel();
////                Log.d("volley", "error : " + error.getMessage());
////
////            }
////        });
////
////        AppController.getInstance().addToRequestQueue(request);
////
////    }
////
//////    private void parseJson() {
//////
//////        String url = "http://152746201341.ip-dynamic.com/ptk/api/pegawai.php";
//////
//////        RequestQueue  requestQueue = Volley.newRequestQueue(this.getApplicationContext());
//////        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url_select, null, new Response.Listener<JSONArray>() {
//////            @Override
//////            public void onResponse(JSONArray response) {
//////
//////                try {
//////                    for (int i =0; i<response.length(); i++){
//////
//////                        JSONObject data = response.getJSONObject(i);
//////                        pegawai pegawaiData = new pegawai();
//////                        pegawaiData.setNama(data.getString("nama"));
//////                        pegawaiData.setNip(data.getString("nip"));
//////                        pegawaiData.setTempatLahir(data.getString("tempat_lhr"));
//////                        mItems.add(pegawaiData);
//////
////////                        JSONObject hit = response.getJSONObject(i);
////////                        String name = hit.getString("nama");
////////                        String image = hit.getString("foto");
//////////                        mResults.add(new pegawai(name, image));
//////////                        adapter = new PegawaiAdapter(mResults, DataActivity.this);
//////////                        recyclerView.setAdapter(adapter);
//////                    }
//////                } catch (JSONException e) {
//////                    e.printStackTrace();
//////                }
//////
//////
//////            }
//////        }, new Response.ErrorListener() {
//////            @Override
//////            public void onErrorResponse(VolleyError error) {
//////
//////            }
//////        });
//////
//////        requestQueue.add(jsonArrayRequest);
//////        requestQueue.getCache().clear();
//////
//////    }
////
//////    private void loadJson(){
//////
//////        progressDialog = new ProgressDialog(DataActivity.this);
//////        progressDialog.setMessage("Mengambil Data");
//////        progressDialog.setIndeterminate(false);
//////        progressDialog.setCancelable(true);
//////        progressDialog.show();
//////
//////        JsonArrayRequest requestData = new JsonArrayRequest(Request.Method.GET, url_select, null, new Response.Listener<JSONArray>() {
//////            @Override
//////            public void onResponse(JSONArray response) {
//////                progressDialog.cancel();
//////                Log.d("volley", "response : " + response.toString());
//////                for (int i = 0; i < response.length(); i++) {
//////                    try {
//////                        JSONObject data = response.getJSONObject(i);
//////                        pegawai pegawaiData = new pegawai();
//////                        pegawaiData.setNama(data.getString("nama"));
//////                        pegawaiData.setNip(data.getString("nip"));
//////                        pegawaiData.setTempatLahir(data.getString("tempat_lhr"));
//////                        mItems.add(pegawaiData);
//////                    } catch (JSONException e) {
//////                        e.printStackTrace();
//////                    }
//////                }
//////
//////            }
//////        }, new Response.ErrorListener() {
//////            @Override
//////            public void onErrorResponse(VolleyError error) {
//////                progressDialog.cancel();
//////                Log.d("volley", "error : " + error.getMessage());
//////            }
//////        });
//////
//////       // AppController.getInstance().addToRequestQueue(requestData);
//////
//////    }
    }

    private void ambilfoto()
    {
        progressDialog = new ProgressDialog(DataActivity.this);
        progressDialog.setMessage("Proses Pengambilan Data, Mohon Tunggu...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.GET, ambilfoto, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray dataArray= new JSONArray(response);
                    progressDialog.dismiss();
                    for (int i =0; i<dataArray.length(); i++) {

                        JSONObject obj = dataArray.getJSONObject(i);
                        int extraId = Integer.parseInt(getIntent().getStringExtra(TAG_ID));

                        int id = obj.getInt("id_peg");
                        if (extraId == id) {
                            String fotobase64 = obj.getString("foto");
                            byte[] decodedString = Base64.decode(fotobase64, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                            if (extraId== id ) {

                                if (fotobase64.isEmpty()) {

                                    Picasso.with(getApplication()).load("http://smknprigen.sch.id/bkk/image/default.png").into(fotoPeg);
                                } else if (fotobase64.equals("null")) {

                                    Picasso.with(getApplication()).load("http://smknprigen.sch.id/bkk/image/default.png").into(fotoPeg);
                                } else {

                                    fotoPeg.setImageBitmap(decodedByte);
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(DataActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            //adding parameters to send
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();

                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(DataActivity.this);
        rQueue.add(request);
    }


//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setTitle("Apa Anda Ingin Keluar ?");
//        builder.setCancelable(false);
//        builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                DataActivity.this.finish();
//            }
//        });
//        builder.setNegativeButton("TIDAK", null);
//        builder.show();
//    }
}
