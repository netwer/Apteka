package aptekaproj.services;

import aptekaproj.controllers.repository.IConcreteDrugsRepository;
import aptekaproj.helpers.DateWorker;
import aptekaproj.models.ConcreteDrug;
import aptekaproj.models.ConcreteIngredient;
import aptekaproj.models.Ingredient;
import aptekaproj.models.RecipeHasDrugs;
import aptekaproj.viewModels.DrugWithPharmacistViewModel;
import aptekaproj.viewModels.RecipeDrugWithPharmacistViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 12.06.2015.
 */
@Service
public class ConcreteDrugService {

    @Autowired
    private IConcreteDrugsRepository concreteDrugsRepository;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private ConcreteIngredientService concreteIngredientsService;

    @Autowired
    private RecipeHasDrugsService recipeHasDrugsService;

    //todo - the method must be tested
    //without refactoring - for debugging
    public void DrugsToProduce(RecipeDrugWithPharmacistViewModel recipeDrugWithPharmacistViewModel) {
        //each drudId and pharmacyStaffId
        for (DrugWithPharmacistViewModel drug : recipeDrugWithPharmacistViewModel.drugsWithPharmacist){
            //create the ConcreteDrug record
            ConcreteDrug concreteDrug = new ConcreteDrug();
            concreteDrug.setDrugId(drug.drugId);
            concreteDrug.setPharmacyStaffId(drug.pharmacyStaffId);
            concreteDrug.setRecipeId(recipeDrugWithPharmacistViewModel.recipeId);
            ConcreteDrug createdConcreteDrug = concreteDrugsRepository.save(concreteDrug);

            //next, create the ConcreteIngredients records for each ConcreteDrug record
            //get record from RecipeHasDrugs by recipeId and drugId
            //this record need for get count drug in recipe
            RecipeHasDrugs recipeHasDrugs = recipeHasDrugsService.getRecipeHasDrugsByRecipeAndDrugIds(recipeDrugWithPharmacistViewModel.recipeId, drug.drugId);
            //get ingredients for current drug
            List<Ingredient> ingredientForDrug = ingredientService.getIngredientsForDrug(drug.drugId);

            for (Ingredient ingredient : ingredientForDrug){

                //get list of ConcreteIngredient grope by last date and get count of records by last date
                List<ConcreteIngredient> concreteIngredientList = concreteIngredientsService.getConcreteIngredientByMaxAvailableDate(ingredient.getId());
                int countRecordsByMaxDate = concreteIngredientList.size();

                //the number of days to be added to the current date, or the date the maximum
                //this variable is needed to change the date of availability of ingredient
                int dateCount = 0;
                Date date;

                //if no records by last/max date. It is mean that the ConcreteIngredient is empty or
                //added new ingredient
                //Adding entries by current day
                if(countRecordsByMaxDate == 0){
                    dateCount = 0;
                    date = new Date();

                    //add new ConcreteIngredient
                    //if the current value of the variable i is divisible by the number of ingredients,
                    //then increase the variable dateCount 1
                    for(int i = 0;i<ingredient.getCount()*recipeHasDrugs.getCount();i++){
                        if(i!=0&&i%ingredient.getCount() == 0){
                            dateCount++;
                        }
                        ConcreteIngredient concreteIngredient = new ConcreteIngredient();
                        concreteIngredient.setConcreteDrugId(createdConcreteDrug.getId());
                        concreteIngredient.setIngredientId(ingredient.getId());
                        concreteIngredient.setAvailabilityDate(DateWorker.AddDaysToDate(date, dateCount));
                        concreteIngredientsService.save(concreteIngredient);
                    }
                }
                //else if countRecordsByMaxDate is not empty
                //and amount of ingredients to be added is less
                //than a predetermined amount of ingredients
                //Adding entries by current day
                else if(countRecordsByMaxDate * recipeHasDrugs.getCount() < ingredient.getCount()){
                    dateCount = 0;
                    date = new Date();
                    for (int i = countRecordsByMaxDate; i < ingredient.getCount()*recipeHasDrugs.getCount();i++){
                        if(i!=0&&i%ingredient.getCount() == 0){
                            dateCount++;
                        }
                        ConcreteIngredient concreteIngredient = new ConcreteIngredient();
                        concreteIngredient.setConcreteDrugId(createdConcreteDrug.getId());
                        concreteIngredient.setIngredientId(ingredient.getId());
                        concreteIngredient.setAvailabilityDate(DateWorker.AddDaysToDate(date, dateCount));
                        concreteIngredientsService.save(concreteIngredient);
                    }
                }
                //amount of ingredients to be added is more
                //than a predetermined amount of ingredients
                //Adding entries by next day
                else if (countRecordsByMaxDate * recipeHasDrugs.getCount() >= ingredient.getCount()){
                    dateCount = 1;
                    for(int i = 0;i<countRecordsByMaxDate * recipeHasDrugs.getCount();i++){
                        if(i!=0&&i%ingredient.getCount() == 0){
                            dateCount++;
                        }
                        ConcreteIngredient concreteIngredient = new ConcreteIngredient();
                        concreteIngredient.setConcreteDrugId(createdConcreteDrug.getId());
                        concreteIngredient.setIngredientId(ingredient.getId());
                        concreteIngredient.setAvailabilityDate(DateWorker.AddDaysToDate(concreteIngredientList.get(0).getAvailabilityDate(), dateCount));
                        concreteIngredientsService.save(concreteIngredient);
                    }
                }
            }
        }
    }

    //todo not implemented
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
        List<Date> drugAvailabilityDate = concreteIngredientsService.getConcreteIngredientDateByConcreteDrugsId(concreteDrugs);

        return DateWorker.MaxDate(drugAvailabilityDate);
    }

    public String GetAvailabilityDrugDate(Integer recipeId,Integer drugId) {
        ConcreteDrug concreteDrug = GetConcreteDrugByRecipeIdAndDrugId(recipeId, drugId);
        return concreteIngredientsService.getConcreteIngredientAvailableDate(concreteDrug.getId(), drugId);
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
