package com.creative.share.apps.wash_squad.models;

import java.io.Serializable;
import java.util.List;

public class CarTypeDataModel implements Serializable {

    private List<CarTypeModel> data;

    public List<CarTypeModel> getData() {
        return data;
    }

    public static class CarTypeModel implements Serializable
    {
        private int id;
        private String ar_title;
        private String en_title;

        public CarTypeModel(String ar_title, String en_title) {
            this.ar_title = ar_title;
            this.en_title = en_title;
        }

        public int getId() {
            return id;
        }

        public String getAr_title() {
            return ar_title;
        }

        public String getEn_title() {
            return en_title;
        }
    }
}
