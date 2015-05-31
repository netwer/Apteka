package aptekaproj.services;

import aptekaproj.controllers.repository.IPharmaciesRepository;
import aptekaproj.models.Pharmacies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Admin on 28.05.2015.
 */
@Service
public class PharmaciesService {

    @Autowired
    private IPharmaciesRepository pharmaciesRepository;

    public List<Pharmacies> getPharmacies() {
        return (List<Pharmacies>)pharmaciesRepository.findAll();
    }

    public Pharmacies getPharmacyById(int pharmacyId) {
        return pharmaciesRepository.findOne(pharmacyId);
    }
}
