package com.weather.WeatherToWear.mapper;

import com.weather.WeatherToWear.vo.BoardVO;
import com.weather.WeatherToWear.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    int countBoardList(@Param("searchType") String searchType,@Param("keyword") String keyword);
    List<BoardVO> selectBoardList(@Param("searchType") String searchType, @Param("keyword") String keyword,
            @Param("pageSize") int pageSize, @Param("offset") int offset);
    BoardVO selectBoardDetail(@Param("boardId") Long boardId);
    int insertBoard(BoardVO board);
    int updateBoard(BoardVO board);
    int deleteBoard(@Param("boardId") Long boardId, @Param("memberId") String memberId);

    int countCommentList(@Param("boardId") Long boardId);
    List<CommentVO> selectCommentList(@Param("boardId") Long boardId, @Param("pageSize") int pageSize, @Param("offset") int offset);
    int insertComment(CommentVO comment);
    int deleteComment(@Param("commentId") Long commentId, @Param("memberId") String memberId);
}