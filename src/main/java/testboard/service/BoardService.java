package testboard.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testboard.domain.*;
import testboard.dto.BoardDto;
import testboard.dto.ReplyDto;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ReplyRepository replyRepository;

    public boolean boardwrite(BoardDto boardDto){

        boolean ctswitch = false ;
        int cno = 0;
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        for(CategoryEntity entity : categoryEntities){
            if(entity.getCname().equals(boardDto.getCategory())){
                ctswitch = true;
                cno = entity.getCno();
            }
        }
        CategoryEntity categoryEntity = null ;
        if(!ctswitch){
            categoryEntity = CategoryEntity.builder().cname(boardDto.getCategory()).build();
            categoryRepository.save(categoryEntity);
        }else{
            categoryEntity = categoryRepository.findById(cno).get();
        }
        BoardEntity boardEntity = BoardEntity.builder().content(boardDto.getContent()).pw(boardDto.getPw()).title(boardDto.getTitle()).writer(boardDto.getWriter()).categoryEntity(categoryEntity).build();
        BoardEntity savedentity = boardRepository.save(boardEntity);
        categoryEntity.getBoardEntityList().add(savedentity);
        return true;
    }

    public JSONArray getlist(int cno){
        JSONArray jsonArray = new JSONArray();
        List<BoardEntity> boardlist =  boardRepository.findAll();
        for(BoardEntity entity : boardlist){
            if(cno==0) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("no", entity.getNo());
                jsonObject.put("title", entity.getTitle());
                jsonObject.put("content", entity.getContent());
                jsonObject.put("writer", entity.getWriter());
                jsonObject.put("pw", entity.getPw());
                jsonArray.put(jsonObject);
            }
            else{
                if(entity.getCategoryEntity().getCno()==cno){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("no", entity.getNo());
                    jsonObject.put("title", entity.getTitle());
                    jsonObject.put("content", entity.getContent());
                    jsonObject.put("writer", entity.getWriter());
                    jsonObject.put("pw", entity.getPw());
                    jsonArray.put(jsonObject);
                }
            }
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
    public JSONArray getcategorylist(){
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        JSONArray jsonArray = new JSONArray();
        for(CategoryEntity entity : categoryEntities){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cno",entity.getCno());
            jsonObject.put("cname",entity.getCname());
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    public boolean replysave(ReplyDto replyDto){
        BoardEntity boardEntity = boardRepository.findById(replyDto.getNo()).get();
        ReplyEntity replyEntity = ReplyEntity.builder().boardEntity(boardEntity).rwriter(replyDto.getRwriter()).rcontent(replyDto.getRcontent()).rpw(replyDto.getRpw()).rindex(0).build();
        ReplyEntity savedentity = replyRepository.save(replyEntity);
        savedentity.setBoardEntity(boardEntity);
        boardEntity.getReplyEntityList().add(savedentity);
        return true;
    }

    public boolean rereplysave(ReplyDto replyDto){
        BoardEntity boardEntity = boardRepository.findById(replyDto.getNo()).get();
        ReplyEntity replyEntity = ReplyEntity.builder().boardEntity(boardEntity).rwriter(replyDto.getRwriter()).rcontent(replyDto.getRcontent()).rpw(replyDto.getRpw()).rindex(replyDto.getRindex()).build();
        ReplyEntity savedentity = replyRepository.save(replyEntity);
        savedentity.setBoardEntity(boardEntity);
        boardEntity.getReplyEntityList().add(savedentity);
        return true;
    }

    public JSONObject getreply(int no){
        JSONObject allObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<ReplyEntity> replylist =  replyRepository.findAll();
        for(ReplyEntity entity : replylist){
            if(entity.getBoardEntity().getNo()==no){
                if(entity.getRindex()==0) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("rno", entity.getRno());
                    jsonObject.put("rwriter", entity.getRwriter());
                    jsonObject.put("rcontent", entity.getRcontent());
                    jsonObject.put("rpw", entity.getRpw());
                    jsonArray.put(jsonObject);
                }
            }
        }
        allObject.put("reply",jsonArray);
        JSONArray jsonArray2 = new JSONArray();
        for(ReplyEntity entity : replylist){
            if(entity.getBoardEntity().getNo()==no){
                if(entity.getRindex()!=0) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("rno", entity.getRno());
                    jsonObject.put("rwriter", entity.getRwriter());
                    jsonObject.put("rcontent", entity.getRcontent());
                    jsonObject.put("rindex", entity.getRindex());
                    jsonObject.put("rpw", entity.getRpw());
                    jsonArray2.put(jsonObject);
                }
            }
        }
        allObject.put("rereply",jsonArray2);

        return allObject;

    }

    public boolean repdelete(int rno){
        ReplyEntity reply = replyRepository.findById(rno).get();
        replyRepository.delete(reply);
        return true;
    }

    @Transactional
    public boolean repupdateok(int rno, String reupdatecontent){
        ReplyEntity reply = replyRepository.findById(rno).get();
        reply.setRcontent(reupdatecontent);
        return true;
    }
}
