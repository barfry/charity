package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.model.VerificationToken;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Boolean existsByEmail(String email);

    List<User> findByRoles_Name(String roleName);

    User findUserById(Long id);

    @Query("SELECT u FROM User u JOIN u.donations d WHERE d.id = :donationId")
    User findByDonationId(Long donationId);

    User findByVerificationToken(String token);

    @Query("SELECT v FROM VerificationToken v WHERE v.user.id = :userId")
    VerificationToken findVerificationTokenByUserId(@Param("userId") Long userId);

}
