package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;
import ru.netology.exception.NotFoundException;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    private final static int ONE_CHARACTER = 1;
    private PostController controller;

    @Override
    public void init() {
        final var context = new AnnotationConfigApplicationContext("ru.netology");
        final var repository = context.getBean(PostRepository.class);
        final var service = context.getBean(PostService.class);
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            if (method.matches("GET|POST") && path.equals("/api/posts")) {
                if (method.equals("GET")) {
                    controller.all(resp);
                } else {
                    controller.save(req.getReader(), resp);
                }
                return;
            }
            if (method.matches("GET|DELETE") && path.matches("/api/posts/\\d+")) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + ONE_CHARACTER));
                if (method.equals("GET")) {
                    controller.getById(id, resp);
                } else {
                    controller.removeById(id, resp);
                }
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            if (e instanceof NotFoundException) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            e.printStackTrace();
            e.printStackTrace(resp.getWriter());
        }
    }
}