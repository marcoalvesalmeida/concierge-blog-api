package com.concierge.apiblog.Controllers;

import com.concierge.apiblog.Models.Author;
import com.concierge.apiblog.Repositories.AuthorRepository;
import com.concierge.apiblog.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping(path = "/authors")
public class AuthorController {
    @Autowired
    private AuthorRepository _authorRepository;

    @RequestMapping(method= RequestMethod.GET, produces="application/json")
    public List<Author> ListAll() {
        return _authorRepository.findAll();
    }

    @RequestMapping(method=RequestMethod.POST, produces="application/json", consumes="application/json")
    public Serializable Create(@Valid @RequestBody Author author)
    {
        try {
            return _authorRepository.save(author);
        }catch(Exception exception){
            return exception;
        }
    }

}
