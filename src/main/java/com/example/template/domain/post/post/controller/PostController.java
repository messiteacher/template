package com.example.template.domain.post.post.controller;

import com.example.template.domain.post.post.entity.Post;
import com.example.template.domain.post.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/write")
    public String showWrite(WriteForm form, BindingResult bindingResult) {
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
    public String doWrite(@Valid WriteForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "domain/post/post/write";
        }

        postService.write(form.getTitle(), form.getContent());

        return "redirect:/posts";
    }

    @GetMapping
    private String showList(Model model) {

        List<Post> posts = postService.getItems();
        model.addAttribute("posts", posts);

        return "domain/post/post/list";
    }

    @GetMapping("/detail/{id}")
    private String detail(@PathVariable long id, Model model) {

        Post post = postService.getItem(id).get();
        model.addAttribute("post", post);

        return "domain/post/post/detail";
    }
}
