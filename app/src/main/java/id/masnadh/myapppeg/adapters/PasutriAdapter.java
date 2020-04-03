package id.masnadh.myapppeg.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.masnadh.myapppeg.R;
import id.masnadh.myapppeg.models.PasutriModel;
import id.masnadh.myapppeg.tambahHapusData.TambahPasutriActivity;

public class PasutriAdapter extends RecyclerView.Adapter<PasutriAdapter.PasutriViewHolder> {

    private List<PasutriModel> mItems;
    private Context context;
    SharedPreferences sharedpreferences;
    Boolean session = false;
    public final static String TAG_ID = "id";
    String id;

    public PasutriAdapter(List<PasutriModel> mItems, Context context) {
        this.mItems = mItems;
        this.context = context;
    }

    @NonNull
    @Override
    public PasutriAdapter.PasutriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_pasutri, parent, false);
        PasutriAdapter.PasutriViewHolder viewHolder = new PasutriAdapter.PasutriViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PasutriAdapter.PasutriViewHolder holder, int position) {

        PasutriModel pasutriModel  = mItems.get(position);
        holder.tvNik.setText(pasutriModel.getNikPas());
        holder.tvNama.setText(pasutriModel.getNamaPas());
        holder.tvTempat.setText(pasutriModel.getTempatPas());
        holder.tvTgl.setText(pasutriModel.getTglPas());
        holder.tvPend.setText(pasutriModel.getPendidikanPas());
        holder.tvPek.setText(pasutriModel.getPekerjaanPas());
        holder.tvHub.setText(pasutriModel.getHubunganPas());

        holder.pm = pasutriModel;

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class PasutriViewHolder extends RecyclerView.ViewHolder {

        TextView tvNik, tvNama, tvTempat, tvTgl, tvPend, tvPek, tvHub;
        PasutriModel pm;

        public PasutriViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNik = (TextView) itemView.findViewById(R.id.detail_pasutri_nik);
            tvNama = (TextView) itemView.findViewById(R.id.detail_nama_pasutri);
            tvTempat = (TextView) itemView.findViewById(R.id.detail_tempat_lahir_pas);
            tvTgl = (TextView) itemView.findViewById(R.id.detail_tgl_pas);
            tvPend = (TextView) itemView.findViewById(R.id.detail_pend_pas);
            tvPek = (TextView) itemView.findViewById(R.id.detail_pekerjaan_pas);
            tvHub = (TextView) itemView.findViewById(R.id.detail_hub_pas);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(context, TambahPasutriActivity.class);
                    update.putExtra("update",1);
                    update.putExtra(TAG_ID, id);
                    update.putExtra("nik",pm.getNikPas());
                    update.putExtra("nama",pm.getNamaPas());
                    update.putExtra("tmp_lhr",pm.getTempatPas());
                    update.putExtra("tgl_lhr",pm.getTglPas());
                    update.putExtra("pendidikan",pm.getPendidikanPas());
                    update.putExtra("pekerjaan",pm.getPekerjaanPas());
                    update.putExtra("status_hub",pm.getHubunganPas());

                    context.startActivity(update);
                }
            });
        }
    }
}
