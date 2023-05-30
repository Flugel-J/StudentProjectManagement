<%@page import="java.util.Enumeration"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Subject</title>
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
        <div class="content-wrapper">
            <!-- jQuery -->
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <h2 class="card-title text-uppercase"><strong>All subject</strong></h2>
                                </div>
                                <form action="subject" method="get">

                                    <div class="card-body">
                                        <div style="display: flex;">
                                            <select class="form-control selectpicker" style="margin-right: 1%;" name="status" onchange="this.form.submit()">
                                                <option value="" ${requestScope.selectedStatus==null?"selected":""}>All statuses</option>
                                                <option value="1" ${requestScope.selectedStatus=="1"?"selected":""}>Active</option>
                                                <option value ="0" ${requestScope.selectedStatus=="0"?"selected":""}>Inactive</option>
                                            </select>
                                                <input class="form-control" type="text" style="margin-right: 1%;" size="25%" placeholder="Type subject code to search" name="code" value="${selectedCode}"/>
                                                <input class="form-control"  type="text" size="25%" placeholder="Or search by name" name="name" value="${selectedName}"/>
                                            <button  type="submit" class="btn btn-primary" style="height: 37px;">Search</button>
                                            <a href="subject?a=add" style="margin-left: 3%; width: 400px;"><i class="fa fa-plus"></i> Add new</a>
                                        </div><br/>


                                        <!-- /.card-header -->

                                        <table id="example2" class="table table-bordered table-hover">
                                            <thead>
                                                <tr>
                                                    <th>Subject ID</th>
                                                    <th>Subject Code</th>   
                                                    <th>Name</th>
                                                    <th>Author</th>
                                                    <th>Status</th>
                                                    <th>Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>

                                                <c:forEach items="${requestScope.listSubject}" var="l">
                                                    <c:if  test="${l.authorStatus==false}">
                                                        <c:set var="note" scope="request" value="${true}"></c:set>
                                                    </c:if>
                                                    <tr>
                                                        <td>${l.subjectId}</td>

                                                        <td>${l.subjectCode}</td>
                                                        <td>${l.subjectName}</td>
                                                        <td ${l.authorStatus==true? "":"style='background-color:rgb(255,99,71);'"}>${l.author}</td>
                                                        <td>${l.status==true?"<span class='text-success'>Active</span>":"<span class='text-danger'>Inactive</span>"}</td>
                                                        <td>

                                                            <a class="btn btn-info btn-sm" style="margin-right:10%;"  href="subject?id=${l.subjectId}&a=update"><i class="fas fa-pencil-alt"></i></a>
                                                            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#modal-default${l.subjectId}"><i class="fa fa-flag"></i></a>
                                                        </td>
                                                    </tr>
                                                <div class="modal fade" id="modal-default${l.subjectId}">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h4 class="modal-title">System alert</h4>
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                    <span aria-hidden="true">×</span>
                                                                </button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <p>Are you sure you want to change status of this subject?</p>
                                                            </div>
                                                            <div class="modal-footer justify-content-center">
                                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                                <button type="button" class="btn btn-primary" onclick=" window.location = 'subject?id=${l.subjectId}'">Change</button>
                                                            </div>
                                                        </div>
                                                        <!-- /.modal-content -->
                                                    </div>
                                                    <!-- /.modal-dialog -->
                                                </div>
                                            </c:forEach>

                                            </tbody>
                                            <c:if test="${requestScope.note==true}">
                                                <tfoot>
                                                <small><i class="fa fa-square" style="color: rgb(255,99,71)"></i> subject are currently not assign or author are not available</small>
                                                </tfoot>
                                            </c:if>
                                        </table>
                                    </div>
                                    <!-- /.card-body -->
                                </form>
                            </div>
                        </div>
                    </div>
                </div>


            </section>
            <nav aria-label="..." id="paging">
                <ul class="pagination d-flex justify-content-center">
                    <li class="page-item disabled">
                        <c:forEach begin="${1}" end="${requestScope.numpage}" var="i">
                        <li class="page-item ${i==page?"active":""}"><a class="page-link" href="subject?page=${i}&status=${selectedStatus}&code=${selectedCode}&name=${selectedName}">${i}</a></li> 
                        </c:forEach>
                </ul>
                </li>
            </nav>
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
                        <div class="toast-body">Update subject successfully!</div>
                    </c:if>
                    <c:if test="${requestScope.success == 'aSuccess'}">
                        <div class="toast-body">Add a new subject successfully!</div>
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
        <%@include file="../home/Footer.jsp" %>
    </body>
</html>
