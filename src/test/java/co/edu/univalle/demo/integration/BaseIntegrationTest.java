package co.edu.univalle.demo.integration;

// @SpringBootTest levanta el contexto completo de Spring para los tests de integración
// RANDOM_PORT asigna un puerto aleatorio disponible al servidor HTTP
// evitando conflictos si hay otra aplicación corriendo en el puerto 8085
import org.springframework.boot.test.context.SpringBootTest;
// @Testcontainers activa la integración de JUnit 5 con Testcontainers
// le indica a JUnit que debe gestionar el ciclo de vida de los contenedores Docker
import org.testcontainers.junit.jupiter.Testcontainers;
// PostgreSQLContainer es el contenedor Docker de PostgreSQL que Testcontainers levanta
// internamente hace un docker pull postgres:16-alpine y arranca el contenedor
import org.testcontainers.containers.PostgreSQLContainer;
// @Container marca el campo como un contenedor gestionado por Testcontainers
// JUnit lo levanta antes de los tests y lo destruye al finalizar
import org.testcontainers.junit.jupiter.Container;
// DynamicPropertyRegistry permite inyectar propiedades dinámicas en el contexto de Spring
// las usamos para sobreescribir la URL de BD con la del contenedor en tiempo de ejecución
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
// La clase es abstract porque no contiene tests propios
// actúa como clase base que todas las clases de integración heredan
// evita repetir la configuración de Testcontainers en cada clase de test
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public abstract class BaseIntegrationTest {
    // @Container con static hace que el contenedor se levante UNA SOLA VEZ
    // para toda la suite de tests — no uno por cada método de test
    // esto mejora significativamente el tiempo de ejecución
    // Testcontainers descarga la imagen postgres:16-alpine la primera vez
    // y la reutiliza desde el cache de Docker en ejecuciones posteriores
    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine")
            // nombre de la base de datos que se crea dentro delcontenedor
            .withDatabaseName("matricula_test")
            // usuario con permisos sobre esa base de datos
            .withUsername("test_user")
            // contraseña del usuario
            .withPassword("test_pass");
    // @DynamicPropertySource se ejecuta ANTES de que Spring arranque
    // sobreescribe las propiedades del application.yml con los valores reales
    // del contenedor que Testcontainers acaba de levantar
    // esto es necesario porque Testcontainers asigna un puerto aleatorio
    // al contenedor cada vez — no podemos hardcodear la URL de conexión
    @DynamicPropertySource
    static void configureProperties(final DynamicPropertyRegistry registry) {
        // postgres::getJdbcUrl devuelve algo como:
        // jdbc:postgresql://localhost:49152/matricula_test
        // donde 49152 es el puerto aleatorio asignado por Testcontainers
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        // Flyway también necesita su propia URL de conexión para ejecutar
        // las migraciones V1__, V2__, V3__ sobre el contenedor de test
        // sin esto Flyway intentaría conectarse a la BD del applicationdev.yml
        registry.add("spring.flyway.url", postgres::getJdbcUrl);
        registry.add("spring.flyway.user", postgres::getUsername);
        registry.add("spring.flyway.password", postgres::getPassword);
    }
}

