package org.example.clases;

public class SolicitudVacaciones extends SolicitudSalida{
    private int diasSolicitados;

    public SolicitudVacaciones(int solicitudID, Empleado empleado, String fechaInicio, String fechaFinal, String razonSolicitud, int diasSolicitados) {
        super(solicitudID, empleado, fechaInicio, fechaFinal, razonSolicitud);
        this.diasSolicitados = diasSolicitados;
    }

    @Override
    public int calcularDias() {
        return diasSolicitados;
    }

    @Override
    public boolean SolicitudProcesamiento() {
        if(diasSolicitados > getEmpleado().getDiasDisponiblesSalidas()){
            System.out.println("No hay suficientes dias disponibles para la solicitud");
            return false;
        }
        System.out.println("Procesando solicitud de vacaciones...");
        System.out.println("Aprobadas");
        return true;
    }
}
