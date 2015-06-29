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

    private int diagnosisId1;
    private int diagnosisId2;
    private int diagnosisId3;

    @Before
    public void setUp() throws Exception {

        diagnosisId1 = 36;
        diagnosisId2 = 37;
        diagnosisId3 = 38;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetDiagnosisById() throws Exception {
        Diagnoses diagnoses = new Diagnoses();
        Diagnoses diagnoses1 = diagnosesService.getDiagnosesById(diagnosisId1);
        Diagnoses diagnoses2 = diagnosesService.getDiagnosesById(diagnosisId2);
        Diagnoses diagnoses3 = diagnosesService.getDiagnosesById(diagnosisId3);


        assertNotNull(diagnoses1);
        assertNotEquals(diagnoses,diagnoses1);
        assertEquals(diagnoses1.getId(), diagnosisId1);

        assertNotNull(diagnoses2);
        assertNotEquals(diagnoses,diagnoses2);
        assertEquals(diagnoses2.getId(), diagnosisId2);

        assertNotNull(diagnoses3);
        assertNotEquals(diagnoses,diagnoses3);
        assertEquals(diagnoses3.getId(), diagnosisId3);

        assertNotEquals(diagnoses1,diagnoses2);
        assertNotEquals(diagnoses1,diagnoses3);
        assertNotEquals(diagnoses2,diagnoses3);
    }
}
