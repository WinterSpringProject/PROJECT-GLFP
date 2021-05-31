package glfp.glfp.dto;

import glfp.glfp.domain.entity.Board;
import glfp.glfp.domain.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    private Long id;
    private String user;
    private String postTitle;
    private Long boardId;
    private int matchStatus;
    private String content;

    public Board toEntity(BoardDto boardDto){
        Board build = Board.builder()
                .id(boardDto.getId())
                .user(boardDto.getUser())
                .postTitle(boardDto.getPostTitle())
                .matchStatus(boardDto.getMatchStatus())
                .boardId(boardDto.getBoardId())
                .content(boardDto.getContent())
                .build();
        return build;
    }

}
