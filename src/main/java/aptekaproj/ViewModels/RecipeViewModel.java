package aptekaproj.viewModels;

import java.util.List;

/**
 * Created by Admin on 28.05.2015.
 */
public class RecipeViewModel {
    public int recipeId;
    public int pharmacyId;
    public int diagnosesId;
    public int recipeProgressStatusId;
    public String recipeTitle;
    public String patientFullName;
    public String availabilityDate;
    public List<DrugViewModel> drugs;
}
