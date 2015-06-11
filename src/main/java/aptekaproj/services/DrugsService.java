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

    public List<Drugs> GetDrugs(){
        return (List<Drugs>)drugsRepository.findAll();
    }

    public List<DrugsViewModel> GetDrugsForRecipe(Integer recipeId) {
        List<RecipesHasDrugs> recipesHasDrugses = recipesHasDrugsService.GetAllRecipesHasDrugs();
        List<DrugsViewModel> drugsViewModels = new ArrayList<>();

        for (RecipesHasDrugs recipesHasDrugs : recipesHasDrugses){
            if(recipesHasDrugs.getRecipe_id() == recipeId){
                DrugsViewModel drugsViewModel = new DrugsViewModel();
                Drugs drug = drugsRepository.findOne(recipesHasDrugs.getDrug_id());

                if(drug == null)
                    continue;

                drugsViewModel.RecipesHasDrugsId = recipesHasDrugs.getId();
                drugsViewModel.DrugId = recipesHasDrugs.getDrug_id();
                drugsViewModel.DrugCount = recipesHasDrugs.getCount();
                drugsViewModel.DrugName = drug.getName();
                drugsViewModel.NeedsToProduce = drug.getNeedsToProduce();

                drugsViewModels.add(drugsViewModel);
            }
        }

        return drugsViewModels;
    }
}
