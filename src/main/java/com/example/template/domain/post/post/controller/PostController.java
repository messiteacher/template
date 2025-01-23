package com.example.template.domain.post.post.controller;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/posts")
@Validated
public class PostController {

    @GetMapping("/write")
    @ResponseBody
    public String showWrite() {
        return getFormHtml("");
    }

    @PostMapping("/write")
    @ResponseBody
    public String doWrite(@NotBlank @Length(min = 5) String title,
                          @NotBlank @Length(min = 10) String content) {

        return """
                <h1>게시물 조회</h1>
                <div>%s</div>
                <div>%s</div>
                """.formatted(title, content);
    }

    private String getFormHtml(String errorMsg) {

        return """
                    <div>%s</div>
                    <form method="post">
                      <input type="text" name="title" placeholder="제목" /> <br>
                      <textarea name="content"></textarea> <br>
                      <input type="submit" value="등록" /> <br>
                    </form>
                    """.formatted(errorMsg);
    }
}
