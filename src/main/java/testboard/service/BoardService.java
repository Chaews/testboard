package testboard.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testboard.domain.BoardEntity;
import testboard.domain.BoardRepository;
import testboard.dto.BoardDto;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;


    public boolean boardwrite(BoardDto boardDto){
        BoardEntity boardEntity = BoardEntity.builder().content(boardDto.getContent()).pw(boardDto.getPw()).title(boardDto.getTitle()).writer(boardDto.getWriter()).build();
        boardRepository.save(boardEntity);
        return true;
    }

    public JSONArray getlist(){
        JSONArray jsonArray = new JSONArray();
        List<BoardEntity> boardlist =  boardRepository.findAll();
        for(BoardEntity entity : boardlist){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("no", entity.getNo());
            jsonObject.put("title", entity.getTitle());
            jsonObject.put("content", entity.getContent());
            jsonObject.put("writer", entity.getWriter());
            jsonObject.put("pw", entity.getPw());
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
    public JSONObject getboard(int no){
        BoardEntity board =  boardRepository.findById(no).get();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("no", board.getNo());
            jsonObject.put("title", board.getTitle());
            jsonObject.put("content", board.getContent());
            jsonObject.put("writer", board.getWriter());
            jsonObject.put("pw", board.getPw());
        return jsonObject;
    }

    public boolean deleteBoard(int no){
        BoardEntity board = boardRepository.findById(no).get();
        boardRepository.delete(board);
        return true;
    }
    @Transactional
    public boolean updateBoard(int no, String title, String content){
        BoardEntity board = boardRepository.findById(no).get();
        board.setTitle(title);
        board.setContent(content);
        return true;
    }
}
