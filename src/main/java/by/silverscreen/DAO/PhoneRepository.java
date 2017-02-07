package by.silverscreen.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by sbaranau on 2/7/2017.
 */
public interface PhoneRepository extends JpaRepository<PhoneDAO, UUID> {

}
