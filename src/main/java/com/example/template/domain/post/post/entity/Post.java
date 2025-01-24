package com.example.template.domain.post.post.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Post {

    private Long id;
    private String title;
    private String content;
}
