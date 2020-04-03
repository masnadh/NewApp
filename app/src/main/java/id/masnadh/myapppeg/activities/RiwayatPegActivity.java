package id.masnadh.myapppeg.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.tabs.TabLayout;

import id.masnadh.myapppeg.R;
import id.masnadh.myapppeg.connections.Server;

public class RiwayatPegActivity extends AppCompatActivity {

    public static final String EXTRA_MENU = "extra_menu_peg";

    String id;
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public final static String TAG = "DataActivity";
    public final static String TAG_ID = "id";
    PagerAdapter pagerAdapter;
    Boolean session = false;

    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_peg);

        Toolbar toolbar = (Toolbar) findViewById(R.id.profileToolbar2);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        getSupportActionBar().setTitle("Riwayat Kepegwaian");

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout2);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager2);

        //Memanggil dan Memasukan Value pada Class PagerAdapter(FragmentManager dan JumlahTab)
        PagerAdapter pagerAdapter = new id.masnadh.myapppeg.adapters.Pager2(getSupportFragmentManager(), tabLayout.getTabCount());

        //Memasang Adapter pada ViewPager
        viewPager.setAdapter(pagerAdapter);

//        //Memanggil dan Memasukan Value pada Class PagerAdapter(FragmentManager dan JumlahTab)
//        PagerAdapter pagerAdapter = new id.masnadh.myapppeg.adapters.Pager2(getSupportFragmentManager(), tabLayout.getTabCount());
//
//        //Memasang Adapter pada ViewPager
//        viewPager.setAdapter(pagerAdapter);

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




        Intent intent = getIntent();

//        Parcelable[] dashboard = intent.getParcelableArrayExtra(EXTRA_MENU);
    }
}
