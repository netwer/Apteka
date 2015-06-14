package aptekaproj.viewModels;

import java.util.List;

/**
 * Created by Admin on 28.05.2015.
 */
public class RecipeViewModel {
    public String RecipeTitle;
    public int RecipeId;
    public int PharmacyId;
    public int DiagnosesId;
    public String AvailabilityDate;
    public List<DrugViewModel> drugViewModelList;
}
