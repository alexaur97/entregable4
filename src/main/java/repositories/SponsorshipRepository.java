
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	//@Query("") 
	//Method 
	@Query("select s from Sponsorship s where s.position.id = ?1 order by rand()")
	Collection<Sponsorship> findSponshorshipsByPosition(int positionId);
}
