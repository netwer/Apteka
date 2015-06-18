package aptekaproj.services;

import aptekaproj.controllers.repository.IPharmaciesRepository;
import aptekaproj.models.Pharmacy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Admin on 28.05.2015.
 */
@Service
public class PharmacyService {

    @Autowired
    private IPharmaciesRepository pharmaciesRepository;

    public List<Pharmacy> getPharmacies() {
        return (List<Pharmacy>)pharmaciesRepository.findAll();
    }

    public Pharmacy getPharmacyById(int pharmacyId) {
        return pharmaciesRepository.findOne(pharmacyId);
    }
}
