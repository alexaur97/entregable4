
package controllers.all;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Message;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final int id = this.actorService.findByPrincipal().getId();
			final Collection<Message> messagesRecived = this.messageService.findRecives(id);
			final Collection<Message> messagesSend = this.messageService.findSend(id);
			final Collection<Message> messagesSpam = this.messageService.findSpam(id);
			final Collection<Message> messagesDeleted = this.messageService.findDeleted(id);
			result = new ModelAndView("message/list");
			result.addObject("messagesRecived", messagesRecived);
			result.addObject("messagesSend", messagesSend);
			result.addObject("messagesSpam", messagesSpam);
			result.addObject("messagesDeleted", messagesDeleted);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int messageId) {
		ModelAndView result;
		final Message msg;

		try {
			final int id = this.actorService.findByPrincipal().getId();
			Assert.notNull(messageId);
			msg = this.messageService.findOne(messageId);
			Assert.isTrue(msg.getOwner().getId() == id);
			Boolean b = true;
			final Collection<String> aux = msg.getTags();
			final ArrayList<String> tags = new ArrayList<>();
			for (final String t : aux)
				if (!t.trim().isEmpty())
					tags.add(t);
			msg.setTags(tags);
			this.messageService.save(msg);
			if (!msg.getTags().isEmpty())
				for (final String t : msg.getTags())
					if (!t.trim().isEmpty())
						b = false;
			result = new ModelAndView("message/show");
			result.addObject("msg", msg);
			result.addObject("b", b);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int messageId) {
		ModelAndView result;
		final Message msg;

		try {
			final int id = this.actorService.findByPrincipal().getId();
			Assert.notNull(messageId);
			msg = this.messageService.findOne(messageId);
			Assert.isTrue(msg.getOwner().getId() == id);
			if (!msg.getDeleted()) {
				final String tag = "DELETED";
				final Collection<String> tags = msg.getTags();
				tags.add(tag);
				msg.setTags(tags);
				msg.setDeleted(true);
				this.messageService.save(msg);
			} else
				this.messageService.delete(msg);
			final Collection<Message> messagesRecived = this.messageService.findRecives(id);
			final Collection<Message> messagesSend = this.messageService.findSend(id);
			final Collection<Message> messagesSpam = this.messageService.findSpam(id);
			final Collection<Message> messagesDeleted = this.messageService.findDeleted(id);
			result = new ModelAndView("message/list");
			result.addObject("messagesRecived", messagesRecived);
			result.addObject("messagesSend", messagesSend);
			result.addObject("messagesSpam", messagesSpam);
			result.addObject("messagesDeleted", messagesDeleted);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Message msg;
		msg = new Message();

		try {
			this.actorService.findByPrincipal();
			msg.setId(0);
			result = new ModelAndView("message/list");
			result.addObject("msg", msg);
			result = this.createEditModelAndView(msg);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message msg) {
		return this.createEditModelAndView(msg, null);
	}
	protected ModelAndView createEditModelAndView(final Message msg, final String messageCode) {
		final ModelAndView res;
		final Collection<Actor> actors = this.actorService.findAll();
		res = new ModelAndView("message/edit");
		res.addObject("msg", msg);
		res.addObject("actors", actors);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("msg") Message msg, final BindingResult binding) {
		ModelAndView res;

		msg = this.messageService.reconstruct(msg, binding);

		if (binding.hasErrors())
			res = this.createEditModelAndView(msg);
		else
			try {
				this.messageService.save(msg);
				Message copy;
				copy = new Message();
				copy.setBody(msg.getBody());
				copy.setDeleted(msg.getDeleted());
				copy.setMoment(msg.getMoment());
				copy.setOwner(msg.getRecipient());
				copy.setRecipient(msg.getRecipient());
				copy.setSender(msg.getSender());
				copy.setSubject(msg.getSubject());
				copy.setCopy(true);
				this.messageService.isSpam(msg);
				copy.setSpam(msg.getSpam());
				copy.setTags(msg.getTags());
				this.messageService.save(copy);
				res = new ModelAndView("redirect:/message/list.do");

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(msg, "msg.commit.error");
			}
		return res;
	}
}
