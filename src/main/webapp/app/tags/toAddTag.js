$(document).ready(function(){

    //处理单个
    // $("#sbutton").click(function(){
    //     var name = $("#name").val();
    //     var rank = $("#rank").val();
    //     var data = {name:name,rank:rank};
    //
    //     $.ajax({
    //         type: "POST",
    //         contentType:"application/json;charset=utf-8",
    //         url: "/tag/addTag",
    //         data: JSON.stringify(data),
    //         dataType:"json",
    //         // success: function (dta, textStatus) {
    //         //     alert(dta);
    //         // },
    //         complete:function(){
    //             alert("done");
    //         }
    //
    //     });
    // });

    //处理多个
    $("#sbutton").click(function(){
        var name = $("#name").val();
        var rank = $("#rank").val();
        var saveDataAry=[];

        if(name.indexOf(",")>-1){
            //多个
            var nameArr = name.split(",");
            var rankArr = rank.split(",");
            for(var i=0;i<nameArr.length;i++){
                var data= {name:nameArr[i],rank:rankArr[i]};
                saveDataAry.push(data);
            }
        }else{
            var data= {name:name,rank:rank};
            saveDataAry.push(data);
        }

        $.ajax({
            type: "POST",
            contentType:"application/json;charset=utf-8",
            url: "/tag/addTag",
            data: JSON.stringify(saveDataAry),
            dataType:"json",
            complete:function(){
                alert("done");
            }

        });
    });

});



