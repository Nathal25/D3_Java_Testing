package co.edu.univalle.demo.controller;

import co.edu.univalle.demo.model.EstudianteModel;
import co.edu.univalle.demo.model.ProfesorModel;
import co.edu.univalle.demo.service.EstudianteService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para la gestión de estudiantes
 */
@RestController
@RequestMapping("/api/v1/estudiantes")
public class EstudianteController {
    /**
     * Servicio de estudiantes
     */
    private final EstudianteService estudianteService;

    /**
     * Constructor con inyección de dependencias
     * @param estudianteService servicio de estudiantes
     */
    public EstudianteController(final EstudianteService estudianteService){
        this.estudianteService=estudianteService;
    }
    /**
     * Crea un nuevo estudiantte
     * @param estudiante datos del estudiante
     * @return estudiante creado
     */
    @PostMapping
    public ResponseEntity<EstudianteModel> crear(
            @RequestBody final EstudianteModel estudiante){
        EstudianteModel creado=estudianteService.crear(estudiante);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(creado);
    }
    /**
     * Actualiza un estudiante existente
     * @param estudiante datos actualizados
     * @return estudiante actualizado
     */
    @PutMapping
    public ResponseEntity<EstudianteModel> actualizar(
            @RequestBody final EstudianteModel estudiante){
        return ResponseEntity.ok(
                estudianteService.actualizar(estudiante)
        );
    }
    /**

     * Elimina un estudiante por id.
     *
     * @param id identificador del estudiante
     * @return respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable final Long id) {

        estudianteService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    /**

     * Obtiene todos los estudiantes.
     *
     * @return lista de estudiantes
     */
    @GetMapping
    public ResponseEntity<List<EstudianteModel>> obtenerTodos() {

        return ResponseEntity.ok(
                estudianteService.obtenerTodos()
        );
    }

    /**

     * Obtiene un estudiante por id.
     *
     * @param id identificador del estudiante
     * @return estudiante encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<EstudianteModel> obtenerPorId(
            @PathVariable final Long id) {

        return ResponseEntity.ok(
                estudianteService.obtenerPorId(id)
        );
    }

    /**

     * Busca estudiantes por nombre.
     *
     * @param nombre nombre a buscar
     * @return lista de coincidencias
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<EstudianteModel>> buscarPorNombre(
            @RequestParam final String nombre) {

        return ResponseEntity.ok(
                estudianteService.buscarPorNombre(nombre)
        );
    }
}

