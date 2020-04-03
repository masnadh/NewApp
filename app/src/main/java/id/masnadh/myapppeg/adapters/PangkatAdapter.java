package id.masnadh.myapppeg.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.masnadh.myapppeg.R;
import id.masnadh.myapppeg.models.JabatanModel;
import id.masnadh.myapppeg.models.PangkatModel;
import id.masnadh.myapppeg.tambahHapusData.TambahJabatanActivity;
import id.masnadh.myapppeg.tambahHapusData.TambahPangkatActivity;

public class PangkatAdapter extends RecyclerView.Adapter<PangkatAdapter.ViewHolder> {

    public final static String TAG_ID = "id";
    String id;
    private Context context;
    private List<PangkatModel> pangkatModels;
    private ProgressDialog dialog;
    Boolean session = false;
    SharedPreferences sharedpreferences;
    ArrayList<HashMap<String ,String >> list_data;

    public PangkatAdapter(Context context, List<PangkatModel> pangkatModels) {
        this.context = context;
        this.pangkatModels = pangkatModels;
    }

    @NonNull
    @Override
    public PangkatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_pangkat, parent, false);
        PangkatAdapter.ViewHolder viewHolder = new PangkatAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PangkatAdapter.ViewHolder holder, int position) {
        final PangkatModel data = pangkatModels.get(position);
        holder.tvPangkat.setText(data.getPangkat());
        holder.tvGol.setText(data.getGol());
        holder.tvJenis.setText(data.getJns_pangkat());
        holder.tvPejabat.setText(data.getPejabat_sk());
        holder.tvSk.setText(data.getNo_sk());
        holder.tvStatus.setText(data.getStatus_pan());
        holder.tvTgl.setText(data.getTgl_sk());
        holder.tvTmt.setText(data.getTmt_pangat());

        holder.pm = data;
    }

    @Override
    public int getItemCount() {
        return pangkatModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPangkat, tvGol, tvJenis, tvPejabat, tvSk, tvTgl, tvTmt, tvStatus;
        PangkatModel pm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPangkat = itemView.findViewById(R.id.detail_pangkat);
            tvGol = itemView.findViewById(R.id.detail_gol);
            tvStatus = itemView.findViewById(R.id.detail_status_pan);
            tvJenis = itemView.findViewById(R.id.detail_jejab);
            tvTgl = itemView.findViewById(R.id.detail_tglSk);
            tvTmt = itemView.findViewById(R.id.detail_tmt_pang);
            tvSk = itemView.findViewById(R.id.detail_noSk);
            tvPejabat = itemView.findViewById(R.id.detail_pejab);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(context, TambahPangkatActivity.class);
                    update.putExtra("update",1);
                    update.putExtra(TAG_ID, id);
                    update.putExtra("pangkat",pm.getPangkat());
                    update.putExtra("gol",pm.getGol());
                    update.putExtra("jns_pangkat",pm.getJns_pangkat());
                    update.putExtra("pejabat_sk",pm.getPejabat_sk());
                    update.putExtra("no_sk",pm.getNo_sk());
                    update.putExtra("tgl_sk",pm.getTgl_sk());
                    update.putExtra("tmt_pangkat",pm.getTmt_pangat());
                    update.putExtra("status_pan",pm.getStatus_pan());
                    context.startActivity(update);
                }
            });

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }
    }
}
