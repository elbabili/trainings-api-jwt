package fr.fms.api.trainings.service;

import fr.fms.api.trainings.entities.Training;

import java.util.List;
import java.util.Optional;

public interface TrainingService {
    public Training saveTraining(Training training);
    public Optional<Training> readTraining(Long id);
    public void deleteTraining(Long id);
    public List<Training> getTrainings();
}
