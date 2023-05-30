<%-- 
    Document   : IterationAdd
    Created on : 05-06-2022, 12:08:13
    Author     : Admin
--%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@include file="../home/HeaderLink.jsp" %>

    </head>

    <body class="layout-top-nav sidebar-closed sidebar-collapse">
        <%@include file="../home/Header.jsp" %>
        <div class="content-wrapper">
            <div class="container h-100">
                <div class="row h-100 justify-content-center align-items-center">
                    <form class = 'card p-3 bg-light' method="post" action="iterationlist"
                          onsubmit="return confirm('Do you really want to ${action} this iteration?');">
                        <!--<input value="add" name="tag" hidden/>-->
                        <input value="${iteration.iterationId}" name="iterationId" hidden/>
                        <h1> Iteration ${action} </h1>
                        <div class="form-row">
                            <div class="col-md-7 mb-5">
                                <small>Subject ID*</small><br>
                                <input type="number" class="form-control" name="subjectId" value="${iteration.subjectId}" required>
                            </div>

                            <div class="col-md-5 mb-3">
                                <small>Iteration Name*</small>
                                <input type="text" class="form-control" name="iterName" value="${iteration.iterName}" required>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="col-md-8 mb-4">
                                <small>Duration</small>
                                <input type="number" name="duration" class="form-control" id="validationDefault03" value="${iteration.duration}" required>
                            </div>
                            <div class="col-md-4 mb-3">
                                <small>Status</small><br>
                                <input type="radio" name="status" value='1' ${iteration.status==true?'checked':''}>Active   &nbsp;&nbsp;
                                <input type="radio" name="status" value='0' ${iteration.status==false?'checked':''}>Inactive
                            </div>  

                        </div>

                        <button class="btn btn-primary" type="submit">${action}</button>
                    </form>
                </div>
            </div>
        </div>


    </body>
    <%@include file="../home/Footer.jsp" %>

</html>
