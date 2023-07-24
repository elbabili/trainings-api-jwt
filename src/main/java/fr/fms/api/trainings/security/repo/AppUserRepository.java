package fr.fms.api.trainings.security.repo;

import fr.fms.api.trainings.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    AppUser findByUsername(String username);    //à partir du nom d'utilisteur, on retourne un objet
}
