/*<![CDATA[*/
var basePath = /*[[${#httpServletRequest.getContextPath()}]]*/
function loginOut() {
    $.ajax({
        url: basePath + "/api/system/logout",
        type: "get",
        success: function (data) {
            window.location = (basePath +"/login");
        },
        error: function (data) {

            alert("登出失败")
        }
    })
}
var loginkey = '${session.logined}'
console.log(loginkey);

if (loginkey === null){
    window.location = (basePath +"/login");
}
/*]]>*/