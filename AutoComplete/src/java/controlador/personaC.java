package controlador;

import dao.PersonaD;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.PersonaM;

@Named(value = "personaC")
@SessionScoped
public class personaC implements Serializable {

    PersonaM persona = new PersonaM();
    private List<PersonaM> lstConsulta;
    private String dni = null;
    
    
    public void limpiar(){ //Creamos un metodo limpiar
        persona = new PersonaM(); //Instanciamos el objeto para que todos sus valores estén vacios
    }
    
    public void guardar() throws Exception { //Creamos un metodo para guardar un registro
        PersonaD dao; //Creamos la variable dao para  estar en la cnonexion
        try { // Try_Cath para el manejo de errores
            dao = new PersonaD(); //Intanciamos la variable dao  para que los valores  esteen vacios
            persona.setUBIGEO_CODUBI(dao.leerUbi(persona.getUBIGEO_CODUBI()));
            dao.guardar(persona);//gurdamos  a la variable persona
            limpiar();//Traemos el metodo  "limpiar"
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "AGREGADO", null)); // Mensaje de GURDADO coorrecto
        } catch (SQLException e) {//cerramos en Try_Catch
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALUMNO YA Registrado", null));  // Mensaje de ERROR 
            limpiar(); // traemos el metodo limpiar
            throw e; //Para mostrar posibles errores
        }
    }
    
    public List<String> completeTextUbi(String query) throws SQLException, Exception{
        PersonaD dao = new PersonaD(); //Instanciamos PersonaD
        return dao.queryAutoCompleteUbi(query); //Retornamos el listado de meotodos de PersonaD
    }
    
    public List<String> completeTextDni(String query) throws SQLException, Exception{
        PersonaD dao = new PersonaD(); //Instanciamos PersonaD
        return dao.queryAutoCompleteDni(query); //Retornamos el listado de meotodos de PersonaD
    }
    
    public void consultar() throws Exception{ // Creamos Metodo Consultar
        PersonaD dao;// //Creamos la variable dao estar en la cnonexion
        try { // Try_Cath para el manejo de errores
            dao = new PersonaD(); //Intanciamos la variable dao  para que los valores del dao esteen vacios
            lstConsulta = dao.consular(dni); //listamos y traemos el metodo consultar del dao con la variable "dni"
            limpiar();// traemos el metodo limpiar
        } catch (Exception e) { //cerramos en Try_Catch
            throw e;//Para mostrar posibles errores
        }
  
    }
//Encapsulado de las variables  y listas que seran llamadas desde las vistas
    
    public PersonaM getPersona() {
        return persona;
    }

    public void setPersona(PersonaM persona) {
        this.persona = persona;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public List<PersonaM> getLstConsulta() {
        return lstConsulta;
    }

    public void setLstConsulta(List<PersonaM> lstConsulta) {
        this.lstConsulta = lstConsulta;
    }

    
}
