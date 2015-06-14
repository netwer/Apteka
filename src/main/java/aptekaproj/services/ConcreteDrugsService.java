package aptekaproj.services;

import aptekaproj.viewModels.DrugWithPharmacistViewModel;
import aptekaproj.viewModels.RecipeDrugWithPharmacistViewModel;
import aptekaproj.controllers.repository.IConcreteDrugsRepository;
import aptekaproj.helpers.DateWorker;
import aptekaproj.models.ConcreteDrug;
import aptekaproj.models.ConcreteIngredient;
import aptekaproj.models.Ingredient;
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
    public void DrugsToProduce(RecipeDrugWithPharmacistViewModel recipeDrugWithPharmacistViewModel) {
        for (DrugWithPharmacistViewModel drug : recipeDrugWithPharmacistViewModel.drugsWithPharmacist){

            ConcreteDrug concreteDrug = new ConcreteDrug();
            concreteDrug.setDrugId(drug.DrugId);
            concreteDrug.setPharmacyStaffId(drug.PharmacyStaffId);
            concreteDrug.setRecipeId(recipeDrugWithPharmacistViewModel.recipeId);
            ConcreteDrug createdConcreteDrug = concreteDrugsRepository.save(concreteDrug);

            List<Ingredient> ingredientForDrug = ingredientsService.GetIngredientsForDrug(drug.DrugId);
            for (Ingredient ingredient : ingredientForDrug){
                List<ConcreteIngredient> concreteIngredientList = concreteIngredientsService.GetConcreteIngredientByMaxAvailableDate(ingredient.getId());
                int countRecordsByMaxDate = concreteIngredientList.size();

                ConcreteIngredient concreteIngredient = new ConcreteIngredient();
                concreteIngredient.setConcreteDrugId(createdConcreteDrug.getId());
                concreteIngredient.setIngredientId(ingredient.getId());

                if(countRecordsByMaxDate < ingredient.getCount()){
                    if(countRecordsByMaxDate == 0){
                        concreteIngredient.setAvailabilityDate(new Date());
                    }
                    else {
                        concreteIngredient.setAvailabilityDate(concreteIngredientList.get(0).getAvailabilityDate());
                    }
                }
                else {
                    concreteIngredient.setAvailabilityDate(DateWorker.AddDaysToDate(concreteIngredientList.get(0).getAvailabilityDate(), 1));
                }

                concreteIngredientsService.Save(concreteIngredient);
            }
        }
    }

    public void UpdateDrugsToProduce(List<DrugWithPharmacistViewModel> drugWithPharmacists) {
        for (DrugWithPharmacistViewModel drugWithPharmacistViewModel1 : drugWithPharmacists){
            //ConcreteDrugs drug =

        }
    }

    public List<ConcreteDrug> GetConcreteDrugsByRecipeId(int recipeId) {
        List<ConcreteDrug> concreteDrugList = GetAll();
        List<ConcreteDrug> concreteDrugListById = new ArrayList<>();

        for (ConcreteDrug drug : concreteDrugList){
            if(drug.getRecipeId() == recipeId){
                concreteDrugListById.add(drug);
            }
        }

        return concreteDrugListById;
    }

    private List<ConcreteDrug> GetAll() {
        return (List<ConcreteDrug>)concreteDrugsRepository.findAll();
    }

    public String GetAvailabilityRecipeDate(int recipeId) {
        List<ConcreteDrug> concreteDrugs = GetConcreteDrugsByRecipeId(recipeId);
        List<Date> drugAvailabilityDate = concreteIngredientsService.GetConcreteIngredientDateByConcreteDrugsId(concreteDrugs);

        return DateWorker.MaxDate(drugAvailabilityDate);
    }

    public String GetAvailabilityDrugDate(Integer recipeId,Integer drugId) {
        ConcreteDrug concreteDrug = GetConcreteDrugByRecipeIdAndDrugId(recipeId, drugId);
        return concreteIngredientsService.GetConcreteIngredientAvailableDate(concreteDrug.getId(),drugId);
    }

    private ConcreteDrug GetConcreteDrugByRecipeIdAndDrugId(Integer recipeId, Integer drugId) {
        List<ConcreteDrug> concreteDrugs = GetAll();
        ConcreteDrug concreteDrug = new ConcreteDrug();
        for (ConcreteDrug drug : concreteDrugs){
            if(drug.getDrugId() == drugId && drug.getRecipeId() == recipeId){
                concreteDrug = drug;
                break;
            }
        }

        return concreteDrug;
    }

    public List<ConcreteDrug> getAllConcreteDrugs() {
        return (List<ConcreteDrug>)concreteDrugsRepository.findAll();
    }
}
