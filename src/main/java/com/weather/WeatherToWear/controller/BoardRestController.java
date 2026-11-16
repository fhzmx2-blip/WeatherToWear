
package com.weather.WeatherToWear.controller;

import com.weather.WeatherToWear.service.BoardService;
import com.weather.WeatherToWear.vo.BoardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardRestController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<List<BoardVO>> getBoardList(
            @RequestParam(defaultValue = "all") String searchType,
            @RequestParam(defaultValue = "") String keyword) {

        return ResponseEntity.ok(
                boardService.getBoardList(searchType, keyword)
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
}
