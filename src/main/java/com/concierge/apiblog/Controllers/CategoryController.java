package com.concierge.apiblog.Controllers;

import com.concierge.apiblog.Models.Category;
import com.concierge.apiblog.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping(path = "/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository _categoryRepository;

    @RequestMapping(method= RequestMethod.GET, produces="application/json")
    public List<Category> ListAll() {
        return _categoryRepository.findAll();
    }

    @RequestMapping(method=RequestMethod.POST, produces="application/json", consumes="application/json")
    public Serializable Create(@Valid @RequestBody Category category)
    {
        try {
            return _categoryRepository.save(category);
        }catch(Exception exception){
            return exception;
        }
    }

    @RequestMapping(path = {"/{id}"}, method=RequestMethod.PUT,  produces="application/json", consumes="application/json")
    public ResponseEntity<Category> Update(@PathVariable Long id, @Valid @RequestBody Category category)
    {
        try {
            if(!_categoryRepository.existsById(id)){
                return ResponseEntity.notFound().build();
            }

            category.setId(id);
            category = _categoryRepository.save(category);

            return ResponseEntity.ok(category);
        }catch(Exception exception){
            return ResponseEntity.notFound().build();
        }
    }

}
