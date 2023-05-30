<%-- 
    Document   : FeatureList
    Created on : Jun 18, 2022, 10:26:49 PM
    Author     : phamt
--%>
<%@page import="java.util.Enumeration"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feature List</title>
        <%@include file="../home/HeaderLink.jsp" %>
    </head>

    <body class="layout-top-nav sidebar-closed sidebar-collapse">
        <%@include file="../home/Header.jsp" %>
        <script type="text/javascript">
            function doChange(id) {

                window.location = "feature?featureId=" + id;
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
        <div class="content-wrapper">
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <h2 class="card-title text-uppercase"><strong>Feature List</strong></h2>
                                </div>
                                <div class="card-body">
                                    <form action="featurelist" method="get">
                                        <div class="row">
                                            <select class="form-control selectpicker col-2" name="status" onchange="this.form.submit()" style="height: 40px;margin-right: 3px; margin-left: 7px">
                                                <option value="" ${requestScope.search_status==null?"selected":""}>All statuses</option>
                                                <option value="1" ${requestScope.search_status==true?"selected":""}>Active</option>
                                                <option value ="0" ${requestScope.search_status==false?"selected":""}>Inactive</option>
                                            </select>
                                            <input type="text" style="margin-right: 3px;" value="${requestScope.search_value==null?"":requestScope.search_value}" size="40%" placeholder="Type Topic Name or Feature Name to search" name="search_value"/>
                                            <button style="margin-right: 3px;" type="submit" class="btn btn-primary" >Search</button>
                                            <a href="featurelist?a=add" style="margin-left: 350px; padding-top: 5px;"><i class="fa fa-plus"></i> Add new</a>

                                        </div>
                                    </form>
                                </div>
                                <table class="table table-bordered table-hover">
                                    <thead>
                                        <tr style="text-align: center">
                                            <th>ID</th>
                                            <th>Topic Name</th>
                                            <th>Feature Name</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${listFeature}" var="l">
                                            <tr style="text-align: center">
                                                <th>${l.featureId}</th>
                                                <td>${l.teamId.topicCode}</td>
                                                <td>${l.featureName}</td>
                                                <td style="color: ${l.status==true?"green":"red"};">${l.status==true?"Active":"Inactive"}</td>

                                        <div class="modal fade" id="modal-default${l.featureId}">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h4 class="modal-title">Confirmation</h4>
                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <p>Are you sure you want to change status of this Feature ?</p>

                                                    </div>
                                                    <div class="modal-footer justify-content-center">
                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                        <button type="button" class="btn btn-primary" onclick=" window.location = 'featurelist?id=${l.featureId}'">Change</button>
                                                    </div>
                                                </div>
                                                <!-- /.modal-content -->
                                            </div>
                                            <!-- /.modal-dialog -->
                                        </div>
                                        <td>
                                            <a class="btn btn-info btn-sm" style="margin-right:10%;" href="featurelist?id=${l.featureId}&a=update"><i class="fas fa-pencil-alt"></i></a>
                                            <a class="btn btn-danger btn-sm" data-toggle="modal" data-target="#modal-default${l.featureId}" href="#" ><i class="fa fa-flag"></i></a>
                                        </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>  
                </div>
            </section>
        </div>

        <c:set var="curPage" value="${requestScope.curPage}" />
        <c:set var="NoPage" value="${requestScope.NoPage}" />
        <nav aria-label="..." id="paging">
            <ul class="pagination d-flex justify-content-center">
                <li class="page-item disabled">
                    <c:if test="${curPage!=1}">
                    <li class="page-item">
                        <a class="page-link" href="featurelist?curPage=${1}&status=${search_status}&search_value=${search_value}">Begin</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="featurelist?curPage=${curPage-1}&status=${search_status}&search_value=${search_value}">Previous</a>
                    </li>
                </c:if>

                <c:forEach begin="${curPage>=2?curPage-1:curPage}" end="${curPage+2>NoPage?NoPage:curPage+2}" var="i">
                    <li class="page-item ${i==curPage?"active":""}">
                        <a class="page-link" href="featurelist?curPage=${i}&status=${search_status}&search_value=${search_value}">${i}</a>
                    </li>
                </c:forEach>

                <c:if test="${curPage!=NoPage}">
                    <li class="page-item">
                        <a class="page-link" href="featurelist?curPage=${curPage+1}&status=${search_status}&search_value=${search_value}">Next</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="featurelist?curPage=${NoPage}&status=${search_status}&search_value=${search_value}">End</a>
                    </li>
                </c:if>

                </li>
            </ul>
        </nav>

    </body>

    <%@include file="../home/Footer.jsp" %>
</html>
