package id.masnadh.myapppeg.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;

//import static id.masnadh.myapppeg.activities.LoginActivity.TAG_ID;
import static id.masnadh.myapppeg.activities.LoginActivity.my_shared_preferences;
import static id.masnadh.myapppeg.activities.LoginActivity.session_status;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import id.masnadh.myapppeg.R;

import id.masnadh.myapppeg.activities.DataActivity;
import id.masnadh.myapppeg.activities.PendidikanActivity;
import id.masnadh.myapppeg.activities.RiwayatPegActivity;
import id.masnadh.myapppeg.activities.RiwayatPegPegActivity;
import id.masnadh.myapppeg.activities.RombelActivity;
import id.masnadh.myapppeg.activities.UbahActivity;
import id.masnadh.myapppeg.adapters.DashboardAdapter;
import id.masnadh.myapppeg.models.dashboard;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Context mContext;

    ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private DashboardAdapter adapter;
    private ArrayList<dashboard> dashboards = new ArrayList<>();
    private String[] menuName;
    private TypedArray menuPhoto;
    String id,idu, nama, nip;
    SharedPreferences sharedpreferences;
    Boolean session = false;

    ImageView imageView;

    final String url = "http://152746201341.ip-dynamic.com/login/pegawai.php";

    TextView tvTitle, tvNip;

    public final static String TAG = HomeFragment.class.getSimpleName();
    public final static String TAG_NAME = "nama";
    public final static String TAG_NIP = "nip";
    public final static String TAG_ID = "id";

    RelativeLayout rlTop;
    AppBarLayout appBar;
    CollapsingToolbarLayout ctLayout;
    Toolbar toolbar;

    boolean ExpandedActionBar = true;



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        adapter = new DashboardAdapter(this.getContext());
        rlTop = view.findViewById(R.id.rlTop);
        appBar = view.findViewById(R.id.appBar);
        ctLayout = view.findViewById(R.id.cToolbar);
        toolbar = view.findViewById(R.id.toolbar);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ctLayout.setTitle("");

        imageView = (ImageView) view.findViewById(R.id.ivProfile);
        imageView.setImageResource(R.mipmap.ic_logo);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvNip = (TextView) view.findViewById(R.id.tvNip);

        id = getActivity().getIntent().getStringExtra(TAG_ID);
        nama = getActivity().getIntent().getStringExtra(TAG_NAME);
        nip = getActivity().getIntent().getStringExtra(TAG_NIP);

        progressDialog = new ProgressDialog(HomeFragment.this.getActivity());
        progressDialog.setMessage("Proses Pengambilan Data, Mohon Tunggu...");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequests =
                new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray dataArray = new JSONArray(response);

                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject object = dataArray.getJSONObject(i);

                                int extraId = Integer.parseInt(getActivity().getIntent().getStringExtra(TAG_ID));
                                String nama = object.getString("nama");
                                String nip = object.getString("nip");
                                int id = object.getInt("id_peg");


                                if ( extraId == id) {


                                    tvTitle.setText(nama);
                                    tvNip.setText("NIP. "+nip);

                                }


                            }

                            Log.d(TAG, "onResponse: " + response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tvTitle.setText(error.getLocalizedMessage());
                    }
                });

        requestQueue.add(stringRequests);



                        ctLayout.setCollapsedTitleTextColor(ContextCompat.getColor((AppCompatActivity) getActivity(), R.color.white));
        ctLayout.setExpandedTitleColor(ContextCompat.getColor((AppCompatActivity)getActivity(), R.color.colorPrimary));

            appBar.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > 200){
                    ExpandedActionBar = false;
                    ctLayout.setTitle("APLIKASI KEPEGAWAIAN SMKN PRIGEN");
                    rlTop.setVisibility(View.GONE);
                    getActivity().invalidateOptionsMenu();
                } else {
                    ExpandedActionBar = true;
                    ctLayout.setTitle("");
                    rlTop.setVisibility(View.VISIBLE);
                    getActivity().invalidateOptionsMenu();
                }
            }
        });

//        ImageView imgProfile = view.findViewById(R.id.ivProfile);
//        Glide.with(mContext)
//                .load(R.drawable.smk)
//                .apply(RequestOptions.circleCropTransform())
//                .into(imgProfile);

        adapter = new DashboardAdapter(mContext, dashboards);

        RecyclerView rvList = view.findViewById(R.id.rvData);
        rvList.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext,2);
        rvList.setLayoutManager(layoutManager);





//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new DashboardAdapter.onItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                switch (position){
                    case 0:
                        sharedpreferences = getActivity().getSharedPreferences(my_shared_preferences,
                                Context.MODE_PRIVATE);


                        session = sharedpreferences.getBoolean(session_status, false);
                        if (session){
                            Intent data = new Intent(getActivity(), DataActivity.class);
                            data.putExtra(  TAG_ID,id);
                            //data.putExtra(TAG_NAME,nama);
                            startActivity(data);
                        }
//                        Intent intent = new Intent(HomeFragment.this.getContext(), DataActivity.class);
//                        //intent.putExtra("extra_menu_data", dashboards.get(position));
//                        startActivity(intent);
                       break;
                    case 1:
                        Intent intent1 = new Intent(HomeFragment.this.getContext(), UbahActivity.class);
                        intent1.putExtra("extra_menu_ubah", dashboards.get(position));
                        intent1.putExtra(TAG_ID, id);
                        startActivity(intent1);
                        break;
                    case 2:
                       // Toast.makeText(getActivity().getBaseContext(), "Menu Masih Dalam Proses Pengembangan", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(HomeFragment.this.getContext(), RiwayatPegPegActivity.class);
                        //intent2.putExtra("extra_menu_pendukung", dashboards.get(position));
                        intent2.putExtra(TAG_ID, id);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(HomeFragment.this.getContext(), PendidikanActivity.class);
                        //intent3.putExtra("extra_menu_pendidikan", dashboards.get(position));
                        intent3.putExtra( TAG_ID, id);
                        startActivity(intent3);
                        break;
                    case 4:
                        Toast.makeText(getActivity().getBaseContext(), "Menu Masih Dalam Proses Pengembangan", Toast.LENGTH_SHORT).show();
                        //Intent intent4 = new Intent(HomeFragment.this.getContext(), PendidikanActivity.class);
//                        intent4.putExtra("extra_menu_pendidikan", dashboards.get(position));
//                        startActivity(intent4);

                        break;
                    case 5:
                        Toast.makeText(getActivity().getBaseContext(), "Menu Masih Dalam Proses Pengembangan", Toast.LENGTH_SHORT).show();
//                        Intent intent5 = new Intent(HomeFragment.this.getContext(), SKPActivity.class);
//                        intent5.putExtra("extra_menu_skp", dashboards.get(position));
//                        startActivity(intent5);
                        break;
                    case 6:
                       // Toast.makeText(getActivity().getBaseContext(), "Menu Masih Dalam Proses Pengembangan", Toast.LENGTH_SHORT).show();
                        Intent intent6 = new Intent(HomeFragment.this.getContext(), RombelActivity.class);
                        intent6.putExtra("extra_menu_rombel", dashboards.get(position));
                        intent6.putExtra( TAG_ID,id);
                        startActivity(intent6);
                        break;
                    case 7:
                        Toast.makeText(getActivity().getBaseContext(), "Menu Masih Dalam Proses Pengembangan", Toast.LENGTH_SHORT).show();
//                        Intent intent7 = new Intent(HomeFragment.this.getContext(), AbsenActivity.class);
//                        intent7.putExtra("extra_menu_absensi", dashboards.get(position));
//                        startActivity(intent7);
                        break;

                }

//                Intent intent = new Intent(HomeFragment.this.getContext(), DataActivity.class);
//                intent.putExtra("extra_menu", dashboards.get(position));
//                startActivity(intent);
            }
        });

        prepare();
        addItem();

        return view;
    }

    private void addItem() {
        dashboards = new ArrayList<>();

        for (int i = 0; i < menuName.length; i++){
            dashboard dashboard = new dashboard();
            dashboard.setTitle(menuName[i]);
            dashboard.setImg(menuPhoto.getResourceId(i, -1));
            dashboards.add(dashboard);
        }

        adapter.setDashboardList(dashboards);
    }

    private void prepare(){
        menuName = getResources().getStringArray(R.array.menu_name);
        menuPhoto = getResources().obtainTypedArray(R.array.menu_photo);
    }

}
