
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Setting Detail</title>

    <!-- Google Font: Source Sans Pro -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/view1/plugins/fontawesome-free/css/all.min.css">
    <!-- jsGrid -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/view1/plugins/jsgrid/jsgrid.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/view1/plugins/jsgrid/jsgrid-theme.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/view1/dist/css/adminlte.min.css">
    <%@include file="../home/HeaderLink.jsp" %>
</head>
<body class="layout-top-nav sidebar-closed sidebar-collapse">
    <%@include file="../home/Header.jsp" %>
    <div class="content-wrapper">
        <div class="container h-100">
            <div class="row h-100 justify-content-center align-items-center">
                <form id="form" class = 'card p-3 bg-light' method="post" action="userlist?tag=update" onsubmit="return confirm('Do you really want to update ?');">
                    <h2 class="text-uppercase"> User details </h2>
                    <input type="text" value="${user.userId}" name="settingId" hidden />
                    <div class="row">
                        <div class="form-row col-7">
                            <input class="col-11" style="margin: 5px; width: 150px" type="text" value="${user.userId}" name="id" hidden />
                            <div class="col-11 ">
                                <label for="name" style="width: 70px;">Name</label>
                                <input id="name" title="Name must be contains a-z"  style="margin-left: 20px; width: 80%" type="text" value="${user.fullName}" name="name"  /> 
                            </div>
                            <div class="col-11 " >
                                <label for="email" style="width: 70px;">Email</label>
                                <input id="email"   style="margin-left: 20px; width: 80%" type="text" value="${user.email}" name="email"  />
                            </div >
                            <div class="col-11">
                                <label  for="mobile" style="width: 70px;">Mobile</label>
                                <input  id="mobile" pattern="(0)+([0-9]{9})\b"  title="PhoneNumber must start with 0,only numbers" style="margin-left: 20px;width: 80%" type="text" value="${user.mobile}" name="mobile"  />
                            </div>
                            <div class="col-11 ">
                                <label for="facebook" style="width: 70px;">Facebook</label>
                                <input id="facebook"  style="margin-left: 20px; width: 80%" type="text" value="${user.facebookLink}" name="facebook"  />
                            </div>
                            <div class="col-11 ">
                                <label for="gender" style="width: 70px;">Gender</label>
                                <select name="gender" style="margin-left: 20px; width: 20%">
                                    <option value="1" ${user.gender == true ? "selected" : ""}>Male</option>
                                    <option value="0" ${user.gender == false ? "selected" : ""}>Female</option>
                                </select>
                            </div>
                            <div class="col-11 row">
                                <div class="col-6 ">
                                    <label for="status" >Status</label>
                                    <select id="status" name="status" style="width: 100px; margin-left: 40px">
                                        <option value="1" ${user.status == true ? "selected" : ""}>Active</option>
                                        <option value="0" ${user.status == false ? "selected" : ""}>Deactive</option>
                                    </select>
                                </div>
                                <div class="col-6 ">
                                    <label for="roleId" >Role</label>
                                    <select id="roleId" name="roleId" style="width: 100px; margin-left: 40px">
                                        <c:forEach items="${listRole}" var = "s" begin="0" varStatus ="i" end="4">
                                            <option value="${i.count}" ${user.roleId == s? "selected":""}>${s}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-row col-5" style="width: 100px; height: 100px">
                            <image  class="col-12 d-flex justify-content-center" style="width: 100px; height: 250px" src="./imgs/${user.avatarLink}">
                        </div>
                    </div>
                    <div class="row">
                        <button class="btn btn-primary col-4 mt-4" type="submit" id="btn">Submit</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%@include file="../home/Footer.jsp" %>
</body>
<script>
 
</script>
</html>
