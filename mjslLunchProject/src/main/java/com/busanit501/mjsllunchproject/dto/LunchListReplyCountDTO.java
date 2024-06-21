package com.busanit501.mjsllunchproject.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LunchListReplyCountDTO {
    private Long mno;
    private String lunchTitle;
    private String writer;
    private LocalDateTime regDate;
    private Long replyCount;

}
