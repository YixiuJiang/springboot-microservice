package spaceshuttle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spaceshuttle.model.Kid;
import spaceshuttle.model.KidPK;
import spaceshuttle.repository.KidRepository;

@RestController
public class KidController {
    @Autowired
    private KidRepository kidRepository;

    @GetMapping("/kid/add")
    public String testAddKid() {
        Kid kid = new Kid();
        KidPK kidPK = new KidPK();
        kidPK.setFatherId("father1");
        kidPK.setMotherId("mother1");
        kid.setId(kidPK);
        kid.setName("just a kid");
        kidRepository.save(kid);
        return "good!";
    }
}
