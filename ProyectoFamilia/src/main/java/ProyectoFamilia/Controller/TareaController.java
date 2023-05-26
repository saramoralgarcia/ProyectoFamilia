package ProyectoFamilia.Controller;

import ProyectoFamilia.Model.Familia;
import ProyectoFamilia.Model.Miembro;
import ProyectoFamilia.Model.Tarea;
import ProyectoFamilia.Service.ServiceFamilia;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TareaController 
{
    @Autowired
    private ServiceFamilia serviceFamilia;
    
    
//    @GetMapping("/gestionTarea")
//    public String Pagina(Model model)
//    {
//        return "gestionTarea";
//    }
    
    @GetMapping("/altaTarea")
    public String MostrarFormularioDeRegistroDeTarea(Model model, HttpSession session) // le pasamos el modelo y el id por la url 
    {
        Tarea tarea = new Tarea(); // creamos objeto
        model.addAttribute("tarea", tarea); // añadimos al modelo el objeto tarea
        Miembro miembro = (Miembro) session.getAttribute("miembro");
        //obtengo la familia de ese miembro
        Familia familia = miembro.getFamilia();
        List<Miembro> listaMiembros = serviceFamilia.ListarMiembrosPorFamilia(familia.getId());
        model.addAttribute("listaMiembros", listaMiembros);
        System.out.println("HOLA:" + listaMiembros.toString());
  
        return "registroTarea"; // retornamos la pagina
    }
    
    @PostMapping("/guardarTarea")
    public String guardarTarea(@ModelAttribute("tarea") Tarea tarea, @RequestParam(required = false, name = "idMiembro") Long idMiembro, HttpServletRequest request, Model model)
    {
        Date fechaActual = new Date();
        tarea.setFecha(fechaActual);
        
        // mando el objeto familia que esta en la session y lo mando a tarea.setFamilia(familia);
        HttpSession session = request.getSession();
        Familia familia = (Familia)session.getAttribute("familia");
        tarea.setFamilia(familia);
        
        if(idMiembro != null)    
        {
            Miembro miembro = serviceFamilia.buscarMiembroPorId(idMiembro);
            tarea.setMiembro(miembro);
        }
        //le digo al service que guarde la tarea.
        serviceFamilia.guardarTarea(tarea); 
        return "redirect:/gestionTarea";
    }
    
    @GetMapping("/gestionTarea")
    public String mostrarListaTareas(HttpSession session, Model model)
    {
        Familia familia = (Familia) session.getAttribute("familia");
        if(familia != null)
        {
            List<Tarea> listaTareas = serviceFamilia.ListarTareas(familia.getId());
            model.addAttribute("familia", familia);
            model.addAttribute("listaTareas", listaTareas);
            return "gestionTarea";
        }
        return "registroTarea";
    }
    
    @GetMapping("/modificarTarea/{id}")//TO DO: el seleccionar miembro tenga el valor que tiene en la base de datos
    public String modificarTarea(@PathVariable("id") Long id, Model model)
    {
        Tarea tarea = serviceFamilia.buscarTareaPorId(id);
        model.addAttribute("tarea", tarea);
        
        Familia familia = tarea.getFamilia();// Se obtiene la familia a partir de la tarea
        List<Miembro> listaMiembros = serviceFamilia.ListarMiembrosPorFamilia(familia.getId());
        System.out.println("ESTA ES LA LISTA: " + listaMiembros);
        
        model.addAttribute("listaMiembros", listaMiembros);
        return "modificarTarea"; 
    }
    
    @GetMapping("/realizarTarea/{id_tarea}")
    public String realizarTarea(@PathVariable("id_tarea") Long idTarea)
    {
        //obtener la tarea a partir de id
        Tarea tarea = serviceFamilia.buscarTareaPorId(idTarea);
        if(tarea == null || tarea.getMiembro() == null)
        {
            //manejar el caso si la tarea o el miembro es nulo.
            return "redirect:/gestionTarea";
        }
        //obtener el miembro asociado a la tarea
        Miembro miembro = tarea.getMiembro();
        //sumar los puntos de la tarea al atributo "puntos" del miembro
        int puntosTarea = tarea.getPuntos();
        int puntosMiembro = miembro.getPuntos() + puntosTarea;
        miembro.setPuntos(puntosMiembro);
        //Guardar los cambios en el miembro
        serviceFamilia.guardarMiembro(miembro);
        serviceFamilia.eliminarTarea(idTarea);
        return "redirect:/gestionTarea";
      
    }
    
    @GetMapping("/eliminarTarea/{id}")
    public String eliminarTarea(@PathVariable("id") Long id)
    {
        serviceFamilia.eliminarTarea(id);
        return "redirect:/gestionTarea";
    }
    
    @GetMapping("/obtenerMiembros")
    public String obtenerMiembros(HttpSession session, Model model)
    {
        Miembro miembro = (Miembro) session.getAttribute("miembro");
        Familia familia = miembro.getFamilia();
        List<Miembro> listaMiembros = serviceFamilia.ListarMiembrosPorFamilia(familia.getId());
        model.addAttribute("familia", familia);
        model.addAttribute("listaMiembros", listaMiembros);
        System.out.println("HOLA:" + listaMiembros.toString());
        
        return "redirect:/registroTarea";
    }
}
        