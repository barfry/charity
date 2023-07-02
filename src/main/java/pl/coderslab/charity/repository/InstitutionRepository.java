package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.model.Institution;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    Institution findInstitutionById(Long id);
}
