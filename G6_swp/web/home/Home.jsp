<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Home</title>

        <%@include file="HeaderLink.jsp" %>


    </head>

    <body class="layout-top-nav sidebar-closed sidebar-collapse">

        <!-- header -->
        <%@include file="Header.jsp" %>

        <!-- Nội dung -->

        <!--   In content-->
        <div class="row mb-2">
            <div class="col-sm-6">
                <h4>FPT Project Management Website</h4>
            </div>
            <div class="col-sm-6">
            </div>
        </div>
        <!--   In content-->  



        <!-- Main content -->

        <!--   In content-->
        <div class="card" style="margin-bottom: 0px;">
            <div class="card-header">
                <h3 class="card-title">About us</h3>

            </div>
            <div class="card-body" style="display: flex;">
                <div class="col-6">
                    <img src="<%=request.getContextPath()%>/imgs/home_img.png" alt="" style="width: 100%; height: 350px"/>
                </div>
                <div class="col-6">
                    <br><br>With the mission of &quot;Providing global competitiveness to a large number of learners, contributing to the expansion of

                    the country&#39;s intellectual frontiers&quot;, the educational philosophy &quot;Education and training is the organization and

                    management of learners&#39; self-study&quot;. education&rdquo;, FPT University aims to build a model of a new generation university;

                    Associate training with real life and human resource needs of the country, contributing to bringing Vietnam&#39;s Education

                    to the level of other countries in the world.
                </div>
            </div>
        </div>
        <!--   In content-->

        <!-- Nội dung -->

        <%@include file="Footer.jsp" %>



    </body>
</html>
