package com.ragabe.repository;

import com.ragabe.model.Usuari;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuariRepository extends JpaRepository<Usuari, Long> {

    Optional<Usuari> findByEmail(String email);

    boolean existsByEmail(String email);
}
