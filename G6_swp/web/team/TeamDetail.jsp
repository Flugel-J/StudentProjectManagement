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
        <title>Team Detail</title>
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
                            <h1 class="text-uppercase card-title"><h1>${title}</h1>
                        </div>
                    </div>
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
                                <form action="team?tag=${action}" method="post" id="form" >
                                    <div class="card-body">
                                        <div class="row">
                                            <input name="id" value="${team.teamId}" hidden/>
                                            <div class="form-group col-md-4">
                                                <label>Team code</label> 
                                                <input class="form-control" id="teamCode" name="teamCode" value="${team.teamCode}"  style="background: white;" />
                                                <span id="errMess" style="color: red"></span>
                                            </div>
                                            <div class="form-group col-md-4">
                                                <label>Class</label>
                                                <select class="selectpicker form-control" name="class">
                                                    <option value="0" >Not assigned</option>
                                                    <c:forEach items="${listClass}" var="s">
                                                        <option value="${s.classId}" ${team.getClassCode() == s.classCode ? "selected":""} >${s.classCode}</option>
                                                    </c:forEach>
                                                </select>  
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-8">
                                                <label for="topicName">Topic Name</label>
                                                <input id="topicName" class="form-control" name="topicN" value="${team.topicName}"  style="background: white;" />
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-4">
                                                <label for="topicCode">Topic Code</label>
                                                <input type="text" id="topicCode" class="form-control" value="${team.topicCode}" name="topC"  style="background: white;" />
                                            </div>
                                            <div class="form-group col-md-4">
                                                <label for="git">GitLab</label>
                                                <input type="text" id="git" class="form-control" value="${team.gitlabUrl}" name="git"  style="background: white;" />
                                            </div>

                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-8 ">
                                                <label for="Status">Status</label> <br/>
                                                <input type="radio" name="status" ${team.status==true ? "checked":""}  id="s1" value="1" required><label for="s1" style="font-weight: 500;">Active</label>   &nbsp;&nbsp;
                                                <input type="radio" name="status" ${team.status==false ? "checked":""} id="s2" value="0"><label for="s2" style="font-weight: 500;">Inactive</label>  &nbsp;&nbsp;
                                            </div>
                                        </div>
                                    </div>
                                    <!-- /.card-body -->
                            </div>
                            <div class="card-footer">
                                <button class="btn btn-primary" type="reset">Reset</button>
                                <button type="submit"  class="btn btn-primary">${btn}</button>
                            </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>
        </div>

<%@include file="../home/Footer.jsp" %>
</body>
<script>
    var teamCode = document.querySelector("#teamCode");
    var errMess = document.querySelector("#errMess");
    var form = document.querySelector("#form");
    let check;
    teamCode.addEventListener('input', () => {
        if (teamCode.value.split('')[1] > 5) {
            check = 0;
            errMess.textContent = "Group Code must be G1-G5"
        } else {
            check = 1;
            errMess.textContent = ""
        }
    })
    form.addEventListener('submit', (e) => {
        if (check == 0) {
            e.preventDefault();
        }
    })
</script>
</html>
