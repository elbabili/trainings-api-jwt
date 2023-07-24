package fr.fms.api.trainings.service;

import fr.fms.api.trainings.dao.CategoryRepository;
import fr.fms.api.trainings.dao.TrainingRepository;
import fr.fms.api.trainings.entities.Category;
import fr.fms.api.trainings.entities.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService {
    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Training saveTraining(Training training) {
        return trainingRepository.save(training);
    }

    @Override
    public Optional<Training> readTraining(Long id) {
        return trainingRepository.findById(id);
    }

    @Override
    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }

    @Override
    public List<Training> getTrainings() {
        return trainingRepository.findAll();
    }


    public List<Category> getCategories() { return categoryRepository.findAll(); }
    public List<Training> getTrainingsByCat(Long id) { return trainingRepository.findByCategoryId(id);  }
    public Category getCategory(Long id) { return categoryRepository.getById(id);}
}
