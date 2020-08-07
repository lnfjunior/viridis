package br.com.vetta.viridis.repository;

import br.com.vetta.viridis.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByLogin(String login);
}
