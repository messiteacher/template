package com.example.template.domain.post.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/posts")
public class PostController {

    @GetMapping("/write")
    @ResponseBody
    public String showWrite() {
        return getFormHtml("");
    }

    @PostMapping("/write")
    @ResponseBody
    public String doWrite(String title, String content) {

        if (title.isBlank() || title == null) {
            return getFormHtml("제목을 입력해주세요.");
        }

        if (content.isBlank() || content == null) {
            return getFormHtml("내용을 입력해주세요.");
        }

        if (title.length() < 5) {
            return getFormHtml("제목은 5글자 이상");
        }

        if (content.length() < 10) {
            return getFormHtml("내용은 10글자 이상");
        }

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
