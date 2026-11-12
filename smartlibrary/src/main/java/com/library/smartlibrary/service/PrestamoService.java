package com.library.smartlibrary.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.smartlibrary.dto.PrestamoRequest;
import com.library.smartlibrary.exception.LibroNoDisponibleException;
import com.library.smartlibrary.exception.RecursoNoEncontradoException;
import com.library.smartlibrary.model.Estudiante;
import com.library.smartlibrary.model.Libro;
import com.library.smartlibrary.model.Prestamo;
import com.library.smartlibrary.repository.EstudianteRepository;
import com.library.smartlibrary.repository.LibroRepository;
import com.library.smartlibrary.repository.PrestamoRepository;

@Service
public class PrestamoService {

    private final EstudianteRepository estudiantes;
    private final LibroRepository libros;
    private final PrestamoRepository prestamos;

    public PrestamoService(EstudianteRepository estudiantes, LibroRepository libros, PrestamoRepository prestamos) {
        this.estudiantes = estudiantes;
        this.libros = libros;
        this.prestamos = prestamos;
    }

    @Transactional
    public Prestamo registrar(PrestamoRequest request) {
        Estudiante estudiante = estudiantes.findById(request.estudianteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado: " + request.estudianteId()));
        Libro libro = libros.findById(request.libroId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado: " + request.libroId()));
        if (!libro.isDisponible()) {
            throw new LibroNoDisponibleException("El libro no esta disponible: " + libro.getTitulo());
        }
        libro.marcarPrestado();
        libros.save(libro);
        Prestamo prestamo = new Prestamo();
        prestamo.setEstudiante(estudiante);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        return prestamos.save(prestamo);
    }

    public List<Prestamo> listar() {
        return prestamos.findAll();
    }
}
