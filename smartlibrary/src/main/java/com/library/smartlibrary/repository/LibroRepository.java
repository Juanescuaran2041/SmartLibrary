package com.library.smartlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.smartlibrary.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}
