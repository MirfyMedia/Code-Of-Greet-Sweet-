package com.greetsweet.plant.Interface;

import com.google.gson.annotations.SerializedName;

public class RawData {

        @SerializedName("plan_id")
        private String text;

        public RawData(String text) {
            this.text = text;
        }

        // getter and setter methods

}
