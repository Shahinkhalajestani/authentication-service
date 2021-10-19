package com.shahintraining.authenticationservice.repo;

import com.shahintraining.authenticationservice.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author sh.khalajestanii on 10/19/2021
 * @project authentication-service
 */
@Repository
public interface AppUserRepo extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findAppUserByUsername(String username);
}
