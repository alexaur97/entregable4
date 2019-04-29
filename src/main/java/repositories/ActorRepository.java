
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findByUserAccount(int id);

	@Query("select u.username from UserAccount u")
	Collection<String> findAllAccounts();

	@Query("select a.email from Actor a")
	Collection<String> findAllEmails();

	@Query("select a from Actor a where a.id != ?1")
	Collection<Actor> findOthersActor(int id);
	@Query("select a from Actor a where a.userAccount.username=?1")
	Actor findByUserName(String name);
}
