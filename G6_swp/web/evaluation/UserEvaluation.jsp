<%-- 
    Document   : UserEvaluation
    Created on : Jun 25, 2022, 10:49:04 AM
    Author     : KHANHHERE
--%>
<%@page import="java.util.Enumeration"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Evaluation</title>
        <%@include file="../home/HeaderLink.jsp" %>
        <script type="text/javascript">
            function addTagExport() {
                var textfield = document.createElement("input");
                textfield.type = "text";
                textfield.value = "export";
                textfield.id = "tag";
                textfield.name = "tag";
                textfield.hidden = "true";
                document.getElementById('form').appendChild(textfield);
            }
            function removeTagExport() {
                var par = document.getElementById("form");
                var ele = document.getElementById("tag");
                if (ele !== null)
                    par.removeChild(ele);
            }
            function onchangeAction(x) {
                removeTagExport();
                x.submit();
            }
        </script>
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
                                    <h2 class="card-title text-uppercase"><strong>User Evaluation list</strong></h2>
                                </div>
                                <div class="card-body">
                                    <form id="form" action="usereval" method="get">
                                        <div class="row">
                                            <select class="form-control selectpicker col-2" name="search_class" onchange="onchangeAction(this.form)" style="height: 40px; margin-right: 3px;">
                                                <c:forEach items="${requestScope.listClass}" var="c">
                                                    <option value="${c.classId}" ${search_class == c.classId ? "selected" : ""}>${c.classCode}</option>
                                                </c:forEach>
                                            </select>
                                            <select class="form-control selectpicker col-2" name="search_team" onchange="onchangeAction(this.form)" style="height: 40px; margin-right: 3px;">
                                                <option value="" ${search_team==null?"selected":""}>All team</option>
                                                <c:forEach items="${requestScope.listTeam}" var="t">
                                                    <option value="${t.teamId}" ${search_team == t.teamId ? "selected" : ""}>${t.teamCode}</option>
                                                </c:forEach>
                                            </select>
                                            <select class="form-control selectpicker col-2" name="search_iter" onchange="onchangeAction(this.form)" style="height: 40px; margin-right: 3px;">
                                                <c:forEach items="${requestScope.listIter}" var="i">
                                                    <option value="${i.iterationId}" ${search_iter == i.iterationId ? "selected" : ""}>${i.iterName}</option>
                                                </c:forEach>
                                            </select>
                                            <input onsubmit="onchangeAction(this.form)" type="text" style="margin-right: 3px;" value="${requestScope.search_value==null?"":requestScope.search_value}" size="25%" placeholder="Type user name to search" name="search_value"/>
                                            <button style="margin-right: 3px;" type="submit" onclick="onchangeAction(this.form)" class="btn btn-primary" >Search</button>   
                                            <c:if test="${sessionScope.useraccount.roleId == 'trainer'}">
                                                <button type="submit" onclick="addTagExport()" class="btn btn-success" style="margin-left: 35px; background-color: #236541;padding: 0.13rem 0.4rem;font-size: 14px;color: white;">
                                                    <i class="bi bi-file-earmark-arrow-down"></i> Export Excel
                                                </button>
                                                <button type="button" data-toggle="modal" data-target="#modal-lgImport" class="btn btn-success" style="margin-left: 10px;padding: 0.13rem 0.4rem;font-size: 14px">
                                                    <a  style="float: right; color: white;"><i class="bi bi-file-earmark-arrow-up"></i> Import </a>
                                                </button>
                                            </c:if>
                                        </div>
                                    </form><br/>
                                    <jsp:useBean id="iedb" class="dal.IterEvalDB" />
                                    <jsp:useBean id="medb" class="dal.MemEvalDB" />
                                    <jsp:useBean id="ecdb" class="dal.EvalCriteriaDB" />
                                    <c:set var="listEC" value="${ecdb.getECOfIter(search_iter)}" />
                                    <table class="table table-bordered table-hover">
                                        <c:if test="${empty requestScope.listPerPage}">
                                            <h2 style="color: red;">No data found!</h2>
                                        </c:if>
                                        <c:if test="${!empty requestScope.listPerPage}">
                                            <thead>                                       
                                                <tr>
                                                    <th rowspan="2" style="padding-bottom: 35px;">Username</th>
                                                    <th rowspan="2" style="padding-bottom: 35px;">Team</th>
                                                    <th rowspan="2" style="width: 70px;padding-bottom: 35px;">Iteration</th>
                                                    <th rowspan="2" style="width: 80px;padding-bottom: 35px;">Team Grade</th>
                                                    <th colspan="${listEC.size()}">Member evaluation</th>
                                                    <th rowspan="2" style="padding-bottom: 35px;">Bonus</th>
                                                    <th rowspan="2" style="padding-bottom: 35px;">Iteration Grade</th>
                                                    <th rowspan="2" style="width: 50px;padding-bottom: 35px;">Action</th>
                                                </tr>
                                                <!--this is for criteria in mem_eval-->                                            
                                                <tr>
                                                    <c:forEach items="${listEC}" var="lec">
                                                        <th>${lec.evalTitle}(${lec.evalWeight}%)</th>
                                                        </c:forEach>
                                                </tr>
                                            </thead>
                                            <tbody>                                         
                                                <c:forEach items="${requestScope.listPerPage}" var="l">    
                                                    <c:set value="${iedb.teamGradeOfIE(l.ie)==-1?'':iedb.teamGradeOfIE(l.ie)/iedb.weightOfTeamGradeOfIE(l.ie)*100}" var="team_grade" />
                                                    <tr>
                                                        <td>${l.ie.user.fullName}</td>
                                                        <td>${l.ie.team.teamCode}</td>
                                                        <td>${l.ie.iteration.iterName}</td>
                                                        <td>
                                                            <fmt:formatNumber type="number" maxFractionDigits="1" value="${team_grade}" />                                         
                                                        </td>
                                                        <c:forEach items="${listEC}" var="lec">
                                                            <td>${medb.getGrade(l.ie.id, lec.criteriaId)==-1?"":medb.getGrade(l.ie.id, lec.criteriaId)}</td>
                                                        </c:forEach>
                                                        <td>${l.ie.bonus}</td>
                                                        <td>${(l.ie.grade + l.ie.bonus>10)?10:l.ie.grade + l.ie.bonus}</td>   
                                                        <td> 
                                                            <a href="usereval?tag=details&id=${l.ie.id}"><i class="bi bi-eye" style="font-size: 1.2rem; color: blue;"></i></a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody> 
                                            <tfoot>
                                            <small>** <b>Grade is included bonus point</b></small>
                                            </tfoot>
                                        </c:if>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <jsp:useBean id="cdb" class="dal.ClassDB" />
        <jsp:useBean id="idb" class="dal.IterationDB" />
        <form action="usereval" method="post" enctype="multipart/form-data">
            <input name="function" value="import" hidden />
            <input name="import_class" value="${requestScope.search_class}" hidden />
            <input name="import_iter" value="${requestScope.search_iter}" hidden />
            <div class="modal fade" id="modal-lgImport" >
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Import User Evaluation</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">×</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <h5>Class ${cdb.getClassById(search_class, null, null).classCode} - ${idb.getIterationByID(search_iter).iterName}</h5>
                            <p><b>Upload .xlsx file</b></p>
                            <input type="file" id="inputImport" name="fileImport" accept=".xlsx" required /><br>
                            <small style="font-size: 0.9em;">** The maximum file size allowed is 10 MB.</small>
                            <div style="display: flex">
                                
                        </div>
                        </div>
                        <div class="mailbox-attachment-info" style="    width: 55%; margin-left: 20px; margin-bottom: 15px;">
                            <p style="color: blue;">Download import template</p>
                            <a href="usereval?tag=export&search_iter=${requestScope.search_iter}&search_class=${requestScope.search_class}" class="mailbox-attachment-name"><i class="fas fa-paperclip"></i> UserEval ${cdb.getClassById(search_class, null, null).classCode}-${idb.getIterationByID(search_iter).iterName}.xlsx</a>
                            <span class="mailbox-attachment-size clearfix mt-1">
                                <a href="usereval?tag=export&search_iter=${requestScope.search_iter}&search_class=${requestScope.search_class}" class="btn btn-default btn-sm float-right"><i class="fas fa-cloud-download-alt"></i></a>
                            </span>
                        </div>
                        
                        <div class="modal-footer justify-content-between">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Import</button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>                     
        </form>
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
                        <div class="toast-body">Update an user evaluation successfully!</div>
                    </c:if>
                    <c:if test="${requestScope.success == 'iSuccess'}">
                        <div class="toast-body">Import successfully!</div>
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
            const input = document.getElementById('inputImport');
            input.addEventListener('change', (event) => {
                const target = event.target;
                if (target.files && target.files[0]) {

                    /*Maximum allowed size in bytes
                     5MB Example
                     Change first operand(multiplier) for your needs*/
                    const maxAllowedSize = 10 * 1024 * 1024; //10MB
                    if (target.files[0].size > maxAllowedSize) {
                        // Here you can ask your users to load correct file
                        target.value = '';
                    }
                }
            });
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
                            <a class="page-link" href="usereval?curPage=${1}&search_class=${requestScope.search_class}&search_team=${requestScope.search_team}&search_iter=${requestScope.search_iter}&search_value=${requestScope.search_value}">Begin</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="usereval?curPage=${curPage-1}&search_class=${requestScope.search_class}&search_team=${requestScope.search_team}&search_iter=${requestScope.search_iter}&search_value=${requestScope.search_value}">Previous</a>
                        </li>
                    </c:if>
                    <c:forEach begin="${curPage>=2?curPage-1:curPage}" end="${curPage+2>NoPage?NoPage:curPage+2}" var="i">
                        <li class="page-item ${i==curPage?"active":""}">
                            <a class="page-link" href="usereval?curPage=${i}&search_class=${requestScope.search_class}&search_team=${requestScope.search_team}&search_iter=${requestScope.search_iter}&search_value=${requestScope.search_value}">${i}</a>
                        </li>
                    </c:forEach>
                    <c:if test="${curPage!=NoPage}">
                        <li class="page-item">
                            <a class="page-link" href="usereval?curPage=${curPage+1}&search_class=${requestScope.search_class}&search_team=${requestScope.search_team}&search_iter=${requestScope.search_iter}&search_value=${requestScope.search_value}">Next</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="usereval?curPage=${NoPage}&search_class=${requestScope.search_class}&search_team=${requestScope.search_team}&search_iter=${requestScope.search_iter}&search_value=${requestScope.search_value}">End</a>
                        </li>
                    </c:if>
                    </li>
                </ul>
            </nav>    
        </c:if>


        <%@include file="../home/Footer.jsp" %>
    </body>
</html>
