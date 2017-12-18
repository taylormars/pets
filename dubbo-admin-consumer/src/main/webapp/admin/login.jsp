<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="admin/lib/html5shiv.js"></script>
<script type="text/javascript" src="admin/lib/respond.min.js"></script>
<![endif]-->
<link href="admin/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="admin/static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="admin/static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
<link href="admin/lib/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="admin/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>NeverGoPet后台登录</title>
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="" />
<div class="header"></div>
<div class="loginWraper">
  <div id="loginform" class="loginBox">
    <form class="form form-horizontal" action="" method="post">
      <div class="row cl">
        <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
        <div class="formControls col-xs-8">
          <input id="userName" name="" type="text" placeholder="账户" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
        <div class="formControls col-xs-8">
          <input id="password" name="" type="password" placeholder="密码" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <div class="formControls col-xs-8 col-xs-offset-3">
          <input class="input-text size-L" type="text" placeholder="验证码"  id="verifyInput" style="width:150px;">
          <img src="/verify.do"onclick="myRefersh(this)" id="verify"> <a id="kanbuq" href="javascript:myRefersh(verify);">换一张</a> </div>
      </div>
      <div class="row cl">
        <div class="formControls col-xs-8 col-xs-offset-3">
          <label for="online">
            <input type="checkbox" name="online" id="online" value="">
            使我保持登录状态</label>
        </div>
      </div>
      <div class="row cl">
        <div class="formControls col-xs-8 col-xs-offset-3">
          <input name="" type="button" class="btn btn-success radius size-L" onclick="login();" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
          <input name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">
        </div>
      </div>
    </form>
  </div>
</div>
<div class="footer">Copyright NevergoPet by H-ui.admin v3.1</div>
<script type="text/javascript" src="admin/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="admin/static/h-ui/js/H-ui.min.js"></script>
<!--此乃百度统计代码，请自行删除-->
<script>
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
    }
    if (null==password||password=="")
    {
        alert("密码为空")
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
                window.open("admin/index.html");

            }
            }
        })
    }

}
</script>
<!--/此乃百度统计代码，请自行删除
</body>
</html>