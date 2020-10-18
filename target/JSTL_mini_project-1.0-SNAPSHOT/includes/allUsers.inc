<div>
    <c:if test ="${requestScope.errorMessage != null}">
        <div class="alert alert-danger" style="padding-bottom: inherit;">
            <p>${requestScope.errorMessage}</p>
        </div>
    </c:if>
</div>
<div>
    <c:if test ="${requestScope.successMessage != null}">
        <div class="alert alert-success" style="padding-bottom: inherit;">
            <p>${requestScope.successMessage}</p>
        </div>
    </c:if>
</div>

<form action="FrontController" method="post">

    <input type="hidden" name="target" value="bulkHandler">

    <div class="row">
        <!--    Padding-left skal ændres i admin CSS files så den flugter med den anden tabel "sb-admin.css" -->
        <div id="bulkOptionContainer" class="col-sx-6 col-sm-6 col-md-3">
            <select class="form-control" name="selector_option" id="">
                <option value="">Select option</option>
                <option value="customer">Convert to Customer</option>
                <option value="admin">Convert to Admin</option>
                <option value="delete">Delete Users</option>
            </select>
            <p></p>
        </div>
        <!--    Padding-left skal ændres i admin CSS files så den flugter med den anden tabel "sb-admin.css" -->
        <div id="applyChangesPost" class="col-sx-6 col-sm-6 col-md-2">
            <input type="submit" name="submit_form" class="btn btn-success" value="Apply">
        </div>
    </div>

    <table class="table table-striped">
        <thead>
        <tr>
            <th><input id="selectAllBoxes" type="checkbox"></th>
            <th scope="col">Id</th>
            <th scope="col">Username</th>
            <th scope="col">Email</th>
            <th scope="col">Role</th>
            <th scope="col">Edit</th>
            <th scope="col">Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${requestScope.allUsersFromDB}">
            <tr>
                <td><input class='checkBoxes' type='checkbox' name='bulkArrayUserId' value='${user.id}'></td>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${user.userRole}</td>
                <td><a type="button" class="btn btn-primary btn-sm" href="FrontController?target=redirect&destination=editUser&user_id=${user.id}">Edit</a></td>
                <td><a type="button" class="btn btn-danger btn-sm" href="FrontController?target=deleteUser&user_id=${user.id}">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</form>