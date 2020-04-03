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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import id.masnadh.myapppeg.R;
import id.masnadh.myapppeg.activities.PendidikanActivity;
import id.masnadh.myapppeg.activities.TambahPendidikanActivity;
import id.masnadh.myapppeg.models.PendidikanModel;

public class PendidikanAdapter extends RecyclerView.Adapter<PendidikanAdapter.ViewHolder> {

    private List<PendidikanModel> mItems;
    private Context context;
    SharedPreferences sharedpreferences;
    Boolean session = false;
    public final static String TAG_ID = "id";
    String id;

    public PendidikanAdapter(List<PendidikanModel> mItems, Context context) {
        this.mItems = mItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_pendidikan, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PendidikanAdapter.ViewHolder holder, int position) {

        PendidikanModel pendidikanModel  = mItems.get(position);
        holder.tvJenjang.setText(pendidikanModel.getJenjang());
        holder.tvNama.setText(pendidikanModel.getNamaSek());
        holder.tvProdi.setText(pendidikanModel.getProdi());
        holder.tvLulus.setText(pendidikanModel.getLulus());

        holder.pm = pendidikanModel;

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNama, tvJenjang, tvLulus, tvProdi;
        PendidikanModel pm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = (TextView) itemView.findViewById(R.id.detail_nama_sekolah);
            tvJenjang = (TextView) itemView.findViewById(R.id.detail_tingkat);
            tvProdi = (TextView) itemView.findViewById(R.id.detail_prodi);
            tvLulus = (TextView) itemView.findViewById(R.id.detail_lulus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(context, TambahPendidikanActivity.class);
                    update.putExtra("update",1);
                    update.putExtra(TAG_ID, id);
                    update.putExtra("tingkat",pm.getJenjang());
                    update.putExtra("nama_sekolah",pm.getNamaSek());
                    update.putExtra("jurusan",pm.getProdi());
                    update.putExtra("thn_lulus",pm.getLulus());

                    context.startActivity(update);
                }
            });
        }
    }
}
