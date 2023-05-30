<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<div class="wrapper">
    <!-- Navbar -->
    <nav class="main-header navbar navbar-expand-md navbar-light navbar-white">
        <div class="container">

            <a href="" class="navbar-brand">
                <img src="<%=request.getContextPath()%>/imgs/FPT.jpg" alt="AdminLTE Logo" class="brand-image img-circle elevation-3" style="opacity: .8; height: 35px">
                <span class="brand-text font-weight-light">FPT Project</span>
            </a>
            <!-- Left navbar links -->
            <ul class="navbar-nav">
                <c:set var="student" value="student"/>
                <c:if test = "${sessionScope.useraccount.roleId != student&&sessionScope.useraccount != null}">
                    <li class="nav-item">
                        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
                    </li>
                </c:if>
                <li class="nav-item d-none d-sm-inline-block">
                    <a href="home" class="nav-link">Home</a>
                </li>
                <c:if test = "${sessionScope.useraccount != null}">
                    <c:set var="student" value="student"/>
                    <c:if test = "${sessionScope.useraccount.roleId == student}">
                        <li class="nav-item dropdown">
                            <a id="dropdownSubMenu1" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link dropdown-toggle"> Dashboard </a>

                            <!--student-->
                            <ul aria-labelledby="dropdownSubMenu1" class="dropdown-menu border-0 shadow" style="left: 0px; right: inherit;">
                                <li><a href="featurelist" class="dropdown-item"> Feature List </a></li>
                                <li><a href="issue" class="dropdown-item"> Issue </a></li>
                                <li><a href="teameval" class="dropdown-item"> Team Evaluation </a></li>
                                <li><a href="usereval" class="dropdown-item"> User Evaluation </a></li>
                            </ul>
                    </c:if>    
                    <!--student-->

                    </li>
                </c:if>
            </ul>

            <c:if test = "${sessionScope.useraccount == null}">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item" style="padding-right: 10px;">
                        <a href="login" ><button type="button" class="btn btn-block btn-light" >Login</button></a>
                    </li>
                    <li class="nav-item">
                        <a href="registration"><button type="button" class="btn btn-block btn-light" >Registration</button></a>
                    </li>
                </ul>
            </c:if>
            <c:if test = "${sessionScope.useraccount != null}">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item dropdown show" style="padding-right: 10px;">
                        <a href="#" class="d-block nav-link" data-toggle="dropdown" aria-expanded="true" style="padding-top: 5px;font-size: 20px; padding-right: 10px;color: #212529">${sessionScope.useraccount.fullName}

                            <c:if test = "${sessionScope.useraccount.avatarLink == null}">
                                <img src="<%=request.getContextPath()%>/home/pages/comment.png" alt="User Image" style="height: 40px; width: 40px; border-radius: 25%">
                            </c:if>
                            <c:if test = "${sessionScope.useraccount.avatarLink != null}">
                                <img src="<%=request.getContextPath()%>/imgs/${sessionScope.useraccount.avatarLink}" alt="User Image" style="height: 40px; width: 40px; border-radius: 25%">
                            </c:if>
                        </a>
                        <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right" style="left: inherit; right: 0px;">
                            <span class="dropdown-item dropdown-header">User features</span>
                            <div class="dropdown-divider"></div>
                            <a href="userprofile" class="dropdown-item">
                                <i class="bi bi-file-person-fill" style="font-size: 21px;"></i> User Profile
                            </a>
                            <div class="dropdown-divider"></div>
                            <a href="changepassword" class="dropdown-item">
                                <i class="bi bi-file-earmark-lock2-fill" style="font-size: 21px;"></i> Change Password
                            </a>
                            <div class="dropdown-divider"></div>
                            <a href="logout" class="dropdown-item">
                                <i class="bi bi-box-arrow-right" style="font-size: 21px;"></i>  Log Out  
                            </a>
                        </div>
                    </li>
                </ul>
            </c:if>



        </div> 
    </nav>
    <!-- /.navbar -->

    <aside class="main-sidebar elevation-4 sidebar-light-navy">
        <!-- Brand Logo -->
        <a href="" class="brand-link">
            <img src="<%=request.getContextPath()%>/imgs/FPT.jpg" alt="AdminLTE Logo" class="brand-image img-circle elevation-3" style="opacity: .8">
            <span class="brand-text font-weight-light">FPT Project</span>
        </a>

        <!-- Sidebar -->
        <div class="sidebar os-theme-dark">
            <!-- Sidebar Menu -->
            <nav class="mt-2">
                <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                    <!-- Add icons to the links using the .nav-icon class
                         with font-awesome or any other icon font library -->
                    <li class="nav-item menu-open">
                        <a href="#" class="nav-link">
                            <i class="nav-icon fas fa-tachometer-alt"></i>
                            <p>
                                Dashboard
                                <i class="right fas fa-angle-left"></i>
                            </p>
                        </a>
                        <ul class="nav nav-treeview">
                            <!--Admin-->                
                            <c:set var="admin" value="admin"/>
                            <c:if test = "${sessionScope.useraccount.roleId == admin}">
                                <li class="nav-item">
                                    <a href="setting" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> System Settings </p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="userlist" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> User List </p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="subject" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> Subject List </p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="subjectsetting" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> Subject Setting </p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="iterationlist" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> Iteration List </p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="class" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> Class List </p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="evalcriteria" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> Criteria List </p>
                                    </a>
                                </li>
                            </c:if>
                            <!--Admin-->  
                            <!--Author-->
                            <c:set var="Author" value="author"/>
                            <c:if test = "${sessionScope.useraccount.roleId == Author}">
                                <li class="nav-item">
                                    <a href="subjectsetting" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> Subject Setting </p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="iterationlist" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> Iteration List </p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="class" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> Class List </p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="evalcriteria" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> Criteria List </p>
                                    </a>
                                </li>
                            </c:if>
                            <!--Author-->
                            <!--Trainer-->
                            <c:set var="Trainer" value="trainer"/>
                            <c:if test = "${sessionScope.useraccount.roleId == Trainer}">
                                <li class="nav-item">
                                    <a href="class" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> Class List </p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="classuser" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> Class User List </p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="team" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> Team List </p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="milestone" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> Milestone List </p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="featurelist" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> Feature List </p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="function" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> Function List </p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="issue" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> Issue </p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="teameval" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> Team Evaluation </p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="usereval" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p> User Evaluation </p>
                                    </a>
                                </li>
                            </c:if>
                            <!--Trainer--> 
                        </ul>
                    </li>

                </ul>
            </nav>
            <!-- /.sidebar-menu -->
        </div>
        <!-- /.sidebar -->
    </aside>

    <!-- Title -->
    <div class="content-wrapper" style="min-height: 632px;">
        <div class="content">
            <div class="container" style="padding-bottom: 16px; padding-top: 10px">