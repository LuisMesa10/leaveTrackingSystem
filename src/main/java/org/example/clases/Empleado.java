package org.example.clases;

public class Empleado {
    //Atributos
    private int empleadoID;
    private String nombre;
    private String area;
    private String email;
    private int diasDisponiblesSalidas= 20;
    public Empleado() {
    }

    public Empleado(int empleadoID, String nombre, String area, String email) {
        this.empleadoID = empleadoID;
        this.nombre = nombre;
        this.area = area;
        this.email = email;
    }

    public int getEmpleadoID() {
        return empleadoID;
    }

    public void setEmpleadoID(int empleadoID) {
        this.empleadoID = empleadoID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDiasDisponiblesSalidas() {
        return diasDisponiblesSalidas;
    }

    public void setDiasDisponiblesSalidas(int diasDisponiblesSalidas) {
        if(diasDisponiblesSalidas >= 0) {
            this.diasDisponiblesSalidas = diasDisponiblesSalidas;
        }else{
            System.out.println("el número de salidas no puede ser negativo");
        }
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "empleadoID=" + empleadoID +
                ", nombre='" + nombre + '\'' +
                ", area='" + area + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
