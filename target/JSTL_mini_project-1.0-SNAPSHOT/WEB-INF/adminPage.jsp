<%--
  Created by IntelliJ IDEA.
  User: Tweny
  Date: 16/10/2020
  Time: 18.43
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../includes/header.inc"%>

<div class="container">
    <%@include file="../includes/navigation.inc"%>

        <div class="row mt-4">
            <div class="col-sm-12 col-md-2" style="border-right: 1px solid gray;">
                <nav class="nav flex-column">
                    <a class="nav-link" href="FrontController?target=redirect&destination=addUser">Add User</a>
                    <a class="nav-link" href="FrontController?target=redirect&destination=allUsers">All Users</a>
                    <a class="nav-link" href="FrontController?target=redirect&destination=addCategory">Add Category</a>
                    <a class="nav-link" href="FrontController?target=redirect&destination=allCategories">All Categories</a>
                    <a class="nav-link" href="FrontController?target=redirect&destination=addCar">Add Car</a>
                    <a class="nav-link" href="FrontController?target=redirect&destination=allCars">All Cars</a>
                </nav>
            </div>

            <div class="col-sm-12 col-md-10" style="">

                <h1 class="text-center">Welcome: ${sessionScope.user.username}</h1>

                <c:choose>
                    <c:when test="${requestScope.adminMenu == 'addUser'}">
                        <%@include file="../includes/addUser.inc"%>
                    </c:when>
                    <c:when test="${requestScope.adminMenu == 'allUsers'}">
                        <%@include file="../includes/allUsers.inc"%>
                    </c:when>
                    <c:when test="${requestScope.adminMenu == 'editUser'}">
                        <%@include file="../includes/editUser.inc"%>
                    </c:when>
                    <c:when test="${requestScope.adminMenu == 'addCategory'}">
                        <%@include file="../includes/addCategory.inc"%>
                    </c:when>
                    <c:when test="${requestScope.adminMenu == 'allCategories'}">
                        <%@include file="../includes/allCategories.inc"%>
                    </c:when>
                    <c:when test="${requestScope.adminMenu == 'addCar'}">
                        <%@include file="../includes/addCar.inc"%>
                    </c:when>
                    <c:when test="${requestScope.adminMenu == 'allCars'}">
                        <%@include file="../includes/allCars.inc"%>
                    </c:when>
                    <c:otherwise>

                    </c:otherwise>
                </c:choose>

<%--                <div>--%>
<%--                    <c:if test ="${requestScope.errorMessage != null}">--%>
<%--                        <div class="alert alert-danger" style="padding-bottom: inherit;">--%>
<%--                            <p>${requestScope.errorMessage}</p>--%>
<%--                        </div>--%>
<%--                    </c:if>--%>
<%--                </div>--%>

            </div>

        </div>


<%@include file="../includes/footer.inc"%>