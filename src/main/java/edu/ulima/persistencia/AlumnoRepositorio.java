
package edu.ulima.persistencia;

import edu.ulima.entidad.Alumno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AlumnoRepositorio extends JpaRepository<Alumno,String> {
    
    public Alumno findByCodigo(String codigo);
    
    
   
    
    
    @Query("SELECT p FROM EALUMNO p WHERE p.edad < ?1")
    public List<Alumno> findByEdadMenorQue(double edad);
    
    
    
    
    
    
    
}
