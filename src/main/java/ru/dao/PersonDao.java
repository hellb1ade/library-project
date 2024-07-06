package ru.dao;

import ru.mappers.PersonMapper;
import ru.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDao {

    private final JdbcTemplate template;

    @Autowired
    public PersonDao(JdbcTemplate template) {
        this.template = template;
    }

    public List<Person> getPeople() {
        return template.query("select * from Person", new PersonMapper());
    }

    public Person getPerson(int id) {
        return template.query("select * from Person where person_id = ?", new Object[]{id}, new PersonMapper()).stream().findAny().orElse(null);
    }

    public void savePerson(Person person) {
        template.update("insert into Person (person_name, year_of_birth) values (?, ?)", person.getName(), person.getYearOfBirth());
    }

    public void updatePerson(Person person) {
        template.update("update Person set person_name = ?, year_of_birth = ? where person_id = ?", person.getName(), person.getYearOfBirth(), person.getId());
    }

    public void deletePerson(int id) {
        template.update("delete from Person where person_id = ?", id);
    }
}
