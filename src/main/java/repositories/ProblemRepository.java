
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

	@Query("select p from Problem p where p.company.id=?1")
	Collection<Problem> findAllByPrincipalId(int companyId);

	@Query("select p.problems from Position p where p.id=?1 and p.mode='FINAL'")
	Collection<Problem> findProblemsByPosition(int positionId);

	//@Query("")
	//Method
	@Query("select p from Problem p where p.company.id=?1 and p.mode='FINAL'")
	Collection<Problem> findAllByPrincipalIdFinal(Integer companyId);

}
