package id.masnadh.myapppeg.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.masnadh.myapppeg.R;
import id.masnadh.myapppeg.activities.DataActivity;
import id.masnadh.myapppeg.connections.AppController;
import id.masnadh.myapppeg.connections.Server;
import id.masnadh.myapppeg.fragments.AnakFragment;
import id.masnadh.myapppeg.models.AnakModel;
import id.masnadh.myapppeg.models.PasutriModel;
import id.masnadh.myapppeg.tambahHapusData.TambahAnakActivity;
import id.masnadh.myapppeg.tambahHapusData.TambahPasutriActivity;

public class AnakAdapter extends RecyclerView.Adapter<AnakAdapter.ViewHolder> {

    public final static String TAG_ID = "id";
    String id;
    private Context context;
    private List<AnakModel> anakModels;
    private ProgressDialog dialog;
    ArrayList<HashMap<String ,String >> list_data;
//    ArrayList<HashMap<String ,String >>  filterL;

//    public AnakAdapter(FragmentActivity activity, List<AnakModel> anakModels) {
//        this.anakModels = anakModels;
//    }


    public AnakAdapter(Context context, List<AnakModel> anakModels) {
        this.context = context;
        this.anakModels = anakModels;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_anak, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnakAdapter.ViewHolder holder, final int position) {
        final AnakModel data = anakModels.get(position);
        holder.tvNik.setText(data.getNikAn());
        holder.tvNama.setText(data.getNamaAn());
        holder.tvTempat.setText(data.getTempatAn());
        holder.tvTgl.setText(data.getTglAn());
        holder.tvPend.setText(data.getPendidikanAn());
        holder.tvPek.setText(data.getPekerjaanAn());
        holder.tvHub.setText(data.getHubunganAn());

      //  holder.am = data;

//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                Intent intent;
//                //final String id = list_data.get(position).get("id_anak");
//                //final String id = anakModels.get(position).getNikAn();
//                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                final ProgressDialog dialog1 = new ProgressDialog(context);
//                dialog1.setMessage("Loading Delete Data");
//                final CharSequence[] dialogItem = {"Edit Data", "Hapus Data"};
//                builder.setTitle(data.getNamaAn());
//                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(final DialogInterface dialog, int i) {
//                        switch (i){
//                            case 0 :
//                                Intent intent2 = new Intent(context, TambahAnakActivity.class);
//                                intent2.putExtra("nik", data.getNikAn());
//                                intent2.putExtra("nama", data.getNamaAn());
//                                intent2.putExtra("tmp_lhr", data.getTempatAn());
//                                intent2.putExtra("tgl_lhr", data.getTglAn());
//                                intent2.putExtra("pendidikan", data.getPendidikanAn());
//                                intent2.putExtra("pekerjaan", data.getPekerjaanAn());
//                                intent2.putExtra("status_hub", data.getHubunganAn());
//                                context.startActivity(intent2);
//                                break;
//
//                            case 1 :
//                                androidx.appcompat.app.AlertDialog.Builder builderDel = new androidx.appcompat.app.AlertDialog.Builder(v.getContext());
//                                builderDel.setTitle(data.getNamaAn());
//                                builderDel.setMessage("Are You Sure, You Want to Delete Data?");
//                                builderDel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, final int i) {
//                                        dialog1.show();
//
//                                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
//                                                Server.URL_DEL_ANAK, new Response.Listener<String>() {
//                                            @Override
//                                            public void onResponse(String response) {
//
//                                                anakModels.remove(position);
//                                                notifyItemRemoved(position);
//                                                notifyDataSetChanged();
//                                                dialog1.hide();
//                                                dialog1.dismiss();
//                                                Toast.makeText(v.getContext(),"Successfully Deleted Data "+ data.getNikAn(),Toast.LENGTH_LONG).show();
//
//                                            }
//                                        }, new Response.ErrorListener() {
//                                            @Override
//                                            public void onErrorResponse(VolleyError error) {
//                                                dialog1.hide();
//                                                dialog1.dismiss();
//                                            }
//                                        }){
//                                            @Override
//                                            protected Map<String, String> getParams() throws AuthFailureError {
//                                                Map<String, String> params= new HashMap<>();
//                                                params.put("id_anak", data.toString());
//                                                return params;
//                                            }
//                                        };
//
//
//                                        AppController.getInstance().addToRequestQueue(stringRequest);
//                                        dialogInterface.dismiss();
//
//                                    }
//                                });
//
//                                builderDel.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog1.dismiss();
//                                    }
//                                });
//
//                                builderDel.create().show();
//                                break;
//                        }
//                    }
//                });
//
//                builder.create().show();
//            }
//        });

//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent;
//                final String id = data.getNikAn();
//                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(v.getContext());
//                final ProgressDialog dialog = new ProgressDialog(v.getContext());
//                dialog.setMessage("Loading Delete Data");
//                final CharSequence[] dialogItem = {"Edit Data", "Hapus Data"};
//                builder.setTitle("Menu !");
//                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                        if (dialogItem[which].equals("Detail Data")){
////                            Intent pindah = new Intent(context, DataActivity.class);
////                            pindah.putExtra(TAG_ID, id);
////                            context.startActivity(pindah);
////                        } else
//                            if (dialogItem[which].equals("Hapus")){
//                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URL_DEL_ANAK, new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
//                                    try {
//                                        JSONObject jsonObject = new JSONObject(response);
//
//                                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//                                    Log.e("TAG", "Error: " + error.getMessage());
//                                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
//                                }
//                            }) {
//                                @Override
//                                protected Map<String, String> getParams() throws AuthFailureError {
//                                    Map<String, String> map = new HashMap<String, String>();
//                                    map.put("nik", data.getNikAn());
//                                    return map;
//                                }
//                            };
//
//                            Intent refresh = new Intent(context, AnakFragment.class);
//                            context.startActivity(refresh);
//                            AppController.getInstance().addToRequestQueue(stringRequest);
//
//                        }
//
//                        else if (dialogItem[which].equals("Batal")){
//                            dialog.dismiss();
//                        }
//
//                    }
//                });
//                builder.show();
//            }
//        });

        holder.am = data;
    }

    @Override
    public int getItemCount() {
        return anakModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNik, tvNama, tvTempat, tvTgl, tvPend, tvPek, tvHub;
        AnakModel am;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNik = itemView.findViewById(R.id.detail_anak_nik);
            tvNama = itemView.findViewById(R.id.detail_nama_anak);
            tvTempat = itemView.findViewById(R.id.detail_tempat_lahir_anak);
            tvTgl = itemView.findViewById(R.id.detail_tgl_anak);
            tvPend = itemView.findViewById(R.id.detail_pend_anak);
            tvPek = itemView.findViewById(R.id.detail_pekerjaan_anak);
            tvHub = itemView.findViewById(R.id.detail_hub_anak);
            //cardView = itemView.findViewById(R.id.card_view_anak);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent update = new Intent(context, TambahAnakActivity.class);
                    Intent update = new Intent(context, TambahAnakActivity.class);
                    update.putExtra("update",1);
                    update.putExtra(TAG_ID, id);
                    update.putExtra("nik",am.getNikAn());
                    update.putExtra("nama",am.getNamaAn());
                    update.putExtra("tmp_lhr",am.getTempatAn());
                    update.putExtra("tgl_lhr",am.getTglAn());
                    update.putExtra("pendidikan",am.getPendidikanAn());
                    update.putExtra("pekerjaan",am.getPekerjaanAn());
                    update.putExtra("status_hub",am.getHubunganAn());

                    context.startActivity(update);
                }
            });

        }
    }
}
