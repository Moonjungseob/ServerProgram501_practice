package com.busanit501.mjsllunchproject.service;

import com.busanit501.mjsllunchproject.dto.LunchDTO;
import com.busanit501.mjsllunchproject.dto.LunchListReplyCountDTO;
import com.busanit501.mjsllunchproject.dto.PageRequestDTO;
import com.busanit501.mjsllunchproject.dto.PageResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class LunchServiceTest {
    @Autowired
    LunchService lunchService;

    @Test
    public void testInsert() {
        // 화면에서 넘어온 더미 데이터 만들기. DTO 타입.
        LunchDTO lunchDTO = LunchDTO.builder()
                .lunchTitle("내일 모하니?")
                .content("부모님 인사하기")
                .writer("이상용")
                .build();

        Long mno = lunchService.register(lunchDTO);
        log.info("추가 후에 반환 게시글 번호 mno : " + mno);

    }

    @Test
    public void testRead() {

        LunchDTO lunchDTO = lunchService.read(11L);
        log.info("하나 조회 lunchDTO : " + lunchDTO);

    }

    @Test
    public void testUpdate() {

        LunchDTO lunchDTO = LunchDTO.builder()
                .mno(1L)
                .lunchTitle("내일 모하니?수정버전")
                .content("부모님 인사하기 : 수정버전")
                .writer("이상용 : 수정버전")
                .build();

        //디비에서 조회하기.
        lunchService.update(lunchDTO);
    }
    @Test
    public void testDelete() {
        //디비에서 조회하기.
        lunchService.delete(2L);


    }

    @Test
    public void testList() {
        // 화면에서 전달할 내용을 담은 PageRequestDTO 더미가 필요.
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("오늘")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<LunchDTO> responseDTO = lunchService.list(pageRequestDTO);
        log.info("list 테스트 responseDTO : " + responseDTO);

    }

    @Test
    public void testListWithCount() {
        // 화면에서 전달할 내용을 담은 PageRequestDTO 더미가 필요.
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("오늘")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<LunchListReplyCountDTO> responseDTO = lunchService.listWithREplyCount(pageRequestDTO);
        log.info("list 테스트 responseDTO : " + responseDTO);
    }
}
