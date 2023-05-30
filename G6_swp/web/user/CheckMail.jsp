<%-- 
    Document   : checkmail
    Created on : May 17, 2022, 5:17:59 PM
    Author     : 03623
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Verify email</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.2/font/bootstrap-icons.css">
</head>
<body class="hold-transition sidebar-mini layout-fixed" style="background: #E9ECEF">

    <div class="container">
        <div class="row">
          <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
            <div class="card border-0 shadow rounded-3 my-5">
              <div class="card-body p-4 p-sm-5">
                <h5 class="card-title text-center mb-5 fw-light fs-5"><img src="<%=request.getContextPath()%>/imgs/patient.png" style="width: 30px; margin-right: 5px" > User registration</h5>
                <p class="thongbao1" style="color: red">${requestScope.notification1}</p>
                <form action="registration" method="POST"> 
                  <div class="form-floating mb-3">
                    <input type="text" name="mail" class="form-control" id="floatingInput" placeholder="Username" required>
                    <label for="floatingInput">Your Email</label>
                  </div>
                  <a href="resetpassword" style="padding-right: 10px;">Forget Password </a>
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
