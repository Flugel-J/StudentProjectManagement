<%-- 
    Document   : SettingDetail
    Created on : May 12, 2022, 11:14:32 AM
    Author     : admin
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Setting Detail</title>

    <!-- Google Font: Source Sans Pro -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/home/plugins/fontawesome-free/css/all.min.css">
    <!-- jsGrid -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/home/plugins/jsgrid/jsgrid.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/home/plugins/jsgrid/jsgrid-theme.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/home/dist/css/adminlte.min.css">
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
                        <h1 class="text-uppercase card-title">
                            <c:if test="${requestScope.tag eq 'update'}">Setting details</c:if>
                            <c:if test="${requestScope.tag eq 'add'}">Setting add</c:if>
                            </h1>
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
                            <c:if test="${requestScope.tag eq 'update'}">
                                <c:set value="${requestScope.setting}" var="s"/>
                                <form id="form" action="setting" method="post">
                                    <input value="update" name="tag" hidden/>
                                    <input type="text" value="${requestScope.id}" name="settingId" hidden />
                                    <div class="card-body">
                                        <div class="form-group">          
                                            <label>Type</label>
                                            <select class="form-control" name="typeId">
                                                <c:forEach items="${requestScope.listType}" var="t">
                                                    <option value="${t.key}" ${s.typeId == t.key ?"selected":""}>${t.value}</option>
                                                </c:forEach>
                                            </select>                                         
                                        </div>

                                        <div class="form-group">
                                            <label>Title*</label>
                                            <input type="text" name="settingTitle" maxlength="20" value="${s.settingTitle}" class="form-control" id="validationDefault03" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Value</label>
                                            <input type="text" name="settingValue" maxlength="20" value="${s.settingValue}" class="form-control" id="validationDefault03" >                      
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6 form-group" style="margin-right: 40px;">
                                                <label>Order*</label>
                                                <input type="number" class="form-control" name="displayOrder" 
                                                       value="${s.displayOrder}" required>
                                            </div>
                                            <div class="col-md-4 mb-3 form-group">
                                                <label>Status*</label><br>
                                                <input type="radio" name="status" ${s.status==true ? "checked":""} value="1">Active   &nbsp;&nbsp;
                                                <input type="radio" name="status" ${s.status==false ? "checked":""} value="0">Inactive
                                            </div> 
                                        </div>
                                        <div class="form-group">
                                            <label>Description</label><br>
                                            <textarea rows="5" cols="120" maxlength="200" name="note">${s.note}</textarea>
                                        </div>
                                    </div>
                                    <!-- /.card-body -->

                                    <div class="card-footer">
                                        <button type="reset" class="btn btn-primary">Reset</button>
                                        <button type="submit" class="btn btn-primary" onclick="addAction()">
                                            <c:if test="${requestScope.tag=='update'}">Update</c:if>
                                            <c:if test="${requestScope.tag=='add'}">Add</c:if>
                                            </button>
                                        </div>
                                    </form>
                            </c:if>
                            <c:if test="${requestScope.tag eq 'add'}">
                                <form id="form" action="setting" method="post">
                                    <input value="add" name="tag" hidden/>
                                    <div class="card-body">
                                        <div class="form-group">          
                                            <label>Type</label>
                                            <select class="form-control" name="typeId">
                                                <c:forEach items="${requestScope.listType}" var="t">
                                                    <option value="${t.key}">${t.value}</option>
                                                </c:forEach>
                                            </select>                                         
                                        </div>
                                        <div class="form-group">
                                            <label>Title*</label>
                                            <input type="text" name="settingTitle" maxlength="20" class="form-control" id="validationDefault03" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Value</label>
                                            <input type="text" maxlength="20" name="settingValue" class="form-control" id="validationDefault03"> 
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6 form-group" style="margin-right: 40px;">
                                                <label>Order*</label>
                                                <input type="number" class="form-control" name="displayOrder" required>
                                            </div>
                                            <div class="col-md-4 mb-3 form-group">
                                                <label>Status*</label><br>
                                                <input type="radio" name="status" checked value="1">Active   &nbsp;&nbsp;
                                                <input type="radio" name="status" value="0">Inactive
                                            </div> 
                                        </div>
                                        <div class="form-group">
                                            <label>Description</label><br>
                                            <textarea rows="5" cols="120" maxlength="200" name="note"></textarea>
                                        </div>
                                    </div>
                                    <!-- /.card-body -->

                                    <div class="card-footer">
                                        <button type="reset" class="btn btn-primary">Reset</button>
                                        <button type="submit" class="btn btn-primary" onclick="addAction()">
                                            <c:if test="${requestScope.tag=='update'}">Update</c:if>
                                            <c:if test="${requestScope.tag=='add'}">Add</c:if>
                                            </button>
                                        </div>
                                    </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <%@include file="../home/Footer.jsp" %>
</body>
</html>
