package edu.ulima.seguridad;

import edu.ulima.entidad.Usuario;
import edu.ulima.persistencia.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByUsuario(username);
        System.out.println("Usuario= " + usuario);

        if (usuario == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        // EMPLOYEE,MANAGER,..
        String role = usuario.getRol();

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

        // ROLE_EMPLOYEE, ROLE_MANAGER
        GrantedAuthority authority = new SimpleGrantedAuthority(role);

        grantList.add(authority);

        boolean enabled = usuario.isActivo();
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        UserDetails userDetails = (UserDetails) new User(
                usuario.getUsuario(), //
                usuario.getClave(), 
                enabled,
                accountNonExpired, //
                credentialsNonExpired, accountNonLocked, grantList
                );

        return userDetails;
    }

}