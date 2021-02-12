package glfp.glfp.controller;

import glfp.glfp.dto.BoardDto;
import glfp.glfp.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) { this.boardService = boardService; }

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

    @PostMapping("")
    public ResponseEntity<String> register(@RequestBody BoardDto boardDto){
        boardService.savePost(boardDto);
        return new ResponseEntity<>(boardDto.getPostTitle(), HttpStatus.OK);
    }

    @PutMapping("") //수정
    public ResponseEntity<Long> revise(@RequestBody BoardDto boardDto){
        boardService.revise(boardDto);
        return new ResponseEntity<>(boardDto.getId(), HttpStatus.OK);
    }

    @DeleteMapping("/{post_id}") //삭제
    public ResponseEntity<Long> delete(@PathVariable("post_id") Long pId){
        boardService.delete(pId);
        return new ResponseEntity<>(pId, HttpStatus.OK);
    }
}
