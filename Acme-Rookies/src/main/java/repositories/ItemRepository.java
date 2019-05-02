
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	@Query("select i from Item i where i.provider.id=?1")
	Collection<Item> getItemsByProvider(int providerId);
	//Method 

	@Query("select avg(1.0*(select count(i) from Item i where i.provider.id = p.id)),min(1.0*(select count(i) from Item i where i.provider.id = p.id)),max(1.0*(select count(i) from Item i where i.provider.id = p.id)),stddev(1.0*(select count(i) from Item i where i.provider.id = p.id)) from Provider p")
	Collection<Double> statsNumberItemsPerProvider();

}
