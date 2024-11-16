/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.salchicha.entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.itson.salchicha.persistencia.Conexion;

/**
 *
 * @author janto
 */
public class Responsable {

    private int id;
    private String nombre;
    private String puesto;
    
    public static List<Responsable> getAll(){
        List<Responsable> responsables = new ArrayList<>();
        try {
            Connection conexion = Conexion.obtener();
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id, nombre, puesto FROM responsable");
            while(rs.next()){
                Responsable r = new Responsable();
                r.setId(rs.getInt(1));
                r.setNombre(rs.getString(2));
                r.setPuesto(rs.getString(3));
                responsables.add(r);
            }
        } catch(Exception ex){
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return responsables;
    }
    
    public static Responsable getById(int id){
        Responsable r = new Responsable();
        try {
            Connection conexion = Conexion.obtener();
            String query = "SELECT id_responsable, nombre, puesto FROM responsable WHERE id = ?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, id);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                r.setId(rs.getInt(1));
                r.setNombre(rs.getString(2));
                r.setPuesto(rs.getString(3));
            }
        } catch(Exception ex){
            System.err.println("Ocurrió un error3: " + ex.getMessage());
        }
        return r;
    }
    
    /**
     * Guarda un registro de responsable en la base de datos.
     * @param nombre Valor del nombre del responsable.
     * @param puesto Valor del puesto del responsable.
     * @return true si se guardó exitosamente; de lo contrario, false.
     */
    public static boolean save(String nombre, String puesto){
        boolean resultado = false;
        try{
            Connection conexion = Conexion.obtener();
            String consulta = "INSERT INTO responsable (nombre, puesto) VALUES (?, ?)";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, nombre);
            statement.setString(2, puesto);
            
            statement.execute();
            resultado = statement.getUpdateCount() == 1;
            conexion.close();
        }catch(Exception ex){
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return resultado;
    }
    
        public static boolean edit(int id, String nombre, String puesto){
        boolean resultado = false;
        try{
            Connection conexion = Conexion.obtener();
            String consulta = "UPDATE responsable SET nombre = ?, puesto = ? WHERE id = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, nombre);
            statement.setString(2, puesto);
            statement.setInt(3, id);
            
            statement.execute();
            resultado = statement.getUpdateCount() == 1;
            conexion.close();
        }catch(Exception ex){
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return resultado;
    }
     
         public static boolean delete(int id){
        boolean resultado = false;
        try{
            Connection conexion = Conexion.obtener();
            String consulta = "DELETE FROM responsable WHERE id = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, id);
            
            statement.execute();
            resultado = statement.getUpdateCount() == 1;
            conexion.close();
        }catch(Exception ex){
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return resultado;
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the puesto
     */
    public String getPuesto() {
        return puesto;
    }

    /**
     * @param puesto the puesto to set
     */
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    
 @Override
 public String toString(){
     return this.nombre;
 }
    
}
