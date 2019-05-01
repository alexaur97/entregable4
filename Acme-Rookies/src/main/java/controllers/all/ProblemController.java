
package controllers.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProblemService;
import controllers.AbstractController;
import domain.Problem;

@Controller
@RequestMapping("/problem")
public class ProblemController extends AbstractController {

	@Autowired
	private ProblemService	problemService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int problemId) {
		ModelAndView result;
		try {
			final Problem problem = this.problemService.findOne(problemId);
			final Boolean b = problem.getAttachments().isEmpty();
			result = new ModelAndView("problem/display");
			result.addObject("problem", problem);
			result.addObject("b", b);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
}
