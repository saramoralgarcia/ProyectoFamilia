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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FamiliaController 
{
    @Autowired
    private ServiceFamilia serviceFamilia;

    @GetMapping("/")
    public String PaginaDeAcceso(Model model) 
    {
        return "index";
    }

    @GetMapping("/paginaConCodigo")
    public String PaginaConCodigo(Model model,HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Familia familia = (Familia) session.getAttribute("familia");
         model.addAttribute("familia", familia);
        return "paginaConCodigo";
    }
    
    @GetMapping("/registroInicial")
    public String MostrarFormularioDeRegistrarFamilia(Model model)
    {
        Familia familia = new Familia();
        model.addAttribute("familia", familia);
        return "registroFamilia";
    }

    @PostMapping("/guardarFamilia")
    public String guardarFamilia(@ModelAttribute("familia") Familia familia, HttpSession session) 
    {
        int codigo = Familia.generarCodigo();
        familia.setCodigo(codigo);
        // hacer: comprobar si codigo está duplicado.
        serviceFamilia.guardarFamilia(familia);
        session.setAttribute("familia", familia);
        return "redirect:/altaMiembro?id=" + familia.getId();
    }

    @GetMapping("/opcionRegistro")
    public String OpcionDeRegistro(Model model) 
    {
        return "opcionRegistro";
    }

//    @GetMapping("/altaMiembro")
//    public String MostrarFormularioDeRegistroMiembro(Model model, HttpSession session)
//    {
//        Miembro miembro = new Miembro();
//        int familiaId = (int)session.getAttribute("familiaId"); // Obtener el "id" de la familia de los atributos de la sesión.
//        Familia familia = serviceFamilia.buscarFamiliaPorId(familiaId); // Buscar la familia por su "id".
//        miembro.setFamilia(familia); // Establecer la familia en el objeto Miembro.
//        model.addAttribute("miembro", miembro);
//        System.out.println(miembro);
//        return "registroMiembro"; 
//    }

    
//    @GetMapping("/identificaMiembro")
//    public String identificarMiembro(@RequestParam("email") String email, HttpServletRequest request) {
//        Miembro miembro = serviceFamilia.buscarMiembroPorEmail(email);
//        HttpSession session = request.getSession(); //agregar objeto a la session
//        session.setAttribute("miembro", miembro);
//        return "redirect:/paginaDeMiembro?email=" + email;
//    }
//

    @GetMapping("/altaMiembroConFamilia")
    public String MostrarFormularioDeRegistroMiembroConFamilia(Model model) 
    {
        return "paginaValidadorcodigoFamilia";
    }

    //hacer: gestionar codigo que no exista en la base de datos.
    @PostMapping("/guardarMiembroConFamilia")
    public String guardarMiembroConFamilia(@RequestParam(name = "codigo") Long codigo, HttpSession session) 
    {
        Familia familia = serviceFamilia.buscarFamiliaPorCodigo(codigo);
        serviceFamilia.guardarFamilia(familia);
        session.setAttribute("familia", familia);
        return "redirect:/altaMiembro?id=" + familia.getId() + "&familia=" + familia.getNombre();
    }

    @GetMapping("/iniciarSesion")
    public String mostrarFormularioAcceso(Model model) 
    {
        model.addAttribute("passIncorrecta", false);
        return "login";
    }

    @PostMapping("/AccesoMiembro")
    public String miembroAccedeAlaAplicacion(@RequestParam(name = "email") String email, @RequestParam(name = "contraseña") String contraseña, Model model, HttpSession session) 
    {
        //hacer: Comprobar si existe email en la base de datos
        Miembro miembro = serviceFamilia.buscarMiembroPorEmail(email);//recuperando un miembro de la bbdd dado un email
        
        if (miembro != null && miembro.getContraseña().equals(contraseña)) 
        {
            Familia familia = miembro.getFamilia();
            model.addAttribute("nombreMiembro", miembro.getNombre());
            session.setAttribute("miembro", miembro);
            session.setAttribute("familia", familia);
            return "paginaDeMiembro";
        }
        model.addAttribute("passIncorrecta", true);
        return "login";
    }

    @GetMapping("/gestionMiembro")
    public String mostrarListaMiembrosFamilia(HttpSession session, Model model)
    {
        Miembro miembro = (Miembro) session.getAttribute("miembro"); //obteniendo el objeto Miembro de la sesión
        if (miembro != null)
        {
            Familia familia = miembro.getFamilia(); //obteniendo la familia del miembro
            List<Miembro> listaMiembros = serviceFamilia.ListarMiembrosPorFamilia(familia.getId()); //obteniendo la lista de miembros
            model.addAttribute("familia", familia); //pasando la familia a la vista
            model.addAttribute("listaMiembros", listaMiembros); //pasando la lista de miembros a la vista
            return "gestionMiembro"; //mostrando la página de gestión de familia
        }
        return "login";
    }
////    @GetMapping("/mostrarMiembros")
////    public String listarMiembros(HttpSession session, Model model)
////    {
////        Familia familia = (Familia) session.getAttribute("familia");
////        List<Miembro> listaMiembros = serviceFamilia.ListarMiembrosPorFamilia(familia.getId());
////        model.addAttribute("listaMiembros", listaMiembros);
////        return "gestionMiembro";
////    }

    @GetMapping("/paginaInicialConMiembro")
    public String mostrarPaginaMiembro(Model model, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Miembro miembro = (Miembro) session.getAttribute("miembro");
        model.addAttribute("miembro", miembro);
        return "paginaDeMiembro";
    }

//    @GetMapping("/RegistroInicial")
//    public String MostrarFormularioDeRegistroMiembro(Model model)
//    {
//        Miembro miembro = new Miembro();
//        model.addAttribute("miembro", miembro);
//        return "/paginaPrincipal";
//    } 
}
