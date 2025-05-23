package org.utl.ejemploretrofit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.utl.ejemploretrofit.R;
import org.utl.ejemploretrofit.model.Character;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Character> data;

    public ListAdapter(List<Character> data){
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Character c = data.get(position);
        Glide.with(holder.context)
                .load(c.getImage())
                .into(holder.imgChar);
        holder.txtNombre.setText(c.getName());
        holder.txtGen.setText(c.getGender());
        holder.txtPoder.setText(c.getKi());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder{
        TextView txtNombre, txtGen, txtPoder;

        ImageView imgChar;
        Context context;
        public  ViewHolder(@NonNull View itemView){
            super(itemView);
            context = itemView.getContext();
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtGen = itemView.findViewById(R.id.txtGen);
            txtPoder = itemView.findViewById(R.id.txtPoder);
            imgChar = itemView.findViewById(R.id.imgChar);
        }

    }
}