<%-- 
    Document   : ClassList
    Created on : Jun 8, 2022, 7:17:55 PM
    Author     : KHANHHERE
--%>
<%@page import="java.util.Enumeration"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Class</title>
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
            <!-- jQuery -->
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <h2 class="card-title text-uppercase"><strong>Class list</strong></h2>
                                </div>
                                <div class="card-body">
                                    <form action="class" method="get">
                                        <div class="row">
                                            <select class="form-control selectpicker col-2" name="search_term" onchange="this.form.submit()" style="height: 40px; margin-right: 3px;">
                                                <option value="" ${search_term==null?"selected":""}>All class term</option>
                                                <option value="1" ${search_term == "1" ?"selected":""}>Spring</option>
                                                <option value="2" ${search_term == "2" ?"selected":""}>Summer</option>
                                                <option value="3" ${search_term == "3" ?"selected":""}>Fall</option>
                                            </select>
                                            <select class="form-control selectpicker col-2" name="search_status" onchange="this.form.submit()" style="height: 40px;margin-right: 3px;">
                                                <option value="" ${requestScope.search_status==null?"selected":""}>All statuses</option>
                                                <option value="1" ${requestScope.search_status=="1"?"selected":""}>Active</option>
                                                <option value ="2" ${requestScope.search_status=="2"?"selected":""}>Closed</option>
                                                <option value ="3" ${requestScope.search_status=="3"?"selected":""}>Canceled</option>
                                            </select>
                                            <input type="text" style="margin-right: 3px;" value="${requestScope.search_value==null?"":requestScope.search_value}" size="30%" placeholder="Type classID or class code to search" name="search_value"/>
                                            <button style="margin-right: 3px;" type="submit" class="btn btn-primary" >Search</button>                      
                                            <c:if test = "${sessionScope.useraccount.roleId == admin or sessionScope.useraccount.roleId == Author}">
                                                <a href="class?tag=add" style="margin-left: 250px; padding-top: 5px;"><i class="fa fa-plus"></i> Add new</a>
                                            </c:if>
                                        </div>
                                    </form><br/>
                                    <table class="table table-bordered table-hover">
                                        <c:if test="${empty requestScope.listPerPage}">
                                            <h2 style="color: red;">No data found!</h2>
                                        </c:if>
                                        <c:if test="${!empty requestScope.listPerPage}">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Class Code</th>
                                                    <th>Trainer</th>
                                                    <th>Subject</th>
                                                    <th>Class Year</th>
                                                    <th>Class Term</th>
                                                    <th>Block</th>
                                                    <th>Status</th>
                                                    <th>Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${requestScope.listPerPage}" var="l">
                                                    <c:if  test="${l.trainer.status==false or l.subject.status == false}">
                                                        <c:set var="note" scope="request" value="${true}"></c:set>
                                                    </c:if>
                                                    <tr>
                                                        <td>${l.classId}</td>
                                                        <td>${l.classCode}</td>
                                                        <td ${l.trainer.status == true?"":"style='background-color:rgb(255,99,71);'"}>${l.trainer.fullName}</td>
                                                        <td ${l.subject.status == true?"":"style='background-color:rgb(255,99,71);'"}>${l.subject.subjectName}</td>
                                                        <td>${l.classYear}</td>
                                                        <td>
                                                            <c:if test="${l.term == 1}">Spring</c:if>
                                                            <c:if test="${l.term == 2}">Summer</c:if>
                                                            <c:if test="${l.term == 3}">Fall</c:if>
                                                            </td>
                                                            <td style="color: ${l.block5Class==true?"green":"blue"};">${l.block5Class==true?"Block 5":"Block 1-4"}</td>
                                                        <td style="color:${l.status==1?'green':l.status==2?'blue':'red'}">
                                                            <c:if test="${l.status == 1}">Active</c:if>
                                                            <c:if test="${l.status == 2}">Closed</c:if>
                                                            <c:if test="${l.status == 3}">Canceled</c:if>
                                                            </td>
                                                        <c:if test = "${sessionScope.useraccount.roleId == admin or sessionScope.useraccount.roleId == Author}">
                                                            <td>
                                                                <a class="btn btn-info btn-sm" style="margin-right:10%;" href="class?tag=update&id=${l.classId}"><i class="fas fa-pencil-alt"></i></a>
                                                                <a class="btn btn-danger btn-sm" data-toggle="modal" data-target="#modal-default${l.classId}" href="#" ><i class="fa fa-flag"></i></a>    
                                                                <div class="modal fade" id="modal-default${l.classId}">                             
                                                                    <div class="modal-dialog">
                                                                        <div class="modal-content">
                                                                            <div class="modal-header">
                                                                                <h4 class="modal-title  text-center">System Alert</h4>
                                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                                    <span aria-hidden="true">×</span>
                                                                                </button>
                                                                            </div>
                                                                            <div class="modal-body">
                                                                                <p class="text-center">Are you sure to change status of this class?</p>
                                                                            </div>
                                                                            <div class="modal-footer justify-content-center">
                                                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                                                <button type="button" class="btn btn-primary" onclick="window.location = 'class?id=${l.classId}'">Change</button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                        </c:if>
                                                        <c:if test="${sessionScope.useraccount.roleId == Trainer}">
                                                            <td>
                                                                <a href="class?tag=update&id=${l.classId}"><i class="bi bi-eye" style="font-size: 1.2rem; color: blue;"></i></a>
                                                            </td>
                                                        </c:if>
                                                    </tr>
                                                </c:forEach>
                                            </tbody> 
                                            <c:if test="${requestScope.note==true}">
                                                <tfoot>
                                                <small><i class="fa fa-square" style="color: rgb(255,99,71);"></i> Trainers/Subjects are not available</small>
                                                </tfoot>
                                            </c:if>
                                        </c:if>
                                    </table>
                                </div>
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
                        <div class="toast-body">Update class successfully!</div>
                    </c:if>
                    <c:if test="${requestScope.success == 'aSuccess'}">
                        <div class="toast-body">Add a new class successfully!</div>
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
                            <a class="page-link" href="class?curPage=${1}&search_term=${requestScope.search_term}&search_status=${requestScope.search_status}&search_value=${requestScope.search_value}">Begin</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="class?curPage=${curPage-1}&search_term=${requestScope.search_term}&search_status=${requestScope.search_status}&search_value=${requestScope.search_value}">Previous</a>
                        </li>
                    </c:if>
                    <c:forEach begin="${curPage>=2?curPage-1:curPage}" end="${curPage+2>NoPage?NoPage:curPage+2}" var="i">
                        <li class="page-item ${i==curPage?"active":""}">
                            <a class="page-link" href="class?curPage=${i}&search_term=${requestScope.search_term}&search_status=${requestScope.search_status}&search_value=${requestScope.search_value}">${i}</a>
                        </li>
                    </c:forEach>
                    <c:if test="${curPage!=NoPage}">
                        <li class="page-item">
                            <a class="page-link" href="class?curPage=${curPage+1}&search_term=${requestScope.search_term}&search_status=${requestScope.search_status}&search_value=${requestScope.search_value}">Next</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="class?curPage=${NoPage}&search_term=${requestScope.search_term}&search_status=${requestScope.search_status}&search_value=${requestScope.search_value}">End</a>
                        </li>
                    </c:if>
                    </li>
                </ul>
            </nav>    
        </c:if>                                

        <%@include file="../home/Footer.jsp" %>
    </body>
</html>
