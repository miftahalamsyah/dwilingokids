package com.example.dwilingokids.model;

public class modelReading {
    private String idCat, titleReading, contentReading, created_AT;

    public modelReading(String idCat, String titleReading, String contentReading, String created_AT) {
        this.idCat = idCat;
        this.titleReading = titleReading;
        this.contentReading = contentReading;
        this.created_AT = created_AT;
    }

    public String getIdCat() {
        return idCat;
    }

    public String getTitleReading() {
        return titleReading;
    }

    public String getContentReading() {
        return contentReading;
    }

    public String getCreated_AT() {
        return created_AT;
    }
}

