
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	@Query("select c from Company c where c.userAccount.id=?1")
	Company findByUserId(int id);

	@Query("select c from Company c where (select count(p) from Position p where p.company.id = c.id) = (select max(1.0*(select count(p) from Position p where p.company.id = co.id)) from Company co)")
	Collection<Company> companiesHaveOfferedMorePositions();

	@Query("select c from Company c where c.banned=false")
	Collection<Company> companiesNotBanned();

	@Query("select avg(0.1*(a.score)) from Audit a where a.position.company.id=?1 and a.mode='FINAL'")
	Double score(int id);

	@Query("select c from Company c where (select max(a.score)from Audit a where a.position.company.id = c.id and a.mode='FINAL' and a.position.mode='FINAL') = (select max(au.score) from Audit au where au.mode='FINAL')")
	Collection<Company> companiesWithHighestAuditScore();
}
