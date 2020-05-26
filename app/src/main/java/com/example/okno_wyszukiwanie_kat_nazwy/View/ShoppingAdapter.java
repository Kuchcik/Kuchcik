package com.example.okno_wyszukiwanie_kat_nazwy.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.okno_wyszukiwanie_kat_nazwy.Model.ProductModel;
import com.example.okno_wyszukiwanie_kat_nazwy.R;

import java.util.ArrayList;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {
    private ArrayList<ProductModel> mProductList;
    private OnItemClickLitener mListener;

    public interface OnItemClickLitener{
        void onItemClick(int posistion);
        void onDeleteClick(int position);
    }

    public void setOnClickListener(OnItemClickLitener listener){
        mListener=listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView shoppingName;
        public TextView shoppingQuantity;
        public ImageView mDeleteImage;


        public ViewHolder(@NonNull View itemView, final OnItemClickLitener listener) {
            super(itemView);
            shoppingName = itemView.findViewById(R.id.shoppingName);
            shoppingQuantity = itemView.findViewById(R.id.shoppingQuantity);
            mDeleteImage=itemView.findViewById(R.id.removeProduct);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public ShoppingAdapter(ArrayList<ProductModel> productList){
        mProductList=productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_item,parent,false);
        ViewHolder evh = new ViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel currentItem = mProductList.get(position);

        holder.shoppingName.setText(currentItem.getProductName());
        holder.shoppingQuantity.setText(currentItem.getQuantity());
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }
}
