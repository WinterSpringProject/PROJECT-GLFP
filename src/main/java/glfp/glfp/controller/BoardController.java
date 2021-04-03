package glfp.glfp.controller;

import glfp.glfp.domain.entity.Board;
import glfp.glfp.dto.BoardDto;
import glfp.glfp.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/{board_id}") //전체조회
    public ResponseEntity<List> getBoardList(@PathVariable("board_id") Long bId){
        List<BoardDto> boardDtoList = boardService.getBoardList(bId);
        return new ResponseEntity<>(boardDtoList, HttpStatus.OK);
    }

    @GetMapping("/post/{post_id}") //조회
    public ResponseEntity<BoardDto> getBoard(@PathVariable("post_id") Long pId){
        BoardDto boardDTO = boardService.getBoard(pId);
        return new ResponseEntity<>(boardDTO, HttpStatus.OK);
    }

    @PostMapping("/post")    //등록
    public ResponseEntity<String> register(@RequestBody BoardDto boardDto){

        boardService.savePost(boardDto);
        return new ResponseEntity<>(boardDto.getPostTitle(), HttpStatus.OK);
    }

    @PutMapping("/post") //수정
    public ResponseEntity<Long> revise(@RequestBody BoardDto boardDto){
        boardService.revisePost(boardDto);
        return new ResponseEntity<>(boardDto.getId(), HttpStatus.OK);
    }

    @DeleteMapping("/post/{post_id}") //삭제
    public ResponseEntity<Long> delete(@PathVariable("post_id") Long pId){
        boardService.deletePost(pId);
        return new ResponseEntity<>(pId, HttpStatus.OK);
    }

    @GetMapping("/paging/{board_id}")  //페이지네이션
    public ResponseEntity<Page<Board>> pagenation(@PathVariable("board_id") Long bId, @PageableDefault Pageable pageable) {
        Page<Board> pageList = boardService.getPageList(pageable, bId);
        return new ResponseEntity<>(pageList, HttpStatus.OK);
    }

    @GetMapping("{board_id}/search") //현재는 게시물의 title을 이용하여
    public ResponseEntity<List> search(@PathVariable("board_id") Long bId, @RequestParam(value = "keyword") String keyword, @RequestBody Long SearchType) {
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword, bId, SearchType);
        return new ResponseEntity<>(boardDtoList, HttpStatus.OK);
    }
}
