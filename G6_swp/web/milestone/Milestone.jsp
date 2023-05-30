<%-- 
    Document   : Milestone
    Created on : Jun 7, 2022, 8:42:34 AM
    Author     : admin
--%>

<%@page import="java.util.Enumeration"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Milestone</title>
        <%@include file="../home/HeaderLink.jsp" %>
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
        <!-- ./wrapper -->
        <div class="content-wrapper">
            <!-- jQuery -->
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <h2 class="card-title text-uppercase"><strong>Milestone List</strong></h2>
                                </div>

                                <form id="form" action="milestone" method="get">
                                    <select class="selectpicker" style="margin-left:3%;width: 12%" name="class" onchange="this.form.submit()">
                                        <option value="" ${selectedClass==null?"selected":""}>All class</option>
                                        <c:forEach items="${classList}" var="c">
                                            <option value="${c.classId}" ${selectedClass == c.classId ?"selected":""}>${c.classCode}_${c.subject.subjectCode}</option>
                                        </c:forEach>
                                    </select>
                                    <select class="selectpicker" style="margin-left:3%;margin-right: 3%;width: 8%" name="status" onchange="this.form.submit()">
                                        <option value="" ${selectedStatus==null?"selected":""}>All status</option>

                                        <option value="1" ${selectedStatus == 1 ?"selected":""}>Open</option>
                                        <option value="2" ${selectedStatus == 2 ?"selected":""}>Close</option>
                                        <option value="3" ${selectedStatus == 3 ?"selected":""}>Canceled</option>
                                    </select>

                                    <label>From:</label>
                                    <input type="date" class="col-2" name="from" value="${selectedFrom}" onchange="this.form.submit()" max="${selectedTo}"/>


                                    <label>To:</label>

                                    <input type="date" class="col-2" name="to"value="${selectedTo}" onchange="this.form.submit()" min="${selectedFrom}"/>
                                          <a href="milestone?a=add" style="margin-left: 17%; padding-top: 5px;"><i class="fa fa-plus"></i> Add new</a>
                                    <div class="card-body">

                                        <!-- /.card-header -->
                                        <c:if test="${not empty listMilestone}">
                                        <table id="example2" class="table table-bordered table-hover">
                                            <thead>
                                                <tr>
                                                    <th>Class</th>
                                                    <th>Iteration</th>  
                                                    <th>Milestone name</th>
                                                    <th>From</th>
                                                    <th>To</th>
                                                    <th>Status</th>
                                                    <th>Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${requestScope.listMilestone}" var="l">
                                                    <tr>
                                                        <td><u><a href="class?tag=update&id=${l.classId.classId}">${l.classId.classCode}_${l.classId.subject.subjectCode}</a></u></td>

                                                        <td>${l.iteration.iterName}</td>
                                                        <td>${l.milestoneName}</td>
                                                        <td><fmt:formatDate type = "date" value = "${l.fromDate}" /></td>
                                                        <td><fmt:formatDate type = "date"  value = "${l.toDate}" /></td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${l.status==1}">
                                                                    <span class="text-success">Open</span>
                                                                </c:when>
                                                                <c:when test="${l.status==2}">
                                                                    <span class="text-blue">Close</span>
                                                                </c:when>
                                                                <c:when test="${l.status==3}">
                                                                    <span class="text-danger">Cancel</span>
                                                                </c:when>

                                                            </c:choose>

                                                        </td>
                                                        <td>
                                                            <a class="btn btn-info btn-sm" style="margin-right:10%;" href="milestone?id=${l.milestoneId}&a=update"><i class="fas fa-pencil-alt"></i></a>
                                                            <a class="btn btn-danger btn-sm" data-toggle="modal" data-target="#modal-default${l.milestoneId}" href="#" ><i class="fa fa-flag"></i></a>
                                                        </td>
                                                    </tr>
                                                <div class="modal fade" id="modal-default${l.milestoneId}">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h4 class="modal-title">Confirmation</h4>
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                            </div>
                                                            <div class="modal-body">
                                                               <c:choose>
                                                                <c:when test="${l.status==1}">
                                                                    <p>Are you sure you want to change status of this milestone to <span class="text-blue">Close</span>?</p>
                                                                </c:when>
                                                                <c:when test="${l.status==2}">
                                                                     <p>Are you sure you want to change status of this milestone to <span class="text-danger">Cancel</span>?</p>
                                                                </c:when>
                                                                <c:when test="${l.status==3}">
                                                                     <p>Are you sure you want to change status of this milestone to <span class="text-success">Open</span>?</p>
                                                                </c:when>

                                                            </c:choose>
                                                                
                                                            </div>
                                                            <div class="modal-footer justify-content-center">
                                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                                <button type="button" class="btn btn-primary" onclick=" window.location = 'milestone?id=${l.milestoneId}'">Change</button>
                                                            </div>
                                                        </div>
                                                        <!-- /.modal-content -->
                                                    </div>
                                                    <!-- /.modal-dialog -->
                                                </div>
                                            </c:forEach>

                                            </tbody>

                                        </table>
                                        </c:if>
                                        <c:if test="${empty listMilestone}">
                                            <h3 class="text-danger">No data found</h3>
                                        </c:if>
                                    </div>
                                    <!-- /.card-body -->
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
        

            </section>
        </div>
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
                        <div class="toast-body">Update milestone successfully!</div>
                    </c:if>
                    <c:if test="${requestScope.success == 'aSuccess'}">
                        <div class="toast-body">Add a new milestone successfully!</div>
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
            function checkForm3() {
                document.getElementById('modal-lg').style.display = 'none';
            }
            setTimeout(function () {
                document.getElementById('modal-lg').classList.add("hide");
            }, 5000);
        </script>
            <nav aria-label="..." id="paging">
                <ul class="pagination d-flex justify-content-center">
                    <li class="page-item disabled">
                        <c:forEach begin="${1}" end="${requestScope.numpage}" var="i">
                        <li class="page-item ${i==page?"active":""}"><a class="page-link" href="milestone?page=${i}&class=${selectedClass}&status=${selectedStatus}&from=${selectedFrom}&to=${selectedTo}">${i}</a></li> 
                        </c:forEach>
                </ul>
            </nav>
        
            <%@include file="../home/Footer.jsp" %>
    </body>
</html>
