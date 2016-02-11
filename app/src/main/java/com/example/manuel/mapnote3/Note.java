package com.example.manuel.mapnote3;

public class Note {
    private String title;
    private String nota;
    private double latitud;
    private double longitud;

    public Note() {
    }

    public Note(String title, String nota, double latitud, double longitud) {
        this.title = title;
        this.nota = nota;
        this.latitud = latitud;
        this.longitud = longitud;
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
}

