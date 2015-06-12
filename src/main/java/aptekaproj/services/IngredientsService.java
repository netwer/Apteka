package aptekaproj.services;

import aptekaproj.controllers.repository.IIngredientsRepository;
import aptekaproj.models.Ingredients;
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
}
