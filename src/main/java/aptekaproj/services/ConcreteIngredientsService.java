package aptekaproj.services;

import aptekaproj.controllers.repository.IConcreteIngredientsRepository;
import aptekaproj.helpers.DateWorker;
import aptekaproj.helpers.TimeIgnoringComparator;
import aptekaproj.models.ConcreteDrugs;
import aptekaproj.models.ConcreteIngredients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 12.06.2015.
 */
@Service
public class ConcreteIngredientsService {

    @Autowired
    private IConcreteIngredientsRepository concreteIngredientsRepository;

    @Autowired
    private ConcreteDrugsService concreteDrugsService;

    //todo - the method must be tested
    //without refactoring - for debugging
    public List<ConcreteIngredients> GetConcreteIngredientByMaxAvailableDate(int ingredientId){
        List<ConcreteIngredients> concreteIngredients = GetAllConcreteIngredients();
        List<ConcreteIngredients> concreteIngredientsById = new ArrayList<>();
        Date maxDate = new Date(0);
        Date currentDate = new Date();
        Date dateForGroup = new Date();
        for (ConcreteIngredients concreteIng : concreteIngredients){
            if(concreteIng.getIngredientId() == ingredientId){
                if(TimeIgnoringComparator.compare(concreteIng.getAvailabilityDate(),maxDate) >= 0){
                    maxDate = concreteIng.getAvailabilityDate();
                }
                concreteIngredientsById.add(concreteIng);
            }
        }

        dateForGroup = DateWorker.MaxDate(maxDate, currentDate);
        List<ConcreteIngredients> concreteIngredientsByDate = new ArrayList<>();
        for(ConcreteIngredients concreteIngredients1 : concreteIngredientsById){
            if(TimeIgnoringComparator.compare(concreteIngredients1.getAvailabilityDate(),dateForGroup) == 0){
                concreteIngredientsByDate.add(concreteIngredients1);
            }
        }
        return concreteIngredientsByDate;
    }

    public List<ConcreteIngredients> GetAllConcreteIngredients(){
        return (List<ConcreteIngredients>)concreteIngredientsRepository.findAll();
    }

    public void Save(ConcreteIngredients concreteIngredient) {
        concreteIngredientsRepository.save(concreteIngredient);
    }

    public List<Date> GetConcreteIngredientDateByConcreteDrugsId(List<ConcreteDrugs> concreteDrugs) {
        List<ConcreteIngredients> concreteIngredients = GetAllConcreteIngredients();
        List<Date> concreteIngredientsById = new ArrayList<>();

        for (ConcreteIngredients ingredient : concreteIngredients){
            for (ConcreteDrugs drug : concreteDrugs){
                if(ingredient.getConcreteDrugId() == drug.getId()){
                    concreteIngredientsById.add(ingredient.getAvailabilityDate());
                }
            }
        }

        return concreteIngredientsById;
    }

    public String GetConcreteIngredientAvailableDate(int concreteDrugId, int drugId) {
        List<ConcreteIngredients> concreteIngredientsList = GetAllConcreteIngredients();
        List<Date> availableDate = new ArrayList<>();
        for (ConcreteIngredients ingredient : concreteIngredientsList){
            if(ingredient.getConcreteDrugId() == concreteDrugId){
                availableDate.add(ingredient.getAvailabilityDate());
            }
        }

        return DateWorker.MaxDate(availableDate);
    }
}
