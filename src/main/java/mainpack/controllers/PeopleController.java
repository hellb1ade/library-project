package mainpack.controllers;

import mainpack.dao.BookDao;
import mainpack.dao.PersonDao;
import mainpack.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String createNewPerson(@ModelAttribute("person") Person person) {
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
    public String updatePerson(@ModelAttribute("person") Person person) {
        personDao.updatePerson(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") int id) {
        personDao.deletePerson(id);
        return "redirect:/people";
    }

    @GetMapping("/new")
    public String addPerson() {
        return "people/newPerson";
    }
}
