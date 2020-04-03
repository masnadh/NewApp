package id.masnadh.myapppeg.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.EditText;

import id.masnadh.myapppeg.R;

public class RombelActivity extends AppCompatActivity {

    //public static final String EXTRA_MENU = "extra_menu_rombel";

    EditText idPeg,nama,nip,nuptk;
    Button btnbatal,btnsimpan;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rombel);

//        Intent intent = getIntent();
//
//        Parcelable[] dashboard = intent.getParcelableArrayExtra(EXTRA_MENU);
        Intent data = getIntent();
        final int update = data.getIntExtra("updates",0);
        String intent_npm = data.getStringExtra("id_peg");
        String intent_nama = data.getStringExtra("nama");
        String intent_nip = data.getStringExtra("nip");
        String intent_nuptk = data.getStringExtra("nuptk");

    }
}
