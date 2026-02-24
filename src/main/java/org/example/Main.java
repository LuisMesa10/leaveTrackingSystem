package org.example;

import org.example.clases.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Empleado emp = new Empleado(1, "Luis", "IT", "luis@email.com");

        ArrayList<SolicitudSalida> solicitudes = new ArrayList<>();

        solicitudes.add(new SolicitudEnfermedad(1, emp, "01-01-2026", "03-01-2026", null, "enfermedad",false ));
        solicitudes.add(new SolicitudVacaciones(2, emp, "05-01-2026", "10-01-2026", "vacaciones",5 ));
        solicitudes.add(new SolicitudMaternidad(3, emp, "15-01-2026", "15-04-2026", "maternidad", 12));

        for (SolicitudSalida solicitud : solicitudes) {
            solicitud.SolicitudProcesamiento(); // 🔥 POLIMORFISMO
        }
    }
}