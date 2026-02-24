package org.example.clases;

import java.util.ArrayList;

public abstract class SolicitudSalida implements Aprobable {
    private int solicitudID;
    private Empleado empleado;
    private String fechaInicio;
    private String fechaFinal;
    private String estadoSolicitud;
    private String razonSolicitud;

    //cambioDeEstado es una clase interna de la clase SolicitudSalida
    private ArrayList<cambioDeEstado> historialEstados = new ArrayList<>();


    public SolicitudSalida() {
    }

    public SolicitudSalida(int solicitudID, Empleado empleado, String fechaInicio, String fechaFinal, String razonSolicitud) {
        this.solicitudID = solicitudID;
        this.empleado = empleado;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.estadoSolicitud = "pendiente"; //estado predeterminado
        this.razonSolicitud = razonSolicitud;
    }

    //metodo abstracto
    public abstract int calcularDias();

    //metodo que puede ser sobreescrito (polimorfimso)
    public boolean SolicitudProcesamiento(){
        System.out.println("Procesando Solicitud...");
        return true;
    }

    //implementación de la interface
    @Override
    public boolean aprobado(String nombreAprobador) {
        return false;
    }

    @Override
    public boolean denegeado(String nombreAprobador, String razon) {
        return false;
    }

    //cambio de estado con historial
    public void cambiarEstado(String nuevoEstado, String cambiadoPor){
        String estadoAnterior = this.estadoSolicitud;
        this.estadoSolicitud = nuevoEstado;

        cambioDeEstado cambio = new cambioDeEstado(
                estadoAnterior,
                nuevoEstado,
                "HOY",
                cambiadoPor
        );
        historialEstados.add(cambio);
    }

    public void mostrasHistorialEstados(){
        for(cambioDeEstado cambio : historialEstados){
            System.out.println(cambio);
        }
    }

    public int getSolicitudID() {
        return solicitudID;
    }

    public void setSolicitudID(int solicitudID) {
        this.solicitudID = solicitudID;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public String getRazonSolicitud() {
        return razonSolicitud;
    }

    public void setRazonSolicitud(String razonSolicitud) {
        this.razonSolicitud = razonSolicitud;
    }

    @Override
    public String toString() {
        return "SolicitudSalida{" +
                "solicitudID=" + solicitudID +
                ", empleado=" + empleado +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaFinal='" + fechaFinal + '\'' +
                ", estadoSolicitud='" + estadoSolicitud + '\'' +
                ", razonSolicitud='" + razonSolicitud + '\'' +
                '}';
    }

    private class cambioDeEstado {
        private String estadoAnterior;
        private String nuevoEstado;
        private String fechaCambio;
        private String cambiadoPor;

        public cambioDeEstado(String estadoAnterior, String nuevoEstado, String fechaCambio, String cambiadoPor) {
            this.estadoAnterior = estadoAnterior;
            this.nuevoEstado = nuevoEstado;
            this.fechaCambio = fechaCambio;
            this.cambiadoPor = cambiadoPor;
        }

        @Override
        public String toString() {
            return "Cambio de estado: "+ estadoAnterior + "-> " + nuevoEstado + "|Fecha: " + fechaCambio +
                    "|por: "+ cambiadoPor;
        }
    }
}
