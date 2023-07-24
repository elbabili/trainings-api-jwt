package fr.fms.api.trainings.web;

import fr.fms.api.trainings.entities.Training;
import fr.fms.api.trainings.exception.RecordNotFoundException;
import fr.fms.api.trainings.service.TrainingService;
import fr.fms.api.trainings.service.TrainingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@CrossOrigin("*")
//@CrossOrigin("http://localhost:4200/")
@RestController
//@RequestMapping("/api")
@Slf4j
public class TrainingController {
    @Autowired
    private TrainingServiceImpl trainingService;

    @GetMapping("/trainings")
    public List<Training> allTrainings(){
        return  trainingService.getTrainings();
    }

    @PostMapping("/trainings")
    public ResponseEntity<Training> saveTraining(@RequestBody Training t){
        t.setCategory(trainingService.getCategory(t.getCategory().getId()));
        Training training = trainingService.saveTraining(t);
        if(Objects.isNull(training)) {
            return ResponseEntity.noContent().build();
        }
        URI location =  ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(training.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/trainings")
    public ResponseEntity<Training> updateTraining(@RequestBody Training t){
        Training training = trainingService.readTraining(t.getId()).get();
        training.setName(t.getName());
        training.setDescription(t.getDescription());
        training.setPrice(t.getPrice());
        training.setCategory(trainingService.getCategory(t.getCategory().getId()));

        if(Objects.isNull(trainingService.saveTraining(training))) {
            return ResponseEntity.noContent().build();
        }
        URI location =  ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(training.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/trainings/{id}")
    public ResponseEntity<?> deleteTraining(@PathVariable("id") Long id) {
        try {
            trainingService.deleteTraining(id);
        }
        catch (Exception e) {
            log.error("Pb avec suppression de la formation d'id : {}",id);
            return ResponseEntity.internalServerError().body(e.getCause());
        }
        log.info("suppression de la formation d'id : {}", id);
        return ResponseEntity.ok().build();
    }   //ToDo lorsque l'admin supprime une formation en relation avec un OrderItem, mettre en place un mécanisme de visibilité de formation ou pas

    @GetMapping("/trainings/{id}")
    public Training getTrainingById(@PathVariable("id") Long id) {
        return trainingService.readTraining(id)
                .orElseThrow( () -> new RecordNotFoundException("Id de Formation "+ id + " n'existe pas") );
    }

    @GetMapping(path="/photo/{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getPhoto(@PathVariable("id") Long id) throws IOException {
        byte[] file = null;
        try {
            Training training = trainingService.readTraining(id).get();
            if(training.getPhoto() == null) training.setPhoto("unknown.png");
            file = Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/trainings/images/" + training.getPhoto()));
        }
        catch (Exception e) {
            log.error("pb avec download de l'image correspondant à la formation d'id : {}",id);
            return ResponseEntity.internalServerError().body(e.getCause());
        }
        //log.info("file download ok {}",id);
        return ResponseEntity.ok().body(file);
    }

    @PostMapping(path="/photo/{id}")
    public ResponseEntity<?> uploadPhoto(MultipartFile file, @PathVariable Long id) throws Exception {
        try {
            Training training = trainingService.readTraining(id).get();
            training.setPhoto(file.getOriginalFilename());
            Files.write(Paths.get(System.getProperty("user.home")+"/trainings/images/" + training.getPhoto()),file.getBytes());
            trainingService.saveTraining(training);
        }
        catch(Exception e) {
            log.error("pb avec upload de l'image correspondant à la formation d'id : {}",id);
            return ResponseEntity.internalServerError().body(e.getCause());
        }
        log.info("file upload ok {}",id);
        return ResponseEntity.ok().build();
    }
}
