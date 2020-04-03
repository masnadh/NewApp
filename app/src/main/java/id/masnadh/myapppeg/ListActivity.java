package id.masnadh.myapppeg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import id.masnadh.myapppeg.R;

import id.masnadh.myapppeg.adapters.PegawaiAdapter;
import id.masnadh.myapppeg.connections.RequestHandler;
import id.masnadh.myapppeg.connections.Server;
import id.masnadh.myapppeg.models.Pegawai;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    public static ListActivity ma;
    protected Cursor cursor;
    ArrayList<Pegawai> thelist;
    ListView listview;
    List<Pegawai> listItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

//        getSupportActionBar().setTitle("Data");
//        recyclerView = (RecyclerView) findViewById(R.id.list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));
//        progressDialog = new ProgressDialog(this);
//        listItems = new ArrayList<>();
//        ma = this;
//        refresh_list();
//
//    }
//
//    public void refresh_list(){
//        listItems.clear();
//        adapter = new PegawaiAdapter(listItems,getApplicationContext());
//        recyclerView.setAdapter(adapter);
//
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        progressDialog.setMessage("Loading");
//        progressDialog.show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URL + "pegawai.php", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                progressDialog.dismiss();
//                try{
//
//                    progressDialog.hide();
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                    Toast.makeText(ListActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
//                    for (int i = 0; i<jsonArray.length(); i++){
//                        JSONObject o = jsonArray.getJSONObject(i);
//                        Pegawai item = new Pegawai(
//                                o.getString("id_peg"),
//                                o.getString("nama"),
//                                o.getString("nip"),
//                                o.getString("tempat_lhr"),
//                                o.getString("tgl_lhr")
//
//                        );
//                        listItems.add(item);
//
//                        adapter = new PegawaiAdapter(listItems,getApplicationContext());
//                        recyclerView.setAdapter(adapter);
//
//                    }
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.hide();
//                Toast.makeText(ListActivity.this, "Failed",Toast.LENGTH_SHORT).show();
//            }
//        }){
//            protected Map<String , String> getParams() throws AuthFailureError {
//                Map<String , String> params = new HashMap<>();
//                params.put("name", "kl");
//                return params;
//            }
//        };
//        RequestHandler.getInstance(ListActivity.this).addToRequestQueue(stringRequest);
    }

}
