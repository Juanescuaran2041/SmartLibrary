package com.library.smartlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.smartlibrary.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
}
