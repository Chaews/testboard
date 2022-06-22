package testboard.controller;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import testboard.dto.BoardDto;
import testboard.dto.ReplyDto;
import testboard.service.BoardService;

import javax.servlet.http.HttpServletResponse;

@Controller
public class Indexcontroller {


    @Autowired
    BoardService boardService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/write")
    public String boardwrite(){
        return "boardwrite";
    }

    @PostMapping("/boardwrite")
    @ResponseBody
    public boolean boardwrite(@RequestParam("title")String title,@RequestParam("content")String content,@RequestParam("pw")String pw,@RequestParam("writer")String writer,@RequestParam("category")String category  ){
        BoardDto boardDto = BoardDto.builder().title(title).pw(pw).content(content).writer(writer).category(category).build();
        return boardService.boardwrite(boardDto);
    }

    @GetMapping("/getlist")
    @ResponseBody
    public void getlist(@RequestParam("cno")int cno, @RequestParam("key")String key,@RequestParam("keyword")String keyword,@RequestParam("page")int page, HttpServletResponse response){
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.getlist(cno,page,key,keyword));
        }
        catch(Exception e){e.printStackTrace();}

    }

    @GetMapping("/boardview")
    public String boardview(){
        return "boardview";
    }

    @GetMapping("/getboard")
    @ResponseBody
    public void getboard(@RequestParam("no")int no , HttpServletResponse response){
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.getboard(no));
        }
        catch(Exception e){e.printStackTrace();}

    }

    @DeleteMapping("/boarddelete")
    @ResponseBody
    public boolean deleteboard(@RequestParam("no") int no){
        return boardService.deleteBoard(no);
    }

    @PutMapping("/boardupdate")
    @ResponseBody
    public boolean updateboard(@RequestParam("no") int no ,@RequestParam("title") String title ,@RequestParam("content") String content){
        return boardService.updateBoard(no,title,content);
    }

    @GetMapping("/getcategorylist")
    public void getcategorylist(HttpServletResponse response){
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.getcategorylist());
        }
        catch (Exception e){ e.printStackTrace(); }
    }

    @GetMapping("/replysave")
    @ResponseBody
    public boolean replysave(@RequestParam("no")int no,@RequestParam("rcontent")String rcontent,@RequestParam("rwriter")String rwriter,@RequestParam("rpw")String rpw ){
        ReplyDto replyDto = ReplyDto.builder().no(no).rcontent(rcontent).rwriter(rwriter).rpw(rpw).rindex(0).build();
        return boardService.replysave(replyDto);
    }

    @GetMapping("/getreply")
    public void getreply(@RequestParam("no")int no , HttpServletResponse response){
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.getreply(no));
        }
        catch(Exception e){e.printStackTrace();}

    }

    @PostMapping("/repdelete")
    @ResponseBody
    public boolean getreply(@RequestParam("rno")int rno){
        return boardService.repdelete(rno);
    }

    @PostMapping("/repupdateok")
    @ResponseBody
    public boolean repupdateok(@RequestParam("rno")int rno,@RequestParam("reupdatecontent")String reupdatecontent ){
        return boardService.repupdateok(rno,reupdatecontent);
    }

    @GetMapping("/reresave")
    @ResponseBody
    public boolean reresave(@RequestParam("no")int no,@RequestParam("rno")int rno,@RequestParam("rerewriter")String rerewriter,@RequestParam("rerepw")String rerepw,@RequestParam("rerecontent")String rerecontent ){
        ReplyDto replyDto = ReplyDto.builder().no(no).rcontent(rerecontent).rwriter(rerewriter).rpw(rerepw).rindex(rno).build();
        return boardService.rereplysave(replyDto);
    }
}
