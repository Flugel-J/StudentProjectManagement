<%-- 
    Document   : ChangePassword
    Created on : May 21, 2022, 3:13:43 PM
    Author     : 03623
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Change Password</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.2/font/bootstrap-icons.css">
</head>
<body class="hold-transition sidebar-mini layout-fixed" style="background: #E9ECEF">

    <div class="container">
        <div class="row">
          <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
            <div class="card border-0 shadow rounded-3 my-5">
              <div class="card-body p-4 p-sm-5">
                  <h5 class="card-title text-center mb-5 fw-light fs-5"><img src="<%=request.getContextPath()%>/imgs/synchronize.png" style="width: 25px; margin-right: 5px" >Password Setup</h5>
                <p class="thongbao1" style="color: red">${requestScope.notification1}</p>
                <form action="resetpassword" method="POST"> 
                    <input type="text" name="check1" value="konull" class="form-control" id="floatingInput" placeholder="Username" hidden>
                    <input type="text" name="mail" value="${requestScope.email}" class="form-control" id="floatingInput" placeholder="Username" hidden>
                  <div class="form-floating mb-3">
                    <input type="password" name="newpassword1" class="form-control" id="floatingPassword" placeholder="Password" value="" required>
                    <label for="floatingPassword">New Password</label>
                  </div>
                  <div class="form-floating mb-3">
                    <input type="password" name="newpassword2" class="form-control" id="floatingPassword" placeholder="Password" value="" required>
                    <label for="floatingPassword">Confirm Password</label>
                  </div>
                  <a href="registration" style="padding-right: 10px;">User registration </a>
                  <a href="login" style="float: right;">Login </a>
                  <div class="d-grid" style="margin-top: 10px;">
                        <button class="btn btn-primary btn-login text-uppercase fw-bold" type="submit">Done</button>
                  </div>
                  </form>
                  <hr class="my-4">
                  <form action="home" method="GET"> 
                  <div class="d-grid">
                    <button class="btn btn-facebook btn-login text-uppercase fw-bold" type="submit">
                        <i class="bi bi-house-heart"></i> Home
                    </button>
                  </div>
                  </form>
              </div>
            </div>
          </div>
        </div>
      </div>


</body>
</html>
