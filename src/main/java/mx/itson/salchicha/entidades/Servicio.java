/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.salchicha.entidades;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.itson.salchicha.persistencia.Conexion;

/**
 *
 * @author janto
 */
public class Servicio {

    private int id;
    private Date fechaRealizacion;
    private Responsable responsable;
    private String descripcionProblema;
    private List<Actividad> actividad;
    
    public static List<Servicio> getAll(){
        List<Servicio> servicios = new ArrayList<>();
        try {
            Connection conexion = Conexion.obtener();
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("Select id, fecha_realizacion, id_responsable,descripcion_problema FROM servicio");
            while(rs.next()){
                Servicio s = new Servicio();
                s.setId(rs.getInt(1));
                s.setFechaRealizacion(rs.getDate(2));
                
                Responsable r = Responsable.getById(rs.getInt(3));
                s.setResponsable(r);
                s.setDescripcionProblema(rs.getString(4));
                
                List<Actividad> actividades = Actividad.getList(rs.getInt(1));
                s.setActividades(actividades);
                
                servicios.add(s);
            }
        } catch(Exception ex){
            System.err.println("Ocurrió     un error2: " + ex.getMessage());
        }
        return servicios;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the fechaRealizacion
     */
    public Date getFechaRealizacion() {
        return fechaRealizacion;
    }

    /**
     * @param fechaRealizacion the fechaRealizacion to set
     */
    public void setFechaRealizacion(Date fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    /**
     * @return the responsable
     */
    public Responsable getResponsable() {
        return responsable;
    }

    /**
     * @param responsable the responsable to set
     */
    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    /**
     * @return the descripcionProblema
     */
    public String getDescripcionProblema() {
        return descripcionProblema;
    }

    /**
     * @param descripcionProblema the descripcionProblema to set
     */
    public void setDescripcionProblema(String descripcionProblema) {
        this.descripcionProblema = descripcionProblema;
    }

    /**
     * @return the actividades
     */
    public List<Actividad> getActividad() {
        return actividad;
    }

    /**
     * @param actividades the actividades to set
     */
    public void setActividades(List<Actividad> actividades) {
        this.actividad = actividades;
    }
    
    

    
}
