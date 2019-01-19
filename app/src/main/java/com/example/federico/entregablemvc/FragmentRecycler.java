package com.example.federico.entregablemvc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.federico.entregablemvc.controller.PaintController;
import com.example.federico.entregablemvc.model.POJO.MoMA;
import com.example.federico.entregablemvc.model.POJO.Paint;
import com.example.federico.entregablemvc.util.ResultListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FragmentRecycler extends Fragment implements PaintAdapter.ListenerPaintAdapter {

    private RecyclerView recyclerViewPaints;
    private PaintAdapter paintAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ListenerFragmentRecycler listenerFragmentRecycler;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recycler, container, false);


        recyclerViewPaints = view.findViewById(R.id.recyclerview_paints);

        List<Paint> recetas = new ArrayList<>();
        paintAdapter = new PaintAdapter(recetas, this);

        recyclerViewPaints.setAdapter(paintAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewPaints.setLayoutManager(linearLayoutManager);

        PaintController paintController = new PaintController();
        paintController.getSpecificPaint2(new ResultListener<MoMA>() {
            @Override
            public void finish(MoMA result) {
                paintAdapter.setListaPaints(result.getPaints());
            }
        });

        return view;

    }


    @Override
    public void informarSeleccion(Paint paint) {
        listenerFragmentRecycler.informarSeleccion(paint);
    }

    public interface ListenerFragmentRecycler{
        public void informarSeleccion(Paint paint);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listenerFragmentRecycler = (ListenerFragmentRecycler) context;
    }

}
