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

public class ExcAdapter extends RecyclerView.Adapter<ExcAdapter.VH> {

    ArrayList<Excercise> exlist;
    Context context;

    public ExcAdapter(ArrayList<Excercise> exlist, Context context) {
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

        holder.btnaddex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialogexselect);
                Button dialogButton = dialog.findViewById(R.id.btnaddex);
                EditText etreps =  dialog.findViewById(R.id.tvreps);
                Spinner spex = dialog.findViewById(R.id.spex);

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (etreps.getText().toString().equals(""))
                        {
                            etreps.setError("Enter Reps");
                        }
                        else {

                            DatabaseReference refap = FirebaseDatabase.getInstance().getReference("cp");
                            String id = refap.push().getKey();

                            BegEx begEx = new BegEx(holder.tvex.getText().toString(),etreps.getText().toString());

                            if (spex.getSelectedItem().toString().equals("Full Body"))
                            {
                                refap.child(FirebaseAuth.getInstance().getUid()).child("fb").child(id).setValue(begEx);
                                Toast.makeText(context, "Excercise Added", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                            else if(spex.getSelectedItem().toString().equals("Upper Body"))
                            {
                                refap.child(FirebaseAuth.getInstance().getUid()).child("ub").child(id).setValue(begEx);
                                Toast.makeText(context, "Excercise Added", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();


                            }
                            else if(spex.getSelectedItem().toString().equals("Lower Body"))
                            {
                                refap.child(FirebaseAuth.getInstance().getUid()).child("lb").child(id).setValue(begEx);
                                Toast.makeText(context, "Excercise Added", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();


                            }
                            else {
                                Toast.makeText(context, "Somthing Went Wrong...", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }

                            
                        }
                    }
                });
                dialog.show();
            }


        });

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
