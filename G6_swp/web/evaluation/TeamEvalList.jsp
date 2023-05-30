<%-- 
    Document   : TeamEval
    Created on : Jun 22, 2022, 8:49:56 PM
    Author     : KHANHHERE
--%>
<%@page import="java.util.Enumeration"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Team Evaluation</title>
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
                                    <h2 class="card-title text-uppercase"><strong>Team Evaluation list</strong></h2>
                                </div>
                                <div class="card-body">
                                    <form action="teameval" method="get">
                                        <div class="row">
                                            <select class="form-control selectpicker col-2" name="search_team" onchange="this.form.submit()" style="height: 40px; margin-right: 3px;">
                                                <option value="" ${search_team==null?"selected":""}>All team</option>
                                                <c:forEach items="${requestScope.listTeam}" var="t">
                                                    <option value="${t.teamId}" ${search_team == t.teamId ? "selected" : ""}>${t.teamCode} - ${t.classId.classCode}</option>
                                                </c:forEach>
                                            </select>
                                            <input type="text" style="margin-right: 3px;" value="${requestScope.search_value==null?"":requestScope.search_value}" size="35%" placeholder="Type here to search" name="search_value"/>
                                            <button style="margin-right: 3px;" type="submit" class="btn btn-primary" >Search</button>                                             
                                            <c:if test = "${sessionScope.useraccount.roleId == Trainer}">
                                                <a href="teameval?tag=add" style="margin-left: 400px; padding-top: 5px;"><i class="fa fa-plus"></i> Add new</a>
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
                                                    <th>Team</th>
                                                    <th>Class</th>
                                                    <th>Subject</th>
                                                    <th>Iteration</th>
                                                    <th>Criteria</th>
                                                    <th>Weight</th>
                                                    <th>Grade</th>
                                                    <th style="width: 110px;">Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <jsp:useBean id="edb" class="dal.EvalCriteriaDB"/>
                                                <jsp:useBean id="sdb" class="dal.SubjectDB" />
                                                <c:forEach items="${requestScope.listPerPage}" var="l">                                             
                                                    <tr>
                                                        <td>${l.id}</td>
                                                        <td>${l.team.teamCode}</td>
                                                        <td>${l.team.classId.classCode}</td>
                                                        <td>${l.team.classId.subject.subjectName}</td>
                                                        <td>${l.evalCriteria.iteration.iterName}</td>
                                                        <td>${l.evalCriteria.evalTitle}</td>
                                                        <td>${l.evalCriteria.evalWeight} %</td>
                                                        <td>${l.grade}</td>     
                                                        <c:if test="${sessionScope.useraccount.roleId == Trainer}">
                                                            <td>
                                                                <a class="btn btn-info btn-sm" style="margin-right:10%;" href="teameval?tag=details&id=${l.id}"><i class="fas fa-pencil-alt"></i></a>
                                                                <a class="btn btn-danger btn-sm" data-toggle="modal" data-target="#modal-default${l.id}" href="#" ><i class="fa fa-trash"></i></a>    
                                                                <div class="modal fade" id="modal-default${l.id}">                             
                                                                    <div class="modal-dialog">
                                                                        <div class="modal-content">
                                                                            <div class="modal-header">
                                                                                <h4 class="modal-title  text-center">System Alert</h4>
                                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                                    <span aria-hidden="true">×</span>
                                                                                </button>
                                                                            </div>
                                                                            <div class="modal-body">
                                                                                <p class="text-center">Are you sure to delete this evaluation ?</p>
                                                                            </div>
                                                                            <div class="modal-footer justify-content-center">
                                                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                                                <button type="button" class="btn btn-primary" onclick="window.location = 'teameval?id=${l.id}'">Delete</button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>                          
                                                            </td>
                                                        </c:if>
                                                        <c:if test="${sessionScope.useraccount.roleId == 'student'}">
                                                            <td>
                                                                <a href="teameval?tag=details&id=${l.id}"><i class="bi bi-eye" style="font-size: 1.2rem; color: blue;"></i></a>
                                                            </td>
                                                        </c:if>
                                                    </tr>
                                                </c:forEach>
                                            </tbody> 
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
                        <div class="toast-body">Update a team evaluation successfully!</div>
                    </c:if>
                    <c:if test="${requestScope.success == 'aSuccess'}">
                        <div class="toast-body">Add a team evaluation successfully!</div>
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
                            <a class="page-link" href="teameval?curPage=${1}&search_team=${requestScope.search_team}&search_value=${requestScope.search_value}">Begin</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="teameval?curPage=${curPage-1}&search_team=${requestScope.search_team}&search_value=${requestScope.search_value}">Previous</a>
                        </li>
                    </c:if>
                    <c:forEach begin="${curPage>=2?curPage-1:curPage}" end="${curPage+2>NoPage?NoPage:curPage+2}" var="i">
                        <li class="page-item ${i==curPage?"active":""}">
                            <a class="page-link" href="teameval?curPage=${i}&search_team=${requestScope.search_team}&search_value=${requestScope.search_value}">${i}</a>
                        </li>
                    </c:forEach>
                    <c:if test="${curPage!=NoPage}">
                        <li class="page-item">
                            <a class="page-link" href="teameval?curPage=${curPage+1}&search_team=${requestScope.search_team}&search_value=${requestScope.search_value}">Next</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="teameval?curPage=${NoPage}&search_team=${requestScope.search_team}&search_value=${requestScope.search_value}">End</a>
                        </li>
                    </c:if>
                    </li>
                </ul>
            </nav>    
        </c:if>    
        <%@include file="../home/Footer.jsp" %>
    </body>
</html>
