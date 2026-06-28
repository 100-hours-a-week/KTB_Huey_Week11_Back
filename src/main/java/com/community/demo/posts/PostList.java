package com.community.demo.posts;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostList {
    private boolean hasNext;
    private List<PostListUnit> posts;

    public PostList(boolean hasNext, List<PostListUnit> posts) {
        this.hasNext = hasNext;
        this.posts = posts;
    }
}
