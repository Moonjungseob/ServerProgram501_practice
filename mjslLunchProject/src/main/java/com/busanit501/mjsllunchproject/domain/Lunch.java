package com.busanit501.mjsllunchproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Lunch extends LunchEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 500,nullable = false)
    private String lunchTitle;

    @Column(length = 2000,nullable = false)
    private String content;

    @Column(length = 50,nullable = false)
    private String writer;

    public void changeTitleAndContent(String lunchTitle, String content) {
        this.lunchTitle = lunchTitle;
        this.content = content;
    }

}
