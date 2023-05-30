<%-- 
    Document   : Details
    Created on : Jun 9, 2022, 5:44:09 PM
    Author     : admin
--%>
<%@page import="dal.EvalCriteriaDB"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Milestones</title>
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
                            <h1 class="text-uppercase card-title">Milestone Details</h1>
                        </div>
                    </div>
                     
                </div><!-- /.container-fluid -->
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <!-- left column -->
                        <div class="col-md-12">
                            <!-- general form elements -->
                            <div class="card card-primary">
                                <!-- /.card-header -->
                                <!-- form start -->
                                <form id="form" action="milestone" method="post">
                                   <c:if test="${tag=='update'}">
                                       <input type="text" name="id" value="${milestone.milestoneId}" hidden/>
                                     <div class="card-body">
                                        <div class="row">
                                            <div class="form-group col-md-4">
                                                <label>Class</label>
                                                <input class="form-control" type="text"  value="${milestone.classId.classCode}_${milestone.classId.subject.subjectCode}" readonly>
                                             
                                            </div>
                                            <div class="form-group col-md-4">
                                                <label>Iteration</label>
                                                <input class="form-control" type="text"  value="${milestone.iteration.iterName}" readonly>
                                              
                                            </div>           
                                            <div class="form-group col-md-4">
                                                <label>Milestone Name:</label>
                                                <input class="form-control" type="text" name="milestone" value="${milestone.milestoneName}" required>
                                                  <small class="text-danger">${err}</small>
                   
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-4">
                                                From:
                                                <input class="form-control" type="date" id="1" name="from" value="${milestone.fromDate}" max="${milestone.toDate}" onchange="change1()" required>
                                            </div>
                                            <div class="form-group col-md-4">
                                                To:
                                                <input class="form-control" type="date" id="2" name="to" value="${milestone.toDate}" min="${milestone.fromDate}" onchange="change2()" required>
                                            </div>

                                            <div class="form-group col-md-4">
                                                <label>Status*</label> <br/>
                                                <input type="radio" name="status" ${milestone.status==1 ? "checked":""} value="1" required>Open  &nbsp;&nbsp;&nbsp; 
                                                <input type="radio" name="status" ${milestone.status==2 ? "checked":""} value="2">Close          &nbsp;&nbsp;&nbsp; 
                                                <input type="radio" name="status" ${milestone.status==3 ? "checked":""} value="3">Cancel
                                            </div>  


                                        </div>
                                    </div>
                                   </c:if>
                                    <c:if test="${tag=='add'}">
                                         
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="form-group col-md-${iteration!=null?"4":"12"}">
                                                <label>Class</label>
                                                    <select class="selectpicker form-control" name="class" onchange="this.form.submit()">
                                                        <option value="">Assign to a class first</option>
                                                        <c:forEach items="${classList}" var="c">
                                                            <option value="${c.classId}" ${selectedClass== c.classId ? "selected":""}>${c.classCode}-${c.subject.subjectCode}</option>
                                                        </c:forEach>
                                                    </select>  
                                                
                                            </div>
                                            <c:if test="${iteration!=null}">
                                            <div class="form-group col-md-4">
                                                <label>Iteration</label>
                                                 <div class="col-12">
                                                    <select class="selectpicker form-control" name="iteration">
                                     
                                                        <c:forEach items="${iteration}" var="i">
                                                            <option value="${i.iterationId}">${i.iterName}</option>
                                                        </c:forEach>
                                                    </select>  
                                                </div>
                                            </div>           
                                            <div class="form-group col-md-4">
                                                <label>Milestone Name:</label>
                                                <input class="form-control" type="text" name="milestone" maxlength="20" required>
                                                  <small class="text-danger">${err}</small>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-4">
                                                From:
                                                <input class="form-control" type="date" id="1" name="from" onchange="change1()" required>
                                            </div>
                                            <div class="form-group col-md-4">
                                                To:
                                                <input class="form-control" type="date" id="2" name="to" onchange="change2()" required>
                                            </div>

                                            <div class="form-group col-md-4">
                                                <label>Status*</label> <br/>
                                                <input type="radio" name="status" value="1" checked>Open  &nbsp;&nbsp;&nbsp; 
                                                <input type="radio" name="status" value="2">Close          &nbsp;&nbsp;&nbsp; 
                                                <input type="radio" name="status" value="3">Cancel
                                            </div>
                                        </c:if>


                                        </div>
                                    </div>
                                    </c:if>
                                    <!-- /.card-body -->

                                    <div class="card-footer">
                                        <button type="reset" class="btn btn-primary">Reset</button>
                                        <button type="submit" class="btn btn-primary" onclick="addAction()" <c:if test="${empty iteration && tag=='add'}">disabled</c:if>>
                                            <c:if test="${requestScope.tag=='update'}">Update</c:if>
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
        <script>
            function change1() {
                var x = document.getElementById("1").value;
                document.getElementById("2").min = x;
            }
            function change2() {
                var x = document.getElementById("2").value;
                document.getElementById("1").max = x;
            } 
            function addAction() {
                var textfield = document.createElement("input");
                textfield.type = "text";
                textfield.value = "action";
                textfield.id = "act";
                textfield.name = "act";
                textfield.hidden = "true";
                document.getElementById('form').appendChild(textfield);
            }
        </script>
    </body>

</html>
