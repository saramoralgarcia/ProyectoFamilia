package ProyectoFamilia.Repository;

import ProyectoFamilia.Model.Miembro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryMiembro extends JpaRepository<Miembro, Long>
{
    Miembro findByEmail(String email);
    List<Miembro> findByFamiliaId(Long FamiliaId);
}
