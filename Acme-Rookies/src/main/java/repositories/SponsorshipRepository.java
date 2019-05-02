
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

	@Query("select s from Sponsorship s where s.provider.id = ?1")
	Collection<Sponsorship> findSponshorshipsByProvider(int providerId);

	@Query("select avg(1.0*(select count(s) from Sponsorship s where s.provider.id = p.id)), min(1.0*(select count(s) from Sponsorship s where s.provider.id = p.id)), max(1.0*(select count(s) from Sponsorship s where s.provider.id = p.id)),stddev(1.0*(select count(s) from Sponsorship s where s.provider.id = p.id)) from Provider p")
	Collection<Double> statsSponsorshipsByProvider();

	@Query("select avg(1.0*(select count(s) from Sponsorship s where s.position.id = p.id)), min(1.0*(select count(s) from Sponsorship s where s.position.id = p.id)), max(1.0*(select count(s) from Sponsorship s where s.position.id = p.id)),stddev(1.0*(select count(s) from Sponsorship s where s.position.id = p.id)) from Position p")
	Collection<Double> statsSponsorshipsByPosition();

}
