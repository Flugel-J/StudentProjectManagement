<%@page import="model.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>User Profile</title>
        <%@include file="../home/HeaderLink.jsp" %>

    </head>
    <body class="layout-top-nav sidebar-closed sidebar-collapse">
        <%@include file="../home/Header.jsp" %>
        <div class="content-wrapper" style="min-height: 632px;">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <div class="container-fluid">
                    <div class="row mb-2">
                        <div class="col-sm-6">
                            <h1 class="text-uppercase">User Profile</h1>
                            <c:if test="${alert!=null}">
                                <p style="color: green">${alert}</p>
                            </c:if>
                        </div>
                    </div>
                </div><!-- /.container-fluid -->
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <!-- left column -->
                        <div class="col-md-12">
                            <!-- general form elements -->
                            <div class="card card-primary">
                                <div class="card-header">

                                    <div class="image">
                                        <c:if test = "${sessionScope.useraccount.avatarLink == null}">
                                            <img style="width: 50px;" src="<%=request.getContextPath()%>/home/pages/comment.png" class="img-circle elevation-2" alt="User Image">
                                        </c:if>
                                        <c:if test = "${sessionScope.useraccount.avatarLink != null}">
                                            <img style="width: 100px;" src="<%=request.getContextPath()%>/imgs/${sessionScope.useraccount.avatarLink}"class="img-circle elevation-2" alt="User Image">
                                        </c:if>
                                    </div> <br/>
                                </div>
                                <!-- /.card-header -->
                                <!-- form start -->
                                <form action="userprofile" method="post" enctype="multipart/form-data">
                                    <div class="card-body">
                                        <div class="form-group">
                                            <label for="role">Role</label>
                                            <input type="text" id="role" name="role" class="form-control" value="${sessionScope.useraccount.roleId}" readonly/>
                                        </div>
                                        <div class="form-group">
                                            <label for="roll">RollNumber</label>
                                            <input type="text" class="form-control" id="roll" value="${sessionScope.useraccount.rollNumber}" name="roll" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label for="name">Full Name</label>
                                            <input type="text" title="Name must be contains a-z" id="name" maxlength="40" class="form-control"  value="${sessionScope.useraccount.fullName}" name="name" required>
                                        </div><!--
                                        -->                                        <div class="form-group">
                                            <label for="dob" >Dob</label>
                                            <input type="date" id="dob" value="${sessionScope.useraccount.dob}" name="dob" required="">
                                        </div><!--
                                        -->                                        <div class="form-group">
                                            <label>Gender</label><br>
                                            <input type="radio"name="gender" ${sessionScope.useraccount.gender==true ? "checked":""} value="1">Male
                                            <input type="radio" name="gender" ${sessionScope.useraccount.gender==false ? "checked":""}  value="0">Female
                                        </div><!--
                                        -->                                        <div class="form-group">
                                            <label for="mobile">Mobile</label>
                                            <input type="text" id="mobile" pattern="(0)+([0-9]{9})\b"  title="PhoneNumber must start with 0,only numbers" class="form-control" id="exampleInputEmail1" value="${sessionScope.useraccount.mobile}" name="mobile" required="">
                                        </div><!--
                                        -->                                        <div class="form-group">
                                            <label for="mail">Email</label>
                                            <input type="email" id="mail" class="form-control" id="mail" value="${sessionScope.useraccount.email}" name="email" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label for="face">FaceBook</label>
                                            <input type="text" class="form-control" id="face" value="${sessionScope.useraccount.facebookLink}" name="facebook" >
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Image</label>
                                            <div class="col-lg-9 col-xl-8">
                                                <input accept=".jpg, .jpeg, .png" class="form-control" type="file" name="image" src="imgs/${sessionScope.useraccount.avatarLink}"/>
                                            </div>
                                        </div>
                                    </div>
                                    <input type="text" value="${sessionScope.useraccount.userId}" name="id" hidden>
                                    <!-- /.card-body -->

                                    <div class="card-footer">
                                        <button type="submit" id="btn" class="btn btn-primary">Edit</button>
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
        const dob = document.querySelector("#dob");
        const btn = document.querySelector("#btn");
        const date = dob.value;
        dob.addEventListener('input', () => {
            let inputYear = dob.value.split('-')[0];
            var today = new Date();
            const currentYear = today.getFullYear();
            if (currentYear - inputYear < 18) {
                alert("Please select date of birth greater than 18");
                dob.value = date;
            }
        })
        btn.addEventListener('click', () => {
           alert("Update Successfully");
        })
    </script>
</html>