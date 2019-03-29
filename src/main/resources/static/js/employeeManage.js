$(document).ready(function () {

    $("#search-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit();

    });

});

var currentPage = "1";
var pageSize = "10";
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
                    "         <th>管理</th>\n" +
                    "      </tr>\n" +
                    "   </thead>\n" +
                    "   <tbody>\n";
                for (i in result.list) {
                    ei += "<tr>\n         <td>" + result.list[i]["employeeId"] + "</td>\n         <td>" + result.list[i]["fullName"]
                        + "</td>\n         <td>" + result.list[i]["gender"] + "</td>\n         <td>" + result.list[i]["birthDate"]
                        + "</td>\n         <td>" + result.list[i]["educationLevel"] + "</td>\n         <td>" + result.list[i]["maritalStatus"];
                    ei += "<td style=\"white-space:nowrap\">\n" +
                        "\t\t<div class=\"btn-group\">\n" +
                        "\t\t\t<button type=\"button\" class=\"btn btn-default\" onclick=\"deleteEmployee(" + result.list[i]["employeeId"] + ", fire_ajax_submit)\">删除</button>\n" +
                        "\t\t\t<button type=\"button\" class=\"btn btn-default\" onclick=\"modifyEmployeeModal(" + result.list[i]["employeeId"] + ", fire_ajax_submit)\">修改</button>\n" +
                        "\t\t</div>\n" +
                        "\t</td>\n      </tr>\n";
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
                });
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

function deleteEmployee(id, callback) {

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/newEmployeeManage/delete/" + id,

        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data.msg == 'delete complete') {
                callback && callback();
            }
            alert(data.msg);
        },
        error: function (e) {
            alert("errorInfo: " + e);
        }
    })
}

function modifyEmployeeModal(id, callback) {

    document.getElementById("model_employee_id").setAttribute("value", id);
    document.getElementById("model_employee_id").setAttribute("readonly", "readonly");

    $('#create_modal').on('show.bs.modal', function (event) {
        var modal = $(this);
        modal.find('.modal-title').text("修改");
    });
    $('#create_modal').modal({
        backdrop: false,
        keyboard: false,
        show: true
    });
}

function createEmployeeModal() {
    document.getElementById("model_employee_id").removeAttribute("value");
    document.getElementById("model_employee_id").removeAttribute("readonly");
    $('#create_modal').on('show.bs.modal', function (event) {
        var modal = $(this);
        modal.find('.modal-title').text("新增员工");
    });
    $('#create_modal').modal({
        backdrop: false,
        keyboard: false,
        show: true
    });
}

function check_form() {
    var employeeId = document.getElementById("model_employee_id").value;
    var firstName = document.getElementById("model_first_name").value;
    var lastName = document.getElementById("model_last_name").value;
    var gender = document.getElementById("model_gender").value;
    var birthDate = document.getElementById("model_birth_date").value;
    var educationLevel = document.getElementById("model_education_level").value;
    var maritalStatus = document.getElementById("model_marital_status").value;
    var salary = document.getElementById("model_salary").value;
    var storeId = document.getElementById("model_store_id").value;
    var departmentId = document.getElementById("model_department_id").value;
    var hireDate = document.getElementById("model_hire_date").value;
    var endDate = document.getElementById("model_end_date").value;
    var employee = {};
    employee.employeeId = employeeId;
    employee.firstName = firstName;
    employee.lastName = lastName;
    employee.gender = gender;
    employee.birthDate = birthDate;
    employee.educationLevel = educationLevel;
    employee.maritalStatus = maritalStatus;
    employee.salary = salary;
    employee.storeId = storeId;
    employee.departmentId = departmentId;
    employee.hireDate = hireDate;
    employee.endDate = endDate;
    employee.fullName = firstName + " " + lastName;

    if (document.getElementById("modal_type").innerText == "新增员工") {
        createEmployee(employee, fire_ajax_submit);
    } else if (document.getElementById("modal_type").innerText == "修改") {
        modifyEmployee(employee, fire_ajax_submit);
    }


}

function createEmployee(employee, callback) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/newEmployeeManage/create",
        data: JSON.stringify(employee),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            var msg = data["msg"];
            console.log("SUCCESS : ", data);
            if (msg == "success") {
                callback && callback();
                alert("添加成功");
            } else {
                alert('添加失败');
            }
        },
        error: function (e) {
            var json = "<h4>error</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);
            console.log("ERROR : ", e);
            alert("ERROR : ", e);

        }
    });
}


function modifyEmployee(employee, callback) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/newEmployeeManage/update",
        data: JSON.stringify(employee),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            var msg = data["msg"];
            console.log("SUCCESS : ", data);
            if (msg == "success") {
                callback && callback();
                alert("修改成功");
            } else {
                alert('修改失败');
            }
        },
        error: function (e) {
            var json = "<h4>error</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);
            console.log("ERROR : ", e);
            alert("ERROR : ", e);

        }
    });
}