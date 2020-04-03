package id.masnadh.myapppeg.fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.masnadh.myapppeg.R;
import id.masnadh.myapppeg.adapters.AnakAdapter;
import id.masnadh.myapppeg.connections.AppController;
import id.masnadh.myapppeg.connections.Server;
import id.masnadh.myapppeg.models.AnakModel;
import id.masnadh.myapppeg.models.PasutriModel;
import id.masnadh.myapppeg.tambahHapusData.TambahAnakActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnakFragment extends Fragment {

    public static AnakFragment af;
    FloatingActionButton fab;
    ListView list;
    SwipeRefreshLayout swipe;
//    List<AnakModel> itemList = new ArrayList<>();
//    Adapter adapter;
//    int success;
    public final static String TAG_ID = "id";
    AlertDialog.Builder dialog;
    //LayoutInflater inflater;
    private List<AnakModel> dataAnak;
    private RecyclerView recyclerView;
//    View dialogView;
//    EditText txt_id, txt_nama, txt_alamat;
//    String id, nama, alamat;

    private static final String TAG = AnakFragment.class.getSimpleName();

//    private static String url_select     = Server.URL + "select.php";
//    private static String url_insert     = Server.URL + "insert.php";
//    private static String url_edit       = Server.URL + "edit.php";
//    private static String url_update     = Server.URL + "update.php";
//    private static String url_delete     = Server.URL + "delete.php";
//
//    public static final String TAG_ID       = "id";
//    public static final String TAG_NAMA     = "nama";
//    public static final String TAG_ALAMAT   = "alamat";
//    private static final String TAG_SUCCESS = "success";
//    private static final String TAG_MESSAGE = "message";
//
//    String tag_json_obj = "json_obj_req";


    public AnakFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anak, container,false);
        // Inflate the layout for this fragment

        // menghubungkan variablel pada layout dan pada java
        fab     = (FloatingActionButton) view.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = getActivity().getIntent().getStringExtra(TAG_ID);
                Intent intent = new Intent(AnakFragment.this.getActivity(), TambahAnakActivity.class);
                intent.putExtra(TAG_ID,Id);
                startActivity(intent);
            }
        });
        swipe   = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        //swipe.setOnRefreshListener(AnakFragmentthis.getActivity());
        list    = (ListView) view.findViewById(R.id.list);

        recyclerView = view.findViewById(R.id.rvAnak);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AnakFragment.this.getActivity()));
        dataAnak = new ArrayList<>();
        final AnakAdapter adapter = new AnakAdapter(AnakFragment.this.getActivity(), dataAnak);
        recyclerView.setAdapter(adapter);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Server.URL_ANAK, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //pd.cancel();
                Log.d("volley","response : " + response.toString());
                for(int i = 0 ; i < response.length(); i++){
                    try {
                        JSONObject data = response.getJSONObject(i);
                        AnakModel pm = new AnakModel();
                        int extraId = Integer.parseInt(getActivity().getIntent().getStringExtra(TAG_ID));
                        int id = data.getInt("id_peg");
                        if ( extraId == id) {
                            pm.setNikAn(data.getString("nik"));
                            pm.setNamaAn(data.getString("nama"));
                            pm.setTempatAn(data.getString("tmp_lhr"));
                            pm.setTglAn(data.getString("tgl_lhr"));
                            pm.setPendidikanAn(data.getString("pendidikan"));
                            pm.setPekerjaanAn(data.getString("pekerjaan"));
                            pm.setHubunganAn(data.getString("status_hub"));
                            dataAnak.add(pm);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    adapter.notifyDataSetChanged();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley", "error : " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        // untuk mengisi data dari JSON ke dalam adapter
//        adapter = new Adapter(AnakFragment.this.getActivity(), itemList);
//        list.setAdapter(adapter);

        // menamilkan widget refresh
//        swipe.setOnRefreshListener(this);
//
//        swipe.post(new Runnable() {
//                       @Override
//                       public void run() {
//                           swipe.setRefreshing(true);
//                           itemList.clear();
//                           adapter.notifyDataSetChanged();
//                           callVolley();
//                       }
//                   }
//        );
//
//        // fungsi floating action button memanggil form biodata
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogForm("", "", "", "SIMPAN");
//            }
//        });



        return view;
    }
}
