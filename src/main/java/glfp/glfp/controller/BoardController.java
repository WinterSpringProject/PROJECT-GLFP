package glfp.glfp.controller;

import glfp.glfp.dto.BoardDto;
import glfp.glfp.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{board_id}")
    public ResponseEntity<Page<BoardDto>> getBoardList(@PathVariable("board_id") Long bId,
                                                       @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
                                                               Pageable pageable) {
        Page<BoardDto> boardList = boardService.getBoardList(bId, pageable);
        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

    @GetMapping("/post/{post_id}")
    public ResponseEntity<BoardDto> getBoard(@PathVariable("post_id") Long pId){
        BoardDto boardDTO = boardService.getBoard(pId);
        if (boardDTO == null)
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        return new ResponseEntity<>(boardDTO, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<Long> register(@RequestBody BoardDto boardDto){
        boardService.savePost(boardDto);
        return new ResponseEntity<>(boardDto.getId(), HttpStatus.OK);
    }

    @PutMapping("/post")
    public ResponseEntity<Long> revise(@RequestBody BoardDto boardDto){
        boardService.revisePost(boardDto);
        return new ResponseEntity<>(boardDto.getId(), HttpStatus.OK);
    }

    @DeleteMapping("/post/{post_id}")
    public ResponseEntity<Long> delete(@PathVariable("post_id") Long pId){
        boardService.deletePost(pId);
        return new ResponseEntity<>(pId, HttpStatus.OK);
    }

    @GetMapping("{board_id}/search")
    public ResponseEntity<Page<BoardDto>> search(@PathVariable("board_id") Long bId,
                                                 @RequestParam(value = "keyword") String keyword,
                                       @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BoardDto> SearchPosts = boardService.searchPosts(bId, keyword, pageable);
        return new ResponseEntity<>(SearchPosts, HttpStatus.OK);
    }
}
