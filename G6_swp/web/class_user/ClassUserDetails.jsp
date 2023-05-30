<%-- 
    Document   : ClassUserDetails
    Created on : Jun 11, 2022, 5:43:43 PM
    Author     : 03623
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Class User Details</title>
        <%@include file="../home/HeaderLink.jsp" %>
    </head>
    <body class="layout-top-nav sidebar-closed sidebar-collapse">
        <%@include file="../home/Header.jsp" %>

        <!--        noi dung-->
        <section class="content">
            <form action="classuser" method="post">
                <input type="text" name="classId" value="${requestScope.classuser.classId.classId}" hidden>
                <input type="text" name="userId" value="${requestScope.classuser.user.userId}" hidden>
                <input type="text" name="TeamId" value="${requestScope.classuser.team.teamId}" hidden>
                <input type="text" name="funtion" value="updateclassuser" hidden>
                <!-- Default box -->
                <div class="card">
                    <div class="card-body row">
                        <div class="col-5 text-center d-flex align-items-center justify-content-center">
                            <div class="">
                                <div class="text-center">
                                    <c:if test="${requestScope.classuser.user.avatarLink==null}"> 
                                        <img class="profile-user-img img-fluid img-circle" src="<%=request.getContextPath()%>/home/pages/comment.png" style="width: 70%" alt="User profile picture">
                                    </c:if>
                                    <c:if test="${requestScope.classuser.user.avatarLink!=null}"> 
                                        <img class="profile-user-img img-fluid img-circle" src="<%=request.getContextPath()%>/imgs/${requestScope.classuser.user.avatarLink}" style="width: 70%" alt="User profile picture">
                                    </c:if>
                                </div>
                                <p class="lead mb-5"><br>
                                    Phone: ${requestScope.classuser.user.mobile}<br>
                                    Email: ${requestScope.classuser.user.email}
                                </p>
                            </div>
                        </div>
                        <div class="col-7">
                            <div class="row">
                                <div class="col-sm-6">
                                    <!-- text input -->
                                    <div class="form-group">
                                        <label>Class Code</label>
                                        <input type="text" class="form-control" value="${requestScope.classuser.classId.classCode}" disabled="">
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label>Subject Code</label>
                                        <input type="text" class="form-control" value="${requestScope.classuser.classId.subject.subjectCode}" disabled="">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label>Roll number</label>
                                        <input type="text" class="form-control" value="${requestScope.classuser.user.rollNumber}" disabled="">
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <!-- text input -->
                                    <div class="form-group">
                                        <label>User name</label>
                                        <input type="text" class="form-control" value="${requestScope.classuser.user.fullName}" disabled="">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <!-- text input -->
                                    <div class="form-group">
                                        <label>Team Code</label>
                                        <select class="form-control" name="teamchange" >
                                            
                                            <c:forEach items="${requestScope.listteamid}" var="t">
                                                <option value="${t.teamId}" ${requestScope.classuser.team.teamId == t.teamId ?"selected":""}> ${t.teamCode}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label>Team leader</label>
                                        <select class="form-control" name="teamleader" >
                                            <option value="true" ${requestScope.classuser.teamLeader == "true" ?"selected":""}>True</option>
                                            <option value="false" ${requestScope.classuser.teamLeader == "false" ?"selected":""}>False</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <!-- text input -->
                                    <div class="form-group">
                                        <label>Dropout Date</label>
                                        <input type="date" value="${requestScope.classuser.dropoutDate}" name="dropoutdate" class="form-control" id="floatingInput">
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label>Ongoing eval</label>
                                        <input type="number" value="${requestScope.classuser.ongoingEval}" name="ongoingeval" class="form-control" disabled>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <!-- text input -->
                                    <div class="form-group">
                                        <label>Final pres eval</label>
                                        <input type="number"  value="${requestScope.classuser.finalPresEval}" name="finalpreseval" class="form-control" disabled>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label>Final topic eval</label>
                                        <input type="number"  name="finaltopiceval" value="${requestScope.classuser.finalTopicEval}" class="form-control" disabled>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputMessage">User note</label>
                                <textarea id="inputMessage" name="usernote" class="form-control" rows="4" style="height: 120px;">${requestScope.classuser.userNotes}</textarea>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-primary" type="reset">Reset</button> <input data-toggle="modal" data-target="#modal-default" type="submit" class="btn btn-primary" value="Submit">
                            </div>
                            
                        </div>
                    </div>
                </div>
            </form>
        </section>

        <!--        noi dung-->



        <%@include file="../home/Footer.jsp" %>
    </body>
</html>
