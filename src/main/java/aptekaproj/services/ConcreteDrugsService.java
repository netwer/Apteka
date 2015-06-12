package aptekaproj.services;

import aptekaproj.ViewModels.DrugsWithPharmasists;
import aptekaproj.controllers.repository.IConcreteDrugsRepository;
import aptekaproj.models.ConcreteDrugs;
import aptekaproj.models.ConcreteIngredients;
import aptekaproj.models.Drugs;
import aptekaproj.models.Ingredients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 12.06.2015.
 */
@Service
public class ConcreteDrugsService {

    @Autowired
    private IConcreteDrugsRepository concreteDrugsRepository;

    @Autowired
    private IngredientsService ingredientsService;

    @Autowired
    private ConcreteIngredientsService concreteIngredientsService;

    @Autowired
    private DrugsService drugsService;

    public void DrugsToProduce(List<ConcreteDrugs> concreteDrugs) {
        for (ConcreteDrugs drug : concreteDrugs){
            //Drugs drug = drugsService.GetDrugById(drugsWithPharmacy.DrugId);
            ConcreteDrugs concreteDrug = new ConcreteDrugs();
            concreteDrug.setDrugId(drug.getDrugId());
            concreteDrug.setPharmacyStaffId(drug.getPharmacyStaffId());
            concreteDrugsRepository.save(drug);

            List<Ingredients> ingredientsForDrug = ingredientsService.GetIngredientsForDrug(drug.getId());
            for (Ingredients ingredient : ingredientsForDrug){
                List<ConcreteIngredients> concreteIngredientsList = concreteIngredientsService.GetConcreteIngredientByMaxAvailableDate(ingredient.getId());
                int countRecordsByMaxDate = concreteIngredientsList.size();

                ConcreteIngredients concreteIngredient = new ConcreteIngredients();
                concreteIngredient.setConcreteDrugId(drug.getDrugId());
                concreteIngredient.setIngredientId(ingredient.getId());

                if(countRecordsByMaxDate < ingredient.getCount()){
                    concreteIngredient.setAvailabilityDate(concreteIngredientsList.get(0).getAvailabilityDate());
                }
                else {
                    concreteIngredient.setAvailabilityDate(AddDaysToDate(concreteIngredientsList.get(0).getAvailabilityDate(),1));
                }

                concreteIngredientsService.Save(concreteIngredient);
            }
        }
    }

    private Date AddDaysToDate(Date currentDate,int countDays){
        Date date = currentDate;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, countDays);
        return c.getTime();
    }
}
