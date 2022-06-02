package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id).orElseThrow(() -> new NotFoundException("Post with id = " + id + " not found"));
    }

    public Post save(Post post) {
        return repository.save(post).orElseThrow(() -> new NotFoundException("Post with id = " + post.getId() + " not found"));
    }

    public Post removeById(long id) {
        return repository.removeById(id).orElseThrow(() -> new NotFoundException("Post with id = " + id + " not found"));
    }
}