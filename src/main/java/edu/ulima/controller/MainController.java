
package edu.ulima.controller;

import edu.ulima.entidad.Alumno;
import edu.ulima.modelo.AlumnoInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import edu.ulima.persistencia.AlumnoRepositorio;

@Controller 
public class MainController {
    
    @Autowired
    private AlumnoRepositorio repo;
    
    @RequestMapping(value="/")
    public String home(){
        return "index";
    }
    
    //INICIO DE ALGORITMO DE PAGINACION ((((((((((((??????????)))))))))))))))))
    
    @RequestMapping({"/listarAlumnos"})
    public String listarProducto(Model model,
            @RequestParam(value="page", defaultValue="1") int page){
        
        //cantidad de objetos al mostar por pantalla
        final int maxResult = 5;
        //en la parte de abajo solo se muestrsa hasta # pag para poder navegas
        final int maxNavigationPage = 10;
        
        final int pageIndex = page-1 < 0 ? 0: page-1;
        
        //Saber cuantos productos hay en la tabla
        int totalRecords = repo.findAll().size();
        
    
        //Calcular cuantas pag debo tener;
        
        int totalPages =0;
        if (totalRecords % maxResult == 0){
            totalPages = totalRecords / maxResult;
        }else{
            totalPages = (totalRecords / maxResult) + 1;
        }
        
        int currentPage = pageIndex +1;
        List<Integer>navigationPages = new ArrayList();
        int current = currentPage > totalPages ? totalPages : currentPage;
        
        int begin = current - maxNavigationPage / 2;
        int end = current - maxNavigationPage / 2;
        
        //La PRIMERA PAGINA
        navigationPages.add(1);
        if(begin > 2 ){
        navigationPages.add(-1);
        }
        
        //Llenar al array con los numeros de paginas
        
        for(int i=begin; i < end ; i++){
            if ( i> 1 && i < totalPages){
                navigationPages.add(i);
            }
        }
        
        if (end < totalPages -2 ){
            navigationPages.add(-1);            
        }
        navigationPages.add(totalPages);
        
        
        //  FIN DEL ALGORIRTMO DE PAGINACION 
        
        
        
        /*
        
        IR A LA BASE DE DATOS PERO ... A BUSCAR UNA PAG DE DATOS
        
        */
        
        Pageable pagina = PageRequest.of(pageIndex, maxResult);
        

        //iNDICAR AL REPOSITORIO PARA QUE RETORNE LA PAG INDICADA
        Page<Alumno> result0 = repo.findAll(pagina);
        
        
        //Convertir la lista de entidad a modelo
        
        List<AlumnoInfo>ltmp = new ArrayList<>();
        for(Alumno p: result0){
            AlumnoInfo p1 = new AlumnoInfo(p.getCodigo(), p.getNombre() , p.getEdad());
            ltmp.add(p1);
        }
        
        
        //Generar una pag
        
        Page<AlumnoInfo> result = new PageImpl(ltmp);
        
        //Poner todo el modelo
        
        model.addAttribute("paginationAlumno",result);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("navigationPages",navigationPages);
        
        return "alumnoLista";
    }
    @RequestMapping(value = {"/alumnoImagen"}, method = RequestMethod.GET)
    public void productImage(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("code") String code) throws IOException {
        Alumno p = null;
        if (code != null) {
            p = repo.findByCodigo(code);
        }

        if (p != null && p.getImagen() != null) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(p.getImagen());
        }

        response.getOutputStream().close();
    }
}
