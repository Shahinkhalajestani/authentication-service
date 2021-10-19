package com.shahintraining.authenticationservice.repo;

import com.shahintraining.authenticationservice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author sh.khalajestanii on 10/19/2021
 * @project authentication-service
 */
@Repository
public interface RoleRepo extends JpaRepository<Role , Long> {
    Optional<Role> findByName(String name);
}
