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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.masnadh.myapppeg.R;
import id.masnadh.myapppeg.models.AnakModel;
import id.masnadh.myapppeg.models.JabatanModel;
import id.masnadh.myapppeg.tambahHapusData.TambahAnakActivity;
import id.masnadh.myapppeg.tambahHapusData.TambahJabatanActivity;

public class JabatanAdapter extends RecyclerView.Adapter<JabatanAdapter.ViewHolder> {

    public final static String TAG_ID = "id";
    String id;
    private Context context;
    private List<JabatanModel> jabatanModels;
    private ProgressDialog dialog;
    Boolean session = false;
    SharedPreferences sharedpreferences;
    ArrayList<HashMap<String ,String >> list_data;

    public JabatanAdapter(Context context, List<JabatanModel> jabatanModels) {
        this.context = context;
        this.jabatanModels = jabatanModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_jabatan, parent, false);
        JabatanAdapter.ViewHolder holder = new JabatanAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JabatanModel data = jabatanModels.get(position);
        holder.tvJab.setText(data.getJabatan());
        holder.tvEse.setText(data.getEselon());
        holder.tvTmt.setText(data.getTmt_jabatan());
        holder.tvStatus.setText(data.getStatus_jab());

        holder.jm = data;
    }

    @Override
    public int getItemCount() {
        return jabatanModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvJab, tvEse, tvTmt, tvStatus;
        JabatanModel jm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          //  tvIdJab = itemView.findViewById(R.id.detail_id_jab);
            tvJab = (TextView) itemView.findViewById(R.id.detail_jab);
            tvEse = (TextView) itemView.findViewById(R.id.detail_ese);
            tvTmt = (TextView) itemView.findViewById(R.id.detail_tmtJab);
            tvStatus = (TextView) itemView.findViewById(R.id.detail_status_jab);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(context, TambahJabatanActivity.class);
                    update.putExtra("update",1);
                    update.putExtra(TAG_ID, id);
                    update.putExtra("jabatan",jm.getJabatan());
                    update.putExtra("eselon",jm.getEselon());
                    update.putExtra("tmt_jabatan",jm.getTmt_jabatan());
                    update.putExtra("status_jab",jm.getStatus_jab());
                    context.startActivity(update);
                }
            });
        }
    }
}
