package org.example.clases;

import java.util.*;

public class LeaveTrackingSystem {
    //MAP para acceso rapido por id de los empleados
    private HashMap<Integer, Empleado> empleadosPorId = new HashMap<>();
    //MAP para acceso rapido por id de las solicitudes
    private HashMap<Integer, SolicitudSalida> solicitudesPorId = new HashMap<>();


    //LIST para orden general de las solicitudes
    private ArrayList<SolicitudSalida> todasLasSolicitudes = new ArrayList<>();

    //SET para areas con solicitudes pendientes
    private HashSet<String> areasConPendientes = new HashSet<>();

    //QUEUE para las solicitudes pendientes de aprobar
    private Queue<SolicitudSalida> colaPendientes = new LinkedList<>();


    //Metodos

    //Agregar empleado
    public void agregarEmpleado(Empleado empleado){
        empleadosPorId.put(empleado.getEmpleadoID(), empleado);
    }

    //Buscar Empleado
    public Empleado buscarEmpleado(int id){
        return empleadosPorId.get(id);
    }

    //Eliminar Empleado
    public boolean eliminarEmpleado(int id){
        return empleadosPorId.remove(id) != null;
    }


    //Registrar Solicitudes
    public void registrarSolicitud(SolicitudSalida solicitud){

        //GUARDAR EN MAP
        solicitudesPorId.put(solicitud.getSolicitudID(), solicitud);

        //GUARDAR EN LISTA
        todasLasSolicitudes.add(solicitud);

        //AGREGAR A COLA PENDIENTES
        colaPendientes.add(solicitud);

        //AGREGAR AL SET EL AREA DEL EMPLEADO QUE REALIZÓ LA SOLICITUD
        areasConPendientes.add(solicitud.getEmpleado().getArea());
    }

    //PROCESAR SOLICITUDES EN COLA
    public void procesarSiguienteSolicitud(){
        if(colaPendientes.isEmpty()){
            System.out.println("No hay solicitudes Pendientes.");
            return;
        }

        SolicitudSalida solicitud = colaPendientes.poll();

        boolean resultado = solicitud.SolicitudProcesamiento();

        if(resultado){
            solicitud.cambiarEstado("Aprobada", "Sistema");

        }else{
            solicitud.cambiarEstado("Denegada", "Sistema");
        }

        actualizarAreasPendientes();
    }

    //Actualiza el set dinamicamente
    public void actualizarAreasPendientes(){
        areasConPendientes.clear();

        for(SolicitudSalida solicitud : colaPendientes){
            areasConPendientes.add(
                    solicitud.getEmpleado().getArea()
            );
        }
    }

    //Verificar si un area tiene pendientes
    public boolean areaTienePendientes(String area){
        return areasConPendientes.contains(area);
    }

    //BUSCAR SOLICITUD POR ID
    public SolicitudSalida obtenerSolicitud(int id){
        return solicitudesPorId.get(id);
    }

    //MOSTRAR TODAS LAS SOLICITUDES EN LA LISTA
    public void mostrarTodasLasSolicitudes(){
        for(SolicitudSalida solicitud : todasLasSolicitudes){
            System.out.println(solicitud + " | \n" );
        }
    }
}
