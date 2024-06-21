package com.busanit501.boot501.repository.search;

import com.busanit501.boot501.domain.Board;
import com.busanit501.boot501.domain.QBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

// Querydsl 사용하기 위한 조건
// 인터페이스 이름 + Impl
// 상속 : QuerydslRepositorySupport,
// BoardSearch 구현을 해야합니다.
@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

  public BoardSearchImpl() {
    super(Board.class);
  }

  @Override
  public Page<Board> search(Pageable pageable) {
    // Querydsl 이용하는 기본 문법
    QBoard board = QBoard.board;
    // select from board 하는 것과 비슷한 기능.
    // query -> sql 관련 문장을, 자바 문법으로 표현하고 있다.
    JPQLQuery<Board> query = from(board);

    //BooleanBuilder를 이용한 조건절 추가 해보기.
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    booleanBuilder.or(board.title.contains("1"));
    booleanBuilder.or(board.content.contains("1"));

    // where 조건절  where title like
//    query.where(board.title.contains("1"));
    // BooleanBuilder를 적용하기.
    query.where(booleanBuilder);
    // bno > 0 보다 큰 조건 넣을 경우
    query.where(board.bno.gt(0L));


    // 페이징 처리 적용하기.
    this.getQuerydsl().applyPagination(pageable, query);

    // 해당 조건에 맞는 데이터 가져오기
    List<Board> list = query.fetch();
    // 해당 조건에 맞는 데이터 갯수
    long count = query.fetchCount();
    return null;
  }

  @Override
  public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
    // Querydsl를 이용할 때는 Q 도메인을 이용한다.
    QBoard board = QBoard.board;
    JPQLQuery<Board> query = from(board);

    // 조건절을 자바 문법으로만 구성해서, 전달해보기.
    if ((types != null && types.length > 0) && keyword != null) {
      // BooleanBuilder , 조건절의 옵션을 추가하기 쉽게하는 도구.
      log.info("조건절 실행여부 확인 1 ");
      BooleanBuilder   booleanBuilder = new BooleanBuilder();
      //String[] types = {"t","w" }
      for (String type : types) {
        switch (type) {
          case "t":
            log.info("조건절 실행여부 확인 2 :  title");
            booleanBuilder.or(board.title.contains(keyword));
          case "w":
            log.info("조건절 실행여부 확인 2 :  writer");
            booleanBuilder.or(board.writer.contains(keyword));
          case "c":
            log.info("조건절 실행여부 확인 2 :  content");
            booleanBuilder.or(board.content.contains(keyword));
        } //switch
      } // end for
      // BooleanBuilder를 적용하기.
      query.where(booleanBuilder);
    } // end if


    // bno >0 보다 큰 조건.
    query.where(board.bno.gt(0L));

    // 페이징 처리 적용하기.
    this.getQuerydsl().applyPagination(pageable, query);

    // 해당 조건에 맞는 데이터 가져오기
    List<Board> list = query.fetch();
    // 해당 조건에 맞는 데이터 갯수
    long count = query.fetchCount();

    // 검색 조건, 페이징 처리 다하고, 재사용해야하니. 반환하기. 결과물.
    Page<Board> result = new PageImpl<>(list, pageable,count);

    return result;
  }
}







