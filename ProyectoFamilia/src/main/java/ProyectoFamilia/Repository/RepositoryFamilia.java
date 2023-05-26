package ProyectoFamilia.Repository;

import ProyectoFamilia.Model.Familia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryFamilia extends JpaRepository<Familia, Long>
{
    Familia findByCodigo(Long codigo);


}
