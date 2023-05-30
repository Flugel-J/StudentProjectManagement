<%-- 
    Document   : ClassUserList
    Created on : Jun 9, 2022, 4:34:37 PM
    Author     : 03623
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Class User List</title>
        <!--        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">-->

        <%@include file="../home/HeaderLink.jsp" %>

    </head>
    <body class="layout-top-nav sidebar-closed sidebar-collapse">
        <%@include file="../home/Header.jsp" %>

        <!--        noidung-->
        <div class="content-wrapper">
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <h2 class="card-title text-uppercase"><strong>Class User List</strong></h2>
                                </div>
                                &nbsp;&nbsp; &nbsp;&nbsp;  
                                <form action="classuser" method="get">
                                    <div style="width: 100%; padding-bottom: 5px">
                                        <div class="input-group" style="width: 90%;margin-left: 15px">
                                            <input style="height: 35px;" name="search" type="search" class="form-control form-control-lg" placeholder="Search name user or rollnumber" value="${search}">
                                            <div class="input-group-append" style="height: 35px;">
                                                <button type="submit" class="btn btn-default">
                                                    <i class="fa fa-search" style="font-size: 1rem"></i>
                                                </button>
                                            </div>
                                            <a href="classuser"><button type="button" class="btn btn-default" style="margin-left: 20px;color: blue"><i class="bi bi-arrow-clockwise"></i> Reset</button></a>
                                            <button data-toggle="modal" data-target="#modalExport" type="button" class="btn btn-success" style="margin-left: 70px; background-color: #236541;padding: 0.13rem 0.4rem;font-size: 14px;color: white;">
                                                <a ><i class="bi bi-file-earmark-arrow-down"></i> Export Excel</a>
                                            </button>


                                            <button data-toggle="modal" data-target="#modal-lgImport" type="button" class="btn btn-success" style="margin-left: 20px;padding: 0.13rem 0.4rem;font-size: 14px">
                                                <a  style="float: right; color: white;"><i class="bi bi-file-earmark-arrow-up"></i> Import </a>
                                            </button>
                                        </div>

                                    </div>

                                    <div style="display: flex;">
                                        <select class="form-control" name="class_code" onchange="this.form.submit()" style="width: 15%; margin-left: 13px; height: 35px">
                                            <c:forEach items="${requestScope.classlist}" var="c">
                                                <option value="${c.classCode}" ${classCode == c.classCode ?"selected":""}>${c.classCode}</option>
                                            </c:forEach>
                                        </select>
                                        <select class="form-control" name="class_year" onchange="this.form.submit()" style="width: 15%; margin-left: 7px; height: 35px">
                                            <option value="" ${classYear==null?"selected":""}>All Class Year</option>
                                            <c:forEach items="${requestScope.classyear}" var="c">
                                                <option value="${c}" ${classYear == c ?"selected":""}>${c}</option>
                                            </c:forEach>
                                        </select> 
                                        <select class="form-control" name="class_term" onchange="this.form.submit()" style="width: 15%; margin-left: 7px; height: 35px">
                                            <option value="" ${classterm==null?"selected":""}>All Class Term</option>
                                            <option value="1" ${requestScope.classterm=="1"?"selected":""}>Spring</option>
                                            <option value ="2" ${requestScope.classterm=="2"?"selected":""}>Summer</option>
                                            <option value ="3" ${requestScope.classterm=="3"?"selected":""}>Fall</option>
                                            <option value ="4" ${requestScope.classterm=="4"?"selected":""}>Winter</option>
                                        </select>
                                        <select class="form-control" name="team_leader" onchange="this.form.submit()" style="width: 15%; margin-left: 7px; height: 35px">
                                            <option value="" ${teamleader==null?"selected":""}>All Team Role</option>
                                            <option value="1" ${requestScope.teamleader=="1"?"selected":""}>Leader</option>
                                            <option value ="0" ${requestScope.teamleader=="0"?"selected":""}>Member</option>
                                        </select>
                                        <select class="form-control" name="dropout_date" onchange="this.form.submit()" style="width: 15%; margin-left: 7px; height: 35px">
                                            <option value="" ${dropoutdate==null?"selected":""}>Status In Class</option>
                                            <option value="true" ${requestScope.dropoutdate=="true"?"selected":""}>dropout</option>
                                            <option value ="false" ${requestScope.dropoutdate=="false"?"selected":""}>studying</option>
                                        </select>
                                        <select class="form-control" name="status" onchange="this.form.submit()" style="width: 15%; margin-left: 7px; height: 35px">
                                            <option value="" ${requestScope.selectedStatus==null?"selected":""}>All statuses</option>
                                            <option value="1" ${requestScope.selectedStatus=="1"?"selected":""}>Activate</option>
                                            <option value ="0" ${requestScope.selectedStatus=="0"?"selected":""}>Deactivate</option>
                                        </select>
                                    </div>

                                </form>
                                <!-- /.card-header -->
                                <div class="card-body">
                                    <table id="example2" class="table table-bordered table-hover" style="font-size: 0.9rem;">
                                        <thead>
                                            <tr>
                                                <th>Class Id</th>
                                                <th>Class Code</th>
                                                <th>Subject Code</th>
                                                <th>Name</th>
                                                <th>Roll number</th>
                                                <th>Team Id</th>
                                                <th>Team Leader</th>
                                                <th>Dropout Date</th>
                                                <th>Status</th>
                                                <th style="width: 93px;">Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:if test="${!empty classuserlist}">
                                                <c:forEach items="${requestScope.classuserlist}" var="l">
                                                    <tr>
                                                        <td>${l.classId.classId}</td>
                                                        <td>${l.classId.classCode}</td>
                                                        <td>${l.classId.subject.subjectCode}</td>
                                                        <td>${l.user.fullName}</td>
                                                        <td>${l.user.rollNumber}</td>
                                                        <td>${l.team.teamId}</td>
                                                        <td>${l.teamLeader}</td>
                                                        <td>${l.dropoutDate}</td>
                                                        <td style="color: ${l.status==true?"green":"red"};">${l.status==true?"Activate":"Deactivate"}</td>
                                                        <th>
                                                            <a  class="btn btn-info btn-sm" href="classuser?funtion=detail&userid=${l.user.userId}&classid=${l.classId.classId}&teamid=${l.team.teamId}">
                                                                <i class="fas fa-pencil-alt"></i>
                                                            </a>
                                                            <a data-toggle="modal" data-target="#modal-default${l.user.userId}" class="btn btn-danger btn-sm" >
                                                                <i class="fa fa-flag"></i>
                                                            </a>
                                                            <div class="modal fade" id="modal-default${l.user.userId}">
                                                                <div class="modal-dialog">
                                                                    <div class="modal-content">
                                                                        <div class="modal-header">
                                                                            <h4 class="modal-title">Status change confirmation</h4>
                                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                                <span aria-hidden="true">&times;</span>
                                                                            </button>
                                                                        </div>
                                                                        <div class="modal-body">
                                                                            <p>Are you sure you want to save your changes?</p>
                                                                        </div>
                                                                        <div class="modal-footer justify-content-center">
                                                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                                            <a href="classuser?funtion=status&statususer=${l.status}&userid=${l.user.userId}&classid=${l.classId.classId}&teamid=${l.team.teamId}&Page=${Page}&class_code=${classCode}&class_year=${classYear}&class_term=${classterm}&team_leader=${teamleader}&dropout_date=${dropoutdate}&status=${selectedStatus}&search=${search}"><button type="button" class="btn btn-primary">Save changes</button></a>
                                                                        </div>
                                                                    </div>
                                                                    <!-- /.modal-content -->
                                                                </div>
                                                                <!-- /.modal-dialog -->
                                                            </div>
                                                        </th>

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
                            <a class="page-link" href="classuser?Page=1&class_code=${classCode}&class_year=${classYear}&class_term=${classterm}&team_leader=${teamleader}&dropout_date=${dropoutdate}&status=${selectedStatus}&search=${search}">Begin</a>
                        </li>
                    </c:if>
                    <c:if test="${Page!=1}">
                        <li class="page-item ">
                            <a class="page-link" href="classuser?Page=${Page-1}&class_code=${classCode}&class_year=${classYear}&class_term=${classterm}&team_leader=${teamleader}&dropout_date=${dropoutdate}&status=${selectedStatus}&search=${search}">${Page-1}</a>
                        </li>
                    </c:if>    

                    <li class="page-item active">
                        <a class="page-link" href="classuser?Page=${Page}&class_code=${classCode}&class_year=${classYear}&class_term=${classterm}&team_leader=${teamleader}&dropout_date=${dropoutdate}&status=${selectedStatus}&search=${search}">${Page}</a>
                    </li>

                    <c:if test="${Page!=count}">
                        <li class="page-item ">
                            <a class="page-link" href="classuser?Page=${Page+1}&class_code=${classCode}&class_year=${classYear}&class_term=${classterm}&team_leader=${teamleader}&dropout_date=${dropoutdate}&status=${selectedStatus}&search=${search}">${Page+1}</a>
                        </li>
                    </c:if>
                    <c:if test="${Page <= count-1}">
                        <li class="page-item ">
                            <a class="page-link" href="classuser?Page=${count}&class_code=${classCode}&class_year=${classYear}&class_term=${classterm}&team_leader=${teamleader}&dropout_date=${dropoutdate}&status=${selectedStatus}&search=${search}">End</a>
                        </li>
                    </c:if>
                </c:if>
            </ul>
        </nav>
        <!--                                        modal Export-->
        <form action="classuser" method="post">
            <input name="funtion" value="export" hidden>
            <div class="modal fade" id="modalExport">
                <div class="modal-dialog">
                    <div class="modal-content">

                        <div class="modal-header">
                            <h4 class="modal-title">Export Class User</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <p>The .xlsx export will be created in the background. Once finished, it will be sent to <span style="color: blue">${sessionScope.useraccount.email}</span> in an attachment.</p>
                            Class: 
                            <select class="form-control" name="class_codeExcel" style="width: 35%;  height: 35px">
                                <c:forEach items="${requestScope.classlist}" var="c">
                                    <option value="${c.classCode}" ${classCode == c.classCode ?"selected":""}>${c.classCode}</option>
                                </c:forEach>
                            </select>

                        </div>
                        <div class="modal-footer justify-content-between">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Export</button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>                      
        </form>
        <form action="classuser" method="post" enctype="multipart/form-data">
            <input name="funtion" value="import" hidden>
            <div class="modal fade" id="modal-lgImport" >
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Import Class User</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">×</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <p>Upload xlsx file.</p>
                            <input type="file" id="inputImport" name="fileImport" accept=".xlsx" required/><br>
                            Class: 
                            <select class="form-control" name="classimport" style="width: 35%;  height: 35px">
                                <c:forEach items="${requestScope.classlist}" var="c">
                                    <option value="${c.classCode}" ${classCode == c.classCode ?"selected":""}>${c.classCode}</option>
                                </c:forEach>
                            </select>

                            <p>It must have a five-column header row: the first column Team code and the second column 
                                Roll number,the third column is Full Name, the fourth column is Team leader, the fifth 
                                column is User Note. The separator is automatically detected. The maximum file size allowed is 10 MB.</p>
                            <img src="<%=request.getContextPath()%>/imgs/importexcel.jpg" alt="" style="width: 60%"/>
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
        <c:if test="${thongbao!=null}">
            <div id="modal-lg" class="toasts-top-right fixed">
                <div class="toast bg-success fade show" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header"><strong class="mr-auto">Notification</strong>
                        <small>Close</small>
                        <button data-dismiss="toast" type="button" class="ml-2 mb-1 close" aria-label="Close" onclick="checkForm3()"><span aria-hidden="true">×</span></button>
                    </div><div class="toast-body">${requestScope.thongbao}</div>
                </div>
            </div>
        </c:if>
        <c:if test="${Thongbaode!=null}">
            <div id="toastsContainerTopRight" class="toasts-top-right fixed">
                <div class="toast bg-success fade show" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header"><strong class="mr-auto">Notification</strong>
                        <small>Close</small>
                        <button data-dismiss="toast" type="button" class="ml-2 mb-1 close" aria-label="Close" onclick="checkForm4()"><span aria-hidden="true">×</span></button>
                    </div><div class="toast-body">${requestScope.Thongbaode}</div>
                </div>
            </div>
        </c:if>
        <c:if test="${thongbaoexport!=null}">
            <div id="toastsContainerTopRight2" class="toasts-top-right fixed">
                <div class="toast bg-success fade show" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header"><strong class="mr-auto">Notification</strong>
                        <small>Close</small>
                        <button data-dismiss="toast" type="button" class="ml-2 mb-1 close" aria-label="Close" onclick="checkForm5()"><span aria-hidden="true">×</span></button>
                    </div><div class="toast-body">${requestScope.thongbaoexport}</div>
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
