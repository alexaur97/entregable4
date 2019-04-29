
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from Application a where a.problem.id=?1")
	Collection<Application> findAllByProblem(int id);

	@Query("select avg(1.0*(select count(a) from Application a where a.rookie.id = h.id)),min(1.0*(select count(a) from Application a where a.rookie.id = h.id)),max(1.0*(select count(a) from Application a where a.rookie.id = h.id)),stddev(1.0*(select count(a) from Application a where a.rookie.id = h.id)) from Rookie h")
	Collection<Double> statsApplicationsPerRookie();

	@Query("select a from Application a where a.position.company.id=?1 order by status")
	Collection<Application> findApplicationsByCompany(int id);
	@Query("select a from Application a where a.rookie.id=?1 order by status")
	Collection<Application> findApplicationsByRookie(int id);

	@Query("select a from Application a where a.status = 'PENDING' and a.position.company.id=?1")
	Collection<Application> findApplicationsPendingByCompany(int id);

	@Query("select a from Application a where a.status = 'PENDING' and a.rookie.id=?1")
	Collection<Application> findApplicationsPendingByRookie(int id);

	@Query("select a from Application a where (a.status = 'ACCEPTED' or a.status = 'REJECTED' or a.status='SUBMITTED') and a.rookie.id=?1")
	Collection<Application> findApplicationsRookie(int id);

	@Query("select a from Application a where (a.status = 'ACCEPTED' or a.status = 'REJECTED' or a.status='SUBMITTED') and a.position.company.id=?1")
	Collection<Application> findApplicationsCompany(int id);
}
