package aptekaproj.services;

import aptekaproj.ViewModels.DrugsWithPharmacists;
import aptekaproj.ViewModels.RecipeDrugsWithPharmacistsViewModel;
import aptekaproj.controllers.repository.IConcreteDrugsRepository;
import aptekaproj.helpers.DateWorker;
import aptekaproj.models.ConcreteDrugs;
import aptekaproj.models.ConcreteIngredients;
import aptekaproj.models.Ingredients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public void DrugsToProduce(RecipeDrugsWithPharmacistsViewModel recipeDrugsWithPharmacistsViewModel) {
        for (DrugsWithPharmacists drug : recipeDrugsWithPharmacistsViewModel.drugsWithPharmacist){

            ConcreteDrugs concreteDrug = new ConcreteDrugs();
            concreteDrug.setDrugId(drug.DrugId);
            concreteDrug.setPharmacyStaffId(drug.PharmacyStaffId);
            concreteDrug.setRecipeId(recipeDrugsWithPharmacistsViewModel.recipeId);
            ConcreteDrugs createdConcreteDrug = concreteDrugsRepository.save(concreteDrug);

            List<Ingredients> ingredientsForDrug = ingredientsService.GetIngredientsForDrug(drug.DrugId);
            for (Ingredients ingredient : ingredientsForDrug){
                List<ConcreteIngredients> concreteIngredientsList = concreteIngredientsService.GetConcreteIngredientByMaxAvailableDate(ingredient.getId());
                int countRecordsByMaxDate = concreteIngredientsList.size();

                ConcreteIngredients concreteIngredient = new ConcreteIngredients();
                concreteIngredient.setConcreteDrugId(createdConcreteDrug.getId());
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

    public void UpdateDrugsToProduce(List<DrugsWithPharmacists> drugsWithPharmacists) {
        for (DrugsWithPharmacists drugsWithPharmacists1 : drugsWithPharmacists){
            //ConcreteDrugs drug =

        }
    }

    public List<ConcreteDrugs> GetConcreteDrugsByRecipeId(int recipeId) {
        List<ConcreteDrugs> concreteDrugsList = GetAll();
        List<ConcreteDrugs> concreteDrugsListById = new ArrayList<>();

        for (ConcreteDrugs drug : concreteDrugsList){
            if(drug.getRecipeId() == recipeId){
                concreteDrugsListById.add(drug);
            }
        }

        return concreteDrugsListById;
    }

    private List<ConcreteDrugs> GetAll() {
        return (List<ConcreteDrugs>)concreteDrugsRepository.findAll();
    }

    public String GetAvailabilityRecipeDate(int recipeId) {
        List<ConcreteDrugs> concreteDrugs = GetConcreteDrugsByRecipeId(recipeId);
        List<Date> drugAvailabilityDate = concreteIngredientsService.GetConcreteIngredientDateByConcreteDrugsId(concreteDrugs);

        return DateWorker.MaxDate(drugAvailabilityDate);
    }

    public String GetAvailabilityDrugDate(Integer recipeId,Integer drugId) {
        ConcreteDrugs concreteDrug = GetConcreteDrugByRecipeIdAndDrugId(recipeId, drugId);
        return concreteIngredientsService.GetConcreteIngredientAvailableDate(concreteDrug.getId(),drugId);
    }

    private ConcreteDrugs GetConcreteDrugByRecipeIdAndDrugId(Integer recipeId, Integer drugId) {
        List<ConcreteDrugs> concreteDrugs = GetAll();
        ConcreteDrugs concreteDrug = new ConcreteDrugs();
        for (ConcreteDrugs drug : concreteDrugs){
            if(drug.getDrugId() == drugId && drug.getRecipeId() == recipeId){
                concreteDrug = drug;
                break;
            }
        }

        return concreteDrug;
    }
}
