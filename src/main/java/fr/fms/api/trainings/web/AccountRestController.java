package fr.fms.api.trainings.web;

import fr.fms.api.trainings.security.entities.AppRole;
import fr.fms.api.trainings.security.entities.AppUser;
import fr.fms.api.trainings.security.service.AccountService;
import fr.fms.api.trainings.security.service.AccountServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class AccountRestController {
    @Autowired
    AccountServiceImpl accountService;

    @GetMapping("/users")
    ResponseEntity<List<AppUser>> getUsers(){  return this.accountService.listUsers(); }

    @PostMapping("/users")
    public AppUser postUser(@RequestBody AppUser user) { return this.accountService.saveUser(user); }

    @PostMapping("/role")
    public AppRole postRole(@RequestBody AppRole role) { return this.accountService.saveRole(role); }

    @PostMapping("/roleUser")
    public void postRoleToUser(@RequestBody UserRoleForm userRoleForm) {
        accountService.addRoleToUser(userRoleForm.getUsername(),userRoleForm.getRolename());
    }
}

@Data
class UserRoleForm {
    private String username;
    private String rolename;
}