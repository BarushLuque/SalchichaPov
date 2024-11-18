/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.salchicha.entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.itson.salchicha.persistencia.Conexion;

/**
 * Clase que representa un Servicio. Incluye métodos para realizar operaciones CRUD
 * y manejar los datos relacionados con los servicios.
 * 
 * @author janto
 */
public class Servicio {

    private int id;
    private Date fechaRealizacion;
    private Responsable responsable;
    private String descripcionProblema;
    private List<Actividad> actividad;

    /**
     * Obtiene todos los servicios registrados en la base de datos.
     * 
     * @return una lista de objetos {@code Servicio} con los datos recuperados.
     */
    public static List<Servicio> getAll() {
        List<Servicio> servicios = new ArrayList<>();
        try {
            Connection conexion = Conexion.obtener();
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id, fecha_realizacion, id_responsable, descripcion_problema FROM servicio");
            while (rs.next()) {
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
        } catch (Exception ex) {
            System.err.println("Ocurrió un error2: " + ex.getMessage());
        }
        return servicios;
    }

    /**
     * Obtiene el identificador único del servicio.
     * 
     * @return el identificador único del servicio.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del servicio.
     * 
     * @param id el identificador único a asignar.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene la fecha de realización del servicio.
     * 
     * @return la fecha de realización del servicio.
     */
    public Date getFechaRealizacion() {
        return fechaRealizacion;
    }

    /**
     * Establece la fecha de realización del servicio.
     * 
     * @param fechaRealizacion la fecha que se desea asignar.
     */
    public void setFechaRealizacion(Date fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    /**
     * Obtiene el responsable asociado al servicio.
     * 
     * @return el responsable del servicio.
     */
    public Responsable getResponsable() {
        return responsable;
    }

    /**
     * Establece el responsable asociado al servicio.
     * 
     * @param responsable el responsable a asignar.
     */
    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    /**
     * Obtiene la descripción del problema del servicio.
     * 
     * @return la descripción del problema.
     */
    public String getDescripcionProblema() {
        return descripcionProblema;
    }

    /**
     * Establece la descripción del problema del servicio.
     * 
     * @param descripcionProblema la descripción a asignar.
     */
    public void setDescripcionProblema(String descripcionProblema) {
        this.descripcionProblema = descripcionProblema;
    }

    /**
     * Obtiene la lista de actividades asociadas al servicio.
     * 
     * @return una lista de actividades del servicio.
     */
    public List<Actividad> getActividad() {
        return actividad;
    }

    /**
     * Establece la lista de actividades asociadas al servicio.
     * 
     * @param actividades la lista de actividades a asignar.
     */
    public void setActividades(List<Actividad> actividades) {
        this.actividad = actividades;
    }

    /**
     * Guarda un nuevo servicio en la base de datos.
     * 
     * @param descripcion_problema la descripción del problema del servicio.
     * @param id_responsable el identificador del responsable asignado al servicio.
     * @return {@code true} si el servicio se guardó correctamente, de lo contrario {@code false}.
     */
    public static boolean save(String descripcion_problema, String id_responsable) {
        boolean resultado = false;
        try {
            Connection conexion = Conexion.obtener();
            String consulta = "INSERT INTO servicio (descripcion_problema, id_responsable, fecha_realizacion) VALUES (?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, descripcion_problema);
            statement.setString(2, id_responsable);
            
            // Hora actual
            LocalDateTime localDate = LocalDateTime.now();
            statement.setObject(3, localDate);
            
            statement.execute();
            resultado = statement.getUpdateCount() == 1;
            conexion.close();
        } catch (Exception ex) {
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return resultado;
    }

    /**
     * Edita un servicio existente en la base de datos.
     * 
     * @param id el identificador del servicio a modificar.
     * @param descripcion_problema la nueva descripción del problema.
     * @param id_responsable el nuevo identificador del responsable asignado.
     * @return {@code true} si el servicio se actualizó correctamente, de lo contrario {@code false}.
     */
    public static boolean edit(int id, String descripcion_problema, String id_responsable) {
        boolean resultado = false;
        try {
            Connection conexion = Conexion.obtener();
            String consulta = "UPDATE servicio SET descripcion_problema = ?, id_responsable = ? WHERE id = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, descripcion_problema);
            statement.setString(2, id_responsable);
            statement.setInt(3, id);
            
            statement.execute();
            resultado = statement.getUpdateCount() == 1;
            conexion.close();
        } catch (Exception ex) {
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return resultado;
    }

    /**
     * Elimina un servicio existente de la base de datos.
     * 
     * @param id el identificador del servicio a eliminar.
     * @return {@code true} si el servicio se eliminó correctamente, de lo contrario {@code false}.
     */
    public static boolean delete(int id) {
        boolean resultado = false;
        try {
            Connection conexion = Conexion.obtener();
            String consulta = "DELETE FROM servicio WHERE id = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, id);
            
            statement.execute();
            resultado = statement.getUpdateCount() == 1;
            conexion.close();
        } catch (Exception ex) {
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return resultado;
    }
}
