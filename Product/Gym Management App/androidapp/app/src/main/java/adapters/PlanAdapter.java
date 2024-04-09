package adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.practice.gt.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import models.BegEx;
import models.Excercise;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.VH> {

    ArrayList<Excercise> exlist;
    Context context;

    public PlanAdapter(ArrayList<Excercise> exlist, Context context) {
        this.exlist = exlist;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardex, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Excercise e = exlist.get(position);
        holder.tvbp.setText(e.getBodyPart().toUpperCase());
        holder.tvdes.setText(e.getDescription());
        holder.tvex.setText(e.getExercise().toUpperCase());


    }

    @Override
    public int getItemCount() {
        return exlist.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView tvex, tvbp, tvdes;
        Button btnaddex;

        public VH(@NonNull View itemView) {
            super(itemView);

            tvex = itemView.findViewById(R.id.tvex);
            tvbp = itemView.findViewById(R.id.tvbp);
            tvdes = itemView.findViewById(R.id.tvdesc);
            btnaddex = itemView.findViewById(R.id.btnadd);
        }
    }
}
