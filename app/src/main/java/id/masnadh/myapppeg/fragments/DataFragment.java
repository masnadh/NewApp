package id.masnadh.myapppeg.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import id.masnadh.myapppeg.R;
import id.masnadh.myapppeg.connections.Server;
//import Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataFragment extends Fragment {

    final String url = Server.URL+"pegawai.php";

    TextView tvNama,tvNip, tvNuptk, tvStatus, tvKtp, tvTempat, tvTanggal, tvAgama, tvTelp, tvJk, tvGolDa, tvNikah, tvAlamat, tvEmail;
    //String id,idu;
    SharedPreferences sharedpreferences;
    public final static String TAG = DataFragment.class.getSimpleName() ;
    public final static String TAG_ID = "id";
    RequestQueue requestQueue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container,false);

        tvNama = (TextView) view.findViewById(R.id.detail_nama);
        tvNip = (TextView)  view.findViewById(R.id.detail_nip);
        tvNuptk = (TextView)  view.findViewById(R.id.detail_nuptk);
        tvStatus = (TextView)  view.findViewById(R.id.detail_statusPeg);
        tvKtp = (TextView)  view.findViewById(R.id.detail_ktp);
        tvTempat = (TextView)  view.findViewById(R.id.detail_tempat_lahir);
        tvTanggal = (TextView)  view.findViewById(R.id.detail_tanggal_lahir);
        tvAgama = (TextView)  view.findViewById(R.id.detail_agama);
       // tvTelp = (TextView)  view.findViewById(R.id.detail_telp);
        tvJk = (TextView)  view.findViewById(R.id.detail_jk);
        tvGolDa = (TextView)  view.findViewById(R.id.detail_golDarah);
        tvNikah = (TextView)  view.findViewById(R.id.detail_nikah);
        tvAlamat = (TextView)  view.findViewById(R.id.detail_alamat);
        //tvEmail = (TextView)  view.findViewById(R.id.detail_email);


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequests =
                new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray dataArray = new JSONArray(response);
                    //JSONArray dataArray = new JSONArray(response);

                    for (int i =0; i < dataArray.length(); i++)
                    {

                        JSONObject obj = dataArray.getJSONObject(i);

                        int extraId = Integer.parseInt(getActivity().getIntent().getStringExtra(TAG_ID));
                        String nama = obj.getString("nama");
                        int id = obj.getInt("id_peg");
                        String nip = obj.getString("nip");
                        String nuptk = obj.getString("nuptk");
                        String jk = obj.getString("jk");
                        String golDa = obj.getString("gol_darah");
                        String nikah = obj.getString("status_nikah");
                        String alamat = obj.getString("alamat");
//                        String email = obj.getString("email");
                        String status = obj.getString("status_kepeg");
                        String ktp = obj.getString("ktp");
                        String tempat = obj.getString("tempat_lhr");
                        String tanggal = obj.getString("tgl_lhr");
                        String agama = obj.getString("agama");
//                        String telp = obj.getString("telp");


                        if ( extraId == id) {


                            tvNama.setText(nama);
                            tvNip.setText(nip);
                            tvNuptk.setText(nuptk);
                            tvStatus.setText(status);
                            tvKtp.setText(ktp);
                            tvTempat.setText(tempat);
                            tvTanggal.setText(tanggal);
                            tvAgama.setText(agama);
//                            tvTelp.setText(telp);
                            tvJk.setText(jk);
                            tvGolDa.setText(golDa);
                            tvNikah.setText(nikah);
                            tvAlamat.setText(alamat);
//                            tvEmail.setText(email);

                        }
                    }
                    Log.d(TAG, "onResponse:" + response);
                }  catch(JSONException e)

                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvNama.setText(error.getLocalizedMessage());
            }
        });
        requestQueue.add(stringRequests);
        return view;

           }

}
