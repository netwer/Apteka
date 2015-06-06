package aptekaproj.services;

import aptekaproj.ViewModels.DrugsViewModel;
import aptekaproj.controllers.repository.IDrugsRepository;
import aptekaproj.models.Drugs;
import aptekaproj.models.RecipesHasDrugs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 28.05.2015.
 */
@Service
public class DrugsService {

    @Autowired
    IDrugsRepository drugsRepository;

    @Autowired
    private RecipesHasDrugsService recipesHasDrugsService;

    public List<Drugs> getDrugs(){
        return (List<Drugs>)drugsRepository.findAll();
    }

    public List<DrugsViewModel> getDrugsForRecipe(Integer recipeId) {
        List<RecipesHasDrugs> recipesHasDrugses = recipesHasDrugsService.getAll();
        List<DrugsViewModel> drugsViewModels = new ArrayList<>();

        for (RecipesHasDrugs recipesHasDrugs : recipesHasDrugses){
            if(recipesHasDrugs.getRecipe_id() == recipeId){
                DrugsViewModel drugsViewModel = new DrugsViewModel();
                Drugs drugs = drugsRepository.findOne(recipesHasDrugs.getDrug_id());
                drugsViewModel.RecipesHasDrugsId = recipesHasDrugs.getId();
                drugsViewModel.DrugId = recipesHasDrugs.getDrug_id();
                drugsViewModel.DrugCount = recipesHasDrugs.getCount();
                drugsViewModel.DrugName = drugs.getName();
                drugsViewModels.add(drugsViewModel);
            }
        }

        return drugsViewModels;
    }
}
