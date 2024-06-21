package com.busanit501.mjsllunchproject.repository.search;

import com.busanit501.mjsllunchproject.domain.Lunch;
import com.busanit501.mjsllunchproject.domain.QLunch;
import com.busanit501.mjsllunchproject.domain.QLunchReply;
import com.busanit501.mjsllunchproject.dto.LunchListReplyCountDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.expression.spel.ast.Projection;

import java.util.List;
@Log4j2
public class LunchSearchImpl extends QuerydslRepositorySupport implements LunchSearch{
    public LunchSearchImpl() {
        super(Lunch.class);
    }



    @Override
    public Page<Lunch> search(Pageable pageable) {
        QLunch lunch = QLunch.lunch;
        JPQLQuery<Lunch> query =from(lunch);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.or(lunch.lunchTitle.contains("1"));
        booleanBuilder.or(lunch.content.contains("1"));
        query.where(booleanBuilder);
        query.where(lunch.mno.gt(0L));

        this.getQuerydsl().applyPagination(pageable,query);
        List<Lunch> list = query.fetch();
        long count = query.fetchCount();
        return null;
    }
    @Override
    public Page<Lunch> searchAll(String[] types, String keyword, Pageable pageable){
        QLunch lunch = QLunch.lunch;
        JPQLQuery<Lunch> query =from(lunch);
        if((types != null) && (types.length > 0) && keyword != null){
            log.info("조건절 실행여부 확인 1 ");
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for (String type : types) {
                switch(type) {
                    case "t":
                        log.info("조건절 실행여부 확인 2 :  lunchTitle");
                        booleanBuilder.or(lunch.lunchTitle.contains(keyword));
                    case "w":
                        log.info("조건절 실행여부 확인 2 :  writer");
                        booleanBuilder.or(lunch.writer.contains(keyword));
                    case "c":
                        log.info("조건절 실행여부 확인 2 :  content");
                        booleanBuilder.or(lunch.content.contains(keyword));
                }//switch
            }//end for
            query.where(booleanBuilder);
        }//end if
        query.where(lunch.mno.gt(0L));
        this.getQuerydsl().applyPagination(pageable,query);
        List<Lunch> list = query.fetch();
        long count = query.fetchCount();
        Page<Lunch> result = new PageImpl<>(list, pageable, count);
        return result;
    }

    @Override
    public Page<LunchListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {
        QLunch lunch = QLunch.lunch;
        QLunchReply lunchReply = QLunchReply.lunchReply;
        JPQLQuery<Lunch> query =from(lunch);
        query.leftJoin(lunchReply).on(lunchReply.lunch.eq(lunch));
        query.groupBy(lunch);

        if ((types != null && types.length > 0) && keyword != null) {
            // BooleanBuilder , 조건절의 옵션을 추가하기 쉽게하는 도구.
            log.info("조건절 실행여부 확인 1 ");
            BooleanBuilder   booleanBuilder = new BooleanBuilder();
            //String[] types = {"t","w" }
            for (String type : types) {
                switch (type) {
                    case "t":
                        log.info("조건절 실행여부 확인 2 :  lunchTitle");
                        booleanBuilder.or(lunch.lunchTitle.contains(keyword));
                        break;
                    case "w":
                        log.info("조건절 실행여부 확인 2 :  writer");
                        booleanBuilder.or(lunch.writer.contains(keyword));
                        break;
                    case "c":
                        log.info("조건절 실행여부 확인 2 :  content");
                        booleanBuilder.or(lunch.content.contains(keyword));
                        break;
                } //switch
            } // end for
            // BooleanBuilder를 적용하기.
            query.where(booleanBuilder);
        } // end if


        // bno >0 보다 큰 조건.
        query.where(lunch.mno.gt(0L));

        JPQLQuery<LunchListReplyCountDTO> dtoQuery = query.select(
                Projections.bean(LunchListReplyCountDTO.class,
                        lunch.mno,
                        lunch.lunchTitle,
                        lunch.writer,
                        lunch.writer,
                        lunch.regDate,
                        lunchReply.count().as("replyCount"))
        );
        this.getQuerydsl().applyPagination(pageable, dtoQuery);

        // 위의 조건으로 디비에서 정보 가져오기.
        List<LunchListReplyCountDTO> dtoList = dtoQuery.fetch();
        // 조회된 데이터의 갯수
        long count = dtoQuery.fetchCount();

        return new PageImpl<>(dtoList, pageable, count);

    }
}

