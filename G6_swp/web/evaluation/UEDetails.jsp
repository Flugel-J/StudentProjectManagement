<%-- 
    Document   : UEDetails
    Created on : Jun 26, 2022, 9:24:37 PM
    Author     : KHANHHERE
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Evaluation</title>
        <%@include file="../home/HeaderLink.jsp" %>
    </head>
    <body class="layout-top-nav sidebar-closed sidebar-collapse">
        <%@include file="../home/Header.jsp" %>
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <div class="container-fluid">
                    <div class="row mb-2">
                        <div class="col-sm-6 card-header">
                            <h1 class="text-uppercase card-title">User Evaluation Details</h1>
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
                                <form id="form" action="usereval" method="post">
                                    <input type="text" name="id" value="${requestScope.ie.id}" hidden/>
                                    <input type="text" name="tag" value="${requestScope.tag}" hidden />
                                    <div class="card-body">
                                        <div class="form-group">          
                                            <label>Iteration</label>
                                            <input class="form-control" value="${requestScope.ie.iteration.iterName}" readonly required/>
                                        </div>
                                        <div class="form-group">
                                            <label >Team</label>
                                            <input required readonly class="form-control" value="${requestScope.ie.team.teamCode} - ${requestScope.ie.classObj.classCode}"/>
                                        </div>
                                        <div class="form-group">
                                            <label >User</label>
                                            <input  required readonly class="form-control" value="${requestScope.ie.user.fullName}"/>
                                        </div>
                                        <div class="form-group">
                                            <label>Grade (Not included bonus)</label>
                                            <input class="form-control" value="${requestScope.ie.grade}" required readonly />
                                        </div>
                                        <div class="form-group">
                                            <label for="b">Bonus</label>
                                            <input id="b" name="bonus" ${sessionScope.useraccount.roleId == 'student' ? "readonly":""} maxlength="20" min="0" max="10" step="0.1" type="number" pattern="[0-9]" class="form-control" value="${requestScope.ie.bonus}" />
                                        </div>
                                        <div class="form-group">
                                            <label for="weight">Note</label>                                         
                                            <textarea name="note" ${sessionScope.useraccount.roleId == 'student' ? "readonly":""} id="weight" maxlength="200" class="form-control" cols="20" rows="5">${requestScope.ie.note}</textarea>
                                        </div> 

                                    </div>
                                    <!-- /.card-body -->
                                    <c:if test="${sessionScope.useraccount.roleId == 'trainer'}">
                                        <div class="card-footer">
                                            <button type="reset" class="btn btn-primary">Reset</button>
                                            <button type="submit" class="btn btn-primary">Update</button>
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
