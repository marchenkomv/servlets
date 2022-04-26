package ru.netology.controller;

import com.google.gson.Gson;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        try {
            final var data = service.all();
            response.getWriter().print(new Gson().toJson(data));
        } catch (NotFoundException e) {
            response.setStatus(SC_NOT_FOUND);
            e.printStackTrace(response.getWriter());
            e.printStackTrace();
        }
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        try {
            final var entity = service.getById(id);
            response.getWriter().print(new Gson().toJson(entity));
        } catch (NotFoundException e) {
            response.setStatus(SC_NOT_FOUND);
            e.printStackTrace(response.getWriter());
            e.printStackTrace();
        }
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        try {
            final var post = gson.fromJson(body, Post.class);
            final var data = service.save(post);
            response.getWriter().print(gson.toJson(data));
        } catch (NotFoundException e) {
            response.setStatus(SC_NOT_FOUND);
            e.printStackTrace(response.getWriter());
            e.printStackTrace();
        }
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        try {
            final var entity = service.removeById(id);
        } catch (NotFoundException e) {
            response.setStatus(SC_NOT_FOUND);
            e.printStackTrace(response.getWriter());
            e.printStackTrace();
        }
    }
}