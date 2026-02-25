package org.example;

import org.example.clases.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        LeaveTrackingSystem sistema = new LeaveTrackingSystem();
        Empleado emp = new Empleado(1,"Luis", "TI", "luis@");
        sistema.agregarEmpleado(emp);

        sistema.registrarSolicitud(
                new SolicitudEnfermedad(1, emp, "01-01-2026", "03-01-2026", "Enfermedad General", false)
        );

        sistema.registrarSolicitud(
                new SolicitudVacaciones(2, emp, "05-01-2026", "10-01-2026", "vacaciones", 5)
        );

        sistema.registrarSolicitud(
                new SolicitudMaternidad(3, emp, "15-01-2026", "15-04-2026", "maternidad", 12)
        );

        sistema.procesarSiguienteSolicitud();
        sistema.areaTienePendientes("TI");
        sistema.procesarSiguienteSolicitud();

        sistema.procesarSiguienteSolicitud();
        sistema.eliminarEmpleado(1);
        sistema.mostrarTodasLasSolicitudes();

    }
}