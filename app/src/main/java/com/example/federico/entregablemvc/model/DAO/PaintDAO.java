package com.example.federico.entregablemvc.model.DAO;

import com.example.federico.entregablemvc.model.POJO.MoMA;
import com.example.federico.entregablemvc.util.MoMAHelper;
import com.example.federico.entregablemvc.util.ResultListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaintDAO extends MyRetrofit {

    private PaintService paintService;

    public PaintDAO() {
        super(MoMAHelper.BASE_URL);
        paintService = retrofit.create(PaintService.class);
    }

    public void getSpecificPaint1(final ResultListener<MoMA> listener){

        Call<MoMA> call = paintService.getSpecificPaint();

        call.enqueue(new Callback<MoMA>() {
            @Override
            public void onResponse(Call<MoMA> call, Response<MoMA> response) {
                listener.finish(response.body());
            }

            @Override
            public void onFailure(Call<MoMA> call, Throwable t) {

            }
        });

    }


}
