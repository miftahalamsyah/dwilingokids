package com.example.dwilingokids.model;

public class modelListening {
    private String idCat, titleListening, contentListening, created_AT;

    public modelListening(String idCat, String titleListening, String contentListening, String created_AT) {
        this.idCat = idCat;
        this.titleListening = titleListening;
        this.contentListening = contentListening;
        this.created_AT = created_AT;
    }

    public String getIdCat() {
        return idCat;
    }

    public String getTitleListening() {
        return titleListening;
    }

    public String getContentListening() {
        return contentListening;
    }

    public String getCreated_AT() {
        return created_AT;
    }
}
