package id.masnadh.myapppeg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import id.masnadh.myapppeg.R;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import id.masnadh.myapppeg.activities.LoginActivity;
import id.masnadh.myapppeg.activities.ProfileActivity;
import id.masnadh.myapppeg.connections.Server;
import id.masnadh.myapppeg.fragments.HomeFragment;
import id.masnadh.myapppeg.fragments.ProfileFragment;

import static id.masnadh.myapppeg.activities.LoginActivity.my_shared_preferences;
import static id.masnadh.myapppeg.activities.LoginActivity.session_status;

public class MainActivity extends AppCompatActivity {

    TextView namaUser, txt_id;
    String id, username, idu,level,levelU;

    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    SharedPreferences sharedpreferences;
    Boolean session = false;

    private static final String TAG_SUCCESS = "success";
    int success;
    private String url = Server.URL + "masuk.php";
    public static final String TAG_ID = "id";
    private static final String TAG_LEVEL = "level";
    public static final String TAG_USERNAME = "username";
    String tag_json_obj = "json_obj_req";

//    private Context mContext = MainActivity.this;
//
//    private RecyclerView recyclerView;
//    private DashboardAdapter adapter;
//    private List<dashboard> dashboardList;
//
//    RelativeLayout rlTop;
//    AppBarLayout appBar;
//    CollapsingToolbarLayout ctLayout;
    Toolbar toolbar;
//
//    boolean ExpandedActionBar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarMain);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setTitle("SIAKAD");
//        toolbar.setSubtitle("SMK NEGERI PRIGEN");
//        toolbar.setLogo(R.mipmap.ic_launcher);


        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(LoginActivity.session_status, false);
        idu = sharedpreferences.getString(TAG_ID, idu);
        id = sharedpreferences.getString(TAG_ID, id);
        levelU = sharedpreferences.getString(TAG_LEVEL, null);

        if(levelU.equals("pegawai")) {

        }

//        }else if(levelU.equals("admin"))
//        {
//            Intent intent = new Intent(MainActivity.this, AdminActivity.class);
//            intent.putExtra(TAG_ID, id);
//            intent.putExtra(TAG_USERNAME, username);
//            intent.putExtra(TAG_LEVEL, level);
//            startActivity(intent);}


        requestQueue = Volley.newRequestQueue(MainActivity.this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null){
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }

//        rlTop = findViewById(R.id.rlTop);
//        appBar = findViewById(R.id.appBar);
//        ctLayout = findViewById(R.id.cToolbar);
//        toolbar = findViewById(R.id.toolbar);
//
//        setSupportActionBar(toolbar);
//        ctLayout.setTitle("");

//        appBar.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (Math.abs(verticalOffset) > 200){
//                    ExpandedActionBar = false;
//                    ctLayout.setTitle("My Dashboard");
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
//        ImageView imgProfile = findViewById(R.id.ivProfile);
//        Glide.with(mContext)
//                .load(R.drawable.smk)
//                .apply(RequestOptions.circleCropTransform())
//                .into(imgProfile);
//
//        recyclerView = findViewById(R.id.rvData);
//        dashboardList = new ArrayList<>();
//        adapter = new DashboardAdapter(mContext, dashboardList);
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 2);
//        RecyclerView.setLayoutManager(layoutManager);



    }

        //requestQueue.add(stringRequest);

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment fragment;

            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    fragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.nav_profile:
//                    sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
//                    session = sharedpreferences.getBoolean(session_status, false);
//                    if (session) {
//                        Intent profil = new Intent(MainActivity.this, ProfileActivity.class);
//                        profil.putExtra(TAG_ID, id);
//                        startActivity(profil);

             //           return true;
                    fragment = new ProfileFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
            }

            return false;
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }


    @Override
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

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);

                //Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;
            }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Apa Anda Ingin Keluar ?");
        builder.setCancelable(false);
        builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();
                MainActivity.this.finish();
            }
        });
        builder.setNegativeButton("TIDAK", null);
        builder.show();
    }
}
