package com.example.federico.entregablemvc.controller;

import com.example.federico.entregablemvc.model.DAO.PaintDAO;
import com.example.federico.entregablemvc.model.POJO.MoMA;
import com.example.federico.entregablemvc.util.ResultListener;

public class PaintController {

    public void getSpecificPaint2(final ResultListener<MoMA> listener) {

        PaintDAO paintDAO = new PaintDAO();

        paintDAO.getSpecificPaint1(new ResultListener<MoMA>() {
            @Override
            public void finish(MoMA result) {
                listener.finish(result);
            }
        });

    }

}
