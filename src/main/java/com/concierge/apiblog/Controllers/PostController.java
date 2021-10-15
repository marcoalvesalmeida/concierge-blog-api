package com.concierge.apiblog.Controllers;

import com.concierge.apiblog.Models.Post;
import com.concierge.apiblog.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostRepository _postRepository;

    @RequestMapping(method=RequestMethod.GET, produces="application/json")
    public List<Post> ListAll() {
        return _postRepository.findAll();
    }

    @RequestMapping(path = {"/latest/{qtd}/{type}", "/latest/{qtd}"}, method=RequestMethod.GET, produces="application/json")
    public ResponseEntity<List<Post>> listNPosts(@PathVariable Integer qtd, @PathVariable Optional<String> type) {
        try {
            Pageable topTwenty = PageRequest.of(0, qtd);
            List<Post> latestPosts;
            if(type.isPresent() && type.get().equalsIgnoreCase("asc")){
                latestPosts = _postRepository.findPostsByIdIsNotNullOrderByIdAsc(topTwenty);
            }else {
                latestPosts = _postRepository.findPostsByIdIsNotNullOrderByIdDesc(topTwenty);
            }
            return ResponseEntity.ok(latestPosts);
        }catch (Exception exception){
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method=RequestMethod.POST, produces="application/json", consumes="application/json")
    public Serializable Create(@Valid @RequestBody Post post)
    {
        try {
            return _postRepository.save(post);
        }catch(Exception exception){
            return exception;
        }
    }

    @RequestMapping(path = {"/{id}"}, method=RequestMethod.PUT,  produces="application/json", consumes="application/json")
    public ResponseEntity<Post> Update(@PathVariable Long id, @Valid @RequestBody Post post)
    {
        try {
            if(!_postRepository.existsById(id)){
                return ResponseEntity.notFound().build();
            }

            post.setId(id);
            post = _postRepository.save(post);

            return ResponseEntity.ok(post);
        }catch(Exception exception){
            return ResponseEntity.notFound().build();
        }
    }
}
