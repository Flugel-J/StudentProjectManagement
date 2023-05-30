<%-- 
    Document   : Details
    Created on : Jun 9, 2022, 5:44:09 PM
    Author     : KHANHHERE
--%>
<%@page import="dal.EvalCriteriaDB"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Evaluation Criteria</title>
        <%@include file="../home/HeaderLink.jsp" %>
    </head>
    <body class="layout-top-nav sidebar-closed sidebar-collapse">
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

        <%@include file="../home/Header.jsp" %>
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <div class="container-fluid">
                    <div class="row mb-2">
                        <div class="col-sm-6 card-header">
                            <h1 class="text-uppercase card-title">Criteria ${requestScope.tag}</h1>
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
                                <form id="form" action="evalcriteria" method="post">
                                    <input value="${requestScope.tag}" name="tag" hidden/>
                                    <input type="text" name="id" value="${requestScope.e==null?"0":requestScope.e.criteriaId}" hidden/>
                                    <div class="card-body">
                                        <div class="form-group">          
                                            <label>Iteration</label>
                                            <jsp:useBean id="sbd" class="dal.SubjectDB"/>
                                            <select class="form-control selectpicker" onchange="this.form.submit()" name="iterationId" style="height: 40px;">
                                                <c:forEach items="${requestScope.listI}" var="i">
                                                    <option value="${i.iterationId}" ${iterationId == i.iterationId ?"selected":""}>${i.iterName} - ${sbd.getSubjectById(i.subjectId).subjectName}</option>
                                                </c:forEach>
                                            </select>                                         
                                        </div>

                                        <div class="form-group">
                                            <label for="title">Title*</label>
                                            <input id="title" class="form-control" value="${requestScope.e==null?"Unknown":requestScope.e.evalTitle}" name="evalTitle" required maxlength="30" />
                                        </div>
                                        <div class="form-group">
                                            <label for="weight">Evaluation Weight* (Max is <fmt:formatNumber type="number" maxFractionDigits="1" value="${requestScope.maxWeight}" /> %)</label>
                                            <input id="weight" required maxlength="20" min="0" max="<fmt:formatNumber type="number" maxFractionDigits="1" value="${requestScope.maxWeight}" />" step="0.1" type="number" name="evalWeight" class="form-control" value="${requestScope.e==null?"0":requestScope.e.evalWeight}"/>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-6">
                                                <label for="order">Criteria Order*</label>
                                                <input type="number" id="order" min="1" maxlength="20" pattern="[0-9]" name="criteriaOrder" class="form-control" value="${requestScope.e==null?"0":requestScope.e.criteriaOrder}" required />
                                            </div>
                                            <div class="form-group col-6">
                                                <label for="value">Max Loc*</label>
                                                <input id="value" maxlength="20" min="0" type="number" pattern="[0-9]" name="maxLoc" class="form-control" value="${requestScope.e==null?"0":requestScope.e.maxLoc}" required />
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-6">
                                                <label>Evaluation for*</label> <br/>
                                                <input type="radio" name="teamEval" ${(requestScope.e.teamEval==true||requestScope.e.teamEval==null) ? "checked":""} value="1">Team   &nbsp;&nbsp;
                                                <input type="radio" name="teamEval" ${requestScope.e.teamEval==false ? "checked":""} value="0">Member
                                            </div>  
                                            <div class="form-group col-md-6">
                                                <label>Status*</label> <br/>
                                                <input type="radio" name="status" ${(requestScope.e.status==true || requestScope.e.status==null)  ? "checked":""} value="1">Active   &nbsp;&nbsp;
                                                <input type="radio" name="status" ${requestScope.e.status==false ? "checked":""} value="0">Inactive
                                            </div>  
                                        </div>

                                    </div>
                                    <!-- /.card-body -->

                                    <div class="card-footer">
                                        <button type="reset" class="btn btn-primary">Reset</button>
                                        <button type="submit" class="btn btn-primary" onclick="addAction()">
                                            <c:if test="${requestScope.tag=='details'}">Update</c:if>
                                            <c:if test="${requestScope.tag=='add'}">Add</c:if>
                                            </button>
                                        </div>
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
