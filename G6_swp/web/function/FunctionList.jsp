<%-- 
    Document   : ClassUserList
    Created on : Jun 9, 2022, 4:34:37 PM
    Author     : 03623
--%>
<%@page import="java.util.Enumeration"%>
<%@page import="dal.FunctionDB"%>
<%@page import="model.Function"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Function List</title>
        <!--        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">-->

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
        <!--        noidung-->
        <div class="content-wrapper">
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <h2 class="card-title text-uppercase"><strong>Function List</strong></h2>
                                </div>
                                &nbsp;&nbsp; &nbsp;&nbsp;  
                                <form action="function" id="classcode2" method="get">
                                    <div style="width: 100%; padding-bottom: 5px">
                                        <div class="input-group" style="width: 90%;margin-left: 15px">
                                            <input style="height: 35px;" name="search" type="search" class="form-control form-control-lg" placeholder="Search function name or feature name or access roles" value="${search}">
                                            <div class="input-group-append" style="height: 35px;">
                                                <button type="submit" class="btn btn-default">
                                                    <i class="fa fa-search" style="font-size: 1rem"></i>
                                                </button>
                                            </div>
                                            <a href="function"><button type="button" class="btn btn-default" style="margin-left: 20px;color: blue"><i class="bi bi-arrow-clockwise"></i> Reset</button></a>

                                            <button data-toggle="modal" data-target="#modalExport" type="button" class="btn btn-success" style="margin-left: 70px; background-color: #236541;padding: 0.13rem 0.4rem;font-size: 14px;color: white;">
                                                <a ><i class="bi bi-file-earmark-arrow-down"></i> Export Excel</a>
                                            </button>


                                            <button data-toggle="modal" data-target="#modal-lgImport" type="button" class="btn btn-success" style="margin-left: 20px;padding: 0.13rem 0.4rem;font-size: 14px">
                                                <a  style="float: right; color: white;"><i class="bi bi-file-earmark-arrow-up"></i> Import </a>
                                            </button>
                                        </div>

                                    </div>

                                    <script>
                                        function submitData()
                                        {
                                            document.getElementById("checkclass").value = "ko null";
                                            document.getElementById("classcode2").submit();
                                        }
                                    </script>
                                    <input id="checkclass" name="checkclass" value="null" type="text" hidden>
                                    <div style="display: flex;">
                                        <select class="form-control"  name="classid" onchange="submitData();" style="width: 15%; margin-left: 13px; height: 35px">
                                            <c:forEach items="${requestScope.classlist}" var="c">
                                                <option value="${c.classId}" ${classid == c.classId ?"selected":""}>${c.classCode}</option>
                                            </c:forEach>
                                        </select>
                                        <select class="form-control" name="teamid" onchange="this.form.submit()" style="width: 15%; margin-left: 7px; height: 35px">
                                            <c:forEach items="${requestScope.teamlist}" var="c">
                                                <option value="${c.teamId}" ${teamid == c.teamId ?"selected":""}>${c.teamCode}</option>
                                            </c:forEach>
                                        </select> 
                                        <select class="form-control" name="settingid" onchange="this.form.submit()" style="width: 15%; margin-left: 7px; height: 35px">
                                            <option value="" ${settingid==null?"selected":""}>All Complexity</option>
                                            <c:forEach items="${requestScope.listsubjectsetting}" var="c">
                                                <option value="${c.settingId}" ${settingid == c.settingId ?"selected":""}>${c.settingTitle}</option>
                                            </c:forEach>
                                        </select>
                                        <select class="form-control" name="priority" onchange="this.form.submit()" style="width: 15%; margin-left: 7px; height: 35px">
                                            <option value="" ${priority==null?"selected":""}>All Priority </option>
                                            <c:forEach items="${requestScope.prioritylist}" var="p"> <!--list int-->
                                                <option value="${p.priority}" ${priority == p.priority ?"selected":""}>${p.priority}</option>
                                            </c:forEach>
                                        </select>
                                        <select class="form-control" name="status" onchange="this.form.submit()" style="width: 15%; margin-left: 7px; height: 35px">
                                            <option value="" ${requestScope.status==null?"selected":""}>All Status</option>
                                            <option value="1" ${requestScope.status=="1"?"selected":""} style="color: #EEE600">Pending</option>
                                            <option value="2" ${requestScope.status=="2"?"selected":""} style="color: #C39953">Planned</option>
                                            <option value="3" ${requestScope.status=="3"?"selected":""} style="color: #ED9121">Evaluated</option>
                                            <option value ="4" ${requestScope.status=="4"?"selected":""} style="color: #9400D3">Rejected</option>
                                        </select>



                                </form>
                                <a href="function?funtion=add&classid=${classid}&teamid=${teamidImport}" style=" margin-left: 5%;">
                                    <button style="height: 40px; color: #4B9AF6; " type="button" class="btn btn-default" ><i class="fa fa-plus"></i> Add new </button>
                                </a>
                            </div>
                            <!-- /.card-header -->
                            <div class="card-body">
                                <table id="example2" class="table table-bordered table-hover" style="font-size: 0.9rem;">
                                    <thead>
                                        <tr>
                                            <th>Function Id</th>
                                            <th>Function Name</th>
                                            <th>Team Name</th>
                                            <th>Feature Name</th>
                                            <th>Access Role</th>
                                            <th>Complexity</th>
                                            <th>Priority</th>
                                            <th>Status</th>
                                            <th style="width: 93px;">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:if test="${!empty functionlist}">
                                            <c:forEach items="${requestScope.functionlist}" var="f">
                                                <tr>
                                                    <td>${f.id}</td>
                                                    <td>${f.functionName}</td>
                                                    <td>${f.team.teamCode}</td>
                                                    <td>${f.feature.featureName}</td>
                                                    <td>${f.accessRoles}</td>
                                                    <td>${f.subjectSetting.settingTitle}</td>
                                                    <td>${f.priority}</td>
                                                    <c:if test="${f.status==1}"><td style="color: #EEE600">Pending</td></c:if>
                                                    <c:if test="${f.status==2}"><td style="color: #C39953">Planned</td></c:if>
                                                    <c:if test="${f.status==3}"><td style="color: #ED9121">Evaluated</td></c:if>
                                                    <c:if test="${f.status==4}"><td style="color: #9400D3">Rejected</td></c:if>
                                                        <td>
                                                            <a  class="btn btn-info btn-sm" href="function?funtion=detail&functionid=${f.id}&teamid=${f.team.teamId}">
                                                            <i class="fas fa-pencil-alt"></i>
                                                        </a>
                                                        <a data-toggle="modal" data-target="#modal-default${f.id}" class="btn btn-danger btn-sm" >
                                                            <i class="fa fa-flag"></i>
                                                        </a>
                                                        <form action="function" method="post">
                                                            <input type="text" value="${f.id}" name="fuidstatus" hidden>
                                                            <input type="text" name="funtion" value="changestatus" hidden>
                                                            <div class="modal fade" id="modal-default${f.id}">
                                                                <div class="modal-dialog">
                                                                    <div class="modal-content">
                                                                        <div class="modal-header">
                                                                            <h4 class="modal-title">Status change confirmation</h4>
                                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                                <span aria-hidden="true">&times;</span>
                                                                            </button>
                                                                        </div>
                                                                        <div class="modal-body">
                                                                            <p>Please select the status you want to change</p>
                                                                        </div>
                                                                        <div class="modal-body">
                                                                        <label style="padding-right: 25px;">Status* </label>
                                                                        <samp style="padding-right: 10px;color: #EEE600"><input type="radio" name="statuschange" value="1" ${f.status==1?"checked":""}>Pending</samp>
                                                                        <samp style="padding-right: 10px;color: #C39953"><input type="radio" name="statuschange" value="2" ${f.status==2?"checked":""}>Planned</samp>
                                                                        <samp style="padding-right: 10px;color: #ED9121"><input type="radio" name="statuschange" value="3" ${f.status==3?"checked":""}>Evaluated</samp>
                                                                        <samp style="padding-right: 10px;color: #9400D3"><input type="radio" name="statuschange" value="4" ${f.status==4?"checked":""}>Rejected</samp>
                                                                        </div>
                                                                        <div class="modal-footer justify-content-center">
                                                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                                            <button type="submit" class="btn btn-primary">Save changes</button>
                                                                        </div>
                                                                    </div>
                                                                    <!-- /.modal-content -->
                                                                </div>
                                                                <!-- /.modal-dialog -->
                                                            </div>
                                                        </form>
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
        <c:if test="${count>0}">


            <c:if test="${Page>2}">
                <li class="page-item">
                    <a class="page-link" href="function?Page=1&search=${search}&classid=${classid}&teamid=${teamid}&settingid=${settingid}&priority=${priority}&status=${status}&checkclass=${checkclass}">Begin</a>
                </li>
            </c:if>
            <c:if test="${Page!=1}">
                <li class="page-item ">
                    <a class="page-link" href="function?Page=${Page-1}&search=${search}&classid=${classid}&teamid=${teamid}&settingid=${settingid}&priority=${priority}&status=${status}&checkclass=${checkclass}">${Page-1}</a>
                </li>
            </c:if>    

            <li class="page-item active">
                <a class="page-link" href="function?Page=${Page}&search=${search}&classid=${classid}&teamid=${teamid}&settingid=${settingid}&priority=${priority}&status=${status}&checkclass=${checkclass}">${Page}</a>
            </li>

            <c:if test="${Page!=count}">
                <li class="page-item ">
                    <a class="page-link" href="function?Page=${Page+1}&search=${search}&classid=${classid}&teamid=${teamid}&settingid=${settingid}&priority=${priority}&status=${status}&checkclass=${checkclass}">${Page+1}</a>
                </li>
            </c:if>
            <c:if test="${Page <= count-1}">
                <li class="page-item ">
                    <a class="page-link" href="function?Page=${count}&search=${search}&classid=${classid}&teamid=${teamid}&settingid=${settingid}&priority=${priority}&status=${status}&checkclass=${checkclass}">End</a>
                </li>
            </c:if>
        </c:if>
    </ul>
</nav>
<!--                                        modal Export-->
<form action="function" method="post">
    <input name="funtion" value="export" hidden>
    <input name="teamId" value="${teamid}" hidden>
    <div class="modal fade" id="modalExport">
        <div class="modal-dialog">
            <div class="modal-content">

                <div class="modal-header">
                    <h4 class="modal-title">Export Function</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p>You want to export the .xlsx file of the Team currently displayed in the List?</p>

                </div>
                <div class="modal-footer justify-content-between">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Yes</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>                      
</form>
<form action="function" method="post" enctype="multipart/form-data">
    <input name="funtion" value="import" hidden>
    <input name="classCodeImport" value="${classCodeImport}" hidden>
    <input name="teamidImport" value="${teamidImport}" hidden>
    <div class="modal fade" id="modal-lgImport" >
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Import Function</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p>Upload xlsx file (maximum file 10MB).</p>
                    <input type="file" id="inputImport" name="fileImport" accept=".xlsx" required/><br><br>
                    <jsp:useBean id="cdb" class="dal.ClassDB" />
                    <jsp:useBean id="tdb" class="dal.TeamDB" />
                    Class: ${cdb.getClassById(classCodeImport).classCode}&nbsp;&nbsp;&nbsp;  Team: ${tdb.getTeam(teamidImport).teamCode}

                    <div style="display: flex">
                        <div class="mailbox-attachment-info" style="margin-left: 20px;">
                            <a href="function?funtion=import2" class="mailbox-attachment-name"><i class="fas fa-paperclip"></i> Template Function.xlsx</a>
                            <span class="mailbox-attachment-size clearfix mt-1">
                                <span>8,8 KB</span>
                                <a href="function?funtion=import2" class="btn btn-default btn-sm float-right"><i class="fas fa-cloud-download-alt"></i></a>
                            </span>
                        </div>  
                    </div>
                </div>
                <div class="modal-footer justify-content-between">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Import</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>                     
</form>
<c:if test="${thongbao1!=null}">
    <div id="modal-lg" class="toasts-top-right fixed">
        <div class="toast bg-success fade show" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-header"><strong class="mr-auto">Notification</strong>
                <button data-dismiss="toast" type="button" class="ml-2 mb-1 close" aria-label="Close" onclick="checkForm3()"><span aria-hidden="true">×</span></button>
            </div><div class="toast-body">${requestScope.thongbao1}</div>
        </div>
    </div>
</c:if>
<c:if test="${Thongbaode!=null}">
    <div id="toastsContainerTopRight" class="toasts-top-right fixed">
        <div class="toast bg-success fade show" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-header"><strong class="mr-auto">Notification</strong>
                <button data-dismiss="toast" type="button" class="ml-2 mb-1 close" aria-label="Close" onclick="checkForm4()"><span aria-hidden="true">×</span></button>
            </div><div class="toast-body">${requestScope.Thongbaode}</div>
        </div>
    </div>
</c:if>


<style>
    .hide{
        display: none;
    }
</style>
<script>
    const input = document.getElementById('inputImport')
    input.addEventListener('change', (event) => {
        const target = event.target
        if (target.files && target.files[0]) {

            /*Maximum allowed size in bytes
             5MB Example
             Change first operand(multiplier) for your needs*/
            const maxAllowedSize = 10 * 1024 * 1024; //10MB
            if (target.files[0].size > maxAllowedSize) {
                // Here you can ask your users to load correct file
                target.value = ''
            }
        }
    })
    function checkForm3() {
        document.getElementById('modal-lg').style.display = 'none';
    }
    function checkForm4() {
        document.getElementById('toastsContainerTopRight').style.display = 'none';
    }

    setTimeout(function () {
        document.getElementById('toastsContainerTopRight').classList.add("hide");
    }, 5000);
    setTimeout(function () {
        document.getElementById('modal-lg').classList.add("hide");
    }, 15000);
    setTimeout(function () {
        document.getElementById('toastsContainerTopRight2').classList.add("hide");
    }, 5000);
</script>                 
<!--                                        modal Export-->
<%@include file="../home/Footer.jsp" %>
</body>
</html>

