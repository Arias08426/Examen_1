package com.example.examen__1.controler;


import com.example.examen__1.controler.DatosCompetidores;
import com.example.examen__1.model.Competidor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet("/gestionarCompetidores")
public class CompetidorControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = Optional.ofNullable(request.getParameter("accion")).orElse("");

        switch (accion) {
            case "eliminar":
                eliminarCompetidor(request, response);
                break;
            case "exportarExcel":
                exportarAExcel(request, response);
                break;
            case "exportarJSON":
                exportarAJSON(request, response);
                break;
            case "buscar":
                buscarCompetidores(request, response);
                break;
            case "detalles":
                verDetallesCompetidor(request, response);
                break;
            case "simularCarrera":
                simularCarrera(request, response);
                break;
            case "generarAleatorio":
                generarCompetidorAleatorio(request, response);
                break;
            default:
                listarCompetidores(request, response, request.getParameter("ordenarPor"), request.getParameter("direccion"));
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = Optional.ofNullable(request.getParameter("accion")).orElse("");

        switch (accion) {
            case "añadir":
                añadirCompetidor(request, response);
                break;
            case "editar":
                editarCompetidor(request, response);
                break;
            case "generarAleatorio":
                generarCompetidorAleatorio(request, response);
                break;
            case "simularCarrera":
                simularCarrera(request, response);
                break;
            default:
                listarCompetidores(request, response);
                break;
        }
    }

    private void listarCompetidores(HttpServletRequest request, HttpServletResponse response) {
    }


    // Métodos para exportar a Excel y JSON
    private void exportarAExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Configuración de la respuesta para el archivo Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=competidores.xlsx");

        // Creación del archivo Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Competidores");

        // Crear fila de encabezado
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Nombre");
        header.createCell(1).setCellValue("Apellido");
        header.createCell(2).setCellValue("País");
        header.createCell(3).setCellValue("Puntaje Total");

        // Obtener la lista de competidores y rellenar los datos
        List<Competidor> competidores = DatosCompetidores.obtenerCompetidores();
        int rowIdx = 1;
        for (Competidor competidor : competidores) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(competidor.getNombre());
            row.createCell(1).setCellValue(competidor.getApellido());
            row.createCell(2).setCellValue(competidor.getPais());
            row.createCell(3).setCellValue(competidor.getPuntajeTotal());
        }

        // Escribir el archivo Excel a la respuesta HTTP
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private void exportarAJSON(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Configuración de la respuesta para el archivo JSON
        response.setContentType("application/json");
        response.setHeader("Content-Disposition", "attachment; filename=competidores.json");

        // Crear un ObjectMapper para convertir objetos a JSON
        ObjectMapper objectMapper = new ObjectMapper();

        // Obtener la lista de competidores
        List<Competidor> competidores = DatosCompetidores.obtenerCompetidores();

        // Convertir la lista a JSON y escribirla en la respuesta HTTP
        objectMapper.writeValue(response.getWriter(), competidores);
    }

    // Otros métodos existentes en el controlador...

    private void listarCompetidores(HttpServletRequest request, HttpServletResponse response, String campoOrden, String direccion) throws ServletException, IOException {
        List<Competidor> competidores = DatosCompetidores.obtenerCompetidores();
        for (int i = 0; i < competidores.size(); i++) {
            competidores.get(i).setIndice(i);
        }

        if (campoOrden != null && direccion != null) {
            Comparator<Competidor> comparador;
            switch (campoOrden) {
                case "nombre":
                    comparador = Comparator.comparing(Competidor::getNombre);
                    break;
                case "apellido":
                    comparador = Comparator.comparing(Competidor::getApellido);
                    break;
                case "pais":
                    comparador = Comparator.comparing(Competidor::getPais);
                    break;
                case "puntajeTotal":
                    comparador = Comparator.comparingInt(Competidor::getPuntajeTotal);
                    break;
                default:
                    comparador = Comparator.comparing(Competidor::getNombre);
                    break;
            }

            if ("descendente".equals(direccion)) {
                comparador = comparador.reversed();
            }

            competidores = competidores.stream()
                    .sorted(comparador)
                    .collect(Collectors.toList());
        }

        request.setAttribute("competidores", competidores);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    // Métodos para añadir, editar, eliminar competidores, etc.

    private void eliminarCompetidor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int indice = Integer.parseInt(request.getParameter("indice"));
        DatosCompetidores.eliminarCompetidor(indice);
        response.sendRedirect("competidores");
    }
    private void añadirCompetidor(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String pais = request.getParameter("pais");
        String puntajeStr = request.getParameter("puntajeTotal");

        // Validaciones simples
        if (nombre == null || apellido == null || pais == null || puntajeStr == null) {
            request.setAttribute("error", "Todos los campos son obligatorios.");
            request.getRequestDispatcher("/agregarcompetidor.jsp").forward(request, response);
            return;
        }

     //  try {
      //      int puntajeTotal = Integer.parseInt(puntajeStr);

      //      Competidor nuevoCompetidor = new Competidor(nombre, apellido,LocalDate.ofEpochDay(3),pais);
      //      DatosCompetidores.agregarCompetidor(nuevoCompetidor);

       //     response.sendRedirect("competidores");
      //  } catch (NumberFormatException e) {
      //      request.setAttribute("error", "El puntaje debe ser un número.");
     //       request.getRequestDispatcher("/formularioCompetidor.jsp").forward(request, response);
        }
  //  }

    private void editarCompetidor(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String indiceStr = request.getParameter("indice");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String pais = request.getParameter("pais");
        String puntajeStr = request.getParameter("puntajeTotal");

        if (nombre == null || apellido == null || pais == null || puntajeStr == null || indiceStr == null) {
            request.setAttribute("error", "Todos los campos son obligatorios.");
            request.getRequestDispatcher("/editarcompetidor.jsp").forward(request, response);
            return;
        }

        try {
            int indice = Integer.parseInt(indiceStr);
            int puntajeTotal = Integer.parseInt(puntajeStr);

            // Verificar si el índice es válido
            List<Competidor> competidores = DatosCompetidores.obtenerCompetidores();
            if (indice < 0 || indice >= competidores.size()) {
                request.setAttribute("error", "Índice de competidor inválido.");
                request.getRequestDispatcher("/editarcompetidor.jsp").forward(request, response);
                return;
            }

            Competidor competidorExistente = competidores.get(indice);
            competidorExistente.setNombre(nombre);
            competidorExistente.setApellido(apellido);
            competidorExistente.setPais(pais);
            competidorExistente.setPuntajeTotal(puntajeTotal);

            response.sendRedirect("competidores");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El puntaje debe ser un número.");
            request.getRequestDispatcher("/editarcompetidor.jsp").forward(request, response);
        }
    }


    private void generarCompetidorAleatorio(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatosCompetidores.agregarCompetidorAleatorio();
        response.sendRedirect("competidores");
    }

    private void simularCarrera(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatosCompetidores.simularCarrera();
        response.sendRedirect("competidores");
    }

    private void buscarCompetidores(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombreBusqueda = request.getParameter("nombreBusqueda");
        String paisBusqueda = request.getParameter("paisBusqueda");
        String puntajeBusquedaStr = request.getParameter("puntajeBusqueda");

        List<Competidor> competidoresFiltrados = DatosCompetidores.obtenerCompetidores().stream()
                .filter(c -> (nombreBusqueda == null || nombreBusqueda.isEmpty() || c.getNombre().toLowerCase().contains(nombreBusqueda.toLowerCase())))
                .filter(c -> (paisBusqueda == null || paisBusqueda.isEmpty() || c.getPais().toLowerCase().contains(paisBusqueda.toLowerCase())))
                .filter(c -> {
                    if (puntajeBusquedaStr == null || puntajeBusquedaStr.isEmpty()) return true;
                    try {
                        int puntajeBusqueda = Integer.parseInt(puntajeBusquedaStr);
                        return c.getPuntajeTotal() > puntajeBusqueda;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());

        request.setAttribute("competidores", competidoresFiltrados);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private void verDetallesCompetidor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int index = Integer.parseInt(request.getParameter("index"));
        Competidor competidor = DatosCompetidores.obtenerCompetidores().get(index);

        request.setAttribute("competidor", competidor);
        request.getRequestDispatcher("/detallescompetidores.jsp").forward(request, response);
    }
}