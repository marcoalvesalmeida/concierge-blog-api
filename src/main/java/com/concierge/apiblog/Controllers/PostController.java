package com.concierge.apiblog.Controllers;

import com.concierge.apiblog.Models.Post;
import com.concierge.apiblog.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PostController {
    @Autowired
    private PostRepository _postRepository;

    @RequestMapping(value="/posts", method=RequestMethod.GET, produces="application/json")
    public List<Post> ListAll() {
        return _postRepository.findAll();
    }

    @RequestMapping(value="/posts", method=RequestMethod.POST, produces="application/json", consumes="application/json")
    public Post Create(@Valid @RequestBody Post post)
    {
        return _postRepository.save(post);
    }

}
