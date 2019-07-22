package com.example.victorreyes.checksafe.Utilidades;

public class Validaciones {

    public static boolean esIpAddress(String ipAdress){

        boolean bandera = false;

        bandera = ipAdress.matches("(\\b(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\\b)");

        return bandera;
    }
}
