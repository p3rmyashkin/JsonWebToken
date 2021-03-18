package com.example.securingweb.repository;

import com.example.securingweb.entites.MetaTokenInf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MetaTokenRepository extends JpaRepository<MetaTokenInf, Long> {

    List<MetaTokenInf> findAll();

    Optional<MetaTokenInf> findById(Long id);

    void deleteById(Long id);

    <S extends MetaTokenInf> S save(S metaInf);

}
