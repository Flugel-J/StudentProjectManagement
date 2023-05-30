<%-- 
    Document   : registration
    Created on : May 16, 2022, 5:24:39 PM
    Author     : 03623
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Registration</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.2/font/bootstrap-icons.css">
</head>
<body class="hold-transition sidebar-mini layout-fixed" style="background: #E9ECEF">


    <div class="container">
        <div class="row">
          <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
            <div class="card border-0 shadow rounded-3 my-5">
              <div class="card-body p-4 p-sm-5">
                <h5 class="card-title text-center mb-5 fw-light fs-5"><img src="<%=request.getContextPath()%>/imgs/log-in.png" style="width: 30px; margin-right: 5px" >User registration</h5>
                <p class="thongbao1" style="color: red">${requestScope.notification1}</p>
                <p class="thongbao1"></p>
                <form action="registration" method="POST">
                    <div class="form-floating mb-3">
                        <input type="text" name="fullname" class="form-control" id="floatingInput" placeholder="Username" maxlength="40" required>
                        <label for="floatingInput">Full name</label>
                      </div> 
                  <div class="form-floating mb-3">
                      <input class="form-control"  type="text" placeholder="Disabled input" aria-label="Disabled input example" value="${requestScope.email2}" disabled>
                      <input name="email" type="hidden" value="${requestScope.email2}">
                  </div>
                  <div class="form-floating mb-3">
                    <input type="password" name="password" class="form-control" id="floatingPassword" placeholder="Password" maxlength="50" required>
                    <label for="floatingPassword">Password</label>
                  </div>
                  <div class="form-floating mb-3">
                    <input type="password" name="re_password" class="form-control" id="floatingPassword" placeholder="Password" maxlength="50" required>
                    <label for="floatingPassword">Re-enter password</label>
                  </div>
                  <div class="form-floating mb-3">
                    <input type="number" name="mobile" class="form-control" id="floatingInput" placeholder="Username" maxlength="10" required>
                    <label for="floatingInput">mobile</label>
                  </div>
                  <div class="form-floating mb-3">
                    <input type="date" name="date" class="form-control" id="floatingInput" placeholder="Username" required>
                    <label for="floatingInput">date of birth </label>
                  </div>
                  Gender:
                  <div class="form-check form-check-inline">
                      <input class="form-check-input" name="gender" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="male" required checked>
                    <label class="form-check-label" for="inlineRadio1">Male</label>
                  </div>
                  <div class="form-check form-check-inline">
                    <input class="form-check-input" name="gender" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="female" required>
                    <label class="form-check-label" for="inlineRadio2">Female</label>
                  </div>
                  <div class="d-grid" style="margin-top: 10px;margin-bottom: 15px;">
                        <button class="btn btn-primary btn-login text-uppercase fw-bold" type="submit">Done</button>
                  </div>
                  </form>
                  <div>
                    <a href="resetpassword" style="padding-right: 10px;">Forget Password </a>
                  <a href="login" style="float: right;">Login</a>
                  </div>
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


