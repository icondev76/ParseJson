package com.example.parsejson;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {
    private final LayoutInflater inflater;
    private Context context;
    ArrayList<Items> items;
//    DatabaseReference reference;
//    private Boolean isExists=false;
    //private RecyclerClickInterface recyclerClickInterface;

    public GalleryAdapter(Context context, ArrayList<Items> items) {
        this.context = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.each_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Items itemlist = items.get(position);
        if(itemlist.getItemUrl()!="") {
            holder.textView.setText(itemlist.getTitle());
            Picasso.with(context)
                    .load(itemlist.getItemUrl())
//                .resize(1300, 1500)
//                .onlyScaleDown()
                    .into(holder.imageView);
        }

//        holder.imageView.setOnClickListener(v -> {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse(itemlist.getItemUrl()));
//            context.startActivity(intent);
//        });
        //holder.favView.setImageResource(R.drawable.ic_fav_empty);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView favView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById((R.id.imageView));
            textView = itemView.findViewById(R.id.titleTextView);
            //favView = itemView.findViewById(R.id.favView);

//            favView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //favView.setImageResource(R.drawable.ic_fav);
////                    if(recyclerClickInterface !=null){
////                        int pos = getAdapterPosition();
////                        recyclerClickInterface.onItemClick(pos);
////
//////                        if(pos != RecyclerView.NO_POSITION){
//////                            recyclerClickInterface.onItemClick(pos);
//////                        }
////
////                    }
//                }
//            });
        }
    }
}
