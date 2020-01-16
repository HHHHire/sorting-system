/**
 * 点击登录按钮响应
 */
$("#login-btn").click(function () {
    login();
});

/**
 * 回车登录事件
 * @param e
 */
document.onkeyup = function (e) {
    var code = e.charCode || e.keyCode;
    if (code === 13) {
        login();
    }
};

/**
 * 登录方法
 */
function login() {
    let username = $("#username").val();
    let password = $("#password").val();
    if (username === "") {
        Notiflix.Notify.Warning("请输入用户名");
    } else if (password === "") {
        Notiflix.Notify.Warning("请输入密码");
    } else {
        let data = {username, password};
        console.log(data);
        localStorage.setItem("sortToken", "sorting-system-user-token");
        Notiflix.Notify.Success("登录成功");
        window.location.href = "index.html";
    }
}