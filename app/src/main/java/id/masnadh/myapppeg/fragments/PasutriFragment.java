package id.masnadh.myapppeg.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.masnadh.myapppeg.R;
import id.masnadh.myapppeg.activities.DeleteActivity;
import id.masnadh.myapppeg.activities.PendidikanActivity;
import id.masnadh.myapppeg.activities.TambahPendidikanActivity;
import id.masnadh.myapppeg.adapters.PasutriAdapter;
import id.masnadh.myapppeg.adapters.PendidikanAdapter;
import id.masnadh.myapppeg.connections.AppController;
import id.masnadh.myapppeg.connections.Server;
import id.masnadh.myapppeg.models.PasutriModel;
import id.masnadh.myapppeg.models.PendidikanModel;
import id.masnadh.myapppeg.tambahHapusData.HapusPasutriActivity;
import id.masnadh.myapppeg.tambahHapusData.TambahPasutriActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasutriFragment extends Fragment {

    String id;
    RecyclerView mRecyclerView;
    Button btnInsertPas, btnDeletePas;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    List<PasutriModel> mItems;
    ProgressDialog pd;
    public final static String TAG_ID = "id";
    TextView tvNama,tvNik, tvTmp, tvTgl, tvPendidikan, tvPek, tvStatus;
    Toolbar toolbar;

    RequestQueue requestQueue;

    public PasutriFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pasutri, container,false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvPasutri);
        btnInsertPas = (Button) view.findViewById(R.id.btn_insert_pasutri);
        btnDeletePas = (Button) view.findViewById(R.id.btn_delete_pasutri);
        mItems = new ArrayList<>();

        mManager = new LinearLayoutManager(PasutriFragment.this.getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
        mAdapter = new PasutriAdapter(mItems, PasutriFragment.this.getActivity());
        //mRecyclerView.setAdapter(mAdapter);

        btnInsertPas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id = getActivity().getIntent().getStringExtra(TAG_ID);
                Intent intent = new Intent(PasutriFragment.this.getActivity(), TambahPasutriActivity.class);
                intent.putExtra(TAG_ID,Id);
                startActivity(intent);
            }
        });

        btnDeletePas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = getActivity().getIntent().getStringExtra(TAG_ID);
                Intent hapus = new Intent(PasutriFragment.this.getActivity(), HapusPasutriActivity.class);
                hapus.putExtra(TAG_ID,Id);
                startActivity(hapus);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, Server.URL_PAS,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // pd.cancel();
                        Log.d("volley","response : " + response.toString());
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                PasutriModel pm = new PasutriModel();
                                int extraId = Integer.parseInt(getActivity().getIntent().getStringExtra(TAG_ID));
                                int id = data.getInt("id_peg");

                                if ( extraId == id) {
                                    pm.setNikPas(data.getString("nik"));
                                    pm.setNamaPas(data.getString("nama"));
                                    pm.setTempatPas(data.getString("tmp_lhr"));
                                    pm.setTglPas(data.getString("tgl_lhr"));
                                    pm.setPendidikanPas(data.getString("pendidikan"));
                                    pm.setPekerjaanPas(data.getString("pekerjaan"));
                                    pm.setHubunganPas(data.getString("status_hub"));
                                    mItems.add(pm);
                                }

                                mRecyclerView.setAdapter(mAdapter);

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
                });

        AppController.getInstance().addToRequestQueue(reqData);
        return view;
    }

}
