package com.concierge.apiblog.Controllers;

import com.concierge.apiblog.Models.Category;
import com.concierge.apiblog.Models.CustomException;
import com.concierge.apiblog.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@RestController
@CrossOrigin(origins={"http://localhost:3000", "https://concierge-blog.netlify.app"})
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
    public ResponseEntity<Serializable> Update(@PathVariable Long id, @Valid @RequestBody Category category)
    {
        try {
            if(!_categoryRepository.existsById(id)){
                CustomException customException = new CustomException();
                customException.setStatus("error");
                customException.setMessage("Categoria não encontrada");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customException);
            }

            category.setId(id);
            category = _categoryRepository.save(category);

            return ResponseEntity.status(HttpStatus.OK).body(category);
        }catch(Exception exception){
            CustomException customException = new CustomException();
            customException.setStatus("error");
            customException.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customException);
        }
    }

}
