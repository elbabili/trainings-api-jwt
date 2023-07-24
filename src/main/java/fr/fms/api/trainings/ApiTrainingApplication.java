/**
 * Api spring de gestion de ventes de formations offrant plusieurs web services sécurisés avec Jwt :
 * ADMIN peut seul ajouter une formation, supprimer ou mettre à jour, changer la photo d'une formation
 * USER peut passer commande : un client est ajouté + commande associée + items associés
 * ALL peut afficher toutes les formations classées ou pas par catégories + les informations détaillées d'une formation
 * @author El babili - 2022
 * @version 1.0
 */
package fr.fms.api.trainings;

import fr.fms.api.trainings.dao.CategoryRepository;
import fr.fms.api.trainings.dao.TrainingRepository;
import fr.fms.api.trainings.entities.Category;
import fr.fms.api.trainings.entities.Training;
import fr.fms.api.trainings.security.entities.AppRole;
import fr.fms.api.trainings.security.entities.AppUser;
import fr.fms.api.trainings.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)        //afin de verouiller les accès vers les ressources directement
public class ApiTrainingApplication implements CommandLineRunner {
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    public AccountService accountService;

    @Bean   //sera executé par Spring au boot de l'appli delors on pourra l'injecter ailleurs (SecurityConfig par ex)
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiTrainingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //generateUsersRoles();
        //generateDatas();
    }

    /*@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedHeaders("*").allowedOrigins("*").allowedMethods("*");
            }
        };
    }*/

    private void generateUsersRoles() {
        accountService.saveUser(new AppUser(null,"papa","1234",new ArrayList<>()));
        accountService.saveUser(new AppUser(null,"mama","1234",new ArrayList<>()));
        accountService.saveRole(new AppRole(null,"ADMIN"));
        accountService.saveRole(new AppRole(null,"USER"));
        accountService.addRoleToUser("papa","ADMIN");
        accountService.addRoleToUser("papa","USER");
        accountService.addRoleToUser("mama","USER");
    }

    private void generateDatas() {
        Category language = categoryRepository.save(new Category(null,"Langage de programmation",null));
        Category frameworks = categoryRepository.save(new Category(null,"Frameworks",null));
        Category cms = categoryRepository.save(new Category(null,"CMS",null));

        trainingRepository.save(new Training(null,"Java","Java Standard Edition 8 sur 10 jours",3500,1,"unknown.png",language));
        trainingRepository.save(new Training(null,"Php","Initiation au Dev/Web avec php 4 jours",1300,1,"unknown.png",language));
        trainingRepository.save(new Training(null,"Python","Formation Python 7 jours",1500,1,"unknown.png",language));
        trainingRepository.save(new Training(null,"C#","Formation C# 5 jours",1500,1,"unknown.png",language));
        trainingRepository.save(new Training(null,"Javascript","Formation Javascript 5 jours",2000,1,"unknown.png",language));

        trainingRepository.save(new Training(null,"Spring","Spring Boot/Mvc/Sec 10 jours",5000,1, "unknown.png",frameworks));
        trainingRepository.save(new Training(null,"Symfony","Symfony Mvc 5 jours",2500,1,"unknown.png",frameworks));
        trainingRepository.save(new Training(null,"Django","Django 5 jours",3000,1,"unknown.png",frameworks));
        trainingRepository.save(new Training(null,"DotNet","DotNet & entityframework en 5 jours",2750,1,"unknown.png",frameworks));
        trainingRepository.save(new Training(null,"NodeJs","Prise en main de NodeJs/Express 2 jours",1400,1,"unknown.png",frameworks));

        trainingRepository.save(new Training(null,"WordPress","Formation Worpress 2 jours",500,1,"unknown.png",cms));
        trainingRepository.save(new Training(null,"PrestaShop","Formation PrestaShop 3 jours",1000,1,"unknown.png",cms));
        trainingRepository.save(new Training(null,"Magento","Formation Magento 4 jours",1300,1,"unknown.png",cms));
    }
}
