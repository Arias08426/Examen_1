package com.example.examen__1.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Competidor {
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String pais;
    private String codigoPais;
    private List<Integer> puntajesRonda;
    private int puntajeTotal;
    private String descripcion;
    private int indice;

    // Constructor principal con datos básicos
    public Competidor(String nombre, String apellido, LocalDate fechaNacimiento, String pais, String descripcion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.pais = pais;
        this.codigoPais = obtenerCodigoPais(pais);
        this.puntajesRonda = List.of(new Integer[10]);
        this.puntajeTotal = 0;
        this.descripcion = descripcion;
    }

    // Constructor extendido con puntajes y código del país
    public Competidor(String nombre, String apellido, LocalDate fechaNacimiento, String pais, String codigoPais, List<Integer> puntajesRonda, int puntajeTotal, String descripcion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.pais = pais;
        this.codigoPais = codigoPais;
        this.puntajesRonda = puntajesRonda;
        this.puntajeTotal = puntajeTotal;
        this.descripcion = descripcion;
    }

    // Métodos de acceso y modificación (Getters y Setters)
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getPais() { return pais; }
    public void setPais(String pais) {
        this.pais = pais;
        this.codigoPais = obtenerCodigoPais(pais);
    }

    public String getCodigoPais() { return codigoPais; }

    public List<Integer> getPuntajesRonda() { return puntajesRonda; }
    public void setPuntajesRonda(List<Integer> puntajesRonda) {
        this.puntajesRonda = puntajesRonda;
        calcularPuntajeTotal();
    }

    public int getPuntajeTotal() { return puntajeTotal; }
    public void setPuntajeTotal(int puntajeTotal) { this.puntajeTotal = puntajeTotal; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getIndice() { return indice; }
    public void setIndice(int indice) { this.indice = indice; }

    // Métodos adicionales
    public int calcularEdad() {
        return (int) ChronoUnit.YEARS.between(this.fechaNacimiento, LocalDate.now());
    }

    private void calcularPuntajeTotal() {
        this.puntajeTotal = this.puntajesRonda.stream().reduce(0, Integer::sum);
    }

    private String obtenerCodigoPais(String pais) {
        return switch (pais.toLowerCase()) {
            case "colombia" -> "COL";
            case "francia" -> "FRA";
            case "estados unidos" -> "USA";
            case "canada" -> "CAN";
            case "brasil" -> "BRA";
            default -> "UNK";
        };
    }

    @Override
    public String toString() {
        return String.format("Competidor { Nombre: %s, Apellido: %s, País: %s, Puntaje Total: %d }",
                nombre, apellido, pais, puntajeTotal);
    }
}
