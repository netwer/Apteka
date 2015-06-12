package aptekaproj.services;

import aptekaproj.controllers.repository.IConcreteIngredientsRepository;
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

    public List<ConcreteIngredients> GetConcreteIngredientByMaxAvailableDate(int ingredientId){
        List<ConcreteIngredients> concreteIngredients = GetAllConcreteIngredients();
        List<ConcreteIngredients> concreteIngredientsById = new ArrayList<>();
        Date maxDate = new Date(0);
        Date currentDate = new Date();
        Date dateForGroup = new Date();
        for (ConcreteIngredients concreteIng : concreteIngredients){
            if(concreteIng.getIngredientId() == ingredientId){
                if(concreteIng.getAvailabilityDate().compareTo(maxDate) >= 0){
                    maxDate = concreteIng.getAvailabilityDate();
                }
                concreteIngredientsById.add(concreteIng);
            }
        }

        dateForGroup = MaxDate(maxDate,currentDate);
        List<ConcreteIngredients> concreteIngredientsByDate = new ArrayList<>();
        for(ConcreteIngredients concreteIngredients1 : concreteIngredientsById){
            if(concreteIngredients1.getAvailabilityDate().compareTo(dateForGroup) == 0){
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

    private Date MaxDate(Date d1,Date d2){
        if(d1.compareTo(d2) >= 0)
            return d1;
        else
            return d2;
    }
}
