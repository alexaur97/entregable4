
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.MiscellaniusData;

@Repository
public interface MiscellaniusDataRepository extends JpaRepository<MiscellaniusData, Integer> {

	//@Query("") 
	//Method 

}
