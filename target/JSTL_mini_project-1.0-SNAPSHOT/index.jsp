<%--
  Created by IntelliJ IDEA.
  User: Tweny
  Date: 16/10/2020
  Time: 15.56
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="includes/header.inc"%>

<div class="container">
    <%@include file="includes/navigation.inc"%>


    <h1>Hello, world!</h1>
        <div class="row">
            <div class="col-sm-12 col-md-1 text-center" style=""></div>

            <div class="col-sm-12 col-md-4 text-center" style="">

                <form class="form-signin" action="FrontController" method="POST" >

                    <input type="hidden" name="target" value="login">

                    <img class="mb-4" src="/docs/4.5/assets/brand/bootstrap-solid.svg" alt="" width="72" height="72">

                    <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>

                    <label for="inputEmail" class="sr-only">Email address</label>
                    <input type="email" name="email" id="inputEmail" class="form-control" placeholder="Email address" required="" autofocus="">

                    <label for="inputPassword" class="sr-only">Password</label>
                    <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required="">

                    <br>

                    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>

                </form>

                <div>
                    <c:if test ="${requestScope.messageSignIn != null}">
                        <div class="alert alert-danger" style="padding-bottom: inherit;">
                            <p>${requestScope.messageSignIn}</p>
                        </div>
                    </c:if>
                </div>

            </div>

            <div class="col-sm-12 col-md-2 text-center" style=""></div>

            <div class="col-sm-12 col-md-4 text-center" style="">

                <form class="form-signin" action="FrontController" method="POST">

                    <input type="hidden" name="target" value="register">

                    <img class="mb-4" src="/docs/4.5/assets/brand/bootstrap-solid.svg" alt="" width="72" height="72">

                    <h1 class="h3 mb-3 font-weight-normal">Please sign up</h1>

                    <label for="username" class="sr-only">Username</label>
                    <input type="text" name="username" id="username" class="form-control" placeholder="Username" required="" autofocus="">

                    <label for="email" class="sr-only">Email address</label>
                    <input type="email" name="email" id="email" class="form-control" placeholder="Email address" required="" autofocus="">

                    <label for="password1" class="sr-only">Enter Password</label>
                    <input type="password" name="password1" id="password1" class="form-control" placeholder="Password" required="">

                    <label for="password2" class="sr-only">Reenter Password</label>
                    <input type="password" name="password2" id="password2" class="form-control" placeholder="Password again" required="">

                    <br>

                    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>

                </form>

                <div>
                    <c:if test ="${requestScope.messageSignUp != null}">
                        <div class="alert alert-danger" style="padding-bottom: inherit;">
                            <p>${requestScope.messageSignUp}</p>
                        </div>
                    </c:if>
                </div>

            </div>

            <div class="col-sm-12 col-md-1 text-center" style=""></div>
        </div>
    
<%@include file="includes/footer.inc"%>
