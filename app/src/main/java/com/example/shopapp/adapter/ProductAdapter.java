package com.example.shopapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.R;
import com.example.shopapp.data.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private ArrayList<Product> dataSet;
    private DatabaseReference databaseReference;
    public ProductAdapter(ArrayList<Product> dataSet) {
        this.dataSet = dataSet ;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewNameProduct;
        TextView textViewAmountProduct;
        ImageButton removeBtn;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameProduct = itemView.findViewById(R.id.product);
            textViewAmountProduct = itemView.findViewById(R.id.amount);
            removeBtn = itemView.findViewById(R.id.remove);
        }
    }

    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row ,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder holder, int position) {
        holder.textViewNameProduct.setText(dataSet.get(position).getNameProduct());
        holder.textViewAmountProduct.setText(dataSet.get(position).getAmount());

        holder.removeBtn.setOnClickListener(v -> {
            removeItem(position);
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void removeItem(int position) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentProduct = mAuth.getCurrentUser();
        String productId = currentProduct.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("products").child(productId);

        String key = dataSet.get(position).getKey(); // Get the key of the item to be removed
        DatabaseReference itemRef = myRef.child(key); // Adjust the reference as per your database structure
        itemRef.removeValue(); // Remove the item from Firebase Realtime Database

        // Remove item from the RecyclerView
        dataSet.remove(position);
        notifyItemRemoved(position);
    }

}
