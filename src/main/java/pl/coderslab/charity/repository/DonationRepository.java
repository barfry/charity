package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.model.Donation;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Donation d SET d.collected = true, d.collectionDateTime = CURRENT_TIMESTAMP WHERE d.id = :id")
    void markDonationAsCollected(@Param("id") Long id);

    @Query("SELECT d FROM Donation d ORDER BY d.collected ASC, d.pickUpDate ASC")
    List<Donation> findAllOrderedByCollectedAndPickUpDate();

}
