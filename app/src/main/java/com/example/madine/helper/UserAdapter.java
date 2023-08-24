package com.example.madine.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madine.R;
import com.example.madine.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UserAdapter extends FirebaseRecyclerAdapter<User,UserAdapter.UserViewholder> {
    private Activity activity;

    private DatabaseReference mbase;
    private String noUnit, name, telpNo, license;

    public UserAdapter(
            @NonNull FirebaseRecyclerOptions<User> options)
    {
        super(options);
        this.activity = activity;
        this.mbase = FirebaseDatabase.getInstance().getReference("user");
    }

    @Override
    protected void
    onBindViewHolder(@NonNull UserViewholder holder,
                     @SuppressLint("RecyclerView") int position, @NonNull User model)
    {
        noUnit = model.getUnitNo();
        name = model.getName();
        telpNo = model.getPhoneNumber();
        license= model.getLicenseNumber();


        holder.noUnittv.setText(noUnit);
        holder.nametv.setText(name);
        holder.telpNotv.setText(telpNo);
        holder.licensetv.setText(license);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mbase.child(model.getUnitNo()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(view.getContext(),"success", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setMessage("Are you sure want delete " + model.getUnitNo() + "?");
                builder.show();
            }
        });


    }

    // Function to tell the class about the Card view (here
    // "User.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public UserViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new UserAdapter.UserViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "User.xml")
    static class UserViewholder
            extends RecyclerView.ViewHolder {
        TextView noUnittv, nametv,telpNotv,licensetv;
        ImageView delete;
        public UserViewholder(@NonNull View itemView)
        {
            super(itemView);

            noUnittv = itemView.findViewById(R.id.info_noUnit_tv);
            telpNotv = itemView.findViewById(R.id.info_telpNo_tv);
            nametv = itemView.findViewById(R.id.info_name_tv);
            licensetv = itemView.findViewById(R.id.info_license_tv);
            delete = itemView.findViewById(R.id.delete);
        }
    }


}
