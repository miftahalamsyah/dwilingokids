package com.example.dwilingokids.model;

public class modelTense {
    private String idCat, titleTense, contentTense, created_AT;

    public modelTense(String idCat, String titleTense, String contentTense, String created_AT) {
        this.idCat = idCat;
        this.titleTense = titleTense;
        this.contentTense = contentTense;
        this.created_AT = created_AT;
    }

    public String getIdCat() {
        return idCat;
    }

    public String getTitleTense() {
        return titleTense;
    }

    public String getContentTense() {
        return contentTense;
    }

    public String getCreated_AT() {
        return created_AT;
    }
}
