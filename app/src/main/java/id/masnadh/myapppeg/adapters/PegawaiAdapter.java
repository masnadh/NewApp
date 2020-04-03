package id.masnadh.myapppeg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.masnadh.myapppeg.R;

import id.masnadh.myapppeg.models.Pegawai;
//import id.masnadh.myapppeg.models.pegawai;

import java.util.List;

public class PegawaiAdapter extends RecyclerView.Adapter<PegawaiAdapter.PegawaiViewHolder>{

    private List<Pegawai> mItems ;
    private Context context;

    public PegawaiAdapter(List<Pegawai> mItems, Context context) {
        this.mItems = mItems;
        this.context = context;
    }



    @NonNull
    @Override
    public PegawaiAdapter.PegawaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_data,parent,false);
        PegawaiViewHolder holderData = new PegawaiViewHolder(view);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull PegawaiAdapter.PegawaiViewHolder holder, int position) {
        Pegawai pd  = mItems.get(position);
        holder.tvNama.setText(pd.getNama());
        holder.tvNip.setText(pd.getNip());
        holder.tvTempat.setText(pd.getTempatLahir());
        holder.tvAgama.setText(pd.getAgama());

        holder.pd = pd;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class PegawaiViewHolder extends RecyclerView.ViewHolder {

        TextView tvNama,tvNip,tvTempat,tvAgama;
        Pegawai pd;

        public PegawaiViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = (TextView) itemView.findViewById(R.id.detail_nama);
            tvNip = (TextView) itemView.findViewById(R.id.detail_nip);
            tvTempat = (TextView) itemView.findViewById(R.id.detail_tempat_lahir);
            tvAgama = (TextView) itemView.findViewById(R.id.detail_agama);
        }
    }

////    ArrayList<pegawai> mPegawai;
////    Context mContext;
////
////    public PegawaiAdapter(ArrayList<pegawai> mPegawai, Context mContext) {
////        this.mPegawai = mPegawai;
////        this.mContext = mContext;
////    }
////
////    @NonNull
////    @Override
////    public PegawaiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////
////        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
////        View view = layoutInflater.inflate(R.layout.item_row_data, parent, false);
////
////        return new ViewHolder(view);
////    }
////
////    @Override
////    public void onBindViewHolder(@NonNull PegawaiAdapter.ViewHolder holder, int position) {
////        pegawai dataPegawai = mPegawai.get(position);
////        holder.tvName.setText(dataPegawai.getNama());
////        String poster = dataPegawai.getPhoto();
////
////        Glide.with(mContext)
////                .load(poster)
////                .apply(RequestOptions.centerCropTransform().placeholder(R.drawable.ic_launcher_foreground))
////                .into(holder.imageView);
////    }
////
////    @Override
////    public int getItemCount() {
////        return mPegawai.size();
////    }
////
////    public class ViewHolder extends RecyclerView.ViewHolder {
////
////        ImageView imageView;
////        TextView tvName;
////
////        public ViewHolder(@NonNull View itemView) {
////            super(itemView);
////
////            imageView = (ImageView) itemView.findViewById(R.id.ivPhoto);
////            tvName = (TextView) itemView.findViewById(R.id.detail_nama);
////        }
////    }
//
////    private Activity activity;
////    private LayoutInflater inflater;
////    private List<pegawai> items;
////
////    public void setItems(List<pegawai> items) {
////        this.items = items;
////    }
////
////    public PegawaiAdapter(Activity activity, List<pegawai> items) {
////        this.activity = activity;
////        this.items = items;
////    }
////
////    @Override
////    public int getCount() {
////        return items.size();
////    }
////
////    @Override
////    public Object getItem(int position) {
////        return items.get(position);
////    }
////
////    @Override
////    public long getItemId(int position) {
////        return position;
////    }
////
////    @Override
////    public View getView(int position, View convertView, ViewGroup parent) {
////
////        if (inflater == null)
////            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////
////        if (convertView == null) convertView = inflater.inflate(R.layout.item_row_data, null);
////
////        TextView nama = (TextView) convertView.findViewById(R.id.detail_nama);
////        TextView nip = (TextView) convertView.findViewById(R.id.detail_nip);
////        TextView tempatLahir = (TextView) convertView.findViewById(R.id.detail_tempat_lahir);
////
////        pegawai dataPegawai = items.get(position);
////
////        nama.setText(dataPegawai.getNama());
////        nip.setText(dataPegawai.getNip());
////        tempatLahir.setText(dataPegawai.getTempatLahir());
////
////        return convertView;
////
////    }
////
//    private ArrayList<pegawai> mData = new ArrayList<>();
//    private Context context;
//
//    public PegawaiAdapter(ArrayList<pegawai> mData, Context context) {
//        this.mData = mData;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public PegawaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_data, parent, false);
//        PegawaiViewHolder holderData = new PegawaiViewHolder(view);
//        return holderData;
//    }
//
//    @Override
//    public void onBindViewHolder(PegawaiViewHolder holder, int position) {
//        pegawai pegawaiData = mData.get(position);
//        holder.tvName.setText(pegawaiData.getNama());
//        holder.tvNip.setText(pegawaiData.getNip());
//        holder.tvTempat.setText(pegawaiData.getTempatLahir());
////        holder.ivPhoto.setImageResource(pegawaiData.getPhoto());
//
//        holder.pegawaiData = pegawaiData;
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mData.size();
//    }
//
//    class PegawaiViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView ivPhoto;
//        TextView tvName, tvNip, tvTempat;
//        pegawai pegawaiData;
//
//        public PegawaiViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            tvName = itemView.findViewById(R.id.detail_nama);
//            tvNip = itemView.findViewById(R.id.detail_nip);
//            tvTempat = itemView.findViewById(R.id.detail_tempat_lahir);
//            ivPhoto = itemView.findViewById(R.id.ivPhoto);
//
////            itemView.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    Intent update = new Intent(context, UbahActivity.class);
////                    update.putExtra("update", 1);
////                    update.putExtra("name", pegawaiData.getNama());
////                    update.putExtra("nip", pegawaiData.getNip());
////                    update.putExtra("tempat_lhr", pegawaiData.getTempatLahir());
////
////                    context.startActivity(update);
////                }
////            });
//        }
//
////        public void bind(pegawai pegawai) {
////
////            String url_image = "https://image.tmdb.org/t/p/w185" + pegawai.getPhoto();
////
////            tvName.setText(pegawai.getNama());
////            tvNip.setText(pegawai.getNip());
////            tvTempat.setText(pegawai.getTempatLahir());
////
////            Glide.with(itemView.getContext())
////                    .load(url_image)
////                    .placeholder(R.color.colorAccent)
////                    .dontAnimate()
////                    .into(ivPhoto);
////
////        }
////
////        @Override
////        public void onClick(View v) {
////            int position = getAdapterPosition();
////            pegawai mPegawai = mData.get(position);
////
////            mPegawai.setNama(mPegawai.getNama());
////            mPegawai.setNip(mPegawai.getNip());
////            mPegawai.setTempatLahir(mPegawai.getTempatLahir());
////
////            Intent moveWithObjectIntent = new Intent(itemView.getContext(), DataActivity.class);
////
////        }
//    }
//
//
//
////    private Activity activity;
////    private LayoutInflater inflater;
////    private List items;
////
////    @Override
////    public int getCount() {
////        return items.size();
////    }
////
////    @Override
////    public Object getItem(int position) {
////        return items.get(position);
////    }
////
////    @Override
////    public long getItemId(int position) {
////        return position;
////    }
////
////    @Override
////    public View getView(int position, View convertView, ViewGroup parent) {
////
////        if (inflater == null)
////            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////
////        if (convertView == null) convertView= inflater.inflate(R.layout.)
////
////        return null;
////    }
////
////
////    public class PegawaiViewHolder extends RecyclerView.ViewHolder {
////        public PegawaiViewHolder(@NonNull View itemView) {
////            super(itemView);
////        }
////    }
}
