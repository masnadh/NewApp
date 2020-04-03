package id.masnadh.myapppeg.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import id.masnadh.myapppeg.R;

public class PendukungActivity extends AppCompatActivity {

    public static final String EXTRA_MENU = "extra_menu_pendukung";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendukung);

        Intent intent = getIntent();

        Parcelable[] dashboard = intent.getParcelableArrayExtra(EXTRA_MENU);
    }
}
