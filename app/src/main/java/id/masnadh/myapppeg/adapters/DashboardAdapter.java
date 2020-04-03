package id.masnadh.myapppeg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.masnadh.myapppeg.R;

import id.masnadh.myapppeg.models.dashboard;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHoder> {

    private Context mContext;
    private List<dashboard> dashboardList;
    private onItemClickListener onItemClickListener;


    public interface onItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener (onItemClickListener listener){
        onItemClickListener = listener;
    }

    public DashboardAdapter(Context mContext, List<dashboard> dashboardList) {
        this.mContext = mContext;
        this.dashboardList = dashboardList;
    }

    public List<dashboard> getDashboardList() {
        return dashboardList;
    }

    public void setDashboardList(List<dashboard> dashboardList) {
        this.dashboardList = dashboardList;
    }

    //    public DashboardAdapter(Context context) {
//        this.mContext = context;
//    }


    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_menu, parent, false);
        final MyViewHoder viewHolder = new MyViewHoder(itemView, onItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHoder holder, final int position) {

        dashboard mDashboard = getDashboardList().get(position);
//        Glide.with(mContext)
//                .load(mDashboard.getImg())
//                .apply(new RequestOptions().override(350,350))
//                .into(holder.imgMenu);

        holder.tvTitle.setText(mDashboard.getTitle());
        holder.imgMenu.setImageResource(mDashboard.getImg());
//        holder.tvTitle.setText(getDashboardList().get(position).getTitle());
//        holder.imgMenu.setImageResource(getDashboardList().get(position).getImg());
//        final dashboard dashboard = dashboardList.get(position);
//        holder.tvTitle.setText(dashboard.getTitle());
//
//        Glide.with(mContext)
//                .load(dashboard.getImg())
//                .into(holder.imgMenu);
//
//        holder.cdView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return dashboardList.size();
    }

    class MyViewHoder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        ImageView imgMenu;
        CardView cdView;

        MyViewHoder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_menu_title);
            imgMenu = itemView.findViewById(R.id.img_menu);
            cdView = itemView.findViewById(R.id.card_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.OnItemClick(position);
                }
            });
        }
    }

//    private Context mContext;
//    private ArrayList<dashboard> dashboarList = new ArrayList<>();
//    private onItemClickListener onItemClickListener;

//    public void setDashboarList(ArrayList<dashboard> items) {
//        dashboarList.clear();
//        dashboarList.addAll(items);
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu, parent, false);
//        final MyViewHoder viewHoder = new MyViewHoder(view, onItemClickListener);
//        return viewHoder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull DashboardAdapter.MyViewHoder holder, int position) {
//        holder.bind(dashboarList.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return dashboarList.size();
//    }
//
//    class MyViewHoder extends RecyclerView.ViewHolder {
//
//        TextView tvTitle;
//        ImageView imgMenu;
//        CardView cdView;
//
//        MyViewHoder(@NonNull View itemView, final onItemClickListener onItemClickListener) {
//            super(itemView);
//
//            tvTitle = itemView.findViewById(R.id.tv_menu_title);
//            imgMenu = itemView.findViewById(R.id.img_menu);
//            cdView = itemView.findViewById(R.id.card_view);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    onItemClickListener.OnItemClick(position);
//                }
//            });
//
//
//        }
//
//        public void bind(dashboard dashboard) {
//
//            tvTitle.setText(dashboard.getTitle());
//
//
//
//        }
//    }
//
//    public interface onItemClickListener{
//        void OnItemClick(int position);
//    }
}
