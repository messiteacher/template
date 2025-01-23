package com.example.template.domain.post.post.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
@Validated
public class PostController {

    @GetMapping("/write")
    @ResponseBody
    public String showWrite() {
        return getFormHtml("");
    }

    @AllArgsConstructor
    public static class WriteForm {

        @NotBlank(message = "제목을 입력해주세요.") @Length(min = 5, message = "제목은 5글자 이상입니다.")
        private String title;

        @NotBlank(message = "내용을 입력해주세요.") @Length(min = 10 ,message = "내용은 10글자 이상입니다.")
        private String content;
    }

    @PostMapping("/write")
    @ResponseBody
    public String doWrite(@ModelAttribute @Valid WriteForm form) {

        return """
                <h1>게시물 조회</h1>
                <div>%s</div>
                <div>%s</div>
                """.formatted(form.title, form.content);
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
