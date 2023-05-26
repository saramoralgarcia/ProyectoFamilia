package ProyectoFamilia.Controller;

import ProyectoFamilia.Service.ServiceFamilia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventoController {

    @Autowired
    private ServiceFamilia serviceFamilia;

    @GetMapping("/gestionEvento")
    public String MostrarPaginaDeEvento(Model model) 
    {
        return "gestionEvento";
    }
}
