package com.example.examen__1.controler;

import com.example.examen__1.model.Competidor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatosCompetidores {
    private static final List<Competidor> competidores = new ArrayList<>();

    // Códigos predefinidos para países
    private static final String[] PAISES = {
            "Colombia", "Francia", "Estados Unidos", "Canada", "Brasil"
    };

    static {
        cargarDatosIniciales();
    }

    // Método para cargar competidores de ejemplo
    private static void cargarDatosIniciales() {
        competidores.add(crearCompetidor("Carlos", "MENDOZA", LocalDate.of(1992, 2, 18), "Argentina", "Competidor experimentado en BMX"));
        competidores.add(crearCompetidor("María", "RODRÍGUEZ", LocalDate.of(2001, 3, 30), "España", "Competidora joven y prometedora"));
        competidores.add(crearCompetidor("Lucía", "FERNÁNDEZ", LocalDate.of(1997, 8, 25), "México", "Competidora talentosa con varios títulos"));
        competidores.add(crearCompetidor("Pedro", "GÓMEZ", LocalDate.of(1995, 1, 15), "Brasil", "Veterano del BMX en Sudamérica"));
        competidores.add(crearCompetidor("Claudia", "HERNÁNDEZ", LocalDate.of(1999, 11, 9), "Italia", "Atleta en ascenso en el BMX europeo"));
        competidores.add(crearCompetidor("Luis", "RAMÍREZ", LocalDate.of(1993, 4, 22), "Estados Unidos", "Referente del BMX estadounidense"));
        competidores.add(crearCompetidor("Isabel", "MARTÍNEZ", LocalDate.of(2002, 10, 14), "Canadá", "Joven promesa del BMX canadiense"));
        competidores.add(crearCompetidor("Sofía", "PÉREZ", LocalDate.of(1998, 12, 6), "Australia", "Competidora destacada en el BMX oceánico"));
        competidores.add(crearCompetidor("Andrés", "GARCÍA", LocalDate.of(1994, 6, 5), "Francia", "Competidor de élite en Europa"));
        competidores.add(crearCompetidor("Jorge", "LOPEZ", LocalDate.of(1996, 9, 19), "Colombia", "Competidor colombiano con varias victorias"));
    }


    // Crear competidor con datos aleatorios
    private static Competidor crearCompetidor(String nombre, String apellido, LocalDate fechaNacimiento, String pais, String descripcion) {
        return new Competidor(nombre, apellido, fechaNacimiento, pais, descripcion);
    }

    // Obtener lista de competidores
    public static List<Competidor> obtenerCompetidores() {
        return new ArrayList<>(competidores);
    }

    // Añadir un nuevo competidor a la lista
    public static void agregarCompetidor(Competidor competidor) {
        competidores.add(competidor);
        ordenarCompetidoresPorPuntaje();
    }

    // Método para eliminar competidor por índice
    public static void eliminarCompetidor(int indice) {
        if (indice >= 0 && indice < competidores.size()) {
            competidores.remove(indice);
        }
    }

    // Actualizar un competidor existente
    public static void actualizarCompetidor(int indice, Competidor competidorActualizado) {
        if (indice >= 0 && indice < competidores.size()) {
            competidores.set(indice, competidorActualizado);
        }
    }

    // Generar competidor con datos aleatorios
    public static void agregarCompetidorAleatorio() {
        Random random = new Random();
        String nombre = "Competidor" + random.nextInt(1000);
        String apellido = "Apellido" + random.nextInt(1000);
        LocalDate fechaNacimiento = LocalDate.of(1990 + random.nextInt(20), random.nextInt(12) + 1, random.nextInt(28) + 1);
        String pais = PAISES[random.nextInt(PAISES.length)];
        Competidor competidor = new Competidor(nombre, apellido, fechaNacimiento, pais, "Competidor generado aleatoriamente");
        competidores.add(competidor);
    }

    // Método para ordenar competidores por puntaje
    private static void ordenarCompetidoresPorPuntaje() {
        competidores.sort((c1, c2) -> Integer.compare(c2.getPuntajeTotal(), c1.getPuntajeTotal()));
    }

    public static void simularCarrera() {
        Random random = new Random();

        // Definir el número de rondas en la carrera
        int numRondas = 5;

        // Iterar sobre cada competidor y simular su puntaje en cada ronda
        for (Competidor competidor : competidores) {
            List<Integer> puntajesRonda = new ArrayList<>();

            // Generar puntaje aleatorio para cada ronda (por ejemplo, entre 0 y 100 puntos)
            for (int i = 0; i < numRondas; i++) {
                int puntajeRonda = random.nextInt(101); // Puntaje entre 0 y 100
                puntajesRonda.add(puntajeRonda);
            }

            // Actualizar los puntajes de las rondas y recalcular el puntaje total
            competidor.setPuntajesRonda(puntajesRonda);
        }

        // Después de simular la carrera, se reordenan los competidores por puntaje total
        ordenarCompetidoresPorPuntaje();
    }

}
