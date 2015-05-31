package aptekaproj.services;

import aptekaproj.controllers.repository.IDrugsRepository;
import aptekaproj.models.Drugs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Admin on 28.05.2015.
 */
@Service
public class DrugsService {

    @Autowired
    IDrugsRepository drugsRepository;

    public List<Drugs> getDrugs(){
        return (List<Drugs>)drugsRepository.findAll();
    }
}
