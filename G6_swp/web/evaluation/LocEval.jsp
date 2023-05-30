<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Loc Evaluation</title>
        <%@include file="../home/HeaderLink.jsp" %>
    </head>
    <body class="layout-top-nav sidebar-closed sidebar-collapse">
        <%@include file="../home/Header.jsp" %>
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <div class="container-fluid">
                    <div class="row mb-2">
                        <div class="col-sm-6 card-header">
                            <h1 class="text-uppercase card-title">Loc Evaluation</h1>
                        </div>
                    </div>
                </div><!-- /.container-fluid -->
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <!-- left column -->
                        <div class="col-md-10">
                            <!-- general form elements -->
                            <div class="card card-primary">
                                <!-- /.card-header -->
                                <!-- form start -->
                                <c:if test="${requestScope.tag == 'view' && requestScope.locEval==null}">
                                    <h2 style="color: red;">This has not been evaluated !!!</h2>
                                </c:if>
                                <c:if test="${requestScope.tag != 'view' || requestScope.locEval!=null}">
                                    <form id="form" action="loceval" method="post">
                                        <input value="${requestScope.tag}" name="tag" hidden/>
                                        <input value="${requestScope.trackingId}" name="trackingId" hidden/>
                                        <input type="text" name="id" value="${requestScope.locEval==null?"0":requestScope.locEval.id}" hidden/>
                                        <c:if test="${requestScope.locEval==null}">
                                            <fmt:formatDate pattern="dd-MM-yyyy" value="<%= new java.util.Date()%>" var="today" />
                                        </c:if>
                                        <div class="card-body">
                                            <c:if test="${requestScope.tag != 'add'}">
                                                <div class="form-group">
                                                    <c:if test="${requestScope.tag == 'view'}">
                                                        <label>Evaluation time</label> 
                                                    </c:if>
                                                    <c:if test="${requestScope.tag == 'update'}">
                                                        <label>Last Evaluation time</label> 
                                                    </c:if>                                              
                                                    <br/>
                                                    <input name="time" class="form-control" value="${requestScope.locEval.time==null?today:requestScope.locEval.time}" readonly />
                                                </div>  
                                            </c:if>
                                            <div class="form-group">          
                                                <label>Complexity*</label> <br/>
                                                <c:if test="${requestScope.tag == 'view'}">
                                                    <input class="form-control" value="${requestScope.locEval.complexity.settingTitle}" readonly />
                                                </c:if>
                                                <c:if test="${requestScope.tag != 'view'}">
                                                    <c:forEach items="${requestScope.complexList}" var="c" varStatus="cS">
                                                        <input ${cS.count==1?"checked":""} ${requestScope.locEval.complexity.settingId==c.settingId?"checked":""} type="radio" name="complexity" required value="${c.settingId}" /> ${c.settingTitle}
                                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                                    </c:forEach>
                                                </c:if>
                                            </div>

                                            <div class="form-group">
                                                <label>Quality*</label> <br/>
                                                <c:if test="${requestScope.tag == 'view'}">
                                                    <input class="form-control" value="${requestScope.locEval.quality.settingTitle}" readonly />
                                                </c:if>
                                                <c:if test="${requestScope.tag != 'view'}">
                                                    <c:forEach items="${requestScope.qualityList}" var="q" varStatus="cS">
                                                        <input ${cS.count==1?"checked":""} ${requestScope.locEval.quality.settingId==q.settingId?"checked":""} type="radio" name="quality" required value="${q.settingId}" /> ${q.settingTitle}
                                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                                    </c:forEach>
                                                </c:if>                                       
                                            </div>
                                            <div class="form-group">
                                                <label for="weight">Note</label>                                         
                                                <textarea ${requestScope.tag == 'view'?"readonly":""} name="note" id="weight" maxlength="200" class="form-control" cols="20" rows="5">${requestScope.locEval.note}</textarea>
                                            </div>
                                            <c:if test="${requestScope.tag != 'view'}">
                                                <div class="row">
                                                    <div class="form-group col-md-6">
                                                        <label>Status*</label> <br/>
                                                        <input type="radio" name="status" ${(requestScope.locEval.status==true || requestScope.locEval.status==null)  ? "checked":""} value="1">Active   &nbsp;&nbsp;
                                                        <input type="radio" name="status" ${requestScope.locEval.status==false ? "checked":""} value="0">Inactive
                                                    </div>  
                                                </div>
                                            </c:if>
                                        </div>
                                        <!-- /.card-body -->
                                        <c:if test="${requestScope.tag != 'view'}">
                                            <div class="card-footer">
                                                <button type="reset" class="btn btn-primary">Reset</button>
                                                <button type="submit" class="btn btn-primary">
                                                    <c:if test="${requestScope.tag=='update'}">Update</c:if>
                                                    <c:if test="${requestScope.tag=='add'}">Add</c:if>
                                                    </button>
                                                </div>
                                        </c:if>
                                    </form>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <%@include file="../home/Footer.jsp" %>
    </body>
</html>
