package aptekaproj.services;

import aptekaproj.controllers.repository.IUnitMeasureRepository;
import aptekaproj.models.UnitMeasure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by org.apteka on 28.06.2015.
 */
@Service
public class UnitMeasureService {

    @Autowired
    private IUnitMeasureRepository unitMeasureRepository;

    public UnitMeasure getUnitMeasureById(Integer unitMeasureId){
        return unitMeasureRepository.findOne(unitMeasureId);
    }
}
