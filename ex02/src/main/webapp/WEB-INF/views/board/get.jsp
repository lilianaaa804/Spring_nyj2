<%--
  Created by IntelliJ IDEA.
  User: yeonjinoh
  Date: 2021/04/01
  Time:
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false" %>

<%@include file="../includes/header.jsp"%>
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Tables</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                DataTables Advanced Tables
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <div class="form-group">
                    <label>Bno</label> <input class="form-control" name='bno'
                                              value='<c:out value="${board.bno }"/>' readonly="readonly">
                </div>

                <div class="form-group">
                    <label>Title</label> <input class="form-control" name='title'
                                                value='<c:out value="${board.title }"/>' readonly="readonly">
                </div>

                <div class="form-group">
                    <label>Text area</label>
                    <textarea class="form-control" rows="3" name='content'
                              readonly="readonly"><c:out value="${board.content}" /></textarea>
                </div>

                <div class="form-group">
                    <label>Writer</label> <input class="form-control" name='writer'
                                                 value='<c:out value="${board.writer }"/>' readonly="readonly">
                </div>

                <%-- 		<button data-oper='modify' class="btn btn-default">
                        <a href="/board/modify?bno=<c:out value="${board.bno}"/>">Modify</a></button>
                        <button data-oper='list' class="btn btn-info">
                        <a href="/board/list">List</a></button> --%>
                <button data-oper='modify' class="btn btn-default">Modify</button>
                <button data-oper='list' class="btn btn-info">List</button>
            </div>
            <!-- end panel-body-->
        </div>
        <!--end panel-->
    </div>
</div>
<!-- /.row-->



<%@include file="../includes/footer.jsp"%>