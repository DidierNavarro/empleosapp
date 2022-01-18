package com.didiernavarro.empleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.didiernavarro.empleos.model.Solicitud;

public interface SolcitudesRepository extends JpaRepository<Solicitud, Integer>
{

}
