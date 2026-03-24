package br.com.lucena.nerdspace.repository;

import br.com.lucena.nerdspace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
