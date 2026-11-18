
package com.weather.WeatherToWear.controller;

import com.weather.WeatherToWear.service.BoardService;
import com.weather.WeatherToWear.vo.BoardVO;
import com.weather.WeatherToWear.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardRestController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getBoardList(
            @RequestParam(defaultValue = "all") String searchType,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "1") int page) {

        return ResponseEntity.ok(
                boardService.getBoardList(searchType, keyword, page)
        );
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardVO> getBoardDetail(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.getBoardDetail(boardId));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> writeBoard(@RequestPart("board") BoardVO board,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal Jwt jwt) {

        boardService.writeBoard(
                board,
                jwt.getSubject(),
                file
        );

        return ResponseEntity.ok().build();
    }

    // 게시글 수정
    @PutMapping(value = "/{boardId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateBoard(
            @PathVariable Long boardId,
            @RequestPart("board") BoardVO board,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestParam String memberId,
            @AuthenticationPrincipal Jwt jwt) {

        String jwtMemberId = jwt.getSubject();

        if (!jwtMemberId.equals(memberId)) {
            throw new IllegalArgumentException("회원 정보가 일치하지 않습니다.");
        }

        board.setBoard_id(boardId);

        boardService.updateBoard(
                board,
                jwtMemberId,
                file
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(
            @PathVariable Long boardId,
            @RequestParam String memberId,
            @AuthenticationPrincipal Jwt jwt) {

        String jwtMemberId = jwt.getSubject();

        if (!jwtMemberId.equals(memberId)) {
            throw new IllegalArgumentException("회원 정보가 일치하지 않습니다.");
        }

        boardService.deleteBoard(boardId, jwtMemberId);

        return ResponseEntity.ok().build();
    }


    //댓글
    @GetMapping("/{boardId}/comments")
    public ResponseEntity<Map<String, Object>> getCommentList(
            @PathVariable Long boardId,
            @RequestParam(defaultValue = "1") int page) {

        return ResponseEntity.ok(
                boardService.getCommentList(boardId, page)
        );
    }

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<Void> writeComment(
            @PathVariable Long boardId,
            @RequestBody CommentVO comment,
            @AuthenticationPrincipal Jwt jwt) {

        comment.setBoard_id(boardId);

        boardService.writeComment(
                comment,
                jwt.getSubject()
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long boardId,
            @PathVariable Long commentId,
            @RequestParam String memberId,
            @AuthenticationPrincipal Jwt jwt) {

        String jwtMemberId = jwt.getSubject();

        if (!jwtMemberId.equals(memberId)) {
            throw new IllegalArgumentException("회원 정보가 일치하지 않습니다.");
        }

        boardService.deleteComment(commentId, jwtMemberId);

        return ResponseEntity.ok().build();
    }
}
