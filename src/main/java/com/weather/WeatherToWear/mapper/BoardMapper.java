package com.weather.WeatherToWear.mapper;

import com.weather.WeatherToWear.vo.BoardVO;
import com.weather.WeatherToWear.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    int insertBoard(BoardVO board);
    List<BoardVO> selectBoardList(@Param("searchType") String searchType, @Param("keyword") String keyword);
    BoardVO selectBoardDetail(@Param("boardId") Long boardId);
    int updateBoard(BoardVO board);
    int deleteBoard(@Param("boardId") Long boardId, @Param("memberId") String memberId);

    List<CommentVO> selectCommentList(@Param("boardId") Long boardId);
    int insertComment(CommentVO comment);
    int deleteComment(
            @Param("commentId") Long commentId,
            @Param("memberId") String memberId
    );
}