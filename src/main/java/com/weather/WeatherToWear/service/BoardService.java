
package com.weather.WeatherToWear.service;

import com.weather.WeatherToWear.vo.BoardVO;
import com.weather.WeatherToWear.vo.CommentVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BoardService {
    Map<String, Object> getBoardList(String searchType, String keyword, int page);
    BoardVO getBoardDetail(Long boardId);
    void writeBoard(BoardVO board, String memberId, MultipartFile file);
    void updateBoard(BoardVO board, String memberId, MultipartFile file);
    void deleteBoard(Long boardId, String memberId);

    Map<String, Object> getCommentList(Long boardId, int page);
    void writeComment(CommentVO comment, String memberId);
    void deleteComment(Long commentId, String memberId);
}
