<%-- 
    Document   : Details
    Created on : Jun 9, 2022, 5:44:09 PM
    Author     : admin
--%>

<%@page import="java.util.Enumeration"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Milestones Sync</title>
        <%@include file="../home/HeaderLink.jsp" %>
    </head>
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
    <body class="layout-top-nav sidebar-closed sidebar-collapse">

        <%@include file="../home/Header.jsp" %>

        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <form action="milestonesync" method="post">
                    <input type="text" name="code" value="${aclass}" hidden/>
                    <input type="text" name="id" value="${id}" hidden/>
                    <button type="submit" class="btn btn-default" value="Sync now"><i class="fa fa-sync"></i> Sync now</button>
                </form>
                <div class="container-fluid">
                    <div class="row mb-2">
                        <div class="col-sm-6 card-header">
                            <h1 class="text-uppercase card-title">Class ${aclass} Milestones on SPM:</h1>
                        </div>
                    </div>

                </div><!-- /.container-fluid -->
            </section>
            <c:forEach items="${listMilestone}" var="l">
                <div class="card collapsed-card ">
                    <div class="card-header">
                        <h3 class="card-title">${l.milestoneName}</h3>

                        <div class="card-tools">
                            <button type="button" class="btn btn-tool" data-card-widget="collapse"><i class="fas fa-plus"></i>
                            </button>
                        </div>
                        <!-- /.card-tools -->
                    </div>
                    <!-- /.card-header -->
                    <div class="card-body">
                        <p>Iteration: ${l.iteration.iterName}</p>
                        <p>From: ${l.fromDate}</p>
                        <p>To: ${l.toDate}</p>
                    </div>
                    <!-- /.card-body -->
                </div>
                <!-- Main content -->
            </c:forEach>
                <section class="content-header">
                <div class="container-fluid">
                    <div class="row mb-2">
                        <div class="col-sm-6 card-header">
                            <h1 class="text-uppercase card-title">Class ${aclass} Milestones on GitLab :</h1>
                        </div>
                    </div>

                </div><!-- /.container-fluid -->
            </section>
                        
                        <c:forEach items="${listGitLab}" var="l">
                <div class="card collapsed-card">
                    <div class="card-header">
                        <h3 class="card-title">${l.milestoneName}</h3>

                        <div class="card-tools">
                            <button type="button" class="btn btn-tool" data-card-widget="collapse"><i class="fas fa-plus"></i>
                            </button>
                        </div>
                        <!-- /.card-tools -->
                    </div>
                    <!-- /.card-header -->
                    <div class="card-body">
                        <p>Iteration: no iteration</p>
                        <p>From: ${l.fromDate}</p>
                        <p>To: ${l.toDate}</p>
                    </div>
                    <!-- /.card-body -->
                </div>
                <!-- Main content -->
            </c:forEach>
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
                  
                    <c:if test="${requestScope.success == 'aSuccess'}">
                        <div class="toast-body">Sync milestone successfully!</div>
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


           <%@include file="../home/Footer.jsp" %>

    </body>

</html>
