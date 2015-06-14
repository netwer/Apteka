package aptekaproj.services;

import aptekaproj.ViewModels.IngredientInDrugViewModel;
import aptekaproj.controllers.repository.IIngredientsRepository;
import aptekaproj.models.ConcreteIngredients;
import aptekaproj.models.Ingredients;
import aptekaproj.models.Materials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 12.06.2015.
 */
@Service
public class IngredientsService {

    @Autowired
    private IIngredientsRepository ingredientsRepository;

    @Autowired
    private ConcreteIngredientsService concreteIngredientsService;

    @Autowired
    private MaterialService materialService;


    public List<Ingredients> GetIngredientsForDrug(int drugId) {
        List<Ingredients> ingredients = GetAllIngredients();
        List<Ingredients> ingredientsForDrug = new ArrayList<>();
        for (Ingredients ingredient : ingredients){
            if(ingredient.getDrug_id() == drugId){
                ingredientsForDrug.add(ingredient);
            }
        }

        return ingredientsForDrug;
    }

    private List<Ingredients> GetAllIngredients() {
        return (List<Ingredients>)ingredientsRepository.findAll();
    }

    public List<IngredientInDrugViewModel> getIngredientsForDrug(int drugId, int concreteDrugId) {
        List<ConcreteIngredients> concreteIngredients = concreteIngredientsService.GetAllConcreteIngredients();
        List<IngredientInDrugViewModel> ingredientsInDrug = new ArrayList<>();

        for (ConcreteIngredients concreteIngredient : concreteIngredients){
            if(concreteIngredient.getConcreteDrugId() == concreteDrugId){
                Ingredients ingredient = getIngredientById(concreteIngredient.getIngredientId());
                Materials material = materialService.getMaterialById(ingredient.getMaterial_id());
                if (ingredient == null || material == null)
                    continue;

                IngredientInDrugViewModel ingredientInDrug = new IngredientInDrugViewModel();
                ingredientInDrug.availableDate = concreteIngredient.getAvailabilityDate();
                ingredientInDrug.count = ingredient.getCount();
                ingredientInDrug.ingredientId = ingredient.getId();
                ingredientInDrug.materialId= ingredient.getMaterial_id();
                ingredientInDrug.materialName = material.getName();

                ingredientsInDrug.add(ingredientInDrug);
            }
        }

        return ingredientsInDrug;
    }

    private Ingredients getIngredientById(int ingredientId) {
        return ingredientsRepository.findOne(ingredientId);
    }
}
