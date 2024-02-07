package com.tryagain.tryagain.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ExampleController {
    @GetMapping("/thymleaf/example")
    public String thymleafExample(Model model){
        Person examplePerson = new Person();
        examplePerson.setId(1L);
        examplePerson.setName("포켓몬");
        examplePerson.setAge(11);
        examplePerson.setHobbies(List.of("운동","독서"));

        model.addAttribute("person",examplePerson);
        model.addAttribute("today",LocalDate.now());

        return "example";

    }
    @Getter
    @Setter
    private class Person {
        private long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }
}
