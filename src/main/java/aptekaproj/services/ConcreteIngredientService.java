package aptekaproj.services;

import aptekaproj.controllers.repository.IConcreteIngredientsRepository;
import aptekaproj.helpers.DateWorker;
import aptekaproj.helpers.TimeIgnoringComparator;
import aptekaproj.models.ConcreteDrug;
import aptekaproj.models.ConcreteIngredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 12.06.2015.
 */
@Service
public class ConcreteIngredientService {

    @Autowired
    private IConcreteIngredientsRepository concreteIngredientsRepository;

    @Autowired
    private ConcreteDrugService concreteDrugService;

    //todo - the method must be tested
    //without refactoring - for debugging
    public List<ConcreteIngredient> GetConcreteIngredientByMaxAvailableDate(int ingredientId){
        List<ConcreteIngredient> concreteIngredients = GetAllConcreteIngredients();
        List<ConcreteIngredient> concreteIngredientById = new ArrayList<>();
        Date maxDate = new Date(0);
        Date currentDate = new Date();
        Date dateForGroup = new Date();
        for (ConcreteIngredient concreteIng : concreteIngredients){
            if(concreteIng.getIngredientId() == ingredientId){
                if(TimeIgnoringComparator.compare(concreteIng.getAvailabilityDate(),maxDate) >= 0){
                    maxDate = concreteIng.getAvailabilityDate();
                }
                concreteIngredientById.add(concreteIng);
            }
        }

        dateForGroup = DateWorker.MaxDate(maxDate, currentDate);
        List<ConcreteIngredient> concreteIngredientByDate = new ArrayList<>();
        for(ConcreteIngredient concreteIngredient1 : concreteIngredientById){
            if(TimeIgnoringComparator.compare(concreteIngredient1.getAvailabilityDate(),dateForGroup) == 0){
                concreteIngredientByDate.add(concreteIngredient1);
            }
        }
        return concreteIngredientByDate;
    }

    public List<ConcreteIngredient> GetAllConcreteIngredients(){
        return (List<ConcreteIngredient>)concreteIngredientsRepository.findAll();
    }

    public void Save(ConcreteIngredient concreteIngredient) {
        concreteIngredientsRepository.save(concreteIngredient);
    }

    public List<Date> GetConcreteIngredientDateByConcreteDrugsId(List<ConcreteDrug> concreteDrugs) {
        List<ConcreteIngredient> concreteIngredients = GetAllConcreteIngredients();
        List<Date> concreteIngredientsById = new ArrayList<>();

        for (ConcreteIngredient ingredient : concreteIngredients){
            for (ConcreteDrug drug : concreteDrugs){
                if(ingredient.getConcreteDrugId() == drug.getId()){
                    concreteIngredientsById.add(ingredient.getAvailabilityDate());
                }
            }
        }

        return concreteIngredientsById;
    }

    public String GetConcreteIngredientAvailableDate(int concreteDrugId, int drugId) {
        List<ConcreteIngredient> concreteIngredientList = GetAllConcreteIngredients();
        List<Date> availableDate = new ArrayList<>();
        for (ConcreteIngredient ingredient : concreteIngredientList){
            if(ingredient.getConcreteDrugId() == concreteDrugId){
                availableDate.add(ingredient.getAvailabilityDate());
            }
        }

        return DateWorker.MaxDate(availableDate);
    }
}
