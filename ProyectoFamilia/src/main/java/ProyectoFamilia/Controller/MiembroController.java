package ProyectoFamilia.Controller;

import ProyectoFamilia.Model.Familia;
import ProyectoFamilia.Model.Miembro;
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
public class MiembroController 
{
    @Autowired
    private ServiceFamilia serviceFamilia;

    @GetMapping("/altaMiembro")
    public String MostrarFormularioDeRegistroMiembro(Model model, @RequestParam("id") Long idFamilia, @ModelAttribute("nombre") String nombre) // le pasamos el modelo y el id por la url 
    {
        Miembro miembro = new Miembro(); // creamos objeto
        model.addAttribute("miembro", miembro); // añadimos al modelo el objeto miembro
        model.addAttribute("idFamilia", idFamilia);// añadimos al modelo el id
        model.addAttribute("nombre", nombre);

        return "registroMiembro"; // retornamos la pagina
    }

    @PostMapping("/guardarMiembro")
    public String guardarMiembro(@ModelAttribute("miembro") Miembro miembro, @ModelAttribute("idFamilia") Long idFamilia, HttpServletRequest request, Model model)// le pasamos el objeto, el id,el request y el modelo
    // http Request -->  mensaje que se envía desde el cliente al servidor para solicitar un recurso.
    {
        Familia familia = serviceFamilia.buscarFamiliaPorId(idFamilia); // Buscar la familia por su "id".
        miembro.setFamilia(familia); // añadimos al miembro el objeto 
        System.out.println("Miembro recibido: " + miembro.toString());

        serviceFamilia.guardarMiembro(miembro);
        HttpSession session = request.getSession();  // Agregamos el objeto miembro a la session Http(como si fuera una caja.
        session.setAttribute("miembro", miembro);
        return "redirect:/paginaInicialConMiembro?miembro=" + miembro.getNombre();
    }

    @PostMapping("/seleccionarFamilia")
    public String seleccionarFamilia(HttpServletRequest request, @RequestParam Long familiaId)
    {
        Familia familia = serviceFamilia.buscarFamiliaPorId(familiaId);
        HttpSession session = request.getSession();
        session.setAttribute("familia", familia);
        return "redirect:/mostrarMiembros";
    }


    @GetMapping("/altaMiembroConUsuario")
    public String DarDeAltaUnMiembroDentroDeApp(Model model) 
    {
        Miembro miembro = new Miembro();
        model.addAttribute("miembro", miembro);
        return "registroMiembroConUsuario";
    }


    @PostMapping("/guardarMiembroConUsuario")
    public String guardarMiembroConUsuario(HttpServletRequest request, @ModelAttribute("miembro") Miembro miembro)
    {
        System.out.println(miembro.toString());
        HttpSession session = request.getSession(); 
        Familia familia = (Familia) session.getAttribute("familia"); //recuperas la session donde se encuentra "familia".
        miembro.setFamilia(familia); // le asignas un miembro
        serviceFamilia.guardarMiembro(miembro);
        return "redirect:/gestionMiembro";
    }
////////
////////    @GetMapping("/mostrarMiembros")
////////    public String listarMiembros(HttpSession session, Model model)
////////    {
////////        Familia familia = (Familia) session.getAttribute("familia");
////////        List<Miembro> listaMiembros = serviceFamilia.ListarMiembrosPorFamilia(familia.getId());
////////        model.addAttribute("listaMiembros", listaMiembros);
////////        return "gestionMiembro";
////////    }
////////
////////    @GetMapping("/paginaInicialConMiembro")
////////    public String mostrarPaginaMiembro(Model model, HttpServletRequest request) {
////////        HttpSession session = request.getSession();
////////        Miembro miembro = (Miembro) session.getAttribute("miembro");
////////        model.addAttribute("miembro", miembro);
////////        return "paginaDeMiembro";
////////    }
//    @GetMapping("/miembroFamilia/{id}")
//    public String miembrosFamilia(@PathVariable("id") Long idFamilia, Model model, HttpSession session) {
//        List<Miembro> miembrosFamilia = serviceFamilia.getMiembrosFamilia(idFamilia);
//    }
//    @GetMapping("/gestionMiembro/{id}")
//    public String PaginaPrincipal(Model model) 
//    {
//        return "gestionMiembro";
//    }
//
//    @GetMapping("/mostrarMiembros")
//    public String ListarMiembros(Model model) {
//        List<Miembro> listaMiembros = serviceFamilia.ListarMiembros();
//        model.addAttribute("listaMiembros", listaMiembros);
//        return "gestionMiembro";
//    }
    @GetMapping("/paginaInicial")
    public String VerPaginaInicial(Model model) 
    {
        return "paginaInicial";
    }
    
    @GetMapping("/modificarMiembro/{id}")
    public String mostrarFormularioModificarMiembro(@PathVariable("id") Long id, Model model)
    {
        Miembro miembro = serviceFamilia.buscarMiembroPorId(id);
        model.addAttribute("miembro", miembro);
        return "modificarMiembro";
    }
    
    @GetMapping("/eliminarMiembro/{id}")
    public String eliminarMiembro(@PathVariable("id") Long id)
    {
        serviceFamilia.eliminarMiembro(id);
        return "redirect:/gestionMiembro";
    }
    
}
