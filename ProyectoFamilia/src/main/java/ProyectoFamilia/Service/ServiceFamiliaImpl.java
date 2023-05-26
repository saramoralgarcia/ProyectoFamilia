package ProyectoFamilia.Service;

import ProyectoFamilia.Model.Familia;
import ProyectoFamilia.Model.Miembro;
import ProyectoFamilia.Model.Recompensa;
import ProyectoFamilia.Model.Tarea;
import ProyectoFamilia.Repository.RepositoryFamilia;
import ProyectoFamilia.Repository.RepositoryMiembro;
import ProyectoFamilia.Repository.RepositoryRecompensa;
import ProyectoFamilia.Repository.RepositoryTarea;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ServiceFamiliaImpl implements ServiceFamilia
{
    @Autowired
    private RepositoryFamilia RepositoryFamilia;
    @Autowired
    private RepositoryMiembro RepositoryMiembro;
    @Autowired
    private RepositoryTarea RepositoryTarea; 
    @Autowired
    private RepositoryRecompensa RepositoryRecompensa;
    

    @Override
    public void guardarFamilia(Familia familia) 
    {
        this.RepositoryFamilia.save(familia);
    }
    
    @Override
    public Familia buscarFamiliaPorId(Long familiaId)
    {
        Optional<Familia> optionalFamilia = RepositoryFamilia.findById(familiaId);
        Familia nuevaFamilia = null;
        if(optionalFamilia.isPresent())
        {
            nuevaFamilia = optionalFamilia.get();
        }
        return nuevaFamilia;
    }
    
    @Override
    public Familia buscarFamiliaPorCodigo(Long codigo)
    {
        Familia familia = RepositoryFamilia.findByCodigo(codigo);
        return familia;
    }
    
    /*-------------------- MIEMBRO ----------------------*/
    
    @Override
    public void guardarMiembro(Miembro miembro)
    {
        this.RepositoryMiembro.save(miembro);
    }
    
    @Override
    public Miembro buscarMiembroPorEmail(String email)
    {
        Miembro miembro = RepositoryMiembro.findByEmail(email);
        return miembro;
    }
    
    @Override
    public Miembro buscarMiembroPorId(Long id)
    {
        Optional<Miembro> optionalMiembro = RepositoryMiembro.findById(id);
        Miembro nuevoMiembro = null;
        if(optionalMiembro.isPresent())
        {
            nuevoMiembro = optionalMiembro.get();
        }
        return nuevoMiembro;
    }
    
    @Override
    public List<Miembro> ListarMiembrosPorFamilia(Long idFamilia) 
    {
     return RepositoryMiembro.findByFamiliaId(idFamilia);
    }

    
    @Override
    public List<Miembro> ListarMiembros()
    {
        List<Miembro> listaMiembros = this.RepositoryMiembro.findAll();
        return listaMiembros;
    }
    
    @Override
    public List<Miembro> ListarMiembrosPorId(Long id)
    {
        List<Miembro> listaMiembrosPorId = this.RepositoryMiembro.findByFamiliaId(id);
        return listaMiembrosPorId;
    }
    
    @Override
    public void eliminarMiembro(Long id) 
    {
        this.RepositoryMiembro.deleteById(id);
    }
    
    /*-------------------- TAREA ----------------------*/
    
    @Override
    public void guardarTarea(Tarea tarea)
    {
        this.RepositoryTarea.save(tarea);
    }
    
    @Override
    public Tarea buscarTareaPorId(Long id)
    {
        Optional<Tarea> optionalTarea = RepositoryTarea.findById(id);
        Tarea nuevaTarea = null;
        if(optionalTarea.isPresent())
        {
            nuevaTarea = optionalTarea.get();
        }
        return nuevaTarea;
    }
    
    @Override
    public List<Tarea> ListarTareas(Long idFamilia)
    {
        return  RepositoryTarea.findByFamiliaId(idFamilia);
    }
    
    @Override
    public void eliminarTarea(Long id)
    {
        this.RepositoryTarea.deleteById(id);
    }
    
    /*-------------------- RECOMPENSA ----------------------*/
    
    @Override
    public void guardarRecompensa(Recompensa recompensa)
    {
        this.RepositoryRecompensa.save(recompensa);
    }
            
    @Override
    public List<Recompensa> ListaRecompensas(Long idMiembro)
    {
        List<Recompensa> listaRecompensas = this.RepositoryRecompensa.findByMiembroId(idMiembro);
        return listaRecompensas;
    }
    
    @Override
    public Recompensa buscarRecompensaPorId(Long id)
    {
        Optional<Recompensa> optionalRecompensa = RepositoryRecompensa.findById(id);
        Recompensa nuevaRecompensa = null;
        if(optionalRecompensa.isPresent())
        {
            nuevaRecompensa = optionalRecompensa.get();
        }
        return nuevaRecompensa;
    }
    
    @Override
    public void eliminarRecompensa(Long id)
    {
        this.RepositoryRecompensa.deleteById(id);
    }
}
