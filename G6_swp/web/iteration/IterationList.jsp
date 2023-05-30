<%@page import="java.util.Enumeration"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Iteration List</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <%@include file="../home/HeaderLink.jsp" %>

    </head>
    <body class="layout-top-nav sidebar-closed sidebar-collapse">
        <%@include file="../home/Header.jsp" %>
        <script type="text/javascript">
            function doChange(id) {

                window.location = "iterationlist?iterationId=" + id;
            }
        </script>
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

        <!-- jQuery -->

        <div class="content-wrapper">
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <h2 class="card-title text-uppercase"><strong>Iteration List</strong></h2>
                                </div>
                                &nbsp;&nbsp; &nbsp;&nbsp;  
                                <form action="iterationlist" method="get">
                                    &nbsp;&nbsp;&nbsp;&nbsp; 
                                    <select class="selectpicker" name="iterName" onchange="this.form.submit()">
                                        <option value="" ${selectedItername==null?"selected":""}>All Iteration Name</option>
                                        <c:forEach items="${requestScope.itername}" var="t">
                                            <option value="${t}" ${selectedItername == t?"selected":""}>${t}</option>
                                        </c:forEach>
                                    </select> 
                                    &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; 



                                    <select class="selectpicker" name="status" onchange="this.form.submit()">
                                        <option value="" ${requestScope.selectedStatus==null?"selected":""}>All statuses</option>
                                        <option value="1" ${requestScope.selectedStatus=="1"?"selected":""}>Active</option>
                                        <option value ="0" ${requestScope.selectedStatus=="0"?"selected":""}>Inactive</option>
                                    </select>
                                    &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
                                    <input type="text" value="" size="35%" placeholder="Type a Subject Id to search" name="subjectId"/>
                                    <button  type="submit" class="btn btn-primary" >Search</button>
                                    <a href="iterationlist?a=add" style="margin-left: 200px; padding-top: 5px;"><i class="fa fa-plus"></i> Add new</a>

                                </form>
                                <!-- /.card-header -->
                                <div class="card-body">
                                    <table id="example2" class="table table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th>Subject ID</th>
                                                <th>Iteration Name</th>
                                                <th>Duration</th>
                                                <th>Status</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:if test="${!empty listIteration}">
                                                <c:forEach items="${requestScope.listIteration}" var="l">
                                                    <tr>
                                                        <td>${l.subjectId}</td>
                                                        <td>${l.iterName}</td>
                                                        <td>${l.duration}</td>
                                                        <td style="color: ${l.status==true?"green":"red"};">${l.status==true?"Active":"Inactive"}</td>
                                                        <td>
                                                            <a class="btn btn-info btn-sm" style="margin-right:5%;" href="iterationlist?a=update&id=${l.iterationId}"><i class="fas fa-pencil-alt"></i></a>
                                                            <a href="#" class="btn btn-danger btn-sm" onclick="doChange('${l.iterationId}')"><i class="fa fa-flag"></i></a>                                             
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </c:if>
                                        </tbody>

                                    </table>
                                </div>
                                <!-- /.card-body -->
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <nav aria-label="..." id="paging">
            <ul class="pagination d-flex justify-content-center">
                <li class="page-item disabled">
                    <c:forEach begin="${1}" end="${requestScope.numpage}" var="i">
                    <li class="page-item ${i==page?"active":""}"><a class="page-link" href="iterationlist?page=${i}&iterName=${selectedItername}&status=${selectedStatus}&subjectId=">${i}</a></li> 
                    </c:forEach>
            </ul>

        </nav> 
        <%@include file="../home/Footer.jsp" %>
    </body>
</html>
