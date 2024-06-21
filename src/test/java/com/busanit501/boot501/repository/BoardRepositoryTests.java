package com.busanit501.boot501.repository;

import com.busanit501.boot501.domain.Board;
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
public class BoardRepositoryTests {

  @Autowired
  BoardRepository boardRepository;

  @Test
  public void testInsert() {
    IntStream.rangeClosed(1, 100).forEach(i ->
        {
          Board board = Board.builder()
              .title("오늘222 점심 뭐 먹지?" + i)
              .content("한식" + i)
              .writer("이상용" + (i % 10))
              .build();
          // 데이터베이스에 추가,
          // save 없으면, 1)추가, 있으면, 2) 수정.
          Board result = boardRepository.save(board);
          log.info("추가한 BNO: " + result.getBno());
        }
    );

  } // inset test

  @Test
  public void testSelect() {
    Long bno = 100L;
    Optional<Board> result = boardRepository.findById(bno);
    Board board = result.orElseThrow();
    log.info("조회 결과 : " + board);
  }

  @Test
  public void testUpdate() {
    Long bno = 100L;
    Optional<Board> result = boardRepository.findById(bno);
    Board board = result.orElseThrow();

    log.info("조회 결과1 전 : " + board);
    board.changeTitleAndContent("오늘 점심 뭐 먹죠 수정버전", "로제 떡볶이, 냉라면, 족발");
    // 반영.
    boardRepository.save(board);
    log.info("조회 결과2 후: " + board);

  }

  @Test
  public void testDelete() {
    Long bno = 100L;
    // 반영.
    boardRepository.deleteById(100L);
    log.info("조회 결과2 후: 디비상에서 삭제 여부 확인 하기.");
  }

  //페이징 테스트
  @Test
  public void testPaging() {
//   준비물 준비
    // 첫번째 파라미터 :페이지수(1페이지 , 0)
    // 두번째 파라미터 :페이지 당 보여줄 갯수(10개)
    // 세번째 파라미터 :정렬 기준, bno , 내림차순.
    Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
    // 10개씩 조회 해보기.
    //Page 타입이라는 것은, 해당 결과에, 여러 정보들이 있음.
    // 예) 10개씩 가져온 데이터, 2)페이지 정보, 3)갯수, 4)전체 갯수 등.
    Page<Board> result = boardRepository.findAll(pageable);

    // 담겨진 페이징 관련 결과를 출력및 알아보기.
    log.info("전체 갯수 total  result.getTotalElements() : " + result.getTotalElements());
    log.info("전체 페이지  result.getTotalPages() : " + result.getTotalPages());
    log.info("페이지 number  result.getNumber() : " + result.getNumber());
    log.info("페이지 당 불러올 수  result.getSize() : " + result.getSize());
    log.info("불러올 데이터 목록  result.getContent() : ");
    // 불러올 목록 데이터를 받아서 처리해보기.
    List<Board> list = result.getContent();
    list.forEach(board -> log.info(board));

  }

  // Querydsl 이용해서 , 조회해보기.
  @Test
  public void testSearch() {

    Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());
    // 실행 여부를 확인 해보기.
    boardRepository.search(pageable);
  }

  // Querydsl 이용해서 , 검색, 페이징 이용해서 조회해보기
  @Test
  public void testSearchAll() {

    // 검색 조건 더미 데이터
    String[] types = {"t", "w", "c"};
    // 검색 조건 더미 데이터2, 키워드
    String keyword = "점심";

    Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());

    // 실행 여부를 확인 해보기.
    // 결과를 반환 타입 Page 받기.
   Page<Board> result =  boardRepository.searchAll(types,keyword,pageable);

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
    List<Board> list = result.getContent();
    list.forEach(board -> log.info(board));
  }

}







