package ru.netology.repository;

import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {

    private ConcurrentMap<Long, Post> repository;
    private AtomicLong counter;
    private final long COUNTER_INITIAL_VALUE = 1;

    public PostRepository() {
        repository = new ConcurrentHashMap<>();
        counter = new AtomicLong(COUNTER_INITIAL_VALUE);
    }

    public List<Post> all() {
        return new ArrayList<>(repository.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(repository.get(id));
    }

    public Optional<Post> save(Post post) {
        if (post.getId() == 0L) {
            var key = counter.getAndIncrement();
            post.setId(key);
            repository.put(key, post);
        } else {
            if (repository.containsKey(post.getId())) {
                repository.get(post.getId()).setContent(post.getContent());
            } else {
                post = null;
            }
        }
        return Optional.ofNullable(post);
    }

    public Optional<Post> removeById(long id) {
        return Optional.ofNullable(repository.remove(id));
    }
}