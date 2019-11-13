package com.example.reto8;

public class Empresa {

    private long empId;


    private String nombre;
    private String url;
    private long telefono;
    private String emailContacto;
    private String productos;
    private String clasificacion;

    public Empresa(long empId, String nombre, String url, long telefono, String emailContacto, String productos, String clasificacion){
        this.empId = empId;
        this.nombre = nombre;
        this.url = url;
        this.telefono = telefono;
        this.emailContacto = emailContacto;
        this.productos = productos;
        this.clasificacion = clasificacion;

    }

    public Empresa(){

    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getEmailContacto() {
        return emailContacto;
    }

    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String toString(){
        return "Emp id: "+getEmpId()+ "\n" +
                "Nombre : "+getNombre() + " " + "\n" +
                "URL: "+getUrl() + "\n" +
                "Telefono : "+getTelefono()+ "\n" +
                "Email contacto" + getEmailContacto() + "\n" +
                "Productos y servicios" + getProductos() + "\n" +
                "Clasificacion" + getClasificacion() + "\n";


    }

}

