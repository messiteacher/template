package com.example.template.domain.post.post.controller;

import com.example.template.domain.post.post.entity.Post;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
public class PostController {

    List<Post> posts = new ArrayList<>();

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
    @ResponseBody
    public String showWrite() {
        return getFormHtml("", "", "");
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
    @ResponseBody
    public String doWrite(@Valid WriteForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            String errorMessage = bindingResult.getFieldErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .sorted()
                    .map(msg -> msg.split("-")[1])
                    .collect(Collectors.joining("<br>"));

            return getFormHtml(errorMessage, form.getTitle(), form.getContent());
        }

        return """
                <h1>게시물 조회</h1>
                <div>%s</div>
                <div>%s</div>
                """.formatted(form.getTitle(), form.getContent());
    }

    private String getFormHtml(String errorMsg, String title, String content) {

        return """
                <div>%s</div>
                <form method="post">
                  <input type="text" name="title" placeholder="제목" value="%s" /> <br>
                  <textarea name="content">%s</textarea> <br>
                  <input type="submit" value="등록" /> <br>
                </form>
                """.formatted(errorMsg, title, content);
    }

    @GetMapping
    @ResponseBody
    public String showList() {

        return """
                <div>글 목록</div>
                <ul>
                    <li>글1</li>
                    <li>글2</li>
                    <li>글3</li>
                </ul>
                """;
    }
}
