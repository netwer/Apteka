package aptekaproj.services;

import aptekaproj.controllers.repository.IRecipesHasDrugsRepository;
import aptekaproj.models.RecipesHasDrugs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Admin on 28.05.2015.
 */
@Service
public class RecipesHasDrugsService {

    @Autowired
    private IRecipesHasDrugsRepository recipesHasDrugsRepository;

    public void Save(RecipesHasDrugs recipesHasDrugs) {
        recipesHasDrugsRepository.save(recipesHasDrugs);
    }
}
