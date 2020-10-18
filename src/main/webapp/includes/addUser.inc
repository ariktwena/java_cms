
<div class="row">
    <div class="col-sm-12 col-md-6" style="">
        <form action="FrontController" method="post">

            <input type="hidden" name="target" value="addUser">

            <div class="form-group">
                <label for="selectUsername">Username</label>
                <input type="text" class="form-control" id="selectUsername" placeholder="Write username" value="${requestScope.username}" name="username">
            </div>
            <div class="form-group">
                <label for="selectPassword">Password</label>
                <input type="password" class="form-control" id="selectPassword" placeholder="Write password" value="${requestScope.password}" name="password">
            </div>
            <div class="form-group">
                <label for="selectEmail">Email address</label>
                <input type="email" class="form-control" id="selectEmail" placeholder="Write email" value="${requestScope.user_email}" name="user_email">
            </div>
            <div class="form-group">
                <label for="selectRole">User Role</label>
                <select class="form-control" id="selectRole" name="user_role">
                    <c:choose>
                        <c:when test="${requestScope.user_role == 'customer'}">
                            <option>Choose One</option>
                            <option value="customer" selected>Customer</option>
                            <option value="admin">Admin</option>
                        </c:when>
                        <c:when test="${requestScope.user_role == 'admin'}">
                            <option>Choose One</option>
                            <option value="customer">Customer</option>
                            <option value="admin" selected>Admin</option>
                        </c:when>
                        <c:otherwise>
                            <option selected>Choose One</option>
                            <option value="customer">Customer</option>
                            <option value="admin">Admin</option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Create User</button>
        </form>

        <!--Error handling -->
        <div>
            <c:if test ="${requestScope.errorMessageCreate != null}">
                <div class="alert alert-danger" style="padding-bottom: inherit;">
                    <p>${requestScope.errorMessageCreate}</p>
                </div>
            </c:if>
        </div>

        <!--Success handling-->
        <div>
            <c:if test ="${requestScope.successMessage != null}">
                <div class="alert alert-success" style="padding-bottom: inherit;">
                    <p>${requestScope.successMessage}</p>
                </div>
            </c:if>
        </div>
    </div>

    <div class="col-sm-12 col-md-6" style=""></div>
</div>

