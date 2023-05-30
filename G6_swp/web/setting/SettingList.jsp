<%@page import="java.util.Enumeration"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>System setting</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <%@include file="../home/HeaderLink.jsp" %>

    </head>
    <body class="layout-top-nav sidebar-closed sidebar-collapse">
        <%@include file="../home/Header.jsp" %>

        <%
            String str = request.getAttribute("javax.servlet.forward.request_uri") + "?";
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);
                for (int i = 0; i < paramValues.length; i++) {
                    String paramValue = paramValues[i];
                    str = str + paramName + "=" + paramValue;
                    str = str + "&";
                }
            }
        %>
        <c:set scope="session" var="pre_url" value="<%= str.substring(0, str.length() - 1)%>" />
        <!-- ./wrapper -->

        <!-- jQuery -->

        <div class="content-wrapper">
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <h2 class="card-title text-uppercase"><strong>System setting</strong></h2>
                                </div>

                                &nbsp;&nbsp; &nbsp;&nbsp;  
                                <form action="setting" method="get">
                                    <div style="display: flex;">
                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                                        <select class="form-control selectpicker" name="type" onchange="this.form.submit()" style="width: 15%; margin-left: 13px; height: 35px;">
                                            <option value="" ${selectedType==null?"selected":""}>All setting type</option>
                                            <c:forEach items="${requestScope.settingType}" var="t">
                                                <option value="${t.key}" ${selectedType == t.key ?"selected":""}>${t.value}</option>
                                            </c:forEach>
                                        </select> 
                                        <select class="form-control selectpicker" name="status" onchange="this.form.submit()" style="width: 15%; margin-left: 13px; height: 35px;">
                                            <option value="" ${requestScope.selectedStatus==null?"selected":""}>All statuses</option>
                                            <option value="1" ${requestScope.selectedStatus=="1"?"selected":""}>Active</option>
                                            <option value ="0" ${requestScope.selectedStatus=="0"?"selected":""}>Inactive</option>
                                        </select>
                                        &nbsp;&nbsp; &nbsp;&nbsp;
                                        <input class="form-control form-control-lg" style="height: 35px; width: 30%" type="text" size="10%" placeholder="Type a title to search" name="name"/>
                                        <button style="height: 35px;" type="submit" class="btn btn-primary" >Search</button>
                                        <a href="setting?tag=add" style="margin-left: 130px; padding-top: 5px;"><i class="fa fa-plus"></i> Add new</a>
                                    </div>
                                </form>

                                <!-- /.card-header -->
                                <div class="card-body">
                                    <table id="example2" class="table table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Setting Type</th>
                                                <th>Title</th>
                                                <th>Order</th>
                                                <th>Value</th>
                                                <th>Status</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:if test="${!empty listSetting}">
                                                <c:forEach items="${requestScope.listSetting}" var="l">
                                                    <tr>
                                                        <td>${l.settingId}</td>

                                                        <c:forEach items="${settingType}" var="s">
                                                            <c:if test="${s.key==l.typeId}">
                                                                <td>${s.value}</td>
                                                            </c:if>
                                                        </c:forEach>
                                                        <td>${l.settingTitle}</td>
                                                        <td>${l.displayOrder}</td>
                                                        <td>${l.settingValue}</td>
                                                        <td style="color: ${l.status==true?"green":"red"};">${l.status==true?"Active":"Inactive"}</td>
                                                        <td>
                                                            <a class="btn btn-info btn-sm" style="margin-right:20%;" href="setting?tag=update&id=${l.settingId}"><i class="fas fa-pencil-alt"></i></a>
                                                            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#modal-default${l.settingId}"><i class="fa fa-flag"></i></a>                                             
                                                        </td>
                                                    </tr>
                                                <div class="modal fade" id="modal-default${l.settingId}">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h4 class="modal-title">System alert</h4>
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                    <span aria-hidden="true">×</span>
                                                                </button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <p>Are you sure you want to change status of this setting?</p>
                                                            </div>
                                                            <div class="modal-footer justify-content-center">
                                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                                <button type="button" class="btn btn-primary" onclick=" window.location = 'setting?id=${l.settingId}'">Change</button>
                                                            </div>
                                                        </div>
                                                        <!-- /.modal-content -->
                                                    </div>
                                                    <!-- /.modal-dialog -->
                                                </div>
                                            </c:forEach>
                                        </c:if>
                                        </tbody>

                                    </table>
                                </div>
                                <!-- /.card-body -->
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <c:if test="${requestScope.success!= null}">
            <div id="modal-lg" class="toasts-top-right fixed" >
                <div class="toast bg-success fade show" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header">
                        <strong class="mr-auto">Notification</strong>
                        <button  type="button" class="close" data-dismiss="toast" aria-label="Close">
                            <span onclick="checkForm3()" aria-hidden="true">×</span>
                        </button>
                    </div>
                    <c:if test="${requestScope.success == 'uSuccess'}">
                        <div class="toast-body">Update a setting successfully!</div>
                    </c:if>
                    <c:if test="${requestScope.success == 'aSuccess'}">
                        <div class="toast-body">Add a new setting successfully!</div>
                    </c:if>
                </div>
            </div>
        </c:if>
        <style>
            .hide{
                display: none;
            }
        </style>
        <script type="text/javascript">
            function checkForm3() {
                document.getElementById('modal-lg').style.display = 'none';
            }
            setTimeout(function () {
                document.getElementById('modal-lg').classList.add("hide");
            }, 5000);
        </script>
        <nav aria-label="..." id="paging">
            <ul class="pagination d-flex justify-content-center">
                <li class="page-item disabled">
                    <c:forEach begin="${1}" end="${requestScope.numpage}" var="i">
                    <li class="page-item ${i==page?"active":""}"><a class="page-link" href="setting?page=${i}&type=${selectedType}&status=${selectedStatus}&name=">${i}</a></li> 
                    </c:forEach>
            </ul>
        </li>
    </nav> 
    <%@include file="../home/Footer.jsp" %>
</body>
</html>
