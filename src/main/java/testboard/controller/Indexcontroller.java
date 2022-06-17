package testboard.controller;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import testboard.dto.BoardDto;
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
    public boolean boardwrite(@RequestParam("title")String title,@RequestParam("content")String content,@RequestParam("pw")String pw,@RequestParam("writer")String writer ){
        BoardDto boardDto = BoardDto.builder().title(title).pw(pw).content(content).writer(writer).build();
        return boardService.boardwrite(boardDto);
    }

    @GetMapping("/getlist")
    @ResponseBody
    public void getlist(HttpServletResponse response){
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.getlist());
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
}
