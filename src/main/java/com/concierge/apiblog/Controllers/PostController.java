package com.concierge.apiblog.Controllers;

import com.concierge.apiblog.Models.*;
import com.concierge.apiblog.Repositories.AuthorRepository;
import com.concierge.apiblog.Repositories.CategoryRepository;
import com.concierge.apiblog.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins={"http://localhost:3000", "https://concierge-blog.netlify.app"})
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostRepository _postRepository;

    @Autowired
    private AuthorRepository _authorRepository;

    @Autowired
    private CategoryRepository _categoryRepository;

    @RequestMapping(method=RequestMethod.GET, produces="application/json")
    public ResponseEntity<CustomListPost> ListAll(@RequestParam(required = false) Integer page, @RequestParam(required = false) Long author, @RequestParam(required = false) Long category) {
        List<Post> posts;
        int qtdByPage = 12;
        CustomListPost listAll = new CustomListPost();
        if(page != null) {
            Pageable pageable = PageRequest.of((qtdByPage*page) - qtdByPage, qtdByPage);
            if(author == null && category == null){
                posts = _postRepository.findPostsByIdIsNotNullOrderByIdDesc(pageable);
                listAll.setTotal(_postRepository.count());
            } else if(author != null && category != null) {
                Optional<Author> authorAux = _authorRepository.findById(author);
                Optional<Category> categoryAux = _categoryRepository.findById(category);
                if(categoryAux.isPresent() && authorAux.isPresent()){
                    posts = _postRepository.findPostsByCategoriesIsContainingAndAuthorIsOrderByViewsDesc(pageable, categoryAux.get(), authorAux.get());
                }else{
                    posts = new ArrayList<>();
                }
                listAll.setTotal(posts.size());
            } else if(author != null) {
                Optional<Author> authorAux = _authorRepository.findById(author);
                posts = authorAux.map(value -> _postRepository.findPostsByAuthorIsOrderByViewsDesc(pageable, value)).orElse(new ArrayList<>());
                listAll.setTotal(posts.size());
            } else {
                Optional<Category> categoryAux = _categoryRepository.findById(category);
                posts = categoryAux.map(value -> _postRepository.findPostsByCategoriesIsContainingOrderByViewsDesc(pageable, value)).orElse(new ArrayList<>());
                listAll.setTotal(posts.size());
            }
        }else {
            posts = _postRepository.findPostsByIdIsNotNullOrderByIdAsc();
            listAll.setTotal(_postRepository.count());
        }
        listAll.setPosts(posts);
        return ResponseEntity.ok(listAll);
    }

    @RequestMapping(path = {"/{id}"}, method=RequestMethod.GET,  produces="application/json")
    public ResponseEntity<Serializable> Update(@PathVariable Long id)
    {
        try {
            if(!_postRepository.existsById(id)){
                CustomException customException = new CustomException();
                customException.setStatus("error");
                customException.setMessage("Post não encontrado");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customException);
            }

            Optional<Post> postAux = _postRepository.findById(id);
            if(postAux.isPresent()){
                return ResponseEntity.status(HttpStatus.OK).body(postAux.get());
            }
            CustomException customException = new CustomException();
            customException.setStatus("error");
            customException.setMessage("Post não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customException);
        }catch(Exception exception){
            CustomException customException = new CustomException();
            customException.setStatus("error");
            customException.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customException);
        }
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
