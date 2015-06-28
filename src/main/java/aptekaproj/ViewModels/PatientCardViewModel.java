package aptekaproj.viewModels;

import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 24.05.2015.
 */
public class PatientCardViewModel {
    public Integer patientId;
    public Integer diagnosisId;
    public Integer doctorId;
    public Integer recipeId;
    public Integer pharmacyId;
    public String patientFullName;
    public String patientAddress;
    public String patientPoliceNumber;
    public String patientEmail;
    public Date visitDate;
    public String complaints;
    public String diagnosis;
    //public String apothecaryName;
    public List<DrugViewModel> drugsInRecipe;
    //public RecipeViewModel recipeViewModel;
}