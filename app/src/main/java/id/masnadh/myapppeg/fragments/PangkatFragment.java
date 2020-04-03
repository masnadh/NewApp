package id.masnadh.myapppeg.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.masnadh.myapppeg.R;
import id.masnadh.myapppeg.adapters.JabatanAdapter;
import id.masnadh.myapppeg.adapters.PangkatAdapter;
import id.masnadh.myapppeg.connections.AppController;
import id.masnadh.myapppeg.connections.Server;
import id.masnadh.myapppeg.models.JabatanModel;
import id.masnadh.myapppeg.models.PangkatModel;
import id.masnadh.myapppeg.tambahHapusData.DeletePangkatActivity;
import id.masnadh.myapppeg.tambahHapusData.HapusPasutriActivity;
import id.masnadh.myapppeg.tambahHapusData.TambahPangkatActivity;
import id.masnadh.myapppeg.tambahHapusData.TambahPasutriActivity;


public class PangkatFragment extends Fragment {

    final String url = Server.URL+"pangkat.php";

    TextView tvPangkat, tvGol, tvJenis, tvPejabat, tvSk, tvTgl, tvTmt, tvStatus;
    RecyclerView mRecyclerView;
    Button btnInsertPan, btnDeletePan;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    List<PangkatModel> mItems;
    ProgressDialog pd;
    SharedPreferences sharedpreferences;

    public final static String TAG_ID = "id";
    RequestQueue requestQueue;

    public PangkatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pangkat, container,false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvPan);
        btnInsertPan = (Button) view.findViewById(R.id.btn_insertPan);
        btnDeletePan = (Button) view.findViewById(R.id.btn_deletePan);
        mItems = new ArrayList<>();

        mManager = new LinearLayoutManager(PangkatFragment.this.getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
        mAdapter = new PangkatAdapter(PangkatFragment.this.getActivity(), mItems);


        btnInsertPan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id = getActivity().getIntent().getStringExtra(TAG_ID);
                Intent intent = new Intent(PangkatFragment.this.getActivity(), TambahPangkatActivity.class);
                intent.putExtra(TAG_ID,Id);
                startActivity(intent);
            }
        });

        btnDeletePan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = getActivity().getIntent().getStringExtra(TAG_ID);
                Intent hapus = new Intent(PangkatFragment.this.getActivity(), DeletePangkatActivity.class);
                hapus.putExtra(TAG_ID,Id);
                startActivity(hapus);
            }
        });

        tvPangkat = (TextView)  view.findViewById(R.id.detail_pangkat);
        tvGol = (TextView) view.findViewById(R.id.detail_gol);
        tvStatus = (TextView) view.findViewById(R.id.detail_status_pan);
        tvJenis = (TextView) view.findViewById(R.id.detail_jejab);
        tvTgl = (TextView) view.findViewById(R.id.detail_tglSk);
        tvTmt = (TextView) view.findViewById(R.id.detail_tmt_pang);
        tvSk = (TextView) view.findViewById(R.id.detail_noSk);
        tvPejabat = (TextView) view.findViewById(R.id.detail_pejab);


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // pd.cancel();
                        Log.d("volley","response : " + response.toString());
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                PangkatModel pm = new PangkatModel();
                                int extraId = Integer.parseInt(getActivity().getIntent().getStringExtra(TAG_ID));
                                int id = data.getInt("id_peg");

                                if ( extraId == id) {
                                    pm.setPangkat(data.getString("pangkat"));
                                    pm.setGol(data.getString("gol"));
                                    pm.setJns_pangkat(data.getString("jns_pangkat"));
                                    pm.setPejabat_sk(data.getString("pejabat_sk"));
                                    pm.setNo_sk(data.getString("no_sk"));
                                    pm.setTgl_sk(data.getString("tgl_sk"));
                                    pm.setTmt_pangat(data.getString("tmt_pangkat"));
                                    pm.setStatus_pan(data.getString("status_pan"));

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
