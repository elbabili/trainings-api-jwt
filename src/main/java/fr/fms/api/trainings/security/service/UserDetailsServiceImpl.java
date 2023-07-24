package fr.fms.api.trainings.security.service;

import fr.fms.api.trainings.security.entities.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service @Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //nous connaissons l'userName, nous avons besoin de l'utilisateur
        //Quand Spring demande à cette classe, cherche moi l'utilisateur
        //Cette classe demande à AccountService de lui renvoyer l'utilisateur correspondant

        AppUser appUser = accountService.findUserByUsername(userName);

        if(appUser == null) {
            log.error("Utilisateur non trouvé en base");
            throw new UsernameNotFoundException(userName);
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getRolename()));
        } );

        return new User(appUser.getUsername(),appUser.getPassword(),authorities);
    }
}
