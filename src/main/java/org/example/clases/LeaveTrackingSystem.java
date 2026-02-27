package org.example.clases;

import java.io.*;
import java.util.*;

public class LeaveTrackingSystem {
    public LeaveTrackingSystem() {
        //INICIALIZACION Y CARGA DE ARCHIVOS
        inicializarEstructuraArchivos();
        cargarEmpleados();
    }

    //PERSISTENCIA DE LOS DATOS POR MEDIO DE CARPETAS Y ARCHIVOS
    private File dataDir = new File("leaveTracker_data");
    private File employeesFile = new File(dataDir, "employees.csv");
    private File requestDir = new File(dataDir, "requests");


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
        guardarEmpleados();
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

        guardarSolicitud(solicitud); //persistencia en directorio y archivo
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
        guardarSolicitud(solicitud); //Actualizar el archivo y cambia el estado
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

   //___________________________________________________________________________________________________________________
    //METODOS PARA EL MANEJO DE DATOS Y DIRECTORIOS
    public void inicializarEstructuraArchivos(){
        if(!dataDir.exists()){
            dataDir.mkdir();
            System.out.println("Directorio Principal Creado");
        }

        if(!requestDir.exists()){
            requestDir.mkdir();
            System.out.println("Directorio de solicitudes creado");
        }
    }

    // Metodo para guardar empleados
    public void guardarEmpleados(){
        try(FileWriter writer = new FileWriter(employeesFile)) {  //declaración de recursos que se cierran automaticamente apenas se acaba el try
            writer.write("ID,Nombre,Area,Email,DiasDisponibles\n");
            for(Empleado emp : empleadosPorId.values()){
                writer.write(
                        emp.getEmpleadoID()+ ","+
                            emp.getNombre()+","+
                                emp.getArea() + "," +
                                emp.getEmail() + "," +
                                emp.getDiasDisponiblesSalidas() + "\n"
                );
            }
            System.out.println("Empleados guardados correctamente");
        }catch (IOException e){
            System.out.println("Error guardando empleados: "+ e.getMessage());
        }
    }

    //METODO PARA CARGAR EMPLEADOS
    public void cargarEmpleados(){
        if(!employeesFile.exists()){
            System.out.println("No existe archivo de empleados");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(employeesFile))){ //declaración de recursos que se cierran automaticamente apenas se acaba el try
            reader.readLine(); //Para saltarse el encabezado
            String linea;
            while ((linea = reader.readLine()) != null){
                String[] partes = linea.split(",");


                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                String area = partes[2];
                String email = partes[3];
                int dias = Integer.parseInt(partes[4]);

                Empleado emp = new Empleado(id, nombre, area, email);
                emp.setDiasDisponiblesSalidas(dias);

                empleadosPorId.put(id, emp);

            }
            System.out.println("Empleados cargados correctamente");
        }catch (Exception e){
            System.out.println("Error cargando empleados: "+ e.getMessage());
        }

    }

    //Metood para guardar solicitudes individuales
    public void guardarSolicitud(SolicitudSalida solicitud) {

        File archivoSolicitud = new File(
                requestDir,
                solicitud.getSolicitudID() + ".txt"
        );

        try (PrintWriter writer = new PrintWriter(new FileWriter(archivoSolicitud))) {

            writer.println("ID: " + solicitud.getSolicitudID());
            writer.println("Empleado: " + solicitud.getEmpleado().getEmpleadoID());
            writer.println("Inicio: " + solicitud.getFechaInicio());
            writer.println("Fin: " + solicitud.getFechaFinal());
            writer.println("Estado: " + solicitud.getEstadoSolicitud());
            writer.println("Razon: " + solicitud.getRazonSolicitud());

            writer.println("Tipo: " + solicitud.getClass().getSimpleName());

            System.out.println("Solicitud guardada.");

        } catch (IOException e) {
            System.out.println("Error guardando solicitud: " + e.getMessage());
        }
    }

    //METODO PARA GUARDAR IMAGENES DE LOS EMPLEADOS, USANDO BYTESTREAMS
    public void guardarImagenEmpleado(Empleado emp, File imagen) {

        File imagesDir = new File(dataDir, "images");

        if (!imagesDir.exists()) {
            imagesDir.mkdir();
        }

        File destino = new File(imagesDir, emp.getEmpleadoID() + ".jpg");

        try (FileInputStream in = new FileInputStream(imagen);
             FileOutputStream out = new FileOutputStream(destino)) {

            byte[] buffer = new byte[1024];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            System.out.println("Imagen guardada correctamente.");

        } catch (IOException e) {
            System.out.println("Error guardando imagen: " + e.getMessage());
        }
    }

    //CARGAR SOLICITUDES AL INICIAR
    public void cargarSolicitudes() {

        File[] archivos = requestDir.listFiles();

        if (archivos == null) return;

        for (File archivo : archivos) {

            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {

                String linea;
                int id = 0;
                int empleadoId = 0;
                String inicio = "";
                String fin = "";
                String estado = "";
                String razon = "";
                String tipo = "";

                while ((linea = reader.readLine()) != null) {

                    if (linea.startsWith("ID:"))
                        id = Integer.parseInt(linea.split(": ")[1]);

                    else if (linea.startsWith("Empleado:"))
                        empleadoId = Integer.parseInt(linea.split(": ")[1]);

                    else if (linea.startsWith("Inicio:"))
                        inicio = linea.split(": ")[1];

                    else if (linea.startsWith("Fin:"))
                        fin = linea.split(": ")[1];

                    else if (linea.startsWith("Estado:"))
                        estado = linea.split(": ")[1];

                    else if (linea.startsWith("Razon:"))
                        razon = linea.split(": ")[1];

                    else if (linea.startsWith("Tipo:"))
                        tipo = linea.split(": ")[1];
                }

                Empleado emp = empleadosPorId.get(empleadoId);
                if (emp == null) continue;

                SolicitudSalida solicitud = null;

                switch (tipo) {

                    case "SolicitudEnfermedad":
                        solicitud = new SolicitudEnfermedad(
                                id,
                                emp,
                                inicio,
                                fin,
                                razon,
                                true   // valor por defecto
                        );
                        break;

                    case "SolicitudVacaciones":
                        solicitud = new SolicitudVacaciones(
                                id,
                                emp,
                                inicio,
                                fin,
                                razon,
                                5   // valor por defecto coherente
                        );
                        break;

                    case "SolicitudMaternidad":
                        solicitud = new SolicitudMaternidad(
                                id,
                                emp,
                                inicio,
                                fin,
                                razon,
                                12  // valor por defecto coherente
                        );
                        break;
                }

                if (solicitud == null) continue;

                solicitud.setEstadoSolicitud(estado);

                solicitudesPorId.put(id, solicitud);
                todasLasSolicitudes.add(solicitud);

                if (estado.equalsIgnoreCase("pendiente")) {
                    colaPendientes.add(solicitud);
                }

            } catch (Exception e) {
                System.out.println("Error cargando solicitud: " + e.getMessage());
            }
        }

        actualizarAreasPendientes();
        System.out.println("Solicitudes cargadas correctamente.");
    }
}
