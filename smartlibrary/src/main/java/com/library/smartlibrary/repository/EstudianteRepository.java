package com.library.smartlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.smartlibrary.model.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
}
