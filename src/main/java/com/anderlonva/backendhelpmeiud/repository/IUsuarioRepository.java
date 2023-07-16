package com.anderlonva.backendhelpmeiud.repository;

import com.anderlonva.backendhelpmeiud.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);  // todos los select a la base de datos usando jpa

}
