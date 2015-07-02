package aptekaproj.TestsSuites.CreateRecordAboutAdmission;

import aptekaproj.AptekaApplication;
import aptekaproj.helpers.TimeIgnoringComparator;
import aptekaproj.models.Diagnoses;
import aptekaproj.models.Recipe;
import aptekaproj.models.RecipeHasDrugs;
import aptekaproj.services.DiagnosesService;
import aptekaproj.services.RecipeHasDrugsService;
import aptekaproj.services.RecipeService;
import aptekaproj.viewModels.DrugViewModel;
import aptekaproj.viewModels.PatientCardViewModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by org.apteka on 27.06.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AptekaApplication.class)
public class CreateRecordAboutAdmissionTest {

    @Autowired
    private DiagnosesService diagnosesService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeHasDrugsService recipeHasDrugsService;

    PatientCardViewModel patientCardViewModel = new PatientCardViewModel();
    private int diagnosisId;
    @Before
    public void setUp() throws Exception {
        patientCardViewModel.visitDate = new Date();
        patientCardViewModel.complaints = "Головная боль, судороги";
        patientCardViewModel.diagnosis = "Общее недомогание";
        patientCardViewModel.pharmacyId = 3;
        List<DrugViewModel> drugViewModels = new ArrayList<>();

        DrugViewModel drugViewModel1 = new DrugViewModel();
        drugViewModel1.drugCount = 2;
        drugViewModel1.drugId = 1;
        drugViewModels.add(drugViewModel1);

        DrugViewModel drugViewModel2 = new DrugViewModel();
        drugViewModel2.drugCount = 2;
        drugViewModel2.drugId = 2;
        drugViewModels.add(drugViewModel2);

        DrugViewModel drugViewModel3 = new DrugViewModel();
        drugViewModel3.drugCount = 4;
        drugViewModel3.drugId = 3;
        drugViewModels.add(drugViewModel3);

        DrugViewModel drugViewModel4 = new DrugViewModel();
        drugViewModel4.drugCount = 4;
        drugViewModel4.drugId = 4;
        drugViewModels.add(drugViewModel4);

        patientCardViewModel.drugs = drugViewModels;
        diagnosisId = 36;
    }

    @After
    public void tearDown() throws Exception {
        Diagnoses diagnoses = diagnosesService.getDiagnosesById(diagnosisId);
        Recipe recipe = recipeService.getRecipeById(diagnoses.getRecipeId());

        diagnoses.setDiagnosis("");
        diagnoses.setComplaints("");
        diagnoses.setRecipeId(null);
        diagnosesService.updateDiagnosis(diagnoses);
        recipeService.deleteRecipe(recipe);
    }

    @Test
    public void testSaveAppointment() throws Exception {
        Diagnoses diagnosesBefore = diagnosesService.getDiagnosesById(diagnosisId);

        diagnosesService.saveAppointment(patientCardViewModel, diagnosisId);

        Diagnoses diagnosesAfter = diagnosesService.getDiagnosesById(diagnosisId);
        Recipe recipe = recipeService.getRecipeById(diagnosesAfter.getRecipeId());
        List<RecipeHasDrugs> recipeHasDrugsList = recipeHasDrugsService.getRecipesHasDrugsByRecipeId(recipe.getId());

        assertNotNull(diagnosesBefore);
        assertNull(diagnosesBefore.getRecipeId());
        assertEquals(diagnosesBefore.getComplaints(), "");
        assertEquals(diagnosesBefore.getDiagnosis(),"");

        assertNotNull(diagnosesAfter);
        assertNotNull(diagnosesAfter.getRecipeId());
        assertEquals(diagnosesAfter.getComplaints(), patientCardViewModel.complaints);
        assertEquals(diagnosesAfter.getDiagnosis(),patientCardViewModel.diagnosis);

        assertNotNull(recipe);
        assertNotNull(recipe.getCreatedAt());
        assertEquals((int)diagnosesAfter.getRecipeId(),recipe.getId());
        assertEquals(TimeIgnoringComparator.compare(diagnosesAfter.getCreatedAt(),recipe.getCreatedAt()),0);

        assertNotNull(recipeHasDrugsList);
        for (RecipeHasDrugs recipeHasDrugs : recipeHasDrugsList){
            assertEquals(recipe.getId(),recipeHasDrugs.getRecipeId());
        }
    }
}
