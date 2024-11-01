package br.com.senac.api.useCases.usuarios.implement.repositorys;

import br.com.senac.api.entitys.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {
    static Optional<Usuarios> findByLogin(String login);
}
