$(document).ready(function () {

    $("#search-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit();

    });

});
var currentPage = "1";
var pageSize = "15";
function fire_ajax_submit() {

    var search = {};

    search["employeeId"] = $("#employeeId").val();
    search["pageIndex"] = currentPage;
    search["pageSize"] = pageSize;

    $("#btn-search").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/newEmployeeManage/search",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            var msg = data["msg"];
            if (msg == "success") {
                var result = data["result"];
                var ei = "<table class=\"table\">\n" +
                    "\t<caption>查询结果</caption>\n" +
                    "   <thead>\n" +
                    "      <tr>\n" +
                    "         <th>id</th>\n" +
                    "         <th>姓名</th>\n" +
                    "         <th>性别</th>\n" +
                    "         <th>生日</th>\n" +
                    "         <th>学历</th>\n" +
                    "         <th>婚姻状况</th>\n" +
                    "      </tr>\n" +
                    "   </thead>\n" +
                    "   <tbody>\n";
                for (i in result.list) {
                    ei += "<tr>\n         <td>" + result.list[i]["employeeId"] + "</td>\n         <td>" + result.list[i]["fullName"]
                        + "</td>\n         <td>" + result.list[i]["gender"] + "</td>\n         <td>" + result.list[i]["birthDate"]
                        + "</td>\n         <td>" + result.list[i]["educationLevel"] + "</td>\n         <td>" + result.list[i]["maritalStatus"] + "</td>\n      </tr>\n";
                }
                ei += "   </tbody>\n" +
                    "</table>\n";
                setPage(result.pageNum, Math.ceil(result.total/result.pageSize), fire_ajax_submit);
                $('#feedback').html(ei);
            } else {
                //查询不到或者错误信息
                $('#errorMessage').on('show.bs.modal', function (event) {
                    var modal = $(this);
                    modal.find('.modal-body').text(msg);
                })
                $('#errorMessage').modal({
                    backdrop: false,
                    keyboard: false,
                    show: true
                });
            }
            console.log("SUCCESS : ", data);
            $("#btn-search").prop("disabled", false);
        },
        error: function (e) {
            var json = "<h4>error</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);
            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        }
    });

}

/**
 *
 * @param pageCurrent 当前所在页
 * @param pageSum 总页数
 * @param callback 调用ajax
 */
function setPage(pageCurrent, pageSum, callback) {
    $(".pagination").bootstrapPaginator({
        //设置版本号
        bootstrapMajorVersion: 3,
        // 显示第几页
        currentPage: pageCurrent,
        // 总页数
        totalPages: pageSum,
        //当单击操作按钮的时候, 执行该函数, 调用ajax渲染页面
        onPageClicked: function (event,originalEvent,type,page) {
            // 把当前点击的页码赋值给currentPage, 调用ajax,渲染页面
            currentPage = page
            callback && callback()
        }
    })
}