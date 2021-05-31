package glfp.glfp.domain.entity;

import glfp.glfp.dto.BoardDto;
import lombok.*;
import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter @Setter
@Builder
@Table(name = "board")
@AllArgsConstructor
@ToString(of = {"id", "user", "postTitle", "matchStatus", "boardId", "content"})
public class Board extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(length = 30, nullable = false)
    private String user;

    @Column(length = 30, nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private int matchStatus;

    @Column(nullable = false)
    private Long boardId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    public BoardDto toDto(Board board){
        BoardDto boardDto = BoardDto.builder()
                .id(board.getId())
                .user(board.getUser())
                .postTitle(board.getPostTitle())
                .matchStatus(board.getMatchStatus())
                .boardId(board.getBoardId())
                .content(board.getContent())
                .build();
        return boardDto;
    }
}
