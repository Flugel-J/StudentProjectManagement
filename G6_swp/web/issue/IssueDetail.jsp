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
        <title>Issue Detail</title>
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
                                <form action="issue?tag=${action}" method="post" id="form" >
                                    <div class="card-body">
                                        <div class="">
                                            <input name="id" value="${issue.id}" hidden/>
                                            <input name="assignId" value="${action == update ? issue.user.userId : useraccount.userId}" hidden/>
                                            <h4 style="display: ${action == "add" ? "none": "block"}">Create by ${issue.user.fullName}</h4>
                                            <br>

                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-6">
                                                <label>Setting Name</label> 
                                                <input class="form-control" name="" value="${useraccount.roleId }" disabled/>
                                                <input name="setting" value="${action == "update" ? issue.setting.settingId : (useraccount.roleId == "student" ? 1 : 3) }" hidden/>
                                            </div>
                                            <div class="form-group col-md-6">
                                                <label for="issueTitle">Issue Title</label>
                                                <input ${useraccount.userId == issue.user.userId || action == "add" ? " " : "disabled"} id="issueTitle" class="form-control" name="title" value="${issue.title}"   />
                                            </div>
                                        </div>

                                        <div class="row">

                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-6">
                                                <label for="issueTitle">GitLab ID</label>
                                                <input readonly id="issueTitle" class="form-control" name="gitlabId" value="${issue.gitlabId == null ? "": issue.gitlabId}"   />
                                            </div>
                                            <div class="form-group col-md-6">
                                                <label for="issueTitle">GitLab URL</label>
                                                <input readonly id="issueTitle" class="form-control" name="gitlabUrl" value="${issue.gitlabUrl}"   />
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group ">
                                                <input hidden id="issueTitle" class="form-control" name="createAt" value="${action == "update" ? issue.createAt : ""}"   />
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-4">
                                                <label for="issueTitle">Due Date</label>
                                                <input ${useraccount.userId == issue.user.userId || action == "add" ? " " : "disabled"} id="dueDate" type="date" class="form-control" name="dueDate" value="${action == "update" ? issue.dueDate : ""}"   />
                                            </div>
                                            <div class="form-group col-md-4">
                                                <label>Mile Stone</label>
                                                <select class="selectpicker form-control" name="milestone" ${useraccount.userId == issue.user.userId || action == "add" ? " " : "disabled"}>
                                                    <c:forEach items="${listMile}" var="s">
                                                        <option value="${s.milestoneId}" ${s.milestoneId == issue.milestone.milestoneId ? "selected":""} >${s.milestoneName} - ${s.getClassName()}</option>
                                                    </c:forEach>
                                                </select>  
                                            </div>
                                            <div class="form-group col-md-4">
                                                <label>Function ID</label>
                                                <input ${useraccount.userId == issue.user.userId || action == "add" ? " " : "disabled"} id="issueTitle"  class="form-control" name="functionIds" value="${issue.functionIds}"   />
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-4">
                                                <c:if test="${action == 'update'}">
                                                    <label>Topic Name</label>
                                                    <input ${useraccount.userId == issue.user.userId ? " " : "disabled"} id="issueTitle" hidden class="form-control" name="teamId" value="${issue.team.teamId }"   />
                                                    <input ${useraccount.userId == issue.user.userId ? " " : "disabled"} id="issueTitle" disabled class="form-control" name="" value="${issue.team.topicName }"   />
                                                </c:if>
                                                <c:if test="${action == 'add'}">
                                                    <label>Topic Name</label>
                                                    <select class="selectpicker form-control" name="teamId" >
                                                        <c:forEach items="${listTeam}" var="s">
                                                            <option value="${s.teamId}">${s.teamCode} - ${s.getClassCode()} - ${s.topicName}</option>
                                                        </c:forEach>
                                                    </select>  
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="row">
                                        </div>
                                        <div class="row">

                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-6">
                                                <label for="issueTitle">Label</label>
                                                <textarea ${useraccount.userId == issue.user.userId || action == "add" ? " " : "disabled"} id="issueTitle" class="form-control" name="label"  >${issue.label}</textarea>
                                            </div>
                                            <div class="form-group col-md-6">
                                                <label for="issueTitle">Issue Descrip</label>
                                                <textarea ${useraccount.userId == issue.user.userId || action == "add" ? " " : "disabled"} id="issueTitle" class="form-control" name="descript"  >${issue.descript}</textarea>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-8 ">
                                                <label for="Status">Status</label> <br/>
                                                <input ${useraccount.userId == issue.user.userId || action == "add" ? " " : "disabled"} type="radio" name="status" ${issue.status==1 ? "checked":""}  id="s1" value="1" required><label for="s1" style="font-weight: 500;">Active</label>   &nbsp;&nbsp;
                                                <input ${useraccount.userId == issue.user.userId || action == "add" ? " " : "disabled"} type="radio" name="status" ${issue.status==0 ? "checked":""} id="s2" value="0"><label for="s2" style="font-weight: 500;">Inactive</label>  &nbsp;&nbsp;
                                            </div>
                                        </div>

                                    </div>
                                    <div class="card-footer">
                                        <button class="btn btn-primary" type="reset">Reset</button>
                                        <button ${useraccount.userId == issue.user.userId || action == "add" ? " " : "disabled"} type="submit" id="btnAdd"  class="btn btn-primary">${btn}</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>

        <%@include file="../home/Footer.jsp" %>
    </body>
    <script>
        const input = document.querySelector('#dueDate');
        input.addEventListener('input', () => {
            const dueDate = new Date(document.querySelector('#dueDate').value);
            const today = new Date();
            const yyyy = today.getFullYear();
            let mm = today.getMonth() + 1; // Months start at 0!
            let dd = today.getDate();
            var nextDay = today.getDate() + 1;
            if (nextDay < 10)
                nextDay = '0' + nextDay;
            if (dd < 10)
                dd = '0' + dd;
            if (mm < 10)
                mm = '0' + mm;
            var now = new Date(yyyy + '-' + mm + '-' + dd);
            if (dueDate < now) {
                input.value = yyyy + '-' + mm + '-' + nextDay;
            }
        })
        btnAdd.addEventListener('click', () => {
            const dueDate = new Date(document.querySelector('#dueDate').value);
            const today = new Date();
            const yyyy = today.getFullYear();
            let mm = today.getMonth() + 1; // Months start at 0!
            let dd = today.getDate();
            var nextDay = today.getDate() + 1;
            if (nextDay < 10)
                nextDay = '0' + nextDay;
            if (dd < 10)
                dd = '0' + dd;
            if (mm < 10)
                mm = '0' + mm;
            var now = new Date(yyyy + '-' + mm + '-' + dd);
            if (input.value == "") {
                input.value = yyyy + '-' + mm + '-' + nextDay;
            }
        })
    </script>
</html>
