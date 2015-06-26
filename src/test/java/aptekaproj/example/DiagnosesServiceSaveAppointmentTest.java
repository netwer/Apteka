package aptekaproj.example;

import aptekaproj.AptekaApplication;
import aptekaproj.services.DiagnosesService;
import aptekaproj.viewModels.DrugViewModel;
import aptekaproj.viewModels.PatientCardViewModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AptekaApplication.class)
public class DiagnosesServiceSaveAppointmentTest {

    @Autowired
    private DiagnosesService diagnosesService;

    PatientCardViewModel patientCardViewModel = new PatientCardViewModel();
    @Before
    public void setUp() throws Exception {
        patientCardViewModel.patientId = 4;
        patientCardViewModel.doctorId = 1;
        patientCardViewModel.recipeId = 0;
        patientCardViewModel.patientFullName = "Иванов Петр Егорыч";
        patientCardViewModel.patientAddress = "Спб ул. Ленина д 45";
        patientCardViewModel.patientPoliceNumber = "125676";
        patientCardViewModel.patientEmail = "patient1@mail.ru";
        patientCardViewModel.visitDate = "2015-06-26";
        patientCardViewModel.complaints = "Головная боль, судороги";
        patientCardViewModel.diagnosis = "Общее недомогание";
        patientCardViewModel.apothecaryName = "Аптека 5";
        List<DrugViewModel> drugViewModels = new ArrayList<>();

        DrugViewModel drugViewModel1 = new DrugViewModel();
        drugViewModel1.drugCount = 2;
        drugViewModel1.drugName = "Синяя смесь";
        drugViewModel1.drugId = 1;
        drugViewModels.add(drugViewModel1);

        DrugViewModel drugViewModel2 = new DrugViewModel();
        drugViewModel2.drugCount = 2;
        drugViewModel2.drugName = "Красная смесь";
        drugViewModel2.drugId = 2;
        drugViewModels.add(drugViewModel2);

        DrugViewModel drugViewModel3 = new DrugViewModel();
        drugViewModel3.drugCount = 4;
        drugViewModel3.drugName = "Шипящий отвар";
        drugViewModel3.drugId = 3;
        drugViewModels.add(drugViewModel3);

        DrugViewModel drugViewModel4 = new DrugViewModel();
        drugViewModel4.drugCount = 4;
        drugViewModel4.drugName = "Газированный напиток";
        drugViewModel4.drugId = 4;
        drugViewModels.add(drugViewModel4);

        patientCardViewModel.drugsInRecipe = drugViewModels;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSaveAppointment() throws Exception {
        diagnosesService.saveAppointment(patientCardViewModel);
    }
}