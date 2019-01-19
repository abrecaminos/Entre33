package com.example.federico.entregablemvc;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.example.federico.entregablemvc.model.POJO.Paint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class PaintAdapter extends RecyclerView.Adapter {


    private List<Paint> listaPaints;
    private ListenerPaintAdapter listenerPaintAdapter;


//INTERFAZ LISTENER DEL ADAPTER
    public interface ListenerPaintAdapter{
        public void informarSeleccion(Paint Paint);
    }

    //PROVIDER,SETTER DEL ADAPTER
    public void setListaPaints(List<Paint> listaPaints) {
        this.listaPaints = listaPaints;
        notifyDataSetChanged();
    }


    //CONSTRUCTORES
    public PaintAdapter(List<Paint> listaPaints, ListenerPaintAdapter listenerPaintAdapter) {
        this.listaPaints = listaPaints;
        this.listenerPaintAdapter = listenerPaintAdapter;
    }

    public PaintAdapter() {
        this.listaPaints = new ArrayList<>();
        this.listenerPaintAdapter = listenerPaintAdapter;
    }


//VIEWHOLDER
    public class PaintViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewtitulo;
        private ImageView imageViewPiaint;
        private Paint paint;
        //private StorageReference mStorageRef;


        public PaintViewHolder(View itemView) {
            super(itemView);
            textViewtitulo = itemView.findViewById(R.id.celda_TextTitulo);

            //mStorageRef = FirebaseStorage.getInstance().getReference();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerPaintAdapter.informarSeleccion(paint);
                }
            });
        }

        public void bind(Paint mPaint){
            this.paint = mPaint;

            textViewtitulo.setText(mPaint.getName());

            //final StorageReference paint_reference = mStorageRef.child(paint.getImage());
            //Glide.with(this).load(paint_reference).into(imageViewPiaint);
        }
    }


    //3 METODOS DEL ADAPTER
    @NonNull
    @Override
    public PaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflador = LayoutInflater.from(parent.getContext());
        View viewCelda = inflador.inflate(R.layout.celda_paints, parent, false);
        PaintViewHolder paintViewHolder = new PaintViewHolder(viewCelda);

        return paintViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Paint paint = listaPaints.get(position);
        PaintViewHolder recetasViewHolder = (PaintViewHolder) holder;
        recetasViewHolder.bind(paint);
    }


    @Override
    public int getItemCount() {
        return listaPaints.size();
    }
}
