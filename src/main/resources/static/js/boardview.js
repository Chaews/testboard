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

$.ajax({
    url : "/getboard",
    method : "GET",
    data : {"no":no},
    success : function(result){
        no = result.no;
        $("#no").val(result.no);
        $("#title").val(result.title);
        $("#writer").val(result.writer);
        $("#content").val(result.content);
        $("#pw").val(result.pw);
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

function boarddelete(){
    if(prompt("비밀번호를 입력해주세요","")==$("#pw").val()){
        $.ajax({
            url : "/boarddelete",
            method :"DELETE",
            data : {"no": $("#no").val()},
            success:function(result){
                if(result){
                    alert("삭제되었습니다");
                    location.href="/"
                }
                else{alert("삭제 실패")};
            }
        })
    }
    else{
        alert("비번틀림");
    }
}

function boardupdate(){
    if(prompt("비밀번호를 입력해주세요","")==$("#pw").val()){
        alert("제목과 내용을 수정하세요");
        $("#title").attr("readonly",false);
        $("#content").attr("readonly",false);
        $("#boardupdate").attr("hidden",true);
        $("#boardrealupdate").attr("hidden",false);
    }else{
        alert("비번틀림");
    }
}

function boardrealupdate(no){
    $.ajax({
        url : "/boardupdate",
        method :"PUT",
        data : {"no": $("#no").val() , "title" : $("#title").val() ,"content" : $("#content").val()},
        success:function(result){
            if(result){
                alert("수정되었습니다");
                location.href="/boardview?no="+$("#no").val() ;
            }
            else{alert("삭제 실패")};
        }
    })
}