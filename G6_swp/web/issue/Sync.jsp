<%-- 
    Document   : Sync
    Created on : Jul 7, 2022, 9:35:01 PM
    Author     : admin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Issue Sync</title>
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
                            <h1 class="text-uppercase card-title">Select a team to Sync:</h1>
                        </div>
                    </div>

                </div><!-- /.container-fluid -->
            </section>
            <c:forEach items="${team}" var="l">
                <div class="card card-primary collapsed-card">
                    <div class="card-header">
                        <h3 class="card-title">${l.teamCode}</h3>

                        <div class="card-tools">
                            <button type="button" class="btn btn-tool" data-card-widget="collapse"><i class="fas fa-plus"></i>
                            </button>
                        </div>
                        <!-- /.card-tools -->
                    </div>
                    <!-- /.card-header -->
                    <div class="card-body">
                        <p>From Class: ${l.classId.classCode}-${l.classId.subject.subjectCode}</p>

                        <div>
                            <form action="issue" method="post">
                                <input name ="team" value="${l.teamId}" hidden>
                                <input name="tag" value="sync" hidden>
                                <input type="submit" class="justify-content-center" value="Sync now"/>
                            </form>
                        </div>
                    </div>
                    <!-- /.card-body -->
                </div>
                <!-- Main content -->
            </c:forEach>
        </div>


        <%@include file="../home/Footer.jsp" %>

    </body>

</html>
