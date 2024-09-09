<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Detalles del Competidor</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>
<header class="header">
  <div class="container">
    <h1>Detalles del Competidor</h1>
  </div>
</header>

<main class="container">
  <h2>${competidor.nombre} ${competidor.apellido}</h2>

  <p><strong>Fecha de Nacimiento:</strong> ${competidor.birthDate}</p>
  <p><strong>País:</strong> ${competidor.country}</p>
  <p><strong>Código de País:</strong> ${competidor.countryCode}</p>
  <p><strong>Puntaje Total:</strong> ${competidor.totalScore}</p>
  <p><strong>Descripción:</strong> ${competidor.description}</p>

  <!-- Botón para regresar -->
  <a href="index.jsp" class="btn">Regresar</a>
</main>

<footer class="footer">
  <div class="container">
    <p>Fecha: <%= new java.util.Date() %></p>
    <p>Nombre Completo: Juan Camilo Arias Ospina</p>
  </div>
</footer>
</body>
</html>
