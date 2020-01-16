/**
 * 加载仓库界面 该界面主要用来显示库存信息
 */
$("#load-warehouse").click(function () {
    localStorage.setItem("sortCurrentPage", "warehouse");
    $("#load-page-area").load("warehouse.html");
    $(this).attr("class", "nav-item active");
    $("#load-stock").attr("class", "nav-item");
    $("#load-category").attr("class", "nav-item");
    $("#load-setting").attr("class", "nav-item");
});

/**
 * 加载库存信息页面 该页面主要用来展示出/如库记录
 */
$("#load-stock").click(function () {
    localStorage.setItem("sortCurrentPage", "stock");
    $("#load-page-area").load("stock.html");
    $("#load-warehouse").attr("class", "nav-item");
    $(this).attr("class", "nav-item active");
    $("#load-category").attr("class", "nav-item");
    $("#load-setting").attr("class", "nav-item");
});

/**
 * 加载分类页面 该页面主要用来展示规格种类分类
 */
$("#load-category").click(function () {
    localStorage.setItem("sortCurrentPage", "category");
    $("#load-page-area").load("category.html");
    $("#load-warehouse").attr("class", "nav-item");
    $("#load-stock").attr("class", "nav-item");
    $(this).attr("class", "nav-item active");
    $("#load-setting").attr("class", "nav-item");
});

/**
 * 加载设置页面 该页面主要用来进行网页设置
 */
$("#load-setting").click(function () {
    localStorage.setItem("sortCurrentPage", "setting");
    $("#load-page-area").load("setting.html");
    $("#load-warehouse").attr("class", "nav-item");
    $("#load-stock").attr("class", "nav-item");
    $("#load-category").attr("class", "nav-item");
    $(this).attr("class", "nav-item active");
});

/**
 * 当前页面
 */
function currentPage() {
    let sortCurrentPage = localStorage.getItem("sortCurrentPage");
    if (sortCurrentPage === null) {
        $("#load-warehouse").attr("class", "nav-item active");
        $("#load-page-area").load("warehouse.html");
    } else {
        $("#load-" + sortCurrentPage).attr("class", "nav-item active");
        $("#load-page-area").load(sortCurrentPage + ".html");
    }
}