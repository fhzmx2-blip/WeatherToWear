
package com.weather.WeatherToWear.service;

import com.weather.WeatherToWear.mapper.BoardMapper;
import com.weather.WeatherToWear.util.CloudinaryUtil;
import com.weather.WeatherToWear.vo.BoardVO;
import com.weather.WeatherToWear.vo.CommentVO;
import com.weather.WeatherToWear.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final MemberService memberService;
    private final CloudinaryUtil cloudinaryUtil;


    //게시글
    @Override
    public Map<String, Object> getBoardList(String searchType, String keyword, int page) {
        if (searchType == null ||
                !List.of("all", "title", "content", "writer").contains(searchType)) {
            throw new IllegalArgumentException("올바르지 않은 검색 유형입니다.");
        }

        int pageSize = 10;
        int blockSize = 10;
        int totalCount = boardMapper.countBoardList(searchType, keyword);

        int[] pageInfo = pagination(page, pageSize, blockSize, totalCount);

        List<BoardVO> list = boardMapper.selectBoardList(
                searchType, keyword, pageSize, pageInfo[4]
        );

        Map<String, Object> result = new java.util.HashMap<>();
        result.put("list", list);
        result.put("currentPage", pageInfo[0]);
        result.put("totalPage", pageInfo[1]);
        result.put("startPage", pageInfo[2]);
        result.put("endPage", pageInfo[3]);

        return result;
    }

    @Override
    public BoardVO getBoardDetail(Long boardId) {
        BoardVO board = boardMapper.selectBoardDetail(boardId);

        if (board == null) {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        }

        return board;
    }

    @Override
    public void writeBoard(BoardVO board, String memberId, MultipartFile file) {
        validateBoard(board);

        MemberVO member = memberService.findByMemberId(memberId);

        if (member == null) {
            throw new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
        }

        board.setMember_id(memberId);
        board.setNickname(member.getNickname());

        uploadBoardImage(board, file);

        int result = boardMapper.insertBoard(board);

        if (result == 0) {
            deleteBoardImage(board.getImage_public_id());
            throw new IllegalArgumentException("게시글 작성에 실패했습니다.");
        }
    }

    @Override
    public void updateBoard(BoardVO board, String memberId, MultipartFile file) {
        validateBoard(board);

        BoardVO existingBoard = boardMapper.selectBoardDetail(board.getBoard_id());

        if (existingBoard == null) {
            throw new IllegalArgumentException("수정할 게시글을 찾을 수 없습니다.");
        }

        if (!existingBoard.getMember_id().equals(memberId)) {
            throw new IllegalArgumentException("게시글 수정 권한이 없습니다.");
        }

        board.setMember_id(memberId);
        board.setNickname(existingBoard.getNickname());

        if (file != null && !file.isEmpty()) {
            uploadBoardImage(board, file);
        } else {
            board.setImage_url(existingBoard.getImage_url());
            board.setImage_public_id(existingBoard.getImage_public_id());
            board.setImage_size(existingBoard.getImage_size());
        }

        int result = boardMapper.updateBoard(board);

        if (result == 0) {
            if (file != null && !file.isEmpty()) {
                deleteBoardImage(board.getImage_public_id());
            }

            throw new IllegalArgumentException("게시글 수정에 실패했거나 변경된 내용이 없습니다.");
        }

        if (file != null && !file.isEmpty()) {
            deleteBoardImage(existingBoard.getImage_public_id());
        }
    }

    @Override
    public void deleteBoard(Long boardId, String memberId) {
        BoardVO existingBoard = boardMapper.selectBoardDetail(boardId);

        if (existingBoard == null) {
            throw new IllegalArgumentException("삭제할 게시글을 찾을 수 없습니다.");
        }

        if (!existingBoard.getMember_id().equals(memberId)) {
            throw new IllegalArgumentException("게시글 삭제 권한이 없습니다.");
        }

        int result = boardMapper.deleteBoard(boardId, memberId);

        if (result == 0) {
            throw new IllegalArgumentException("게시글 삭제에 실패했습니다.");
        }

        deleteBoardImage(existingBoard.getImage_public_id());
    }




    //댓글
    @Override
    public Map<String, Object> getCommentList(Long boardId, int page) {
        int pageSize = 20;
        int blockSize = 10;
        int totalCount = boardMapper.countCommentList(boardId);

        int[] pageInfo = pagination(page, pageSize, blockSize, totalCount);

        List<CommentVO> list = boardMapper.selectCommentList(
                boardId, pageSize, pageInfo[4]
        );

        Map<String, Object> result = new java.util.HashMap<>();
        result.put("list", list);
        result.put("currentPage", pageInfo[0]);
        result.put("totalPage", pageInfo[1]);
        result.put("startPage", pageInfo[2]);
        result.put("endPage", pageInfo[3]);

        return result;
    }

    @Override
    public void writeComment(CommentVO comment, String memberId) {
        validateComment(comment);

        BoardVO board = boardMapper.selectBoardDetail(comment.getBoard_id());

        if (board == null) {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        }

        MemberVO member = memberService.findByMemberId(memberId);

        if (member == null) {
            throw new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
        }

        comment.setMember_id(memberId);
        comment.setNickname(member.getNickname());

        boardMapper.insertComment(comment);
    }

    @Override
    public void deleteComment(Long commentId, String memberId) {
        int result = boardMapper.deleteComment(commentId, memberId);

        if (result == 0) {
            throw new IllegalArgumentException("댓글 삭제에 실패했습니다.");
        }
    }



    //메서드
    private void uploadBoardImage(BoardVO board, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            board.setImage_url(null);
            board.setImage_public_id(null);
            board.setImage_size(null);
            return;
        }

        try {
            Map<String, Object> uploadResult = cloudinaryUtil.uploadImage(file);

            board.setImage_url((String) uploadResult.get("url"));
            board.setImage_public_id((String) uploadResult.get("public_id"));
            board.setImage_size((Long) uploadResult.get("size"));

        } catch (IOException e) {
            throw new IllegalStateException("이미지 업로드에 실패했습니다.", e);
        }
    }

    private void deleteBoardImage(String publicId) {
        if (publicId == null || publicId.isBlank()) {
            return;
        }

        try {
            cloudinaryUtil.deleteImage(publicId);
        } catch (IOException e) {
            throw new IllegalStateException("이미지 처리에 실패했습니다.", e);
        }
    }

    private void validateBoard(BoardVO board) {
        if (board.getTitle() == null || board.getTitle().isBlank()) {
            throw new IllegalArgumentException("게시글 제목을 입력해 주세요.");
        }

        if (board.getTitle().length() > 100) {
            throw new IllegalArgumentException("제목은 100자 이하로 입력해 주세요.");
        }

        if (board.getContent() == null || board.getContent().isBlank()) {
            throw new IllegalArgumentException("게시글 내용을 입력해 주세요.");
        }
    }

    private void validateComment(CommentVO comment) {

        if (comment.getContent() == null || comment.getContent().isBlank()) {
            throw new IllegalArgumentException("댓글 내용을 입력해 주세요.");
        }

        if (comment.getContent().length() > 1000) {
            throw new IllegalArgumentException("댓글은 1000자 이하로 입력해 주세요.");
        }
    }

    private int[] pagination(int page, int pageSize, int blockSize, int totalCount) {
        int totalPage = (totalCount + pageSize - 1) / pageSize;

        int currentPage = page;
        if (currentPage < 1) {
            currentPage = 1;
        }
        if (totalPage > 0 && currentPage > totalPage) {
            currentPage = totalPage;
        }

        int startPage = ((currentPage - 1) / blockSize) * blockSize + 1;

        int endPage = startPage + blockSize - 1;
        if (endPage > totalPage) {
            endPage = totalPage;
        }

        int offset = (currentPage - 1) * pageSize;

        return new int[]{currentPage, totalPage, startPage, endPage, offset};
    }
}
