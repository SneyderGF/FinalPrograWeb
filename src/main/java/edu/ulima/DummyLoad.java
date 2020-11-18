
package edu.ulima;

import edu.ulima.entidad.Alumno;
import edu.ulima.entidad.Usuario;
import edu.ulima.persistencia.UsuarioRepositorio;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import edu.ulima.persistencia.AlumnoRepositorio;

@Component
public class DummyLoad implements CommandLineRunner {
    
    @Autowired
    private UsuarioRepositorio repo;
    
    @Autowired
    private AlumnoRepositorio repo2;
    
    @Autowired
    private PasswordEncoder pwEncoder;
    @Override
    public void run(String... args) throws Exception{  
        //Aqui se ejectua la logica principal
        //Insert = save
        //Delete = remove
        //UPDATE = morge
        //SELECT FIND = select
        repo.save(new Usuario("profesor",true ,pwEncoder.encode("1234"),"ROLE_TEACHER"));
        repo.save(new Usuario("administrador",true ,pwEncoder.encode("1234"),"ROLE_ADMINISTRATION"));       
        repo.save(new Usuario("alumno",true ,pwEncoder.encode("1234"),"ROLE_ALUMNO")); 
        
    }
    
    
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
           
    }
    
}
