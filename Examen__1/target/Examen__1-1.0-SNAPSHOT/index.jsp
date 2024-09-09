<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Clasificación Olímpica de BMX</title>
    <link rel="stylesheet" href="styles.css">
</head>

<body>
<header class="header">
    <div class="container">
        <h1>Clasificación Olímpica de BMX</h1>
    </div>
</header>

<main class="container">
    <!-- Formulario de búsqueda -->
    <form action="competidores" method="get" class="search-form">
        <input type="hidden" name="action" value="search">
        <div class="form-row">
            <div class="form-group">
                <label for="searchNombre">Nombre:</label>
                <input type="text" id="searchNombre" name="searchNombre" maxlength="40">
            </div>
            <div class="form-group">
                <label for="searchPais">País:</label>
                <input type="text" id="searchPais" name="searchPais" maxlength="40">
            </div>
            <div class="form-group">
                <label for="searchPuntaje">Puntaje Total Mayor a:</label>
                <input type="number" id="searchPuntaje" name="searchPuntaje">
            </div>
        </div>
        <button type="submit" class="btn">Buscar</button>
    </form>

    <!-- Botones de acciones -->
    <div class="action-buttons">
        <a href="agregarCompetidor.jsp" class="btn btn-add">Añadir Competidor</a>
        <a href="competidores?action=agregarCompetidorAleatorio" class="btn btn-generate">Generar Competidor Aleatorio</a>
        <a href="competidores?action=simularCarrera" class="btn btn-simulate">Simular Carrera</a>
    </div>

    <!-- Tabla de competidores -->
    <div class="competidores-table">
        <table>
            <thead>
            <tr>
                <th><a href="competidores?sortField=nombre&sortDirection=asc">Nombre ▲</a> | <a href="competidores?sortField=nombre&sortDirection=desc">▼</a></th>
                <th><a href="competidores?sortField=apellido&sortDirection=asc">Apellido ▲</a> | <a href="competidores?sortField=apellido&sortDirection=desc">▼</a></th>
                <th>Fecha de Nacimiento</th>
                <th><a href="competidores?sortField=pais&sortDirection=asc">País ▲</a> | <a href="competidores?sortField=pais&sortDirection=desc">▼</a></th>
                <th>Código País</th>
                <th>Puntaje R1</th>
                <th>Puntaje R2</th>
                <th>Puntaje R3</th>
                <th><a href="competidores?sortField=puntajeTotal&sortDirection=asc">Puntaje Total ▲</a> | <a href="competidores?sortField=puntajeTotal&sortDirection=desc">▼</a></th>
                <th>Acciones</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="competidor" items="${competidores}" varStatus="status">
                <tr class="${status.index == 0 ? 'gold-medal' : status.index == 1 ? 'silver-medal' : status.index == 2 ? 'bronze-medal' : ''}">
                    <td>
                        <a href="competidores?action=viewDetails&index=${status.index}">${competidor.nombre}</a>
                    </td>
                    <td>${competidor.apellido}</td>
                    <td>${competidor.fechaNacimiento}</td>
                    <td>${competidor.pais}</td>
                    <td>${competidor.codigoPais}</td>
                    <td>${competidor.puntajesRonda[0]}</td>
                    <td>${competidor.puntajesRonda[1]}</td>
                    <td>${competidor.puntajesRonda[2]}</td>
                    <td>${competidor.puntajeTotal}</td>
                    <td>
                        <!-- Link to Edit Competitor -->
                        <a href="editarCompetidor.jsp?index=${status.index}" class="btn btn-sm">Editar</a>
                        <!-- Link to Delete Competitor -->
                        <a href="competidores?action=delete&index=${status.index}" class="btn btn-sm btn-delete">Eliminar</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Botones para exportar la tabla -->
    <form action="competidores" method="get" class="export-form">
        <button type="submit" name="action" value="exportExcel" class="btn btn-export">Exportar a Excel</button>
        <button type="submit" name="action" value="exportJSON" class="btn btn-export">Exportar a JSON</button>
    </form>
</main>

<footer class="footer">
    <div class="container">
        <p>Fecha: <%= new java.util.Date() %></p>
        <p>Nombre Completo: Juan Camilo Arias Ospina</p>
    </div>
</footer>
</body>
</html>
