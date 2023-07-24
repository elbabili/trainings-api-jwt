package fr.fms.api.trainings.web;

import fr.fms.api.trainings.entities.Category;
import fr.fms.api.trainings.entities.Training;
import fr.fms.api.trainings.service.TrainingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
//@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private TrainingServiceImpl implTrainingService;

    @GetMapping("/categories")
    public List<Category> allCategories(){
        return  implTrainingService.getCategories();
    }

    @GetMapping("/categories/{id}/trainings")
    public List<Training> allTrainingsByCatId(@PathVariable("id") Long id){
        return  implTrainingService.getTrainingsByCat(id);
    }
}
