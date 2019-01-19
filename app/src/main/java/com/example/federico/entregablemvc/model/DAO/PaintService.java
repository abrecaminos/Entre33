package com.example.federico.entregablemvc.model.DAO;


import com.example.federico.entregablemvc.model.POJO.MoMA;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PaintService {

    @GET("x858r")
    Call<MoMA> getSpecificPaint();
}
