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
        <title>Team List</title>
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
                                    <h2 class="card-title text-uppercase"><strong>Team List</strong></h2>
                                </div>
                                <div class="card-body">
                                    <form action="team" method="get">
                                        <div class="row">
                                            <select class="form-control selectpicker col-2" name="search_class" onchange="this.form.submit()" style="height: 40px; margin-right: 3px;">
                                                <option value="">All Class</option>
                                                <c:forEach items="${listClass}" var = "s" >
                                                    <option value="${s.classId}" ${search_class == s.classId ? "selected": ""}>${s.classCode}</option>
                                                </c:forEach>
                                            </select>
                                            <select class="form-control selectpicker col-2" name="search_status" onchange="this.form.submit()" style="height: 40px;margin-right: 3px;">
                                                <option value="" >All Status</option>
                                                <option value="1" ${search_status == "1" ? "selected":""}>Active</option>
                                                <option value="0" ${search_status == "0" ? "selected":""}>	Inactive</option>
                                            </select>
                                            <input type="text" style="margin-right: 3px;" value="${requestScope.search_value==null?"":requestScope.search_value}" size="30%" placeholder="Type Topic Name to search" name="search_value"/>
                                            <button style="margin-right: 3px;" type="submit" class="btn btn-primary" >Search</button>
                                            <a href="team" style=" margin-right: 3px;"><button style="height: 40px;" type="button" class="btn btn-default">Reset </button></a>                      

                                            <a href="team?tag=add" style="margin-left: 200px; padding-top: 5px;"><i class="fa fa-plus"></i> Add new</a>
                                        </div>
                                    </form><br/>
                                    <table class="table table-bordered table-hover">
                                        <c:if test="${list.size() == 0}">
                                            <h2 style="color: red;">No data found!</h2>
                                        </c:if>
                                        <thead>
                                            <tr class="text-center">
                                                <th>Team ID</th>
                                                <th>Team Code</th>
                                                <th>Class Name</th>
                                                <th>Topic Code</th>
                                                <th>Topic Name</th>
                                                <th>Gitlab URL</th>
                                                <th>Status</th>
                                                <th style="width: 130px;">Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${listPerPage}" var="l">
                                                <tr class="text-center" >
                                                    <td>${l.getTeamId()}</td>
                                                    <td>${l.getTeamCode()}</td>
                                                    <td>${l.getClassId().classCode}</td>
                                                    <td>${l.getTopicCode()}</td>
                                                    <td>${l.getTopicName()}</td>
                                                    <td>${l.getGitlabUrl()}</td>
                                                    <td style="color: ${l.isStatus()==true?"green":"red"};">${l.isStatus()==true?"Active":"Inactive"}</td>
                                                    <td>
                                                        <a class="btn btn-info btn-sm" style="margin-right:10%;" href="?id=${l.getTeamId()}&tag=update"><i class="fas fa-pencil-alt"></i></a>
                                                        <a class="btn btn-danger btn-sm"  data-toggle="modal" data-target="#modal-default${l.getTeamId()}" href="#" ><i class="fa fa-flag"></i></a>   
                                                        <div class="modal fade" id="modal-default${l.getTeamId()}">                             
                                                            <div class="modal-dialog">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <h4 class="modal-title  text-center">System Alert</h4>
                                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                            <span aria-hidden="true">×</span>
                                                                        </button>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <p class="text-center">Are you sure to change status of this Team?</p>
                                                                    </div>
                                                                    <div class="modal-footer justify-content-center">
                                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                                        <button type="button" class="btn btn-primary" onclick="window.location = 'team?id=${l.getTeamId()}&isActive=${!l.isStatus()}&tag=changeStatus'">Change</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody> 
                                    </table>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <c:set var="curPage" value="${requestScope.curPage}" />
        <c:set var="NoPage" value="${requestScope.NoPage}" />
        <c:if test="${! empty listPerPage}">
            <nav aria-label="..." id="paging">
                <ul class="pagination d-flex justify-content-center">
                    <li class="page-item disabled">
                        <c:if test="${curPage!=1}">
                        <li class="page-item">
                            <a class="page-link" href="team?curPage=1&search_class=${search_class}&search_status=${search_status}&search_value=${search_value}">Begin</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="team?curPage=${curPage-1}&search_class=${search_class}&search_status=${search_status}&search_value=${search_value}">Previous</a>
                        </li>
                    </c:if>
                    <c:forEach begin="${curPage>=2?curPage-1:curPage}" end="${curPage+2>NoPage?NoPage:curPage+2}" var="i">
                        <li class="page-item ${i==curPage?"active":""}">
                            <a class="page-link" href="team?curPage=${i}&search_class=${search_class}&search_status=${search_status}&search_value=${search_value}">${i}</a>
                        </li>
                    </c:forEach>
                    <c:if test="${curPage!=NoPage}">
                        <li class="page-item">
                            <a class="page-link" href="team?curPage=${curPage+1}&search_class=${search_class}&search_status=${search_status}&search_value=${search_value}">Next</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="team?curPage=${NoPage}&search_class=${search_class}&search_status=${search_status}&search_value=${search_value}">End</a>
                        </li>
                    </c:if>
                    </li>
                </ul>
            </nav>    
        </c:if>                                

        <%@include file="../home/Footer.jsp" %>
    </body>
    <script type="text/javascript">
        function checkForm3() {
            document.getElementById('modal-lg').style.display = 'none';
        }
        setTimeout(function () {
            document.getElementById('modal-lg').classList.add("hide");
        }, 5000);
    </script>
</html>
