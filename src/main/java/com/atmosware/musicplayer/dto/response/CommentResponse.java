package com.atmosware.musicplayer.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
    private Long id;
    private Long songId;
    private Long userId;
    private String content;
}
