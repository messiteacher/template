package com.example.template.domain.post.post.controller;

import com.example.template.domain.post.post.entity.Post;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
public class PostController {

    private List<Post> posts = new ArrayList<>();
    private long lastId = 3L;

    public PostController() {

        Post p1 = Post.builder()
                .id(1L)
                .title("title1")
                .content("content")
                .build();

        Post p2 = Post.builder()
                .id(2L)
                .title("title2")
                .content("content")
                .build();

        Post p3 = Post.builder()
                .id(3L)
                .title("title3")
                .content("content")
                .build();

        posts.add(p1);
        posts.add(p2);
        posts.add(p3);
    }

    @GetMapping("/write")
    public String showWrite(@Valid WriteForm form, BindingResult bindingResult) {
        return "domain/post/post/write";
    }

    @AllArgsConstructor
    @Getter
    public static class WriteForm {

        @NotBlank(message = "01-제목을 입력해주세요.")
        @Length(min = 5, message = "02-제목은 5글자 이상입니다.")
        private String title;

        @NotBlank(message = "03-내용을 입력해주세요.")
        @Length(min = 10, message = "04-내용은 10글자 이상입니다.")
        private String content;
    }

    @PostMapping("/write")
    public String doWrite(@Valid WriteForm form, BindingResult bindingResult,
                          Model model) {

        if (bindingResult.hasErrors()) {

//            String errorMessage = bindingResult.getFieldErrors()
//                    .stream()
//                    .map(err -> err.getDefaultMessage())
//                    .sorted()
//                    .map(msg -> msg.split("-")[1])
//                    .collect(Collectors.joining("<br>"));
//
//            model.addAttribute("errorMessage", errorMessage);

            return "domain/post/post/write";
        }

        Post post = Post.builder()
                .id(++lastId)
                .title(form.getTitle())
                .content(form.getContent())
                .build();

        posts.add(post);

        return "redirect:/posts";
    }

    @GetMapping
    public String showList(Model model) {

        String lis = posts.stream()
                .map(p -> "<li>" + p.getTitle() + "</li>")
                .collect(Collectors.joining());

        String ul = "<ul>" + lis + "</ul>";

        model.addAttribute("posts", posts);

        return "domain/post/post/list";
    }
}
