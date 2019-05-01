
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import domain.Auditor;
import domain.Company;
import domain.Position;

@Service
@Transactional
public class PositionService {

	//Managed repository -------------------
	@Autowired
	private PositionRepository				positionRepository;

	//Supporting Services ------------------
	@Autowired
	private ConfigurationParametersService	configParamsService;

	@Autowired
	private CompanyService					companyService;

	@Autowired
	private MessageService					messageService;

	@Autowired
	private AuditorService					auditorService;

	@Autowired
	private Validator						validator;


	//COnstructors -------------------------
	public PositionService() {
		super();
	}

	//Simple CRUD methods--------------------

	public Position create() {
		Position result;

		result = new Position();

		return result;
	}

	public Collection<Position> findAll() {
		Collection<Position> result;

		result = this.positionRepository.findAll();

		return result;
	}

	public Position findOne(final int positionId) {
		Position result;

		result = this.positionRepository.findOne(positionId);

		return result;
	}

	public Position save(final Position position) {
		Assert.notNull(position);
		final Position result = this.positionRepository.save(position);
		if (position.getMode() == "FINAL")
			this.messageService.newPositionFinder(position);
		return result;
	}
	public void delete(final Position position) {
		Assert.isTrue(position.getMode().equals("DRAFT"));
		this.positionRepository.delete(position);
	}

	public Collection<Position> findByCompany(final Integer companyId) {
		Assert.notNull(companyId);
		final Collection<Position> res = this.positionRepository.findByCompany(companyId);
		return res;
	}

	public Collection<Position> findByCompanyCancelled(final Integer companyId) {
		Assert.notNull(companyId);
		final Collection<Position> res = this.positionRepository.findByCompanyCancelled(companyId);
		return res;
	}
	//Other Methods--------------------
	public Collection<Position> searchPosition(final String keyword) {
		return this.positionRepository.searchPositionsKeyWord(keyword);

	}

	public Collection<Double> statsPositionsPerCompany() {
		final Collection<Double> result = this.positionRepository.statsPositionsPerCompany();
		Assert.notNull(result);
		return result;
	}

	public Collection<Double> statsSalaryOfferedPerPosition() {
		final Collection<Double> result = this.positionRepository.statsSalaryOfferedPerPosition();
		Assert.notNull(result);
		return result;
	}

	public Collection<Position> bestPositionsSalary() {
		final Collection<Position> result = this.positionRepository.bestPositionsSalary();
		Assert.notNull(result);
		return result;
	}

	public Collection<Position> worstPositionsSalary() {
		final Collection<Position> result = this.positionRepository.worstPositionsSalary();
		Assert.notNull(result);
		return result;
	}

	public Collection<Position> findByCompanyFinal(final Integer companyId) {
		Assert.notNull(companyId);
		final Collection<Position> res = this.positionRepository.findByCompanyFinal(companyId);
		return res;
	}

	public Collection<Position> findFinal() {
		final Collection<Position> result = this.positionRepository.findFinal();
		Assert.notNull(result);
		return result;
	}

	public Collection<Position> findFinalNotBanned() {
		final Collection<Position> result = this.positionRepository.findFinalNotBanned();
		Assert.notNull(result);
		return result;
	}

	public Collection<Position> findAllByProblem(final int id) {
		final Collection<Position> result = this.positionRepository.findAllByProblem(id);
		return result;
	}

	public Collection<Position> searchPositions(final String keyword, final Integer minSalary, final Integer maxSalary, final Date deadline) {
		Collection<Position> positionsByKeyWord = new ArrayList<>();
		Collection<Position> positionsByMinSalary = new ArrayList<>();
		Collection<Position> positionsByMaxSalary = new ArrayList<>();
		Collection<Position> positionsByDeadline = new ArrayList<>();
		Collection<Position> result = new ArrayList<>();
		if (keyword.isEmpty())
			positionsByKeyWord = this.positionRepository.findFinalNotBanned();
		else
			positionsByKeyWord = this.positionRepository.searchPositionsKeyWord(keyword);
		if (Objects.equals(null, minSalary))
			positionsByMinSalary = this.positionRepository.findFinalNotBanned();
		else
			positionsByMinSalary = this.positionRepository.searchPositionsMinSalary(minSalary);

		if (Objects.equals(null, maxSalary))
			positionsByMaxSalary = this.positionRepository.findFinalNotBanned();
		else
			positionsByMaxSalary = this.positionRepository.searchPositionsMaxSalary(maxSalary);

		if (Objects.equals(null, deadline))
			positionsByDeadline = this.positionRepository.findFinalNotBanned();
		else
			positionsByDeadline = this.positionRepository.searchPositionsDeadline(deadline);

		positionsByKeyWord.retainAll(positionsByMinSalary);
		positionsByKeyWord.retainAll(positionsByMaxSalary);
		positionsByKeyWord.retainAll(positionsByDeadline);
		if (this.configParamsService.find().getFinderMaxResults() < positionsByKeyWord.size())
			for (int i = 0; i < this.configParamsService.find().getFinderMaxResults(); i++)
				result.add((Position) positionsByKeyWord.toArray()[i]);
		else
			result = positionsByKeyWord;
		return result;

	}

	public Position reconstruct(final Position position, final BindingResult binding) {

		final Position res = position;

		if (res.getMode() != null)
			Assert.isTrue(!(position.getMode().equals("FINAL")));

		final Company c = this.companyService.findByPrincipal();

		res.setCompany(c);
		final String name = this.createName(position.getCompany().getCommercialName());
		final String num = this.creaNum();
		final String ticker = name + "-" + num;
		res.setTicker(ticker);
		res.setCancelled(false);

		if (res.getMode() == null)
			res.setMode("DRAFT");

		this.validator.validate(res, binding);
		return res;
	}

	private String createName(final String commercialName) {
		String resultado;
		final char[] res = new char[4];

		final Collection<String> elementos = new ArrayList<>();
		elementos.add("A");
		elementos.add("N");
		elementos.add("B");
		elementos.add("O");
		elementos.add("C");
		elementos.add("P");
		elementos.add("D");
		elementos.add("Q");
		elementos.add("E");
		elementos.add("R");
		elementos.add("F");
		elementos.add("S");
		elementos.add("G");
		elementos.add("T");
		elementos.add("H");
		elementos.add("U");
		elementos.add("I");
		elementos.add("V");
		elementos.add("J");
		elementos.add("W");
		elementos.add("K");
		elementos.add("X");
		elementos.add("L");
		elementos.add("Y");
		elementos.add("M");
		elementos.add("Z");

		final char[] nuevo = commercialName.toUpperCase().toCharArray();
		int a = 0;
		for (int i = 0; i <= nuevo.length; i++) {
			char letra;
			final String s = String.valueOf(nuevo[i]);
			if (elementos.contains(s)) {
				letra = commercialName.charAt(i);
				res[a] = letra;
				a++;
				if (a == 4)
					break;
			}
		}
		resultado = new String(res);
		if (resultado.length() < 4)
			for (int j = resultado.length(); j <= 4; j++)
				resultado = resultado.concat("X");
		return resultado;
	}

	public String creaNum() {
		String res = null;

		final char[] elementos = {
			'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
		};

		final char[] conjunto = new char[4];

		for (int i = 0; i < 4; i++) {

			final int el = (int) (Math.random() * 10);

			conjunto[i] = elementos[el];
		}
		res = new String(conjunto);

		return res;
	}

	public Position saveMode(final Position position) {
		Assert.isTrue(position.getProblems().size() > 1);
		final Position res = position;
		res.setMode("FINAL");
		return res;
	}

	public Position cancel(final Position position) {
		Assert.isTrue(position.getMode().equals("FINAL"));

		final Position res = position;
		res.setCancelled(true);
		return res;
	}
	public Collection<Position> findPositionsFinal() {
		final Date date = new Date();
		final Collection<Position> result = this.positionRepository.findPositionsFinal(date);
		return result;
	}

	public Collection<Position> searchPositionsForNotifications(final String keyword, final Integer minSalary, final Integer maxSalary, final Date deadline) {
		Collection<Position> positionsByKeyWord = new ArrayList<>();
		Collection<Position> positionsByMinSalary = new ArrayList<>();
		Collection<Position> positionsByMaxSalary = new ArrayList<>();
		Collection<Position> positionsByDeadline = new ArrayList<>();
		Collection<Position> result = new ArrayList<>();

		if (!keyword.isEmpty())
			positionsByKeyWord = this.positionRepository.searchPositionsKeyWord(keyword);

		if (!Objects.equals(null, minSalary))
			positionsByMinSalary = this.positionRepository.searchPositionsMinSalary(minSalary);

		if (!Objects.equals(null, maxSalary))
			positionsByMaxSalary = this.positionRepository.searchPositionsMaxSalary(maxSalary);

		if (!Objects.equals(null, deadline))
			positionsByDeadline = this.positionRepository.searchPositionsDeadline(deadline);

		final Set<Position> set = new HashSet<>();
		set.addAll(positionsByDeadline);
		set.addAll(positionsByKeyWord);
		set.addAll(positionsByMaxSalary);
		set.addAll(positionsByMinSalary);

		if (!positionsByKeyWord.isEmpty())
			set.retainAll(positionsByKeyWord);
		if (!positionsByMinSalary.isEmpty())
			set.retainAll(positionsByMinSalary);
		if (!positionsByMaxSalary.isEmpty())
			set.retainAll(positionsByMaxSalary);
		if (!positionsByDeadline.isEmpty())
			set.retainAll(positionsByDeadline);

		if (this.configParamsService.find().getFinderMaxResults() < positionsByKeyWord.size())
			for (int i = 0; i < this.configParamsService.find().getFinderMaxResults(); i++)
				result.add((Position) positionsByKeyWord.toArray()[i]);
		else
			result = positionsByKeyWord;
		return result;

	}

	public Collection<Position> findPositionsRequisitos() {
		final Collection<Position> pos = this.positionRepository.findPositionsReq();
		final Auditor auditor = this.auditorService.findByPrincipal();
		final Collection<Position> list = auditor.getPositions();
		final Collection<Position> res = new ArrayList<>();
		for (final Position p : pos)
			if (!list.contains(p))
				res.add(p);

		return res;
	}

}
