<%-- 
    Document   : FeatureDetails
    Created on : Jun 19, 2022, 3:52:23 AM
    Author     : phamt
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feature Details</title>
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
                            <h1 class="text-uppercase card-title">Feature ${action}</h1>
                        </div>
                    </div>
                    <small class="text-danger">${err}</small>
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
                                <form id="myform" action="featurelist" method="post" >
                                    <div class="card-body">
                                        <c:if test="${action == 'update'}">  
                                            <div class="form-group">
                                                <label>Feature Id:</label>
                                                <input class="form-control" name="featureId" value="${feature.featureId}" readonly>

                                            </div>
                                        </c:if>
                                        <div class="form-group">
                                            <label>Team Id: </label>

                                            <input class="form-control" name="teamId" value="${feature.teamId.teamId}" >

                                        </div>
                                        <c:if test="${action == 'update' }">
                                            <div class="row">
                                                <!--<input name="id" value="${feature.featureId}" hidden/>-->
                                                <div class="form-group col-md-6">
                                                    <label>Topic Code</label>
                                                    <input type="text" class="form-control" name="topicCode" value="${feature.teamId.topicCode}" readonly="">
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label>Topic Name</label>
                                                    <input type="text" class="form-control" name="topicName" value="${feature.teamId.topicName}" readonly="">
                                                </div>
                                            </div>
                                        </c:if>

                                        <div class="form-group">
                                            <label>Feature Name*</label>
                                            <input type="text" class="form-control" name="featureName" value="${feature.featureName}" required>
                                        </div>

                                        <div class="row">

                                            <div class="col-md-4 mb-3">
                                                <small>Status</small><br>
                                                <input type="radio" name="status" value='1' ${feature.status==true?'checked':''} ${feature.status == null?'checked':''}>Active   &nbsp;&nbsp;
                                                <input type="radio" name="status" value='0' ${feature.status==false?'checked':''}>Inactive
                                            </div> 
                                        </div>

                                        <!-- /.card-body -->


                                    </div>
                                    <%--<c:if test="${useraccount.roleId!='trainer'}">--%>
                                    <div class="card-footer">
                                        <button class="btn btn-primary" type="reset">Reset</button>
                                        <button type="submit" class="btn btn-primary">${action}</button>
                                    </div>
                                    <%--</c:if>--%>
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
