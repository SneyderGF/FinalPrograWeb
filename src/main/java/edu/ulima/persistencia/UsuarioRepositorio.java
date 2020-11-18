package edu.ulima.persistencia;

import edu.ulima.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario,String>{
    
    public Usuario findByUsuario(String usr);
        
}
