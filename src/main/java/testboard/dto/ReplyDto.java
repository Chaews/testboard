package testboard.dto;


import lombok.*;
import testboard.domain.BoardEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReplyDto {
    private int rno;
    private String rwriter;
    private String rcontent;
    private String rpw;
    private int rindex;
    private int no;
}
