package com.concierge.apiblog.Controllers;

import com.concierge.apiblog.Models.*;
import com.concierge.apiblog.Repositories.CommentRepository;
import com.concierge.apiblog.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@RestController
@CrossOrigin(origins={"http://localhost:3000", "https://concierge-blog.netlify.app"})
@RequestMapping(path = "/comments")
public class CommentController {
    @Autowired
    private CommentRepository _commentRepository;

    @Autowired
    private PostRepository _postRepository;

    @RequestMapping(method= RequestMethod.GET, produces="application/json")
    public List<Comment> ListAll() {
        return _commentRepository.findAll();
    }

    @RequestMapping(method=RequestMethod.POST, produces="application/json", consumes="application/json")
    public Serializable Create(@Valid @RequestBody Comment comment)
    {
        try {
            return _commentRepository.save(comment);
        }catch(Exception exception){
            return exception;
        }
    }

    @RequestMapping(path = {"/{id}"}, method=RequestMethod.PUT,  produces="application/json", consumes="application/json")
    public ResponseEntity<Serializable> Update(@PathVariable Long id, @Valid @RequestBody Comment comment)
    {
        try {
            if(!_commentRepository.existsById(id)){
                CustomException customException = new CustomException();
                customException.setStatus("error");
                customException.setMessage("Comentário não encontrado.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customException);
            }
            Comment commentAux = _commentRepository.getById(id);
            if(!comment.getToken().equals(commentAux.getToken())){
                CustomException customException = new CustomException();
                customException.setStatus("error");
                customException.setMessage("Permissão negada.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(customException);
            }

            Post postAux = _postRepository.getById(comment.getPost().getId());

            comment.setId(id);
            comment.setPost(postAux);
            _commentRepository.save(comment);

            return ResponseEntity.status(HttpStatus.OK).build();
        }catch(Exception exception){
            CustomException customException = new CustomException();
            customException.setStatus("error");
            customException.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customException);
        }
    }

}
