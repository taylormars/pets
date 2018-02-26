<%--
  Created by IntelliJ IDEA.
  User: liyut
  Date: 2017-12-15
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script language="JavaScript" defer="defer" src="js/jquery-easyui-1.5.4/jquery-1.8.0.min.js" type="text/javascript"></script>

<style>
    header {
        color:black;
        text-align:center;
        padding:5px;
    }
    nav {
        line-height:30px;
        background-color:#eeeeee;
        height:300px;
        width:100px;
        float:left;
        padding:5px;
    }
    section {
        width:400px;
        padding:10px;
    }
    footer {
        background-color:black;
        color:white;
        clear:both;
        text-align:center;
        padding:5px;
    }
</style>
<head>
    <title>ForeverGoPet 后台管理</title>
</head>
<body>
<script type="text/javascript">

    function myRefersh( e ) {

        const source = e.src ; // 获得原来的 src 中的内容
        //console.log( "source : " + source  ) ;

        var index = source.indexOf( "?" ) ;  // 从 source 中寻找 ? 第一次出现的位置 (如果不存在则返回 -1 )
        //console.log( "index : " + index  ) ;

        if( index > -1 ) { // 如果找到了 ?  就进入内部
            var s = source.substring( 0 , index ) ; // 从 source 中截取 index 之前的内容 ( index 以及 index 之后的内容都被舍弃 )
            //console.log( "s : " + s  ) ;

            var date = new Date(); // 创建一个 Date 对象的 一个 实例
            var time = date.getTime() ; // 从 新创建的 Date 对象的实例中获得该时间对应毫秒值
            e.src = s + "?time=" + time ; // 将 加了 尾巴 的 地址 重新放入到 src 上

            //console.log( e.src ) ;
        } else {
            var date = new Date();
            e.src = source + "?time=" + date.getTime();
        }

    }

    function login() {
        var userName=document.getElementById('userName').value;
        var password=document.getElementById('password').value;
        var verify=document.getElementById('verifyInput').value;

        if (null==userName||userName=="")
        {
            alert("用户名为空")
        }else if (null==password||password=="")
        {
            alert("密码为空")
        }else if (null==verify||verify==""){
            alert("验证码为空")
        }
        else {
            alert(userName +"ad"+password)
            $.ajax({
                type: "POST",
                url: "/login.do",
                dataType : 'json',
                async : false,
                data : {
                    "userName" : userName,
                    "password":password,
                    "verify": verify,
                },
                success : function(data) {
                    alert(data)

                    if(data==2){
                        alert("用户名密码错误")
                    }
                    else if(data==3){
                        alert("系统错误")
                    }
                    else if (data==1){
                        location.href = "index.jsp";
//                        window.open("admin/index.html");
//                        window.close();
                    }
                }
            })
        }

    }
</script>

<div><header>
    <h1>ForeverGoPet 后台管理</h1>
</header>
</div>
    <CENTER>
        <section>
<form >
<div>
    用户名：
    <input type="text" name="userName" id="userName"></div>
   <div>密码：
       <input type="text" name="password" id="password"></div>
    <div>验证码：
    <input type="text" name="verfyCode" id="verifyInput">
    <img src="/verify.do" onclick="myRefersh(this)">
    </div>
    <input type="button" value="登录" onclick="login()" >
</form>
        </section>
    </CENTER>
</body>
</html>
