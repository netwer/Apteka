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
    private DrugService drugService;

    @Autowired
    private RecipeHasDrugsService recipeHasDrugsService;

    //todo - the method must be tested
    //without refactoring - for debugging
    public void DrugsToProduce(RecipeDrugWithPharmacistViewModel recipeDrugWithPharmacistViewModel) {
        for (DrugWithPharmacistViewModel drug : recipeDrugWithPharmacistViewModel.drugsWithPharmacist){

            ConcreteDrug concreteDrug = new ConcreteDrug();
            concreteDrug.setDrugId(drug.drugId);
            concreteDrug.setPharmacyStaffId(drug.pharmacyStaffId);
            concreteDrug.setRecipeId(recipeDrugWithPharmacistViewModel.recipeId);
            ConcreteDrug createdConcreteDrug = concreteDrugsRepository.save(concreteDrug);

            //====
            RecipeHasDrugs recipeHasDrugs = recipeHasDrugsService.getRecipeHasDrugsByRecipeAndDrugIds(recipeDrugWithPharmacistViewModel.recipeId, drug.drugId);
            //====
            List<Ingredient> ingredientForDrug = ingredientService.GetIngredientsForDrug(drug.drugId);
            for (Ingredient ingredient : ingredientForDrug){
                List<ConcreteIngredient> concreteIngredientList = concreteIngredientsService.GetConcreteIngredientByMaxAvailableDate(ingredient.getId());
                int countRecordsByMaxDate = concreteIngredientList.size();
                //=====

                if(countRecordsByMaxDate == 0){
                    for(int i = 0;i<ingredient.getCount();i++){
                        ConcreteIngredient concreteIngredient = new ConcreteIngredient();
                        concreteIngredient.setConcreteDrugId(createdConcreteDrug.getId());
                        concreteIngredient.setIngredientId(ingredient.getId());
                        concreteIngredient.setAvailabilityDate(new Date());
                        concreteIngredientsService.Save(concreteIngredient);
                    }
                }
                else if(countRecordsByMaxDate * recipeHasDrugs.getCount() < ingredient.getCount()){
                    int availablePositionForDrug = ingredient.getCount() - countRecordsByMaxDate;
                    if(recipeHasDrugs.getCount() < availablePositionForDrug){
                        for(int i = 0;i<countRecordsByMaxDate * recipeHasDrugs.getCount();i++){
                            ConcreteIngredient concreteIngredient = new ConcreteIngredient();
                            concreteIngredient.setConcreteDrugId(createdConcreteDrug.getId());
                            concreteIngredient.setIngredientId(ingredient.getId());
                            concreteIngredient.setAvailabilityDate(concreteIngredientList.get(0).getAvailabilityDate());
                            concreteIngredientsService.Save(concreteIngredient);
                        }
                    }
                    if(recipeHasDrugs.getCount() * recipeHasDrugs.getCount() >= availablePositionForDrug){
                        for(int i = 0;i<countRecordsByMaxDate * recipeHasDrugs.getCount();i++){
                            ConcreteIngredient concreteIngredient = new ConcreteIngredient();
                            concreteIngredient.setConcreteDrugId(createdConcreteDrug.getId());
                            concreteIngredient.setIngredientId(ingredient.getId());
                            concreteIngredient.setAvailabilityDate(DateWorker.AddDaysToDate(concreteIngredientList.get(0).getAvailabilityDate(), 1));
                            concreteIngredientsService.Save(concreteIngredient);
                        }
                    }
                }
                else if (countRecordsByMaxDate >= ingredient.getCount()){
                    for(int i = 0;i<countRecordsByMaxDate * recipeHasDrugs.getCount();i++){
                        ConcreteIngredient concreteIngredient = new ConcreteIngredient();
                        concreteIngredient.setConcreteDrugId(createdConcreteDrug.getId());
                        concreteIngredient.setIngredientId(ingredient.getId());
                        concreteIngredient.setAvailabilityDate(DateWorker.AddDaysToDate(concreteIngredientList.get(0).getAvailabilityDate(), 1));
                        concreteIngredientsService.Save(concreteIngredient);
                    }
                }
                //=====

                /*ConcreteIngredient concreteIngredient = new ConcreteIngredient();
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

                concreteIngredientsService.Save(concreteIngredient);*/
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
