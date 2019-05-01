
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	//@Query("") 
	//Method
	@Query("select p.title, (select case when avg(1.0*(a.score)) = null then '-' else avg(1.0*(a.score)) end from Audit a where a.position.id = p.id and a.mode='FINAL'),(select case when min(1.0*(a.score)) = null then '-' else min(1.0*(a.score)) end from Audit a where a.position.id = p.id and a.mode='FINAL'),(select case when max(1.0*(a.score)) = null then '-' else max(1.0*(a.score)) end from Audit a where a.position.id = p.id and a.mode='FINAL'),(select case when stddev(1.0*(a.score)) = null then '-' else stddev(1.0*(a.score)) end from Audit a where a.position.id = p.id and a.mode='FINAL') from Position p where p.mode = 'FINAL'")
	Collection<Object> statsAuditScorePerPosition();

	@Query("select c.commercialName, (select case when avg(1.0*(a.score)) = null then '-' else avg(1.0*(a.score)) end from Audit a where a.position.company.id = c.id and a.mode='FINAL' and a.position.mode = 'Final'),(select case when min(1.0*(a.score)) = null then '-' else min(1.0*(a.score)) end from Audit a where a.position.company.id = c.id and a.mode='FINAL' and a.position.mode = 'Final'),(select case when max(1.0*(a.score)) = null then '-' else max(1.0*(a.score)) end from Audit a where a.position.company.id = c.id and a.mode='FINAL' and a.position.mode = 'Final'),(select case when stddev(1.0*(a.score)) = null then '-' else stddev(1.0*(a.score)) end from Audit a where a.position.company.id = c.id and a.mode='FINAL' and a.position.mode = 'Final') from Company c")
	Collection<Object> statsAuditScorePerCompany();

}
