
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select p from Position p where p.company.id=?1 and p.cancelled=false")
	Collection<Position> findByCompany(Integer id);

	@Query("select p from Position p where p.company.id=?1 and p.cancelled=true")
	Collection<Position> findByCompanyCancelled(Integer id);

	@Query("select avg(1.0*(select count(p) from Position p where p.company.id = c.id)),min(1.0*(select count(p) from Position p where p.company.id = c.id)),max(1.0*(select count(p) from Position p where p.company.id = c.id)),stddev(1.0*(select count(p) from Position p where p.company.id = c.id)) from Company c ")
	Collection<Double> statsPositionsPerCompany();

	@Query("select avg(1.0*p.salaryOffered),min(1.0*p.salaryOffered),max(1.0*p.salaryOffered),stddev(1.0*p.salaryOffered) from Position p")
	Collection<Double> statsSalaryOfferedPerPosition();

	@Query("select p from Position p where p.salaryOffered = (select max(1.0*(1.0*pa.salaryOffered)) from Position pa)")
	Collection<Position> bestPositionsSalary();

	@Query("select p from Position p where p.salaryOffered = (select min(1.0*(1.0*pa.salaryOffered)) from Position pa)")
	Collection<Position> worstPositionsSalary();

	@Query("select p from Position p where p.company.id=?1 and p.mode='FINAL'")
	Collection<Position> findByCompanyFinal(Integer id);

	@Query("select p from Position p where p.mode='FINAL'")
	Collection<Position> findFinal();

	@Query("select p from Position p where p.mode='FINAL' AND p.company.banned=false")
	Collection<Position> findFinalNotBanned();

	@Query("select p from Position p join p.problems pr where pr.id=?1")
	Collection<Position> findAllByProblem(Integer id);

	@Query("select p from Position p where ((p.title like %?1%)or (p.description like %?1%)or (p.profileRequired like %?1%) or (p.ticker like %?1%) or (p.techRequired like %?1%) or (p.skillRequired like %?1%)or(p.company.commercialName like  %?1%))and p.mode='FINAL' and p.cancelled is false and p.company.banned is false")
	Collection<Position> searchPositionsKeyWord(String keyword);

	@Query("select p from Position p where p.salaryOffered >= ?1 ")
	Collection<Position> searchPositionsMinSalary(Integer minSalary);

	@Query("select p from Position p where p.salaryOffered <= ?1 ")
	Collection<Position> searchPositionsMaxSalary(Integer maxSalary);

	@Query("select p from Position p where p.deadline <= ?1 ")
	Collection<Position> searchPositionsDeadline(Date deadline);
	@Query("select p from Position p where p.mode='FINAL' and p.cancelled is false and p.company.banned is false and p.deadline >= ?1")
	Collection<Position> findPositionsFinal(Date deadline);

	@Query("select avg(1.0*p.salaryOffered) from Position p where (select avg(1.0*(a.score)) from Audit a where a.position.id = p.id and a.mode='FINAL') = (select max(1.0*(select avg(1.0*(au.score)) from Audit au where au.position.id = pa.id and au.mode='FINAL')) from Position pa)")
	Double avgSalaryHighestPositions();
	@Query("select p from Position p where p.mode='FINAL' and p.cancelled is false and p.company.banned is false")
	Collection<Position> findPositionsReq();

}
