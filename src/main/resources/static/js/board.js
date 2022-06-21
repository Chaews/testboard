boardlist(0);
getcategorylist();

let no = Request("no");// 게임방번호

function Request(valuename){
	let rtnval = "";
	let nowAddress = unescape(location.href);
	let parameters = (nowAddress.slice(nowAddress.indexOf("?")+1,nowAddress.length)).split("&");
	for(let i = 0 ; i < parameters.length ; i++){
		let varName = parameters[i].split("=")[0];
		if(varName.toUpperCase() == valuename.toUpperCase()){
			rtnval = parameters[i].split("=")[1];
			break;
		}
	}
	return rtnval;
}


function boardlist(cno){
    $.ajax({
        url : "/getlist",
        data : {"cno" : cno},
        method : "GET",
        success : function(result){
            let html = '<thead><tr><td>번호</td><td>제목</td><td>작성자</td></tr></thead>';
            for(let i = 0 ; i < result.length ; i ++){
            html+= '<tr><td>'+result[i].no+'</td><td><a href="/boardview?no='+result[i].no+'">'+result[i].title+'</a></td><td>'+result[i].writer+'</td></tr>'
            }
            $("#boardtable").html(html);
        }
    })
}

function boardwrite(){
    let title = $("#title").val();
    let writer = $("#writer").val();
    let pw = $("#pw").val();
    let content = $("#content").val();
    let category =$("#category").val();
    $.ajax({
        url : "/boardwrite",
        method : "POST",
        data : {"title":title,"writer":writer,"pw":pw,"content":content, "category" : category},
        success : function(result){
            if(result){
                alert("작성되었습니다");
                location.href="/"
            }
            else{alert("작성실패")};
        }
    })
}

function getcategorylist(){
    $.ajax({
        url : "/getcategorylist",
        method : "GET",
        success : function(result){
            html = '';
            for(let i = 0 ; i < result.length ; i++ ){
                html+= '<button onclick="boardlist('+result[i].cno+')" type="button">'+result[i].cname+'</button> ';
            }
            html += '<button onclick="boardlist('+0+')" type="button">전체보기</button>';
            $("#categorybox").html(html);
        }
    })
}
