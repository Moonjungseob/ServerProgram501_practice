package com.busanit501.mjsllunchproject.repository;

import com.busanit501.mjsllunchproject.domain.Lunch;
import com.busanit501.mjsllunchproject.domain.LunchReply;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
@Log4j2
public class LunchReplyRepositoryTest {
    @Autowired
    private LunchReplyRepository lunchReplyRepository;

    @Test
    public void testInsert(){
        Lunch lunch = Lunch.builder()
                .mno(8233L)
                .build();
        LunchReply lunchreply = LunchReply.builder()
                .lunch(lunch)
                .replyText("오늘 점심은 나가서 먹기")
                .replyer("문정섭")
                .build();
        lunchReplyRepository.save(lunchreply);
    }
    @Transactional
    @Test
    public void testLunchReplies(){
        Long mno = 8233L;
        Pageable pageable =
        PageRequest.of(0,10, Sort.by("rno").descending());
        Page<LunchReply> result = lunchReplyRepository.listOfLunch(mno, pageable);
        result.getContent().forEach(lunchReply -> {
            log.info("lunchReply 확인 : "+lunchReply);
        });
    }
}
