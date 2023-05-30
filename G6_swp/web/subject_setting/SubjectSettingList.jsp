<%-- 
    Document   : SubjectSettingList
    Created on : May 21, 2022, 9:14:47 PM
    Author     : KHANHHERE
--%>
<%@page import="java.util.Enumeration"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Subject Setting</title>
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
        <div class="content-wrapper">
            <!-- Main content -->
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <h2 class="card-title text-uppercase"><strong>Subject Setting List</strong></h2>
                                </div>
                                <form action="subjectsetting" method="get">
                                    <div class="card-body">
                                        <div class="row" style="display: flex;">
                                            <select class="form-control selectpicker col-2" name="search_type" onchange="this.form.submit()" style="height: 35px; margin-right: 3px;">
                                                <option value="" ${search_type==null?"selected":""}>All setting type</option>
                                                <c:forEach items="${requestScope.listType}" var="t">
                                                    <option value="${t.key}" ${search_type == t.key ?"selected":""}>${t.value}</option>
                                                </c:forEach>
                                            </select>
                                            <select class="form-control selectpicker col-2" name="search_status" onchange="this.form.submit()" style="height: 35px;margin-right: 3px;">
                                                <option value="" ${requestScope.search_status==null?"selected":""}>All statuses</option>
                                                <option value="1" ${requestScope.search_status=="1"?"selected":""}>Active</option>
                                                <option value ="0" ${requestScope.search_status=="0"?"selected":""}>Inactive</option>
                                            </select>                              
                                            <input type="text" style="margin-right: 3px;" value="${requestScope.search_value==null?"":requestScope.search_value}" size="35%" placeholder="Type setting title, subject name to search" name="search_value"/>
                                            <button style="height: 35px;" type="submit" class="btn btn-primary" >Search</button>               
                                            <a href="subjectsetting?tag=add" style="margin-left: 240px;"><i class="fa fa-plus"></i> Add new</a>
                                        </div> <br/>
                                        <table class="table table-bordered table-hover">
                                            <c:if test="${empty requestScope.listPerPage}">
                                                <h2 style="color: red;">No data found!</h2>
                                            </c:if>
                                            <c:if test="${!empty requestScope.listPerPage}">  
                                                <thead>
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Subject Name</th>
                                                        <th>Type</th>
                                                        <th>Title</th>
                                                        <th>Value</th>
                                                        <th>Display Order</th>
                                                        <th>Status</th>
                                                        <th>Action</th>

                                                    </tr>
                                                </thead>
                                                <tbody>      
                                                    <jsp:useBean id="sdb" class="dal.SubjectDB" />
                                                    <c:forEach items="${requestScope.listPerPage}" var="l">
                                                        <tr>
                                                            <td>${l.settingId}</td>
                                                            <td>${sdb.getSubjectById(l.subjectId).subjectName}</td> 
                                                            <c:forEach items="${requestScope.listType}" var="t">
                                                                <c:if test="${t.key==l.typeId}">
                                                                    <td>${t.value}</td>
                                                                </c:if>
                                                            </c:forEach>
                                                            <td>${l.settingTitle}</td>
                                                            <td>${l.settingValue}</td>
                                                            <td>${l.displayOrder}</td>
                                                            <td style="color: ${l.status==true?"green":"red"};">${l.status==true?"Active":"Inactive"}</td>
                                                            <td>
                                                                <a class="btn btn-info btn-sm" style="margin-right:10%;" href="subjectsetting?tag=update&id=${l.settingId}"><i class="fas fa-pencil-alt"></i></a>
                                                                <a class="btn btn-danger btn-sm" data-toggle="modal" data-target="#modal-default${l.settingId}" href="#" ><i class="fa fa-flag"></i></a>    
                                                                <div class="modal fade" id="modal-default${l.settingId}">                             
                                                                    <div class="modal-dialog">
                                                                        <div class="modal-content">
                                                                            <div class="modal-header">
                                                                                <h4 class="modal-title  text-center">System Alert</h4>
                                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                                    <span aria-hidden="true">×</span>
                                                                                </button>
                                                                            </div>
                                                                            <div class="modal-body">
                                                                                <p class="text-center">Are you sure to change status of this subject setting?</p>
                                                                            </div>
                                                                            <div class="modal-footer justify-content-center">
                                                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                                                <button type="button" class="btn btn-primary" onclick="window.location = 'subjectsetting?id=${l.settingId}'">Change</button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                </form>
                            </div>
                            <!-- /.card-body -->
                        </div>
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </section>
            <!-- /.content -->
        </div>
        <!-- /.container-fluid -->
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
                        <div class="toast-body">Update a subject setting successfully!</div>
                    </c:if>
                    <c:if test="${requestScope.success == 'aSuccess'}">
                        <div class="toast-body">Add a new subject setting successfully!</div>
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
        <c:set var="curPage" value="${requestScope.curPage}" />
        <c:set var="NoPage" value="${requestScope.NoPage}" />
        <c:if test="${! empty requestScope.listPerPage}">
            <nav aria-label="..." id="paging">
                <ul class="pagination d-flex justify-content-center">
                    <li class="page-item disabled">
                        <c:if test="${curPage!=1}">
                        <li class="page-item">
                            <a class="page-link" href="subjectsetting?curPage=${1}&search_type=${requestScope.search_type}&search_status=${requestScope.search_status}&search_value=${requestScope.search_value}&search_subject_id=${requestScope.search_subject_id}">Begin</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="subjectsetting?curPage=${curPage-1}&search_type=${requestScope.search_type}&search_status=${requestScope.search_status}&search_value=${requestScope.search_value}&search_subject_id=${requestScope.search_subject_id}">Previous</a>
                        </li>
                    </c:if>
                    <c:forEach begin="${curPage>=2?curPage-1:curPage}" end="${curPage+2>NoPage?NoPage:curPage+2}" var="i">
                        <li class="page-item ${i==curPage?"active":""}">
                            <a class="page-link" href="subjectsetting?curPage=${i}&search_type=${requestScope.search_type}&search_status=${requestScope.search_status}&search_value=${requestScope.search_value}&search_subject_id=${requestScope.search_subject_id}">${i}</a>
                        </li>
                    </c:forEach>
                    <c:if test="${curPage!=NoPage}">
                        <li class="page-item">
                            <a class="page-link" href="subjectsetting?curPage=${curPage+1}&search_type=${requestScope.search_type}&search_status=${requestScope.search_status}&search_value=${requestScope.search_value}&search_subject_id=${requestScope.search_subject_id}">Next</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="subjectsetting?curPage=${NoPage}&search_type=${requestScope.search_type}&search_status=${requestScope.search_status}&search_value=${requestScope.search_value}&search_subject_id=${requestScope.search_subject_id}">End</a>
                        </li>
                    </c:if>
                    </li>
                </ul>
            </nav>    
        </c:if>

        <%@include file="../home/Footer.jsp" %>

    </body>
</html>
