<%-- 
    Document   : List
    Created on : Jun 9, 2022, 5:44:01 PM
    Author     : KHANHHERE
--%>
<%@page import="dal.EvalCriteriaDB"%>
<%@page import="java.util.Enumeration"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Evaluation Criteria</title>
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
                                    <h2 class="card-title text-uppercase"><strong>Criteria list</strong></h2>
                                </div>
                                <div class="card-body">
                                    <form action="evalcriteria" method="get">
                                        <div class="row">
                                            <select class="form-control selectpicker col-2" name="search_teamEval" onchange="this.form.submit()" style="height: 40px; margin-right: 3px;">
                                                <option value="" ${search_teamEval==null?"selected":""}>All evaluation</option>
                                                <option value="1" ${search_teamEval == "1" ?"selected":""}>Team Evaluation</option>
                                                <option value="0" ${search_teamEval == "0" ?"selected":""}>Member Evaluation</option>
                                            </select>
                                            <select class="selectpicker form-control col-2" name="search_status" onchange="this.form.submit()" style="height: 40px;margin-right: 3px;">
                                                <option value="" ${requestScope.search_status==null?"selected":""}>All statuses</option>
                                                <option value="1" ${requestScope.search_status=="1"?"selected":""}>Active</option>
                                                <option value ="0" ${requestScope.search_status=="0"?"selected":""}>Inactive</option>
                                            </select>
                                            <input type="text" style="margin-right: 3px;" value="${requestScope.search_value==null?"":requestScope.search_value}" size="35%" placeholder="Type iteration, subject or title to search" name="search_value"/>
                                            <button style="margin-right: 3px;" type="submit" class="btn btn-primary" >Search</button>                     
                                            <c:if test = "${sessionScope.useraccount.roleId == admin or sessionScope.useraccount.roleId == Author}">
                                                <a href="evalcriteria?tag=add" style="margin-left: 255px; padding-top: 5px;"><i class="fa fa-plus"></i> Add new</a>
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
                                                    <th>Iteration</th>
                                                    <th>Subject</th>
                                                    <th>Title</th>
                                                    <th>Weight</th>
                                                    <th>Evaluation for</th>
                                                    <th>Order</th>
                                                    <th>Max Loc</th>
                                                    <th>Status</th>
                                                    <th style="width: 110px;">Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <jsp:useBean id="edb" class="dal.EvalCriteriaDB"/>
                                                <jsp:useBean id="sdb" class="dal.SubjectDB" />
                                                <c:forEach items="${requestScope.listPerPage}" var="l">
                                                    <c:set var="id" value="${l.iteration.iterationId}" />
                                                    <c:set var="teamEval" value="${l.teamEval}" />
                                                    <c:if  test="${edb.getTotalWeight(id)!=100}">
                                                        <c:set var="note" scope="request" value="${true}"></c:set>
                                                    </c:if>                                               
                                                    <tr style="background-color: ${edb.getTotalWeight(id)==100?"":"rgba(255, 255, 0, 0.5)"}">
                                                        <td>${l.criteriaId}</td>
                                                        <td>${l.iteration.iterName}</td>
                                                        <td>${sdb.getSubjectById(l.iteration.subjectId).subjectName}</td>
                                                        <td>${l.evalTitle}</td>
                                                        <td>${l.evalWeight} %</td>
                                                        <td>
                                                            <!--need icon-->
                                                            <c:if test="${l.teamEval}">Team</c:if>
                                                            <c:if test="${!l.teamEval}">Member</c:if>
                                                            </td>
                                                            <td>${l.criteriaOrder}</td>
                                                        <td>${l.maxLoc}</td>                       
                                                        <td style="color: ${l.status==true?"green":"red"};">${l.status==true?"Active":"Inactive"}</td>
                                                        <td>
                                                            <a  class="btn btn-info btn-sm" style="margin-right:10%;" href="evalcriteria?tag=details&id=${l.criteriaId}"><i class="fas fa-pencil-alt"></i></a>
                                                            <a class="btn btn-danger btn-sm" data-toggle="modal" data-target="#modal-default${l.criteriaId}" href="#" ><i class="fa fa-flag"></i></a>    
                                                            <div class="modal fade" id="modal-default${l.criteriaId}">                             
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
                                                                            <button type="button" class="btn btn-primary" onclick="window.location = 'evalcriteria?id=${l.criteriaId}'">Change</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody> 
                                            <c:if test="${requestScope.note==true}">
                                                <tfoot>
                                                <small><i class="fa fa-square" style="color: rgba(255, 255, 0, 0.5)"></i> Total evaluation weight of iteration ≠ 100%</small>
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
                        <div class="toast-body">Update a criteria successfully!</div>
                    </c:if>
                    <c:if test="${requestScope.success == 'aSuccess'}">
                        <div class="toast-body">Add a new criteria successfully!</div>
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
                            <a class="page-link" href="evalcriteria?curPage=${1}&search_teamEval=${requestScope.search_teamEval}&search_status=${requestScope.search_status}&search_value=${requestScope.search_value}">Begin</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="evalcriteria?curPage=${curPage-1}&search_teamEval=${requestScope.search_teamEval}&search_status=${requestScope.search_status}&search_value=${requestScope.search_value}">Previous</a>
                        </li>
                    </c:if>
                    <c:forEach begin="${curPage>=2?curPage-1:curPage}" end="${curPage+2>NoPage?NoPage:curPage+2}" var="i">
                        <li class="page-item ${i==curPage?"active":""}">
                            <a class="page-link" href="evalcriteria?curPage=${i}&search_teamEval=${requestScope.search_teamEval}&search_status=${requestScope.search_status}&search_value=${requestScope.search_value}">${i}</a>
                        </li>
                    </c:forEach>
                    <c:if test="${curPage!=NoPage}">
                        <li class="page-item">
                            <a class="page-link" href="evalcriteria?curPage=${curPage+1}&search_teamEval=${requestScope.search_teamEval}&search_status=${requestScope.search_status}&search_value=${requestScope.search_value}">Next</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="evalcriteria?curPage=${NoPage}&search_teamEval=${requestScope.search_teamEval}&search_status=${requestScope.search_status}&search_value=${requestScope.search_value}">End</a>
                        </li>
                    </c:if>
                    </li>
                </ul>
            </nav>    
        </c:if>                                

        <%@include file="../home/Footer.jsp" %>

    </body>
</html>
