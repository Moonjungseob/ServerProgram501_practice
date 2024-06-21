package com.busanit501.mjsllunchproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(name="lunch_reply", indexes = {
        @Index(name="idx_lunch_reply_lunch_mno", columnList = "lunch_mno")
})
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LunchReply extends LunchEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lunch lunch;

    private String replyText;

    private String replyer;

    public void changeText(String text){
        this.replyText = text;
    }
}
