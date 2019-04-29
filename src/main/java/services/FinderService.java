
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Finder;
import domain.Rookie;
import domain.Position;

@Service
@Transactional
public class FinderService {

	//Managed repository -------------------
	@Autowired
	private FinderRepository				finderRepository;

	//Supporting Services ------------------
	@Autowired
	private ConfigurationParametersService	configParamsService;

	@Autowired
	private PositionService					positionService;

	@Autowired
	private RookieService					rookieService;


	//COnstructors -------------------------
	public FinderService() {
		super();
	}

	//Simple CRUD methods--------------------

	public Collection<Finder> findAll() {
		Collection<Finder> result;

		result = this.finderRepository.findAll();

		return result;
	}

	public Finder findOne(final int finderId) {
		Finder result;

		result = this.finderRepository.findOne(finderId);

		return result;
	}

	public void delete(final Finder finder) {
		this.finderRepository.delete(finder);
	}

	//Other Methods--------------------

	public Collection<Double> statsResultsFinders() {
		final Collection<Double> result = this.finderRepository.statsResultsFinders();
		Assert.notEmpty(result);
		return result;
	}

	public Collection<Double> emptyVsNonEmptyFindersRatio() {
		final Collection<Double> result = this.finderRepository.emptyVsNonEmptyFindersRatio();
		Assert.notEmpty(result);
		return result;
	}

	public Finder getFinderFromRookie(final int id) {
		return this.finderRepository.getFinderFromRookie(id);
	}

	public void createFinder(final Rookie rookieCreated) {
		final Finder finder = new Finder();
		final Collection<Position> positions = new ArrayList<Position>();
		finder.setKeyword("");
		finder.setRookie(rookieCreated);
		finder.setLastSearch(new Date());
		finder.setPositions(positions);
		this.finderRepository.save(finder);
	}

	public void save(final Finder finder) {
		Assert.notNull(finder);
		finder.setLastSearch(new Date());
		finder.setRookie(this.rookieService.findByPrincipal());
		finder.setPositions(this.positionService.searchPositions(finder.getKeyword(), finder.getMinSalary(), finder.getMaxSalary(), finder.getDeadline()));
		this.finderRepository.save(finder);
	}

	public Finder reconstruct(final Finder finder) {
		Assert.notNull(finder);
		final Finder result = this.finderRepository.findOne(finder.getId());
		result.setDeadline(finder.getDeadline());
		result.setKeyword(finder.getKeyword());
		result.setMaxSalary(finder.getMaxSalary());
		result.setMinSalary(finder.getMinSalary());
		return result;
	}

	public void saveAfterClean(final Finder finder) {
		Assert.notNull(finder);
		final Collection<Position> positions = new ArrayList<Position>();
		finder.setLastSearch(new Date());
		finder.setRookie(this.rookieService.findByPrincipal());
		finder.setPositions(positions);
		this.finderRepository.save(finder);
	}

	public void cleanFinder(final Finder finder) {
		Assert.notNull(finder);
		final Collection<Position> positions = new ArrayList<Position>();
		finder.setDeadline(null);
		finder.setKeyword("");
		finder.setMaxSalary(null);
		finder.setMinSalary(null);
		finder.setLastSearch(new Date());

		finder.setPositions(positions);
		this.finderRepository.save(finder);
	}

	public void cleanCacheIfNecessary() {
		final int cachedHours = this.configParamsService.find().getFinderCachedHours();
		final Collection<Finder> finders = this.finderRepository.findAll();
		final Date now = new Date();
		for (final Finder f : finders)
			if ((now.getTime() - f.getLastSearch().getTime()) / 3600000 >= cachedHours)
				this.cleanFinder(f);

	}
}
