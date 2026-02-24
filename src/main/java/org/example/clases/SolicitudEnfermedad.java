package org.example.clases;

public class SolicitudEnfermedad extends SolicitudSalida {
    private boolean certificadoMedicoValido;

    public SolicitudEnfermedad(int solicitudID, Empleado empleado, String fechaInicio, String fechaFinal, String estadoSolicitud, String razonSolicitud, boolean certificadoMedicoValido) {
        super(solicitudID, empleado, fechaInicio, fechaFinal, razonSolicitud);
        this.certificadoMedicoValido = certificadoMedicoValido;
    }


    public boolean esCertificadoMedicoValido(){
        return certificadoMedicoValido;
    }

    public boolean isCertificadoMedicoValido() {
        return certificadoMedicoValido;
    }

    public void setCertificadoMedicoValido(boolean certificadoMedicoValido) {
        this.certificadoMedicoValido = certificadoMedicoValido;
    }

    @Override
    public int calcularDias() {
        return 3;
    }

    @Override
    public boolean SolicitudProcesamiento() {
        if(!certificadoMedicoValido && calcularDias() > 2){
            System.out.println("Se requiere certificado medico. Solicitud por enfermedad Denegada");
            return false;
        }
        System.out.println("Procesando solicitud por enfermedad..");
        return true;

    }

    @Override
    public String toString() {
        return "SolicitudEnfermedad{" +
                "certificadoMedicoValido=" + certificadoMedicoValido +
                '}';
    }
}
