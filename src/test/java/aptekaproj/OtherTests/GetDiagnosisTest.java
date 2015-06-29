package aptekaproj.OtherTests;

import aptekaproj.AptekaApplication;
import aptekaproj.models.Diagnoses;
import aptekaproj.services.DiagnosesService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by org.apteka on 27.06.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AptekaApplication.class)
public class GetDiagnosisTest {
    @Autowired
    private DiagnosesService diagnosesService;

    private int diagnosisId;

    @Before
    public void setUp() throws Exception {

        diagnosisId = 21;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetDiagnosisById() throws Exception {
        Diagnoses diagnoses = new Diagnoses();
        Diagnoses diagnoses1 = diagnosesService.getDiagnosesById(diagnosisId);


        assertNotNull(diagnoses1);
        assertNotEquals(diagnoses,diagnoses1);
        assertEquals(diagnoses1.getId(),diagnosisId);
    }
}
