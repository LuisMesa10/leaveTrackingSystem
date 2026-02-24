package org.example.clases;

public class SolicitudMaternidad extends SolicitudSalida {

    private int semanasLicencia;

    public SolicitudMaternidad(int solicitudID, Empleado empleado, String fechaInicio, String fechaFinal, String razonSolicitud, int semanasLicencia) {
        super(solicitudID, empleado, fechaInicio, fechaFinal, razonSolicitud);
        this.semanasLicencia = semanasLicencia;
    }

    @Override
    public int calcularDias() {
        return 0;
    }

    @Override
    public boolean SolicitudProcesamiento() {
        if(semanasLicencia <= 0){
            System.out.println("no encuentra con una licencia de maternidad activa");
            return false;
        }
        System.out.println("procesando solicitud de maternidad");
        System.out.println("Aprobadas");
        return true;
    }
}
