
package edu.ulima.formulario;

import edu.ulima.entidad.Alumno;
import lombok.Data;

import org.springframework.web.multipart.MultipartFile;

@Data
public class AlumnoForm {
    private String codigo;
    private String nombre;
    private double edad;
    
    private boolean newAlumno = false;
    
    //Para cargar las imagenes
    private MultipartFile fileData;

    public AlumnoForm() {
        this.newAlumno = true;
    }

    public AlumnoForm(Alumno p) {
        this.codigo = p.getCodigo();
        this.nombre = p.getNombre();
        this.edad = p.getEdad();
    }
    
    
    
    
    
    
    
}
