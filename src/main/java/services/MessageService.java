
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import domain.Actor;
import domain.Administrator;
import domain.Finder;
import domain.Rookie;
import domain.Message;
import domain.Position;
import domain.SpamWord;

@Service
@Transactional
public class MessageService {

	//Managed repository -------------------
	@Autowired
	private MessageRepository		messageRepository;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private SpamWordService			spamWordService;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private Validator				validator;


	//Supporting Services ------------------

	//COnstructors -------------------------
	public MessageService() {
		super();
	}

	//Simple CRUD methods--------------------

	public Message create() {
		Message result;

		result = new Message();

		return result;
	}

	public Collection<Message> findAll() {
		Collection<Message> result;

		result = this.messageRepository.findAll();

		return result;
	}

	public Message findOne(final int messageId) {
		Message result;

		result = this.messageRepository.findOne(messageId);

		return result;
	}

	public void save(final Message message) {
		Assert.notNull(message);

		this.messageRepository.save(message);
	}

	public void delete(final Message message) {
		this.messageRepository.delete(message);
	}

	//Other Methods--------------------

	public Collection<Message> findRecives(final int id) {
		final Collection<Message> result = this.messageRepository.findRecives(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Message> findSend(final int id) {
		Collection<Message> result;
		Assert.notNull(id);
		result = this.messageRepository.findSend(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Message> findSpam(final int id) {
		Collection<Message> result;
		Assert.notNull(id);
		result = this.messageRepository.findSpam(id);
		Assert.notNull(result);
		return result;

	}

	public Collection<Message> findSender(final int id) {
		Collection<Message> result;
		Assert.notNull(id);
		result = this.messageRepository.findSender(id);
		Assert.notNull(result);
		return result;

	}

	public Collection<Message> findSenderSpam(final int id) {
		Collection<Message> result;
		Assert.notNull(id);
		result = this.messageRepository.findSenderSpam(id);
		Assert.notNull(result);
		return result;

	}

	public Collection<Message> findDeleted(final int id) {
		Collection<Message> result;
		Assert.notNull(id);
		result = this.messageRepository.findDeleted(id);
		Assert.notNull(result);
		return result;
	}

	public Message reconstruct(final Message msg, final BindingResult binding) {
		final Message res = msg;
		final Actor a = this.actorService.findByPrincipal();
		msg.setSender(a);
		msg.setOwner(a);
		final Date moment = new Date();
		msg.setMoment(moment);
		msg.setSpam(false);
		msg.setDeleted(false);
		msg.setCopy(false);
		final Collection<String> aux = msg.getTags();
		final ArrayList<String> tags = new ArrayList<>();
		for (final String t : aux)
			if (!t.trim().isEmpty())
				tags.add(t);
		msg.setTags(tags);
		this.validator.validate(res, binding);
		return res;
	}

	public Message reconstructAdmnistrator(final Message msg, final BindingResult binding) {
		final Message res = msg;
		final Administrator admin = this.administratorService.findByPrincipal();
		res.setDeleted(false);
		final Date moment = new Date();
		res.setMoment(moment);
		res.setSender(admin);
		final Collection<String> tags = new ArrayList<>();
		tags.add("SYSTEM");
		res.setTags(tags);
		this.isSpam(res);
		res.setRecipient(admin);
		res.setOwner(admin);
		res.setCopy(false);
		this.validator.validate(res, binding);

		return res;
	}
	public Message reconstructAdmnistrator2(final Message msg, final Actor actor, final BindingResult binding) {
		msg.setRecipient(actor);
		msg.setOwner(msg.getSender());
		msg.setCopy(false);
		return msg;
	}
	public Message reconstructAdmnistrator2Copy(final Message msg, final Actor actor, final BindingResult binding) {
		msg.setRecipient(actor);
		msg.setOwner(actor);
		msg.setCopy(true);
		return msg;
	}

	public List<String> spamwords(final Collection<SpamWord> sw) {

		Collection<SpamWord> spamwords = new ArrayList<>();
		spamwords = this.spamWordService.findAll();
		final List<SpamWord> list = new ArrayList<>(spamwords);
		final List<String> res = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			final String palabra = list.get(i).getWord().toUpperCase();
			res.add(palabra);
		}

		return res;

	}

	public void isSpam(final Message message) {
		Collection<SpamWord> spamwords = new ArrayList<>();
		spamwords = this.spamWordService.findAll();
		final String[] mensaje = message.getBody().trim().split(" ");
		List<String> lista = new ArrayList<>();
		lista = Arrays.asList(mensaje);

		final String[] titulo = message.getSubject().trim().split(" ");
		List<String> list = new ArrayList<>();
		list = Arrays.asList(titulo);
		Boolean isSpam = false;

		final List<String> sw = this.spamwords(spamwords);

		for (final String l : list)
			if (sw.contains(l.toUpperCase())) {

				message.setSpam(true);
				final Collection<String> tags = message.getTags();
				tags.add("SPAM");
				message.setTags(tags);
				isSpam = true;
				break;
			} else
				message.setSpam(false);

		if (isSpam == false)
			for (final String j : lista)
				if (sw.contains(j.toUpperCase())) {
					message.setSpam(true);
					final Collection<String> tags = message.getTags();
					tags.add("SPAM");
					message.setTags(tags);
					break;
				} else
					message.setSpam(false);
	}

	public void changedStatus(final Actor actor) {
		final Message message = this.create();
		message.setRecipient(actor);
		message.setOwner(actor);
		final Date moment = new Date();
		message.setMoment(moment);
		message.setCopy(true);
		message.setDeleted(false);
		message.setSender(null);
		message.setSpam(false);
		message.setSubject("System message");
		message.setBody("One of your applications has changed its status. / Una de tus aplicaciones ha cambiado su estado.");
		final Collection<String> tags = new ArrayList<>();
		tags.add("SYSTEM");
		message.setTags(tags);
		this.save(message);

	}

	public void newPositionFinder(final Position position) {
		final Collection<Rookie> allRookies = this.rookieService.findAll();
		for (final Rookie h : allRookies) {
			final Finder finder = this.finderService.getFinderFromRookie(h.getId());
			final Collection<Position> positions = this.positionService.searchPositionsForNotifications(finder.getKeyword(), finder.getMinSalary(), finder.getMaxSalary(), finder.getDeadline());
			if (positions.contains(position)) {
				final Message message = this.create();
				message.setRecipient(h);
				message.setOwner(h);
				final Date moment = new Date();
				message.setMoment(moment);
				message.setCopy(true);
				message.setDeleted(false);
				message.setSender(null);
				message.setSpam(false);
				message.setSubject("System message");
				message.setBody("Refresh your finder to find a new position that may interest you. / Refresca tu buscador para encontrar una nueva posición que te pueda interesar.");
				final Collection<String> tags = new ArrayList<>();
				tags.add("SYSTEM");
				message.setTags(tags);
				this.save(message);
			}

		}
	}
}
