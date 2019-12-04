package com.example.reto10;

public class Museo {

    private long no;

    private String nombreMuseo;

    private String telefonoFijo;

    private String celular;

    private String correo;

    private String paginaWeb;

    private String direccion;

    private String upz;

    private String entidadAdministradora;

    private String localidad;

    private long   anoInicio;

    private String caracter;

    private String delOrden;


    public Museo(long no, String nombreMuseo,String telefonoFijo,String celular,String correo, String paginaWeb,
        String direccion, String localidad,long   anoInicio,String caracter,String delOrden, String upz, String entidadAdministradora ){
        this.no = no;
        this.nombreMuseo = nombreMuseo;
        this.telefonoFijo = telefonoFijo;
        this.celular = celular;
        this.correo = correo;
        this.upz = upz;
        this.entidadAdministradora = entidadAdministradora;
        this.paginaWeb = paginaWeb;
        this.direccion = direccion;
        this.localidad = localidad;
        this.anoInicio = anoInicio;
        this.caracter = caracter;
        this.delOrden = delOrden;

    }
    public Museo(){

    }

    public long getNo() {
        return no;
    }

    public void setNo(long no) {
        this.no = no;
    }

    public String getNombreMuseo() {
        return nombreMuseo;
    }

    public void setNombreMuseo(String nombreMuseo) {
        this.nombreMuseo = nombreMuseo;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public long getAnoInicio() {
        return anoInicio;
    }

    public void setAnoInicio(long anoInicio) {
        this.anoInicio = anoInicio;
    }

    public String getCaracter() {
        return caracter;
    }

    public void setCaracter(String caracter) {
        this.caracter = caracter;
    }

    public String getDelOrden() {
        return delOrden;
    }

    public void setDelOrden(String delOrden) {
        this.delOrden = delOrden;
    }

    public String getUpz() {
        return upz;
    }

    public void setUpz(String upz) {
        this.upz = upz;
    }

    public String getEntidadAdministradora() {
        return entidadAdministradora;
    }

    public void setEntidadAdministradora(String entidadAdministradora) {
        this.entidadAdministradora = entidadAdministradora;
    }

    public String toString(){
        return "No: " + getNo() + "\n" +
                "Nombre: "+ getNombreMuseo() + "\n" +
                "Telefono fijo: " +getTelefonoFijo()+ "\n" +
                "Celular: " + getCelular() + "\n" +
                "Correo electronico: " + getCorreo() + "\n" +
                "Pagina web: " + getPaginaWeb() + "\n" +
                "Direccion: " + getDireccion() + "\n" +
                "Localidad: " + getLocalidad() + "\n" +
                "Entidad Administradora: " + getEntidadAdministradora() + "\n" +
                "Caracter: " +getCaracter() + "\n" +
                "Del Orden: " +getDelOrden() + "\n";

    }
}
