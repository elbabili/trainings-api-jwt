package fr.fms.api.trainings.dao;

import fr.fms.api.trainings.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    public List<Training> findByCategoryId(Long categoryId);
}
