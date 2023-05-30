<%-- 
    Document   : SubjectSettingDetail
    Created on : May 22, 2022, 10:21:04 AM
    Author     : KHANHHERE
--%>
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
        <c:if test="${requestScope.tag eq 'update'}">
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <div class="container-fluid">
                        <div class="row mb-2">
                            <div class="col-sm-6 card-header">
                                <h1 class="text-uppercase card-title">Subject Setting Details</h1>
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
                                    <form action="subjectsetting" method="post">
                                        <input value="update" name="tag" hidden/>
                                        <input type="text" name="id" value="${requestScope.ss.settingId}" hidden/>
                                        <input type="text" name="subjectId" value="${requestScope.subject.subjectId}" hidden />
                                        <div class="card-body">
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label>Subject Code</label>
                                                    <input class="form-control" value="${requestScope.subject.subjectCode}" readonly/>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label>Subject Name</label>
                                                    <input class="form-control" value="${requestScope.subject.subjectName}" readonly/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>Type</label>
                                                <input class="form-control" value="${requestScope.ssType}" readonly />
                                                <input name="typeId" value="${requestScope.ss.typeId}" hidden />
                                            </div>
                                            <div class="form-group">
                                                <label for="title">Title*</label>
                                                <input id="title" required maxlength="40" type="text" name="settingTitle" class="form-control" value="${requestScope.ss.settingTitle}" />
                                            </div>
                                            <div class="form-group">
                                                    <label for="value">Value</label>
                                                    <input id="value" maxlength="40" type="text" name="settingValue" class="form-control" value="${requestScope.ss.settingValue}" />
                                                </div>
                                            <div class="row">                                              
                                                <div class="form-group col-md-4" style="margin-right: 30px;">
                                                    <label for="order">Display Order*</label>
                                                    <input type="number" id="order" min="1" name="displayOrder" class="form-control" value="${requestScope.ss.displayOrder}" required />
                                                </div>
                                                <div class="form-group">
                                                <label>Status*</label> <br/>
                                                <input type="radio" name="status" ${ss.status==true ? "checked":""} value="1">Active   &nbsp;&nbsp;
                                                <input type="radio" name="status" ${ss.status==false ? "checked":""} value="0">Inactive
                                            </div>
                                            </div>
                                            
                                        </div>
                                        <!-- /.card-body -->

                                        <div class="card-footer">
                                            <button type="reset" class="btn btn-primary">Reset</button>
                                            <button type="submit" class="btn btn-primary">Update</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </c:if>
        <c:if test="${requestScope.tag eq 'add'}">
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <div class="container-fluid">
                        <div class="row mb-2">
                            <div class="col-sm-6 card-header">
                                <h1 class="text-uppercase card-title">Subject Setting Add</h1>
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
                                    <form action="subjectsetting" method="post" >                                        
                                        <input value="add" name="tag" hidden/>
                                        <div class="card-body">
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label>Subject*</label> <br/>
                                                    <select class="form-control" name="subjectId">
                                                        <c:forEach items="${requestScope.listS}" var="s">
                                                            <option value="${s.subjectId}">${s.subjectCode}-${s.subjectName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label>Type*</label>
                                                    <select class="form-control" name="typeId">
                                                        <c:forEach items="${requestScope.listType}" var="t">
                                                            <option value="${t.key}">${t.value}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="title">Title*</label>
                                                <input id="title" required maxlength="40" type="text" name="settingTitle" class="form-control" value="${requestScope.ss.settingTitle}" />
                                            </div>
                                            <div class="form-group">
                                                <label for="value">Value</label>
                                                <input id="value" maxlength="40" type="text" name="settingValue" class="form-control"/>
                                            </div>
                                            <div class="row">                              
                                                <div class="form-group col-md-4" style="margin-right: 30px;">
                                                    <label for="order">Display Order*</label>
                                                    <input type="number" id="order" min="0" name="displayOrder" class="form-control" required />
                                                </div>
                                                <div class="form-group">
                                                    <label>Status*</label> <br/>
                                                    <input type="radio" name="status" value="1" checked>Active   &nbsp;&nbsp;
                                                    <input type="radio" name="status" value="0">Inactive
                                                </div>
                                            </div>

                                        </div>
                                        <!-- /.card-body -->

                                        <div class="card-footer">
                                            <button type="reset" class="btn btn-primary">Reset</button>
                                            <button type="submit" class="btn btn-primary">Add</button>
                                        </div>

                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </c:if>
        <%@include file="../home/Footer.jsp" %>
    </body>
</html>
