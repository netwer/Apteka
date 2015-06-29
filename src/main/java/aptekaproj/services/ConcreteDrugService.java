package aptekaproj.services;

import aptekaproj.controllers.repository.IConcreteDrugsRepository;
import aptekaproj.helpers.DateWorker;
import aptekaproj.helpers.enums.ProgressStatusEnum;
import aptekaproj.models.*;
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

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private PharmacyStaffService pharmacyStaffService;

    @Autowired
    private DrugService drugService;

    //todo - the method must be tested
    //without refactoring - for debugging
    public boolean drugsToProduce(List<RecipeDrugWithPharmacistViewModel> recipeDrugWithPharmacistViewModel) {
        //each drudId and pharmacyStaffId
        for (RecipeDrugWithPharmacistViewModel drug : recipeDrugWithPharmacistViewModel){
            //create the ConcreteDrug record
            PharmacyStaff pharmacyStaff = pharmacyStaffService.getPharmacyStaffByUserId(drug.apothecaryId);

            if(pharmacyStaff == null)
                continue;

            ConcreteDrug concreteDrug = new ConcreteDrug();
            concreteDrug.setDrugId(drug.drugId);
            concreteDrug.setPharmacyStaffId(pharmacyStaff.getId());
            concreteDrug.setRecipeId(drug.recipeId);
            ConcreteDrug createdConcreteDrug = concreteDrugsRepository.save(concreteDrug);

            //next, create the ConcreteIngredients records for each ConcreteDrug record
            //get record from RecipeHasDrugs by recipeId and drugId
            //this record need for get count drug in recipe
            RecipeHasDrugs recipeHasDrugs = recipeHasDrugsService.getRecipeHasDrugsByRecipeAndDrugIds(drug.recipeId, drug.drugId);

            if(recipeHasDrugs == null)
                return false;

            //get ingredients for current drug
            List<Ingredient> ingredientForDrug = ingredientService.getIngredientsForDrug(drug.drugId);

            if(ingredientForDrug == null || ingredientForDrug.size() == 0)
                return false;

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
                        concreteIngredient.setAvailabilityDate(DateWorker.addDaysToDate(date, dateCount));
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
                        concreteIngredient.setAvailabilityDate(DateWorker.addDaysToDate(date, dateCount));
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
                        concreteIngredient.setAvailabilityDate(DateWorker.addDaysToDate(concreteIngredientList.get(0).getAvailabilityDate(), dateCount));
                        concreteIngredientsService.save(concreteIngredient);
                    }
                }
            }
        }
        boolean changedStatusResult = recipeService.changeStatus(recipeDrugWithPharmacistViewModel.get(0).recipeId, ProgressStatusEnum.IN_PROCESS.toString());

        if ((changedStatusResult)) return true;
        else return false;
    }

    //todo test
    public void updateDrugToProduce(RecipeDrugWithPharmacistViewModel drugWithApothecaries) {
        List<ConcreteDrug> concreteDrugsToUpdate = getAll();
        for(ConcreteDrug concreteDrug : concreteDrugsToUpdate) {
            if(concreteDrug.getDrugId() == drugWithApothecaries.drugId && concreteDrug.getRecipeId() == drugWithApothecaries.recipeId){
                PharmacyStaff pharmacyStaff = pharmacyStaffService.getPharmacyStaffByUserId(drugWithApothecaries.apothecaryId);
                if(pharmacyStaff == null)
                    continue;
                concreteDrug.setPharmacyStaffId(pharmacyStaff.getId());
                concreteDrugsRepository.save(concreteDrug);
                break;
            }
        }
    }

    public List<ConcreteDrug> getConcreteDrugsByRecipeId(int recipeId) {
        List<ConcreteDrug> concreteDrugList = getAll();
        List<ConcreteDrug> concreteDrugListById = new ArrayList<>();

        for (ConcreteDrug drug : concreteDrugList){
            if(drug.getRecipeId() == recipeId){
                concreteDrugListById.add(drug);
            }
        }

        return concreteDrugListById;
    }

    private List<ConcreteDrug> getAll() {
        return (List<ConcreteDrug>)concreteDrugsRepository.findAll();
    }

    public String getAvailabilityRecipeDate(int recipeId) {
        List<ConcreteDrug> concreteDrugs = getConcreteDrugsByRecipeId(recipeId);
        List<Date> drugAvailabilityDate = concreteIngredientsService.getConcreteIngredientDateByConcreteDrugsId(concreteDrugs);

        return DateWorker.maxDate(drugAvailabilityDate);
    }

    public String getAvailabilityDrugDate(Integer recipeId, Integer drugId) {
        ConcreteDrug concreteDrug = getConcreteDrugByRecipeIdAndDrugId(recipeId, drugId);
        return concreteIngredientsService.getConcreteIngredientAvailableDate(concreteDrug.getId(), drugId);
    }

    private ConcreteDrug getConcreteDrugByRecipeIdAndDrugId(Integer recipeId, Integer drugId) {
        List<ConcreteDrug> concreteDrugs = getAll();
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

    public ConcreteDrug getConcreteDrugByRecipeAndDrugIds(int recipeId, int drugId) {
        List<ConcreteDrug> concreteDrugs = getAllConcreteDrugs();
        ConcreteDrug concreteDrug = new ConcreteDrug();

        if(concreteDrugs == null)
            return concreteDrug;

        for(ConcreteDrug concreteDrug1 : concreteDrugs){
            if(concreteDrug1.getRecipeId() == recipeId && concreteDrug1.getDrugId() ==drugId){
                concreteDrug = concreteDrug1;
                break;
            }
        }

        return concreteDrug;
    }

    public List<ConcreteDrug> getConcreteDrugsForApothecary(int pharmacyId) {
        List<ConcreteDrug> concreteDrugs = getAllConcreteDrugs();
        List<ConcreteDrug> concreteDrugsForApothecary = new ArrayList<>();
        for (ConcreteDrug concreteDrug : concreteDrugs){
            RecipeHasDrugs recipeHasDrugs = recipeHasDrugsService.getRecipeHasDrugsByRecipeAndDrugIds(concreteDrug.getRecipeId(),concreteDrug.getDrugId());
            if(concreteDrug.getPharmacyStaffId() == pharmacyId && recipeHasDrugs.getDone() == false){
                concreteDrugsForApothecary.add(concreteDrug);
            }
        }

        return concreteDrugsForApothecary;
    }

    public void deleteConcreteDrugs(List<ConcreteDrug> concreteDrugsToProduce) {
        for (ConcreteDrug concreteDrug : concreteDrugsToProduce)
            concreteDrugsRepository.delete(concreteDrug);
    }

    public void saveConcreteDrug(ConcreteDrug concreteDrug) {
        concreteDrugsRepository.save(concreteDrug);
    }
}
