
package com.weather.WeatherToWear.service;

import com.weather.WeatherToWear.vo.BoardVO;
import com.weather.WeatherToWear.vo.CommentVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    List<BoardVO> getBoardList(String searchType, String keyword);
    BoardVO getBoardDetail(Long boardId);
    void writeBoard(BoardVO board, String memberId, MultipartFile file);
    void updateBoard(BoardVO board, String memberId, MultipartFile file);
    void deleteBoard(Long boardId, String memberId);

    List<CommentVO> getCommentList(Long boardId);
    void writeComment(CommentVO comment, String memberId);
    void deleteComment(Long commentId, String memberId);
}
