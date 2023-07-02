package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.model.Institution;
import pl.coderslab.charity.repository.InstitutionRepository;

import java.util.List;

@Service
public class InstitutionService {

    @Autowired
    InstitutionRepository institutionRepository;

    public InstitutionService(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    public List<Institution> getAllInstitutions(){
        return institutionRepository.findAll();
    }

    public Institution saveOrUpdateInstitution(Institution institution){
        return institutionRepository.save(institution);
    }

    public Institution getInstitutionById(Long id){
        return institutionRepository.findInstitutionById(id);
    }

    public void removeInstitutionById(Long id){
        institutionRepository.deleteById(id);
    }
}
