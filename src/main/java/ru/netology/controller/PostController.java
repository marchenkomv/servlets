package ru.netology.controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

@Controller
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws NotFoundException, IOException {
        response.setContentType(APPLICATION_JSON);
        response.getWriter().print(new Gson().toJson(service.all()));
    }

    public void getById(long id, HttpServletResponse response) throws NotFoundException, IOException {
        response.setContentType(APPLICATION_JSON);
        response.getWriter().print(new Gson().toJson(service.getById(id)));
    }

    public void save(Reader body, HttpServletResponse response) throws NotFoundException, IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var data = service.save(gson.fromJson(body, Post.class));
        response.getWriter().print(gson.toJson(data));
    }

    public void removeById(long id, HttpServletResponse response) throws NotFoundException {
        service.removeById(id);
    }
}