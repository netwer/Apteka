package aptekaproj.services;

import aptekaproj.ViewModels.DrugsWithPharmacists;
import aptekaproj.controllers.repository.IConcreteDrugsRepository;
import aptekaproj.helpers.DateWorker;
import aptekaproj.models.ConcreteDrugs;
import aptekaproj.models.ConcreteIngredients;
import aptekaproj.models.Ingredients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //todo - the method must be tested
    //without refactoring - for debugging
    public void DrugsToProduce(List<DrugsWithPharmacists> drugsWithPharmacist) {
        for (DrugsWithPharmacists drug : drugsWithPharmacist){

            ConcreteDrugs concreteDrug = new ConcreteDrugs();
            concreteDrug.setDrugId(drug.DrugId);
            concreteDrug.setPharmacyStaffId(drug.PharmacyStaffId);
            concreteDrugsRepository.save(concreteDrug);

            List<Ingredients> ingredientsForDrug = ingredientsService.GetIngredientsForDrug(drug.DrugId);
            for (Ingredients ingredient : ingredientsForDrug){
                List<ConcreteIngredients> concreteIngredientsList = concreteIngredientsService.GetConcreteIngredientByMaxAvailableDate(ingredient.getId());
                int countRecordsByMaxDate = concreteIngredientsList.size();

                ConcreteIngredients concreteIngredient = new ConcreteIngredients();
                concreteIngredient.setConcreteDrugId(drug.DrugId);
                concreteIngredient.setIngredientId(ingredient.getId());

                if(countRecordsByMaxDate < ingredient.getCount()){
                    if(countRecordsByMaxDate == 0){
                        concreteIngredient.setAvailabilityDate(new Date());
                    }
                    else {
                        concreteIngredient.setAvailabilityDate(concreteIngredientsList.get(0).getAvailabilityDate());
                    }
                }
                else {
                    concreteIngredient.setAvailabilityDate(DateWorker.AddDaysToDate(concreteIngredientsList.get(0).getAvailabilityDate(), 1));
                }

                concreteIngredientsService.Save(concreteIngredient);
            }
        }
    }
}
