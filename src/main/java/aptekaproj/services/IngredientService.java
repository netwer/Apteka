package aptekaproj.services;

import aptekaproj.viewModels.IngredientInDrugViewModel;
import aptekaproj.controllers.repository.IIngredientsRepository;
import aptekaproj.models.ConcreteIngredient;
import aptekaproj.models.Ingredient;
import aptekaproj.models.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 12.06.2015.
 */
@Service
public class IngredientService {

    @Autowired
    private IIngredientsRepository ingredientsRepository;

    @Autowired
    private ConcreteIngredientService concreteIngredientsService;

    @Autowired
    private MaterialService materialService;


    public List<Ingredient> getIngredientsForDrug(int drugId) {
        List<Ingredient> ingredients = getAllIngredients();
        List<Ingredient> ingredientForDrug = new ArrayList<>();
        for (Ingredient ingredient : ingredients){
            if(ingredient.getDrugId() == drugId){
                ingredientForDrug.add(ingredient);
            }
        }

        return ingredientForDrug;
    }

    private List<Ingredient> getAllIngredients() {
        return (List<Ingredient>)ingredientsRepository.findAll();
    }

    public List<IngredientInDrugViewModel> getIngredientsForDrug(int drugId, int concreteDrugId) {
        List<ConcreteIngredient> concreteIngredients = concreteIngredientsService.getAllConcreteIngredients();
        List<IngredientInDrugViewModel> ingredientsInDrug = new ArrayList<>();

        for (ConcreteIngredient concreteIngredient : concreteIngredients){
            if(concreteIngredient.getConcreteDrugId() == concreteDrugId){
                Ingredient ingredient = getIngredientById(concreteIngredient.getIngredientId());
                Material material = materialService.getMaterialById(ingredient.getMaterialId());
                if (ingredient == null || material == null)
                    continue;

                IngredientInDrugViewModel ingredientInDrug = new IngredientInDrugViewModel();
                ingredientInDrug.availableDate = concreteIngredient.getAvailabilityDate();
                ingredientInDrug.count = ingredient.getCount();
                ingredientInDrug.ingredientId = ingredient.getId();
                ingredientInDrug.materialId= ingredient.getMaterialId();
                ingredientInDrug.materialName = material.getName();

                ingredientsInDrug.add(ingredientInDrug);
            }
        }

        return ingredientsInDrug;
    }

    private Ingredient getIngredientById(int ingredientId) {
        return ingredientsRepository.findOne(ingredientId);
    }
}
