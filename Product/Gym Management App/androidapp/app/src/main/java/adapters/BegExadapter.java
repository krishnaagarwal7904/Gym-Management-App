package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.practice.gt.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import models.BegEx;

public class BegExadapter extends RecyclerView.Adapter<BegExadapter.VH> {
    ArrayList<BegEx> arrayList;
    Context context;


    public BegExadapter(ArrayList<BegEx> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public BegExadapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardbegex, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BegExadapter.VH holder, int position) {

            BegEx b = arrayList.get(position);
            holder.tvreps.setText(b.getReps());
            holder.tvname.setText(b.getName());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class VH extends RecyclerView.ViewHolder {

        TextView tvname, tvreps;

        public VH(@NonNull View itemView) {
            super(itemView);

            tvname = itemView.findViewById(R.id.tvname);
            tvreps = itemView.findViewById(R.id.tvreps);


        }
    }
}
