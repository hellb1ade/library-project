package mainpack.controllers;

import jakarta.validation.Valid;
import mainpack.dao.BookDao;
import mainpack.dao.PersonDao;
import mainpack.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@ComponentScan("dao")
@RequestMapping("/people")
public class PeopleController {

    PersonDao personDao;
    BookDao bookDao;

    @Autowired
    public PeopleController(PersonDao personDao, BookDao bookDao) {
        this.personDao = personDao;
        this.bookDao = bookDao;
    }

    @GetMapping()
    public String show(Model model) {
        model.addAttribute("people", personDao.getPeople());
        return "people/show";
    }

    @PostMapping()
    public String createNewPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/newPerson";

        personDao.savePerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDao.getPerson(id));
        model.addAttribute("takenBooks", bookDao.getTakenBooks(id));
        return "people/showPerson";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDao.getPerson(id));
        return "people/editPerson";
    }

    @PatchMapping("/{id}/edit")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/editPerson";

        personDao.updatePerson(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") int id) {
        personDao.deletePerson(id);
        return "redirect:/people";
    }

    @GetMapping("/new")
    public String addPerson(@ModelAttribute("person") Person person) {
        return "people/newPerson";
    }
}
