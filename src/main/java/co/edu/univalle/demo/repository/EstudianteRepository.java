package co.edu.univalle.demo.repository;

import co.edu.univalle.demo.model.EstudianteModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<EstudianteModel, Long>
{
    /**
     * Busca profesores cuyo nombre contenga el texto indicado.
     * @param nombre fragmento del nombre a buscar
     * @return lista de profesores que coinciden
     */
    List<EstudianteModel> findAllByNombreContainingIgnoreCase(String nombre);

    Optional<EstudianteModel> findByEmail(String email);

    Optional<EstudianteModel> findByIdentificacion(String identificacion);

    Optional<EstudianteModel> findBySemestre(Integer semestre);
}
