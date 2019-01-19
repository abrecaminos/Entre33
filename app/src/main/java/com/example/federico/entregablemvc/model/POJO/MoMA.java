package com.example.federico.entregablemvc.model.POJO;

import com.example.federico.entregablemvc.model.POJO.Paint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoMA {

        @SerializedName("paints")
        @Expose
        private List<Paint> paints;

        public List<Paint> getPaints() {
            return paints;
        }

        public void setPaints(List<Paint> paints) {
            this.paints = paints;
        }

}
