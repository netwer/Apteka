package aptekaproj.services;

import aptekaproj.controllers.repository.IMaterialsRepository;
import aptekaproj.models.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Admin on 14.06.2015.
 */
@Service
public class MaterialService {

    @Autowired
    private IMaterialsRepository materialsRepository;


    public Material getMaterialById(int materialId) {
        return materialsRepository.findOne(materialId);
    }
}
