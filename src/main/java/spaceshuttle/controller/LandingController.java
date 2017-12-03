package spaceshuttle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import spaceshuttle.repository.CourseRepository;

import java.util.Map;

@Controller
public class LandingController {

    @Autowired
    private CourseRepository courseRepository;

    @RequestMapping("/")
    public String landing(Map<String, Object> model) {
        model.put("message", "Welcome");
        model.put("courses", courseRepository.findAll());
        return "index";
    }
}