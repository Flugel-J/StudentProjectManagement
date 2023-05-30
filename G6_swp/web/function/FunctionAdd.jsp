<%-- 
    Document   : ClassUserDetails
    Created on : Jun 11, 2022, 5:43:43 PM
    Author     : 03623
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>${thongbao2}</title>
        <%@include file="../home/HeaderLink.jsp" %>
    </head>
    <body class="layout-top-nav sidebar-closed sidebar-collapse">
        <%@include file="../home/Header.jsp" %>
        
        <jsp:useBean id="fudb" class="dal.FunctionDB" />
        
        <!--        noi dung-->
        <div class="content-wrapper" >
            <section class="content" style="padding-left: 10%; padding-right: 10%">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <div class="container-fluid">
                        <div class="row mb-2">
                            <div class="col-sm-6 card-header">
                                <h1 class="text-uppercase card-title">${thongbao2}</h1>
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
                                    <!-- /.card-header -->
                                    <!-- form start -->
                                    <form id="myform" action="function" method="post" >
                                        <input name="userid" value="${fudb.getFunctionbyid(functionid).user.userId}" hidden/> <!-- user id-->
                                        <input name="funtion" value="add" hidden/> <!-- user id-->
                                        <input name="funtionid" value="${fudb.getFunctionbyid(functionid).id}" hidden/> <!-- user id-->
                                        <c:set var="add" value="add new function"/>
                                        <c:if test="${thongbao2 == add}">
                                            <input name="teamId" value="${teamname.teamId}" hidden/> <!-- user id-->
                                        </c:if>
                                        <c:set var="detail" value="detail"/>
                                        <c:if test="${thongbao2 == detail }">
                                            <input name="teamId" value="${fudb.getFunctionbyid(functionid).team.teamId}" hidden/> <!-- user id-->
                                        </c:if>
                                        <div class="card-body">
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label>Team Name</label>
                                                    <c:set var="add" value="add new function"/>
                                                    <c:if test="${thongbao2 == add}">
                                                        <input class="form-control" name="teamname" value="${teamname.teamCode}" style="" disabled/>
                                                    </c:if>
                                                    <c:set var="detail" value="detail"/>
                                                    <c:if test="${thongbao2 == detail }">
                                                        <input name="funtiondetail" value="funtiondetail" hidden/> 
                                                        <input class="form-control" name="teamname" value="${fudb.getFunctionbyid(functionid).team.teamCode}" style="" disabled/>
                                                    </c:if>
                                                </div>
                                                <div class="form-group col-md-6">
                                                <label>Feature Name</label>
                                                <select class="selectpicker form-control" name="featureid">
                                                    <c:forEach items="${featurelist}" var="t">
                                                        <option value="${t.featureId}" ${t.featureId==fudb.getFunctionbyid(functionid).feature.featureId?"selected":""}>${t.featureName}</option>
                                                    </c:forEach>
                                                </select>  
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label>Function Name*</label>
                                                    <input class="form-control" name="functionname" value="${fudb.getFunctionbyid(functionid).functionName}" style="" required/>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label>Access Roles*</label>
                                                    <input class="form-control" name="accessroles" value="${fudb.getFunctionbyid(functionid).accessRoles}" style="" required/>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label>Complexity*</label>
                                                    <select class="selectpicker form-control" name="complexity">
                                                        <c:forEach items="${listsubjectsetting}" var="t">
                                                            <option value="${t.settingId}" ${t.settingId==fudb.getFunctionbyid(functionid).subjectSetting.settingId?"selected":""}>${t.settingTitle}</option>
                                                        </c:forEach>
                                                    </select>  
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label>Priority*</label>
                                                    <input value="${fudb.getFunctionbyid(functionid).priority}" class="form-control" name="priority" type="number" maxlength="2" oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);" required/>
                                                </div>
                                            </div>
                                            <div class="row">    
                                                <c:if test="${thongbao2 == add}"> 
                                                    <div class="form-group col-md-12" style="margin-bottom: 0.1rem;margin-top: 0.1rem;">
                                                    <label style="padding-right: 25px;">Status*</label>
                                                    <samp style="padding-right: 20px;color: #EEE600"><input type="radio" name="status" value="1" checked>Pending</samp>
                                                    <samp style="padding-right: 20px;color: #C39953"><input type="radio" name="status" value="2" >Planned</samp>
                                                    <samp style="padding-right: 20px;color: #ED9121"><input type="radio" name="status" value="3" >Evaluated</samp>
                                                    <samp style="padding-right: 20px;color: #9400D3"><input type="radio" name="status" value="4" >Rejected</samp>
                                                    </div>
                                                </c:if>
                                                <c:if test="${thongbao2 == detail }"> 
                                                    <div class="form-group col-md-12" style="margin-bottom: 0.1rem;margin-top: 0.1rem;">
                                                    <label style="padding-right: 25px;">Status*</label>
                                                    <samp style="padding-right: 20px;color: #EEE600"><input type="radio" name="status" value="1" ${status=="1"?"checked":""}>Pending</samp>
                                                    <samp style="padding-right: 20px;color: #C39953"><input type="radio" name="status" value="2" ${status=="2"?"checked":""}>Planned</samp>
                                                    <samp style="padding-right: 20px;color: #ED9121"><input type="radio" name="status" value="3" ${status=="3"?"checked":""}>Evaluated</samp>
                                                    <samp style="padding-right: 20px;color: #9400D3"><input type="radio" name="status" value="4" ${status=="4"?"checked":""}>Rejected</samp>
                                                    </div>
                                                </c:if>
                                            </div>
                                            <div class="form-group">
                                                <label>Description*</label>
                                                <textarea class="form-control" rows="4" name="description" maxlength="300">${fudb.getFunctionbyid(functionid).description}</textarea>
                                            </div>
                                            <!-- /.card-body -->


                                        </div>
                                            <div class="card-footer">
                                                <button class="btn btn-primary" type="reset">Reset</button>
                                                <button type="submit" class="btn btn-primary">Add</button>
                                            </div>
                                    </form>

                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </section>
        </div>

        <!--        noi dung-->



        <%@include file="../home/Footer.jsp" %>
    </body>
</html>
