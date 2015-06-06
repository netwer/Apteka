package aptekaproj.ViewModels;

import java.util.List;

/**
 * Created by Admin on 28.05.2015.
 */
public class RecipeViewModel {
    public String RecipeTitle;
    public int RecipeId;
    public int PharmacyId;
    public int DiagnosesId;
    public List<DrugsViewModel> drugsViewModelList;
}
