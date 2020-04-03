package id.masnadh.myapppeg.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

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

public class PendidikanActivity extends AppCompatActivity {

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
                        pd.cancel();
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

}
