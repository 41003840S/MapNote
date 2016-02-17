package com.example.manuel.mapnote3;

public class Note {
    private String title;
    private String nota;
    private double latitud;
    private double longitud;
    private String imagePath;

    public Note() {
    }

    public Note(String title, String nota, double latitud, double longitud, String imagePath) {
        this.title = title;
        this.nota = nota;
        this.latitud = latitud;
        this.longitud = longitud;
        this.imagePath=imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

