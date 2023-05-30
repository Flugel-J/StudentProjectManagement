<%-- 
    Document   : Detail
    Created on : Jun 8, 2022, 8:20:43 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Class Details</title>
        <%@include file="../home/HeaderLink.jsp" %>
    </head>
    <body class="layout-top-nav sidebar-closed sidebar-collapse">
        <%@include file="../home/Header.jsp" %>

        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <div class="container-fluid" style="display: flex;">
                    <div class="col-8">
                        <div class="col-sm-12 card-header">
                            <h1 class="text-uppercase card-title">Class Details</h1>
                        </div>
                    </div>
                    <c:if test="${useraccount.roleId=='trainer'}">
                        <div class="col-2">
                            <button class="btn btn-default" onclick="window.location.href = 'milestonesync?id=${aclass.classId}&code=${aclass.classCode}'"><i class="fa fa-sync"></i>  Sync Milestones</button>
                        </div>
                    </c:if>
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
                                <form id="myform" action="class" method="post" >
                                    <div class="card-body">
                                        <div class="form-group">
                                            <label>Class code*</label>
                                            <input class="form-control" maxlength="20" name="code" value="${aclass.classCode}" ${useraccount.roleId=="trainer"? 'readonly':'required'} style="background: white;" />

                                        </div>
                                        <div class="form-group">
                                            <label>Subject</label>
                                            <select class="selectpicker form-control" name="subject">
                                                <option value="0" ${useraccount.roleId=="trainer"? 'disabled':''} ${useraccount.roleId=="author"&&tag=="update"? 'disabled':''}>Not assigned</option>
                                                <c:forEach items="${subjectList}" var="s">
                                                    <option value="${s.key}" ${aclass.subject.subjectId== s.key ? "selected":""} ${useraccount.roleId=="trainer"? 'disabled':''} ${useraccount.roleId=="author"&&tag=="update"&&aclass.subject.subjectId!= s.key ? 'disabled':''}>${s.value}</option>
                                                </c:forEach>
                                            </select>

                                        </div>
                                        <div class="row">
                                            <input name="id" value="${aclass.classId}" hidden/>
                                            <div class="form-group col-md-6">
                                                <label>Trainer</label>
                                                <select class="selectpicker form-control" name="trainer">
                                                    <option value="0" ${useraccount.roleId=="trainer"? 'disabled':''}>Not assigned</option>
                                                    <c:forEach items="${trainerList}" var="t">
                                                        <option value="${t.key}" ${aclass.trainer.userId== t.key ? "selected":""} ${useraccount.roleId=="trainer"? 'disabled':''}>${t.value}</option>
                                                    </c:forEach>
                                                </select>  
                                            </div>
                                            <div class="form-group col-md-6">
                                                <label>Term*</label>
                                                <select class="selectpicker form-control" name="term">                                                
                                                    <option value="1" ${aclass.term==1? 'selected':''} ${useraccount.roleId=="trainer"? 'disabled':''}>Spring</option>
                                                    <option value="2" ${aclass.term==2? 'selected':''} ${useraccount.roleId=="trainer"? 'disabled':''}>Summer</option>
                                                    <option value="3" ${aclass.term==3? 'selected':''} ${useraccount.roleId=="trainer"? 'disabled':''}>Fall</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>Year*</label>
                                            <input type="number" class="form-control" value="${aclass.classYear}" name="year" ${useraccount.roleId=="trainer"? 'readonly':'required'} style="background: white;" />
                                        </div>
                                        <div class="row">
                                            <div class="form-group clearfix col-md-6">
                                                <div class="icheck-success d-inline">
                                                    <input name="bl5" type="checkbox" ${aclass.block5Class==true? 'checked':''} id="checkboxSuccess1" ${useraccount.roleId=="trainer"? "onclick='return false;'":""}>
                                                    <label for="checkboxSuccess1">
                                                        Block 5 class
                                                    </label>
                                                </div>
                                            </div>                                         
                                            <div class="form-group">
                                                <label>Status*</label> <br/>
                                                <input type="radio" name="status" ${(aclass.status==1 ||aclass.status==null )? "checked":""} ${useraccount.roleId=="trainer"? 'disabled':''} value="1" required>Active   &nbsp;&nbsp;
                                                <input type="radio" name="status" ${aclass.status==2 ? "checked":""} ${useraccount.roleId=="trainer"? 'disabled':''} value="2">Closed  &nbsp;&nbsp;
                                                <input type="radio" name="status" ${aclass.status==3 ? "checked":""} ${useraccount.roleId=="trainer"? 'disabled':''} value="3">Canceled
                                            </div>
                                        </div>
                                        <c:if test="${useraccount.roleId=='trainer'}">
                                            <div class="form-group">
                                                <a href="milestone?class=${aclass.classId}&status=&from=&to=" style="float:left;"><h4>See class milestone <i class="fa fa-arrow-right"></i></h4></a>  
                                            </div>
                                        </c:if>
                                        <!-- /.card-body -->


                                    </div>
                                    <c:if test="${useraccount.roleId!='trainer'}">
                                        <div class="card-footer">
                                            <button class="btn btn-primary" type="reset">Reset</button>
                                            <button type="submit" class="btn btn-primary">${tag}</button>
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
