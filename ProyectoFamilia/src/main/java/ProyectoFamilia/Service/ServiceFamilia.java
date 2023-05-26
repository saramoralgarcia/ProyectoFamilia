package ProyectoFamilia.Service;

import ProyectoFamilia.Model.Familia;
import ProyectoFamilia.Model.Miembro;
import ProyectoFamilia.Model.Recompensa;
import ProyectoFamilia.Model.Tarea;
import java.util.List;

public interface ServiceFamilia
{
/*-------------------------------------------------*/
                  //  Familia
/*-------------------------------------------------*/    
    void guardarFamilia(Familia familia);
    Familia buscarFamiliaPorId(Long familiaId);
    Familia buscarFamiliaPorCodigo(Long codigo);
/*-------------------------------------------------*/    
                    //Miembro
/*-------------------------------------------------*/
    
    void guardarMiembro(Miembro miembro);
    Miembro buscarMiembroPorEmail(String Email);
    Miembro buscarMiembroPorId(Long id);
    List<Miembro> ListarMiembros();
    List <Miembro> ListarMiembrosPorId(Long idFamilia);
    List<Miembro> ListarMiembrosPorFamilia(Long idFamilia);
    void eliminarMiembro(Long id);
    
/*-------------------------------------------------*/
                    //Tarea
/*-------------------------------------------------*/    
    void guardarTarea(Tarea tarea);
    Tarea buscarTareaPorId(Long id);
    List<Tarea> ListarTareas(Long idFamilia);
    void eliminarTarea(Long id);
    
/*-------------------------------------------------*/
                    //Evento
/*-------------------------------------------------*/   
    
//    void guardarEvento(Evento evento);
//    Evento buscarEventoPorId(Long id);
//    List<Evento> ListarEventos(Long idFamilia);
//    void eliminarEvento(Long id);


/*-------------------------------------------------*/
                    //Recompensa
/*-------------------------------------------------*/
    
    void guardarRecompensa(Recompensa recompensa);
    Recompensa buscarRecompensaPorId(Long id);
    List<Recompensa> ListaRecompensas(Long idMiembro);
    void eliminarRecompensa(Long id);
}
