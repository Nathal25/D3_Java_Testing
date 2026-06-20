package co.edu.univalle.demo.controller;

import co.edu.univalle.demo.model.ProfesorModel;
import co.edu.univalle.demo.service.ProfesorService;
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

 * Controlador REST para la gestión de profesores.
 */
@RestController
@RequestMapping("/api/v1/profesores")
public class ProfesorController {

    /**

     * Servicio de profesores.
     */
    private final ProfesorService profesorService;

    /**

     * Constructor con inyección de dependencias.
     *
     * @param profesorService servicio de profesores
     */
    public ProfesorController(final ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    /**

     * Crea un nuevo profesor.
     *
     * @param profesor datos del profesor
     * @return profesor creado
     */
    @PostMapping
    public ResponseEntity<ProfesorModel> crear(
            @RequestBody final ProfesorModel profesor) {

        ProfesorModel creado = profesorService.crear(profesor);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(creado);
    }

    /**

     * Actualiza un profesor existente.
     *
     * @param profesor datos actualizados
     * @return profesor actualizado
     */
    @PutMapping
    public ResponseEntity<ProfesorModel> actualizar(
            @RequestBody final ProfesorModel profesor) {

        return ResponseEntity.ok(
                profesorService.actualizar(profesor)
        );
    }

    /**

     * Elimina un profesor por id.
     *
     * @param id identificador del profesor
     * @return respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable final Long id) {

        profesorService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    /**

     * Obtiene todos los profesores.
     *
     * @return lista de profesores
     */
    @GetMapping
    public ResponseEntity<List<ProfesorModel>> obtenerTodos() {

        return ResponseEntity.ok(
                profesorService.obtenerTodos()
        );
    }

    /**

     * Obtiene un profesor por id.
     *
     * @param id identificador del profesor
     * @return profesor encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProfesorModel> obtenerPorId(
            @PathVariable final Long id) {

        return ResponseEntity.ok(
                profesorService.obtenerPorId(id)
        );
    }

    /**

     * Busca profesores por nombre.
     *
     * @param nombre nombre a buscar
     * @return lista de coincidencias
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<ProfesorModel>> buscarPorNombre(
            @RequestParam final String nombre) {

        return ResponseEntity.ok(
                profesorService.buscarPorNombre(nombre)
        );
    }
}

