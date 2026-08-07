package com.community.demo.posts.dto;

import com.community.demo.posts.entity.Post;
import com.community.demo.posts.entity.PostMetadata;
import com.community.demo.time.TimestampUtils;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReadPostResponseDto {

    private String title;
    private String userNickname;
    private String userProfileImageUrl;
    private String postedTime;
    private String content;
    private String imageUrl;
    private Long likes;
    private Long views;
    private Long comments;
    private Boolean edited;

    public static ReadPostResponseDto fromEntity(Post post, PostMetadata postMetadata, long count) {
        return new ReadPostResponseDto(
                post.getTitle(),
                post.getAuthor().getNickname(),
                post.getAuthor().getProfileImage().getFilePath(),
                TimestampUtils.getZonedTime(postMetadata.getPostedTime()),
                post.getContent(),
                post.getPostAttachment().getFilePath(),
                postMetadata.getLikes(),
                postMetadata.getViews(),
                count,
                postMetadata.isEdited()
                );
    }
}
