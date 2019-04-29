
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	//@Query("") 
	//Method 
	@Query("select avg(f.positions.size),min(f.positions.size),max(f.positions.size),stddev(f.positions.size) from Finder f")
	Collection<Double> statsResultsFinders();

	@Query("select (sum(case when f.keyword='' and f.minSalary=null and f.maxSalary=null and f.deadline=null then 1.0 else 0.0 end)/count(f)),sum(case when f.keyword='' and f.minSalary=null and f.maxSalary=null and f.deadline=null then 0.0 else 1.0 end)/(count(f)) from Finder f")
	Collection<Double> emptyVsNonEmptyFindersRatio();

	@Query("select f from Finder f where f.rookie.id = ?1")
	Finder getFinderFromRookie(int rookieId);
}
