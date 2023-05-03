package com.example.attestationsp.repository;

import com.example.attestationsp.model.Socks;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SocksRepository extends JpaRepository<Socks, Long> {
    Socks getSocksByColorAndCottonPart(String color, Integer cottonPart);

}
