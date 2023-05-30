<%-- 
    Document   : Add
    Created on : May 24, 2022, 8:16:36 PM
    Author     : admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Subject Detail</title>
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
                            <h1 class="text-uppercase card-title">${action}</h1>
                        </div>

                    </div>
                </div>
            </section>
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <!-- left column -->
                        <div class="col-md-10">
                            <!-- general form elements -->
                            <div class="card card-primary">

                                <!-- form start -->

                                <form id="myform" method="post" action="subject">
                                    <div class="card-body">
                                        
                                            <input type="text" value="${Subject.subjectId}" name="id" hidden />
                                            <div class="row">
                                            <div class="form-group col-6">
                                                <small>Subject code*</small>
                                                <input type="text" class="form-control" name="code" 
                                                       value="${Subject.subjectCode}" maxlength="10" required>
                                            </div>
                                            <div class="form-group col-6">
                                                <small>Name*</small>
                                                <input type="text" name="name" maxlength="40" value="${Subject.subjectName}" class="form-control" required>
                                            </div>
                                            </div>                                       
                                            <div class="form-group">
                                                <small>Assign Author</small><br>
                                                    <select class="selectpicker form-control" name="author">
                                                        <option value="">Not assigned</option>
                                                        <c:forEach items="${requestScope.authorList}" var="t">
                                                            <option value="${t.key}" ${Subject.author== t.value ? "selected":""}>${t.value}</option>
                                                        </c:forEach>
                                                    </select>  
                                            </div>
                                            <div class="form-group col-md-4 mb-3"> 
                                                <small>Status</small><br>
                                                <input type="radio" name="status" ${(Subject.status==true || Subject.status==null) ? "checked":""} value="1" required>Active 
                                                <input type="radio" name="status" ${Subject.status==false ? "checked":""} value="0">Inactive
                                            </div>  

                                        

                                        <div class="card-footer">
                                            <button class="btn btn-primary"  type="reset">Reset</button>&nbsp;
                                            <button type="submit" class="btn btn-primary">
                                                Save changes
                                            </button>
                                        </div>
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
