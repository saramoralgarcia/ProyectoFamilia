
package ProyectoFamilia.Repository;

import ProyectoFamilia.Model.Tarea;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RepositoryTarea extends JpaRepository<Tarea, Long>
{
    List<Tarea> findByFamiliaId(Long FamiliaId);
}
