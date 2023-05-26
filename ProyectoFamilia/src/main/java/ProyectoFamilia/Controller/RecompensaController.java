package ProyectoFamilia.Controller;

import ProyectoFamilia.Model.Familia;
import ProyectoFamilia.Model.Miembro;
import ProyectoFamilia.Model.Recompensa;
import ProyectoFamilia.Service.ServiceFamilia;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
public class RecompensaController 
{
    @Autowired
    private ServiceFamilia serviceFamilia;

//    @GetMapping("/gestionRecompensa")
//    public String PaginaPrincipalRecompensa(Model model) 
//    {
//        return "gestionRecompensa";
//    }
    
    @GetMapping("/altaRecompensa")
    public String MostrarFormularioDeRegistroDeRecompensa(Model model) 
    {
        Recompensa recompensa = new Recompensa();
        model.addAttribute("recompensa", recompensa);
        return "registroRecompensa"; 
    }
    
    @PostMapping("/guardarRecompensa")
    public String guardarRecompensa(@ModelAttribute ("recompensa") Recompensa recompensa, HttpServletRequest request, Model model)
    {
        HttpSession session = request.getSession();
        Miembro miembro = (Miembro) session.getAttribute("miembro");
        recompensa.setMiembro(miembro);
        serviceFamilia.guardarRecompensa(recompensa);
        return "redirect:/gestionRecompensa";
    }
    
    @GetMapping("/gestionRecompensa")
    public String mostrarListaRecompensas(HttpSession session, Model model)
    {
        Familia familia = (Familia) session.getAttribute("familia");
        if(familia != null)
        {
            List<Recompensa> listaRecompensa = serviceFamilia.ListaRecompensas(familia.getId());
            model.addAttribute("familia", familia);
            model.addAttribute("listaRecompensa", listaRecompensa);
            return "gestionRecompensa";
        }
        return "registroRecompensa";
    }
    
    @GetMapping("/modificarRecompensa/{id}")// MUESTRA TODAS LAS RECOMPENSA DE LA BASE DE DATOS CAMBIAR A MOSTRAR SOLO LAS DE ESA FAMILIA
    public String modificarRecompensa(@PathVariable("id") Long id, Model model)
    {
        Recompensa recompensa = serviceFamilia.buscarRecompensaPorId(id);
        model.addAttribute("recompensa", recompensa);
        System.out.println("HOLA: " + recompensa.toString());
        return "modificarRecompensa";
    }
    
    @GetMapping("canjearRecompensa/{id_recompensa}")
    public String canjerRecompensa(@PathVariable("id_recompensa") Long id_recompensa, HttpSession session)
    {
        //obtener la recompensa a partir del id
        Recompensa recompensa = serviceFamilia.buscarRecompensaPorId(id_recompensa);

        //obtener el miembro actual de la familia
        Miembro miembro = (Miembro) session.getAttribute("miembro");
        
        if(miembro != null && miembro.getPuntos() >= recompensa.getPuntos())
        {
            System.out.println("La recompensa es: " + recompensa + "el miembro es: " + miembro);
            miembro.setPuntos(miembro.getPuntos() - recompensa.getPuntos());
            System.out.println("ESte es el miembro: " + miembro);
            serviceFamilia.guardarMiembro(miembro);
            serviceFamilia.eliminarRecompensa(id_recompensa);
            return "redirect:/gestionRecompensa";
        }
       return "redirect:/gestionRecompensa"; 
    }
   
    @GetMapping("/eliminarRecompensa/{id}")
    public String eliminarRecompensa(@PathVariable("id") Long id)
    {
        serviceFamilia.eliminarRecompensa(id);
        return "redirect:/gestionRecompensa";
    }
}
