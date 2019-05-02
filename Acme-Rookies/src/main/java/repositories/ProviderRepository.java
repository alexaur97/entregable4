
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {

	//@Query("") 
	//Method 
	@Query("select a from Provider a where a.userAccount.id=?1")
	Provider findByUserId(int id);

	@Query("select p from Provider p order by p.items.size desc")
	Collection<Provider> top5ProvidersPerItems();

	@Query("select p from Provider p where (select count(s) from Sponsorship s where s.provider.id = p.id) > (1.10*(select avg(1.0*(select count(s) from Sponsorship s where s.provider.id = pr.id)) from Provider pr)))")
	Collection<Provider> providersWithMoreThan10pcSponsorships();
}
