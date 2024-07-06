package ru.controllers;

import jakarta.validation.Valid;
import ru.models.Person;
import ru.services.BooksService;
import ru.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    PeopleService peopleService;
    BooksService booksService;

    @Autowired
    public PeopleController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping()
    public String show(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/show";
    }

    @PostMapping()
    public String createNewPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/newPerson";

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("takenBooks", booksService.getTakenBooks(id));
        return "people/showPerson";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/editPerson";
    }

    @PatchMapping("/{id}/edit")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/editPerson";

        peopleService.update(person, person.getId());
        return "redirect:/people";
    }

    @DeleteMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }

    @GetMapping("/new")
    public String addPerson(@ModelAttribute("person") Person person) {
        return "people/newPerson";
    }
}
