package com.concierge.apiblog.Controllers;

import com.concierge.apiblog.Models.Author;
import com.concierge.apiblog.Models.CustomException;
import com.concierge.apiblog.Models.Post;
import com.concierge.apiblog.Repositories.AuthorRepository;
import com.concierge.apiblog.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins={"http://localhost:3000", "https://concierge-blog.netlify.app"})
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostRepository _postRepository;

    @RequestMapping(method=RequestMethod.GET, produces="application/json")
    public ResponseEntity<List<Post>> ListAll(@RequestParam(required = false) Integer page) {
        List<Post> posts;
        int qtdByPage = 12;
        if(page != null) {
            Pageable pageable = PageRequest.of((qtdByPage*page) - qtdByPage, qtdByPage);
            posts = _postRepository.findPostsByIdIsNotNullOrderByIdDesc(pageable);
        }else{
            posts = _postRepository.findPostsByIdIsNotNullOrderByIdAsc();
        }
        return ResponseEntity.ok(posts);
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

    @RequestMapping(path = {"/views/{qtd}/{type}", "/views/{qtd}"}, method=RequestMethod.GET, produces="application/json")
    public ResponseEntity<List<Post>> listViewsNPosts(@PathVariable Integer qtd, @PathVariable Optional<String> type) {
        try {
            Pageable topViews = PageRequest.of(0, qtd);
            List<Post> posts;
            if(type.isPresent() && type.get().equalsIgnoreCase("asc")){
                posts = _postRepository.findPostsByIdIsNotNullOrderByViewsAsc(topViews);
            }else {
                posts = _postRepository.findPostsByIdIsNotNullOrderByViewsDesc(topViews);
            }
            return ResponseEntity.ok(posts);
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
    public ResponseEntity<Serializable> Update(@PathVariable Long id, @Valid @RequestBody Post post)
    {
        try {
            if(!_postRepository.existsById(id)){
                CustomException customException = new CustomException();
                customException.setStatus("error");
                customException.setMessage("Post não encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customException);
            }

            post.setId(id);
            post = _postRepository.save(post);

            return ResponseEntity.status(HttpStatus.OK).body(post);
        }catch(Exception exception){
            CustomException customException = new CustomException();
            customException.setStatus("error");
            customException.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customException);
        }
    }

    @RequestMapping(path = {"/{id}/views"}, method=RequestMethod.PUT,  produces="application/json", consumes="application/json")
    public ResponseEntity<Serializable> addView(@PathVariable Long id)
    {
        try {
            Optional<Post> optionalPost = _postRepository.findById(id);
            if(!optionalPost.isPresent()){
                CustomException customException = new CustomException();
                customException.setStatus("error");
                customException.setMessage("Post não encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customException);
            }

            Post post = optionalPost.get();
            post.setId(id);
            post.setViews();
            post = _postRepository.save(post);

            return ResponseEntity.status(HttpStatus.OK).body(post);
        }catch(Exception exception){
            CustomException customException = new CustomException();
            customException.setStatus("error");
            customException.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customException);
        }
    }
}
