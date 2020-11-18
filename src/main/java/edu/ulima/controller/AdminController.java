package edu.ulima.controller;

import edu.ulima.entidad.Alumno;
import edu.ulima.formulario.AlumnoForm;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import edu.ulima.persistencia.AlumnoRepositorio;


@Controller
//@Transactional
public class AdminController {
    
    @Autowired
    private AlumnoRepositorio repo;

   // GET: Show Login Page
   @RequestMapping(value = { "/admin/login" }, method = RequestMethod.GET)
   public String login(Model model) {
      return "login";
   }

   @RequestMapping(value = { "/admin/usuarioInfo" }, method = RequestMethod.GET)
   public String accountInfo(Model model) {

      UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      model.addAttribute("userDetails", userDetails);
      return "usuarioInfo";
   }

   
   @RequestMapping(value = { "/admin/manejarAlumno" }, method = RequestMethod.GET)
   public String producto(Model model,
           @RequestParam(value="code", defaultValue="") String code){
       
       AlumnoForm pf = null;
       
       if(code != null && code.length()> 0 ){
           Alumno p =  repo.findByCodigo(code);
           if(p!= null){
               pf = new AlumnoForm(p);
           }
        }else{
               pf = new AlumnoForm();
        }
       
       model.addAttribute("alumnoForm",pf);
       
       
       
       return "alumno";
   }
   
   @RequestMapping(value = { "/admin/manejarAlumno" }, method = RequestMethod.POST)
   public String grabarProducto(Model model,
           @ModelAttribute("alumnoForm")AlumnoForm productoForm) {
       
        try {
            String code = productoForm.getCodigo();
            Alumno p = null;
            
            boolean isNew = false;
            if ( code != null){
                p = repo.findByCodigo(code);
            }
            
            if( p == null){
                isNew = true;
                p = new Alumno();
                p.setFecha_vigencia(new Date());
            }
            
            
            p.setCodigo(code);
            p.setNombre(productoForm.getNombre());
            p.setEdad(productoForm.getEdad());
            p.setImagen(productoForm.getFileData().getBytes());
            p.setFecha_vigencia(new Date());
            
            repo.save(p);
                      
        } catch (IOException ex) {
            ex.printStackTrace();
        }
         return "redirect:/listarProductos";
   }
}