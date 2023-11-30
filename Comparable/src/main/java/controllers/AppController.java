package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import repositories.AgenceRepository;
import repositories.OffreRepository;

@Controller
public class AppController {
    @Autowired
    private OffreRepository Orepository;
    @Autowired
    private AgenceRepository Arepository;




}
