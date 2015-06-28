package aptekaproj.viewModels;
import java.util.List;

public class PatientRecipeViewModel {
    public Integer recipeId;
    public Integer doctorId;
    public Integer recipeStatusId;
    public Integer patientId;
    public String recipeStatusName;
    public String doctorName;
    public String recipeTitle;
    public String recipeCreated;
    public String pharmaciesAddress;
    public String pharmaciesName;
    public String complaints;
    public String diagnosis;
    public List<DrugViewModel> drugs;
}
