package com.busanit501.mjsllunchproject.repository;

import com.busanit501.mjsllunchproject.domain.Lunch;
import com.busanit501.mjsllunchproject.dto.LunchDTO;
import com.busanit501.mjsllunchproject.dto.LunchListReplyCountDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class LunchRepositoryTest {
    @Autowired
    LunchRepository lunchRepository;

   @Test
    public void testInsert(){
        IntStream.rangeClosed(1,100).forEach(i ->
                {
                    Lunch lunch = Lunch.builder()
                            .lunchTitle("오늘 점심 뭐 먹지?" + i)
                            .content("한식" + i)
                            .writer("문정섭"+(i%10))
                            .build();
                    // 데이터베이스에 추가,
                    // save 없으면, 1)추가, 있으면, 2) 수정.
                    Lunch result = lunchRepository.save(lunch);
                    log.info("추가한 MNO: " + result.getMno());
                }




        );

    }
    @Test
    public void testSelect() {
        Long mno = 100L;
        Optional<Lunch> result = lunchRepository.findById(mno);
        Lunch lunch = result.orElseThrow();
        log.info("조회 결과 : " + lunch);
    }
    @Test
    public void testUpdate() {
        Long mno = 100L;
        Optional<Lunch> result = lunchRepository.findById(mno);
        Lunch lunch = result.orElseThrow();

        log.info("조회 결과1 전 : " + lunch);
        lunch.changeTitleAndContent("오늘 점심 뭐 먹죠 수정버전","로제 떡볶이, 냉라면, 족발");
        // 반영.
        lunchRepository.save(lunch);
        log.info("조회 결과2 후: " + lunch);

    }
    @Test
    public void testDelete() {
        Long mno = 100L;
        // 반영.
        lunchRepository.deleteById(100L);
        log.info("조회 결과2 후: 디비상에서 삭제 여부 확인 하기.");

    }

    @Test
    public void testPaging() {
//   준비물 준비
        // 첫번째 파라미터 :페이지수(1페이지 , 0)
        // 두번째 파라미터 :페이지 당 보여줄 갯수(10개)
        // 세번째 파라미터 :정렬 기준, bno , 내림차순.
        Pageable pageable = PageRequest.of(0,10, Sort.by("mno").descending());
        // 10개씩 조회 해보기.
        //Page 타입이라는 것은, 해당 결과에, 여러 정보들이 있음.
        // 예) 10개씩 가져온 데이터, 2)페이지 정보, 3)갯수, 4)전체 갯수 등.
        Page<Lunch> result = lunchRepository.findAll(pageable);
        // 담겨진 페이징 관련 결과를 출력및 알아보기.
        log.info("전체 갯수 total  result.getTotalElements() : " + result.getTotalElements());
        log.info("전체 페이지  result.getTotalPages() : " + result.getTotalPages());
        log.info("페이지 number  result.getNumber() : " + result.getNumber());
        log.info("페이지 당 불러올 수  result.getSize() : " + result.getSize());
        log.info("불러올 데이터 목록  result.getContent() : " );
        // 불러올 목록 데이터를 받아서 처리해보기.
        List<Lunch> list = result.getContent();
        list.forEach(lunch -> log.info(lunch));


    }
    @Test
    public void testSearch() {

        Pageable pageable = PageRequest.of(1, 10, Sort.by("mno").descending());
        // 실행 여부를 확인 해보기.
        lunchRepository.search(pageable);
    }
    @Test
    public void testSearchAll() {

        // 검색 조건 더미 데이터
        String[] types = {"t", "w", "c"};
        // 검색 조건 더미 데이터2, 키워드
        String keyword = "점심";

        Pageable pageable = PageRequest.of(1, 10, Sort.by("mno").descending());

        // 실행 여부를 확인 해보기.
        // 결과를 반환 타입 Page 받기.
        Page<Lunch> result =  lunchRepository.searchAll(types,keyword,pageable);

        log.info("Querydsl 결과 : 전체 갯수 total  result.getTotalElements() : " + result.getTotalElements());
        log.info("Querydsl 결과 : 전체 페이지  result.getTotalPages() : " + result.getTotalPages());
        log.info("Querydsl 결과 : 페이지 number  result.getNumber() : " + result.getNumber());
        log.info("Querydsl 결과 : 페이지 당 불러올 수  result.getSize() : " + result.getSize());
        log.info("Querydsl 결과 : 불러올 데이터 목록  result.getContent() : ");
        log.info("Querydsl 결과 : 이전 페이지  존재 여부  result.hasPrevious() : " + result.hasPrevious());
        log.info("Querydsl 결과 : 다음 페이지  존재 여부  result.hasNext() : " + result.hasNext());

        List<Lunch> list = result.getContent();
        list.forEach(lunch -> log.info(lunch));

    }
    @Test
    public void testSearchReplyCount() {

        // 검색 조건 더미 데이터
        String[] types = {"t", "w", "c"};
        // 검색 조건 더미 데이터2, 키워드
        String keyword = "점심";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        // 실행 여부를 확인 해보기.
        // 결과를 반환 타입 Page 받기.
        Page<LunchListReplyCountDTO> result =
                lunchRepository.searchWithReplyCount(types,keyword,pageable);

        // 페이징 된 결과물 확인.
        // 담겨진 페이징 관련 결과를 출력및 알아보기.
        log.info("Querydsl 결과 : 전체 갯수 total  result.getTotalElements() : " + result.getTotalElements());
        log.info("Querydsl 결과 : 전체 페이지  result.getTotalPages() : " + result.getTotalPages());
        log.info("Querydsl 결과 : 페이지 number  result.getNumber() : " + result.getNumber());
        log.info("Querydsl 결과 : 페이지 당 불러올 수  result.getSize() : " + result.getSize());
        log.info("Querydsl 결과 : 불러올 데이터 목록  result.getContent() : ");
        log.info("Querydsl 결과 : 이전 페이지  존재 여부  result.hasPrevious() : " + result.hasPrevious());
        log.info("Querydsl 결과 : 다음 페이지  존재 여부  result.hasNext() : " + result.hasNext());
        // 불러올 목록 데이터를 받아서 처리해보기.
        List<LunchListReplyCountDTO> list = result.getContent();
        list.forEach(lunch -> log.info(lunch));
    }





}
