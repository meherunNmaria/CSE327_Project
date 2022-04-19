package com.example.ambulanceapp;

public class confirmRide {
    String fee,service_type;

    public confirmRide(String fee, String service_type) {
        this.fee = fee;
        this.service_type = service_type;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }
}
