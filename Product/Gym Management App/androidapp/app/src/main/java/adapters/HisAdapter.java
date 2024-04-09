package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.practice.gt.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import models.Excercise;
import models.Histrory;

public class HisAdapter extends RecyclerView.Adapter<HisAdapter.VH> {

    ArrayList<Histrory> exlist;
    Context context;

    public HisAdapter(ArrayList<Histrory> exlist, Context context) {
        this.exlist = exlist;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardhistory, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Histrory e = exlist.get(position);
        holder.tvdate.setText(e.getDate());
        holder.tvplan.setText(e.getPlan());
        holder.tvtime.setText(e.getTime());

    }

    @Override
    public int getItemCount() {
        return exlist.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView tvdate, tvtime, tvplan;


        public VH(@NonNull View itemView) {
            super(itemView);

            tvdate = itemView.findViewById(R.id.tvdate);
            tvtime = itemView.findViewById(R.id.tvtime);
            tvplan = itemView.findViewById(R.id.tvplan);
        }
    }
}

