<%-- 
    Document   : TeamEvalDetail
    Created on : Jun 22, 2022, 9:29:57 PM
    Author     : KHANHHERE
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Team Evaluation</title>
        <%@include file="../home/HeaderLink.jsp" %>
        <script type="text/javascript">
            function addAction() {
                var textfield = document.createElement("input");
                textfield.type = "text";
                textfield.value = "action";
                textfield.id = "act";
                textfield.name = "act";
                textfield.hidden = "true";
                document.getElementById('form').appendChild(textfield);
            }
            function stopAction() {
                var par = document.getElementById("form");
                var ele = document.getElementById("act");
                par.removeChild(ele);
            }
        </script>
    </head>
    <body class="layout-top-nav sidebar-closed sidebar-collapse">
        <%@include file="../home/Header.jsp" %>
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <div class="container-fluid">
                    <div class="row mb-2">
                        <div class="col-sm-6 card-header">
                            <h1 class="text-uppercase card-title">Team Evaluation ${requestScope.tag}</h1>
                        </div>
                    </div>
                </div><!-- /.container-fluid -->
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <!-- left column -->
                        <div class="col-md-10">
                            <!-- general form elements -->
                            <div class="card card-primary">
                                <!-- /.card-header -->
                                <!-- form start -->
                                <form id="form" action="teameval" method="post">
                                    <input value="${requestScope.tag}" name="tag" hidden/>
                                    <input type="text" name="id" value="${requestScope.te==null?"0":requestScope.te.id}" hidden/>
                                    <div class="card-body">
                                        <c:if test="${requestScope.tag=='details'}">
                                            <div class="form-group">          
                                                <label>Team</label>
                                                <input class="form-control" value="${requestScope.te.team.teamCode} - ${requestScope.te.team.classId.classCode}" readonly/>                                      
                                            </div>
                                            <div class="form-group">
                                                <label>Iteration - Subject</label>
                                                <input class="form-control" value="${requestScope.te.evalCriteria.iteration.iterName} - ${requestScope.te.team.classId.subject.subjectName}" readonly />
                                            </div>
                                            <div class="form-group">
                                                <label>Evaluation Criteria</label>
                                                <input class="form-control" value="${requestScope.te.evalCriteria.evalTitle} (Weight: ${requestScope.te.evalCriteria.evalWeight}%)" readonly />
                                            </div>
                                            <div class="form-group">
                                                <label for="value">Grade*</label>
                                                <input id="value" maxlength="20" ${sessionScope.useraccount.roleId == 'student' ? "readonly":""} min="0" max="10" step="0.1" type="number" name="grade" value="${requestScope.te.grade}" class="form-control" required />
                                            </div>
                                            <div class="form-group">
                                                <label for="weight">Note</label>                                         
                                                <textarea name="note" id="weight" ${sessionScope.useraccount.roleId == 'student' ? "readonly":""} maxlength="200" class="form-control" cols="20" rows="5">${requestScope.te.note}</textarea>
                                            </div>

                                        </c:if>
                                        <c:if test="${requestScope.tag=='add'}">
                                            <div class="form-group">          
                                                <label>Team</label>
                                                <select class="form-control selectpicker" onchange="this.form.submit()" name="teamId" style="height: 40px;">
                                                    <option value="">Assign to team first</option>
                                                    <c:forEach items="${requestScope.listTeam}" var="t">
                                                        <option value="${t.teamId}" ${requestScope.teamId == t.teamId?"selected":""} >${t.teamCode} - ${t.classId.classCode}</option>
                                                    </c:forEach>
                                                </select>                                
                                            </div>
                                            <c:if test="${requestScope.teamId != null}">
                                                <div class="form-group">
                                                    <label>Subject</label>
                                                    <input class="form-control" value="${requestScope.team.classId.subject.subjectName}" readonly />
                                                </div>
                                                <div class="form-group">          
                                                    <label>Evaluation Criteria</label>
                                                    <select class="form-control selectpicker" name="criteriaId" style="height: 40px;">
                                                        <c:forEach items="${requestScope.listEC}" var="ec">
                                                            <option value="${ec.criteriaId}" ${requestScope.criteriaId== ec.criteriaId?"selected":""} >${ec.iteration.iterName} - ${ec.evalTitle} (Weight: ${ec.evalWeight}%)</option>
                                                        </c:forEach>
                                                    </select>                                
                                                </div>
                                                <div class="form-group">
                                                    <label for="value">Grade*</label>
                                                    <input id="value" maxlength="20"  min="0" max="10" step="0.1" type="number" name="grade" value="${requestScope.grade}" class="form-control" required />
                                                </div>
                                                <div class="form-group">
                                                    <label for="weight">Note</label>                                         
                                                    <textarea name="note" id="weight" maxlength="200" class="form-control" cols="20" rows="5">${requestScope.note}</textarea>
                                                </div>
                                            </c:if>
                                        </c:if>
                                    </div>
                                    <!-- /.card-body -->
                                    <c:if test="${sessionScope.useraccount.roleId == 'trainer'}">
                                        <div class="card-footer">
                                            <button type="reset" class="btn btn-primary">Reset</button>
                                            <button type="submit" class="btn btn-primary" onclick="addAction()">
                                                <c:if test="${requestScope.tag=='details'}">Update</c:if>
                                                <c:if test="${requestScope.tag=='add'}">Add</c:if>
                                                </button>
                                            </div>
                                    </c:if>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <%@include file="../home/Footer.jsp" %>
    </body>
</html>
