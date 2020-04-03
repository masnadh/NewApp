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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.masnadh.myapppeg.R;
import id.masnadh.myapppeg.adapters.JabatanAdapter;
import id.masnadh.myapppeg.adapters.PasutriAdapter;
import id.masnadh.myapppeg.connections.AppController;
import id.masnadh.myapppeg.connections.Server;
import id.masnadh.myapppeg.models.JabatanModel;
import id.masnadh.myapppeg.models.PasutriModel;
import id.masnadh.myapppeg.tambahHapusData.DeleteJabatanActivity;
import id.masnadh.myapppeg.tambahHapusData.HapusPasutriActivity;
import id.masnadh.myapppeg.tambahHapusData.TambahJabatanActivity;
import id.masnadh.myapppeg.tambahHapusData.TambahPasutriActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class GolFragment extends Fragment {

    final String url = Server.URL+"jabatan2.php";

    TextView tvIdJab,tvJab,tvEse,tvTmt,tvStatus;
    RecyclerView mRecyclerView;
    Button btnInsertJab, btnDeleteJab;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    List<JabatanModel> mItems;
    ProgressDialog pd;
    SharedPreferences sharedpreferences;

    public final static String TAG_ID = "id";
    RequestQueue requestQueue;

    public GolFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gol, container,false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvJab);
        btnInsertJab = (Button) view.findViewById(R.id.btn_insertJab);
        btnDeleteJab = (Button) view.findViewById(R.id.btn_deleteJab);
        mItems = new ArrayList<>();

        mManager = new LinearLayoutManager(GolFragment.this.getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
        mAdapter = new JabatanAdapter(GolFragment.this.getActivity(), mItems);

        btnInsertJab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id = getActivity().getIntent().getStringExtra(TAG_ID);
                Intent intent = new Intent(GolFragment.this.getActivity(), TambahJabatanActivity.class);
                intent.putExtra(TAG_ID,Id);
                startActivity(intent);
            }
        });


        btnDeleteJab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = getActivity().getIntent().getStringExtra(TAG_ID);
                Intent hapus = new Intent(GolFragment.this.getActivity(), DeleteJabatanActivity.class);
                hapus.putExtra(TAG_ID,Id);
                startActivity(hapus);
            }
        });


       // tvIdJab = (TextView) view.findViewById(R.id.detail_id_jab);
        tvStatus = (TextView)  view.findViewById(R.id.detail_status_jab);
        tvJab = (TextView) view.findViewById(R.id.detail_jab);
        tvEse = (TextView)  view.findViewById(R.id.detail_ese);
        tvTmt = (TextView)  view.findViewById(R.id.detail_tmtJab);


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
                                JabatanModel jm = new JabatanModel();
                                int extraId = Integer.parseInt(getActivity().getIntent().getStringExtra(TAG_ID));
                                int id = data.getInt("id_peg");

                                if ( extraId == id) {
                                    jm.setJabatan(data.getString("jabatan"));
                                    jm.setEselon(data.getString("eselon"));
                                    jm.setTmt_jabatan(data.getString("tmt_jabatan"));
                                    jm.setStatus_jab(data.getString("status_jab"));
//                                    pm.setPendidikanPas(data.getString("pendidikan"));
//                                    pm.setPekerjaanPas(data.getString("pekerjaan"));
//                                    pm.setHubunganPas(data.getString("status_hub"));
                                    mItems.add(jm);
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

//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
//        StringRequest stringRequests = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try {
//
//                    JSONArray dataArray = new JSONArray(response);
//
//                    for (int i = 0; i < dataArray.length(); i++) {
//
//                        JSONObject obj = dataArray.getJSONObject(i);
//
//                        int extraId = Integer.parseInt(getActivity().getIntent().getStringExtra(TAG_ID));
//                        int id = obj.getInt("id_peg");
//                        //String idJab = obj.getString("id_jab");
//                        String jabatan = obj.getString("jabatan");
//                        String eselon = obj.getString("eselon");
//                        String tmt = obj.getString("tmt_jabatan");
//                        String status = obj.getString("status_jab");
//
//                        if ( extraId == id) {
//
//                            //tvIdJab.setText(idJab);
//                            tvJab.setText(jabatan);
//                            tvEse.setText(eselon);
//                            tvTmt.setText(tmt);
//                            tvStatus.setText(status);
//
//                        }
//                    }
//
//                    Log.d(TAG, "onResponse:" + response);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                tvJab.setText(error.getLocalizedMessage());
//            }
//        });

        AppController.getInstance().addToRequestQueue(reqData);
        return view;

    }



}

