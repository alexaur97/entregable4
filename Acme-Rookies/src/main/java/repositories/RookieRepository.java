
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rookie;

@Repository
public interface RookieRepository extends JpaRepository<Rookie, Integer> {

	@Query("select h from Rookie h where h.userAccount.id=?1")
	Rookie findByUserId(int id);

	@Query("select h from Rookie h where (select count(a) from Application a where a.rookie.id = h.id) = (select max(1.0*(select count(a) from Application a where a.rookie.id = ha.id)) from Rookie ha)")
	Collection<Rookie> rookiesHaveMadeMoreApplications();

	@Query("select avg(1.0*h.curriculums.size),min(1.0*h.curriculums.size),max(1.0*h.curriculums.size),stddev(1.0*h.curriculums.size) from Rookie h")
	Collection<Double> statsCurriculaPerRookie();
}
