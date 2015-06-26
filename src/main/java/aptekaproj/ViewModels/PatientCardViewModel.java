package aptekaproj.viewModels;

import java.util.List;

/**
 * Created by Admin on 24.05.2015.
 */
public class PatientCardViewModel {
    public int patientId;
    public int doctorId;
    public int recipeId;
    public String patientFullName;
    public String patientAddress;
    public String patientPoliceNumber;
    public String patientEmail;
    public String visitDate;
    public String complaints;
    public String diagnosis;
    public String apothecaryName;
    public List<DrugViewModel> drugsInRecipe;
    //public RecipeViewModel recipeViewModel;
}
