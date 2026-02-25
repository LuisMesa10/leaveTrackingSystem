package org.example.clases;

public interface Aprobable {

    boolean aprobado(String nombreAprobador);
    boolean denegado(String nombreAprobador, String razon);
}
