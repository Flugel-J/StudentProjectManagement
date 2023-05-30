<%@page import="java.util.Enumeration"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>System setting</title>
        <%@include file="../home/HeaderLink.jsp" %>
    </head>
    <body class="layout-top-nav sidebar-closed sidebar-collapse">
        <%@include file="../home/Header.jsp" %>

        <div class="content-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h2 class="card-title text-uppercase"><strong>User List</strong></h2>
                            </div>
                            &nbsp;&nbsp; &nbsp;&nbsp;  
                            <div class="d-flex justify-content-around align-items-center">
                                <form action="userlist" method="get">
                                    &nbsp;&nbsp;&nbsp;&nbsp; 
                                    <select id="roleId" name="roleId" style="width: 100px; margin-left: 40px" onchange="this.form.submit()">
                                        <option value="" >All Role</option>
                                        <c:forEach items="${listRole}" var = "s" begin="0" varStatus ="i" end="4">
                                            <option value="${i.count}" ${role == i.count? "selected":""}>${s}</option>
                                        </c:forEach>
                                    </select>
                                    &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; 

                                    <select id="status" name="status" style="width: 100px; margin-left: 40px" onchange="this.form.submit()">
                                        <option value="" >All Status</option>
                                        <option value="1" ${status == "1" ? "selected":""}>Active</option>
                                        <option value="0" ${status == "0" ? "selected":""}>Inactive</option>
                                    </select>
                                    &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
                                    <input type="text" size="35%" placeholder="Type name to search" name="name"/>
                                    <button  type="submit" class="btn btn-primary" >Search</button>
                                </form>
                                <!--<h5><a href="?tag=create">Add New</a></h5>-->
                            </div>
                            <!-- /.card-header -->
                            <div class="card-body">
                                <table id="example2" class="table table-bordered table-hover">
                                    <thead>
                                        <tr class="text-center">
                                            <th>ID</th>
                                            <th>Name</th>
                                            <th>Email</th>
                                            <th>Mobile</th>
                                            <th>Role</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:if test="${!empty list}">
                                            <c:forEach items="${list}" var="l">
                                                <c:forEach items="${listRole}" var = "s" begin="0" varStatus ="i" end="4">
                                                    <c:if test="${l.roleId == i.count}">
                                                        <tr class="text-center">
                                                            <td>${l.userId}</td>
                                                            <td>${l.fullName}</td>
                                                            <td>${l.email}</td>
                                                            <td>${l.mobile}</td>
                                                            <td>${s}</td>
                                                            <td>${l.status==true?"Activate":"Inactivate"}</td>
                                                            <td><a class="btn btn-info btn-sm" href="?id=${l.userId}&tag=update"><i class="fas fa-pencil-alt"></i></a>
                                                                <a class="btn btn-danger btn-sm" href="?id=${l.userId}&isActive=${!l.status}"><i class="fa fa-flag"></i></a></td>
                                                        </tr>
                                                    </c:if>
                                                </c:forEach>

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
        </div>
        <nav aria-label="Page navigation example"  id="paginaton">
            <ul class="pagination justify-content-center">
                <c:forEach begin="1" end="${pageNumber}" var="i">
                    <li class="page-item"><a class="page-link" href="userlist?index=${i}&roleId=${role}&status=${status}&name=${name}" style="${i == index ? "background-color: blue; color:white" : ""}" >${i}</a></li>
                    </c:forEach>
            </ul>
        </nav>
        <%@include file="../home/Footer.jsp" %>
    </body>
</html>
