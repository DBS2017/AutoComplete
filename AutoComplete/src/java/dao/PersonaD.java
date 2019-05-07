
package dao;

import interfas.personaI;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.PersonaM;


public class PersonaD  extends Dao implements personaI{

    @Override
    public void guardar(PersonaM persona) throws Exception { //creamos el metodo para guardar
        try { // Try_Cath para el manejo de errores
            this.conectar();// llamamos a la conexion con este metodo
            String sql="EXEC SP_INSERTARPERSONA ?,?,?,?"; //Guardamos la sentencia dentro de un store procedures 
            PreparedStatement ps = this.getCn().prepareStatement(sql);// Llamamos y preparamos la consulta desde la variable SQL
            ps.setString(1, persona.getNOMPER()); //Guaradmos los valores en cada campo
            ps.setString(2, persona.getAPEPER()); 
            ps.setString(3, persona.getDNIPER());
            ps.setString(4, persona.getUBIGEO_CODUBI());
            ps.executeUpdate();//Ejecutamos la sentencia
        } catch (SQLException e) { //cerramos en Try_Catch
            throw e;//Para mostrar posibles errores
        }finally{
            this.cerrar(); //Cerramos la conexion
        }
    }

    
    public List<String> queryAutoCompleteUbi(String a) throws SQLException, ClassNotFoundException, Exception{ // Creamos un metodo listar con una variable "a"
        this.conectar(); //Llamamos a la conexion para este metodo
        ResultSet rs; //Objeto para traer los datos de la BD
        List<String> lista; //Guardamos toda la lista dentro de una variable "lista"
        String sql; //Creamos una variable "sql"
        try { //Try_Catch para el manejo de errores
                sql = "SELECT CODUBI,CONCAT(DEPUBI,', ',PROUBI,', ',DISUBI) AS UBIGEO FROM UBIGEO WHERE PROUBI LIKE ?";//Guardamos la sentencia en una variable tipo String llamada sql
                a = "CAÑETE";
            PreparedStatement ps = this.getCn().prepareCall(sql);//Llamamos y preparamos la consulta desde la variable SQL
            ps.setString(1, "%" + a + "%");// Funcion es autocompletar mediante los porcentajes
            rs = ps.executeQuery(); //ejecutamos la consulta 
            lista = new ArrayList<>();//Instanciamos  la variables  para que todos sus valores estén vacios dentro del array
            while (rs.next()){
                lista.add(rs.getString("UBIGEO"));
            }
            return lista; //retorna el listado
        } catch (SQLException e) { //cerramos en Try_Catch
            throw e; //Para mostrar posibles errores
        }finally {
            this.cerrar(); //Cerramos la conexion
        }
    }
    
    public List<String> queryAutoCompleteDni(String a) throws SQLException, Exception{ // Creamos un metodo listar con una variable "a" para consulta el DNI
        this.conectar(); //Llamamos a la conexion para este metodo
            ResultSet rs; //Objeto para traer los datos de la BD
            List<String> lista;  //Guardamos toda la lista dentro de una variable "lista"
            try { //Try_Catch para el manejo de errores
                String sql="SELECT DNIPER FROM VW_CONSULTA WHERE DNIPER LIKE ? ";//Guardamos la sentencia en una variablr tipo String llamada sql
                PreparedStatement ps=this.getCn().prepareCall(sql); // Llamamos y preparamos la consulta desde la variable SQL
                ps.setString(1, "%" + a + "%"); // Funcion es autocompletar mediante los porcentajes
                rs = ps.executeQuery(); // el objeto "rs" trae los datos de la BD  y  seguido ejecuta la consulta 
                lista = new ArrayList<>(); //Instanciamos  la variables  para que todos sus valores estén vacios dentro del array
                while(rs.next()) {
                    lista.add(rs.getString("DNIPER")); // //Agregamos la lista al modelo 
                }
                return  lista; //retorna el listado
            } catch (SQLException e) { //cerramos en Try_Catch
            throw e; //Para mostrar posibles errores
            }finally{
                this.cerrar(); //Cerramo la Conexion 
            }
    }
    
    public String leerUbi(String a) throws Exception{ //Creamos metodo  leerUbi
        this.conectar(); //Llamamos a la conexion para este metodo
        ResultSet rs; //Objeto para traer los datos de la BD
        try { //Try_Catch para el manejo de errores
            String sql="SELECT CODUBI FROM UBIGEO WHERE CONCAT(DEPUBI,', ',PROUBI,', ',DISUBI) = ?"; //Guardamos la sentencia en una variable tipo String llamada sql
            PreparedStatement ps  = this.getCn().prepareCall(sql); //Llamamos y preparamos la consulta desde la variable SQL
            ps.setString(1, a); //vamos guardando el dato en el parametro 
            rs = ps.executeQuery(); // Ejecutamos la consulta
            if (rs.next()) { 
                return rs.getString("CODUBI");
            }
            return null; //Retornamos consulta Vacia
        } catch (SQLException e) { //cerramos en Try_Catch
            throw e; //Para mostrar posibles errores
        }finally{
            this.cerrar(); //Cerramo la Conexion
        }
    }
    
    public List<PersonaM> consular(String dni) throws Exception{
        List<PersonaM> consulta;
        ResultSet rs;
        try {
            this.conectar();
            String sql="SELECT * FROM VW_CONSULTA WHERE DNIPER LIKE ?"; ////Guardamos la sentencia en una variable tipo String llamada sql
            PreparedStatement ps=this.getCn().prepareStatement(sql);//Llamamos y preparamos la consulta desde la variable SQL
            ps.setString(1, dni); //vamos guardando el dato
            rs = ps.executeQuery(); // Ejecutamos la consulta
            consulta = new ArrayList(); //Creamos un Array para mostrar los valores desde la variable consulta
            PersonaM persona; // creamos un variable "persona" para el modelo
            while (rs.next()){ ////Bucle "mientras" para traer los datos uno por uno
                persona = new PersonaM(); //Instanciamos el objeto para que todos sus valores estén vacios en la 
                persona.setCODPER(rs.getString("CODPER"));// Jalamos el valor de la base de datos con el objeto "rs" del campo "CODPER"
                persona.setNOMPER(rs.getString("NOMPER"));// Jalamos el valor de la base de datos con el objeto "rs" del campo "NOMPER"
                persona.setAPEPER(rs.getString("APEPER"));// Jalamos el valor de la base de datos con el objeto "rs" del campo "APEPER"
                persona.setDNIPER(rs.getString("DNIPER"));// Jalamos el valor de la base de datos con el objeto "rs" del campo "DNIPER"
                consulta.add(persona); //Agregamos la lista al modelo
            }
            return consulta; //le pedimos que nos devuelva una lista ya que el método es tipo Lista
        } catch (SQLException e) { //cerramos en Try_Catch manejo de errrores
            throw e; //Para mostrar posibles errores en caso que hubiera
        }finally{
            this.cerrar(); //Cerramo la Conexion
        }
    }
}
