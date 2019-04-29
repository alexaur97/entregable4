
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PositionDataRepository;
import domain.Curriculum;
import domain.PositionData;

@Service
@Transactional
public class PositionDataService {

	//Managed repository -------------------
	@Autowired
	private PositionDataRepository	positionDataRepository;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private CurriculumService		curriculumService;


	//Supporting Services ------------------

	//COnstructors -------------------------
	public PositionDataService() {
		super();
	}

	//Simple CRUD methods--------------------

	public PositionData create() {
		PositionData result;

		result = new PositionData();

		return result;
	}

	public Collection<PositionData> findAll() {
		Collection<PositionData> result;

		result = this.positionDataRepository.findAll();

		return result;
	}

	public PositionData findOne(final int positionDataId) {
		PositionData result;

		result = this.positionDataRepository.findOne(positionDataId);

		return result;
	}

	public void save(final PositionData positionData, final Curriculum curriculum) {
		Assert.notNull(positionData);
		if (positionData.getEndDate() != null)
			Assert.isTrue(positionData.getStartDate().before(positionData.getEndDate()));

		if (positionData.getId() != 0) {
			final Curriculum c = this.curriculumService.findByPositionData(positionData.getId());
			Assert.isTrue(c.getCopy() == false);
		} else {
			final Collection<PositionData> ed = curriculum.getPositionData();
			ed.add(positionData);
			curriculum.setPositionData(ed);
			this.curriculumService.save(curriculum);
		}
		this.positionDataRepository.save(positionData);
	}

	public void delete(final PositionData positionData) {
		this.rookieService.findByPrincipal();
		final Curriculum c = this.curriculumService.findByPositionData(positionData.getId());
		Assert.isTrue(c.getCopy() == false);
		final Collection<PositionData> pd = c.getPositionData();
		pd.remove(positionData);
		c.setPositionData(pd);
		this.curriculumService.save(c);
		this.positionDataRepository.delete(positionData);
	}

	//Other Methods--------------------
}
