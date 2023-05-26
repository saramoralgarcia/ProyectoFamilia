
package ProyectoFamilia.Repository;

import ProyectoFamilia.Model.Recompensa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryRecompensa extends JpaRepository<Recompensa, Long>
{
    List<Recompensa> findByMiembroId(Long idMiembro);
}
