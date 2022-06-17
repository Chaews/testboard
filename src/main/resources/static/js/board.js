$.ajax({
    url : "/getlist",
    method : "GET",
    success : function(result){
        let html = '<thead><tr><td>번호</td><td>제목</td><td>작성자</td></tr></thead>';
        for(let i = 0 ; i < result.length ; i ++){
        html+= '<tr><td>'+result[i].no+'</td><td><a href="/boardview?no='+result[i].no+'">'+result[i].title+'</a></td><td>'+result[i].writer+'</td></tr>'
        }
        $("#boardtable").html(html);
    }
})

function boardwrite(){
    let title = $("#title").val();
    let writer = $("#writer").val();
    let pw = $("#pw").val();
    let content = $("#content").val();
    $.ajax({
        url : "/boardwrite",
        method : "POST",
        data : {"title":title,"writer":writer,"pw":pw,"content":content},
        success : function(result){
            if(result){
                alert("작성되었습니다");
                location.href="/"
            }
            else{alert("작성실패")};
        }
    })
}