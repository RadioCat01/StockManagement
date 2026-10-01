<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="../jspf/Headers.jspf"%>
    <title>User Administration</title>
</head>
<body class="container py-4">
<main>
    <h1>User Administration</h1>
    <p>Companies, account roles, and store-front assignments are enforced by the server.</p>

    <nav id="company-operations" class="card mb-4" aria-label="Company operations" hidden>
        <div class="card-header"><h2>Company operations</h2></div>
        <div class="card-body d-flex flex-wrap" style="gap: .5rem">
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/inventory">Inventory</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/items">Items and stock</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/suppliers">Suppliers</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/grnSummery">Goods received</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/customerJobs">Customer jobs</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/pos">Point of sale</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/sales">Sales</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/invoice">Invoices</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/company">Company setup</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/store">Stores</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/storefront">Store fronts</a>
        </div>
    </nav>

    <section id="company-create-section" class="card mb-4" hidden>
        <div class="card-header"><h2>Create company and company administrator</h2></div>
        <div class="card-body">
            <form id="company-create-form" class="row">
                <label class="col-md-3" for="new-company-name">Company name</label>
                <input class="form-control col-md-8 mb-2" id="new-company-name" required maxlength="150">
                <label class="col-md-3" for="new-admin-username">Administrator username</label>
                <input class="form-control col-md-8 mb-2" id="new-admin-username" required maxlength="80">
                <label class="col-md-3" for="new-admin-password">Temporary password</label>
                <input class="form-control col-md-8 mb-2" id="new-admin-password" type="password"
                       autocomplete="new-password" minlength="12" maxlength="72" required>
                <button class="btn btn-primary mt-2" type="submit">Create company</button>
            </form>
        </div>
    </section>

    <section class="card mb-4">
        <div class="card-header"><h2>Create employee account</h2></div>
        <div class="card-body">
            <form id="user-create-form" class="row">
                <div id="user-company-row" class="row w-100" hidden>
                    <label class="col-md-3" for="user-company">Company</label>
                    <select class="form-control col-md-8 mb-2" id="user-company"></select>
                </div>
                <label class="col-md-3" for="user-username">Username</label>
                <input class="form-control col-md-8 mb-2" id="user-username" required maxlength="80">
                <label class="col-md-3" for="user-password">Temporary password</label>
                <input class="form-control col-md-8 mb-2" id="user-password" type="password"
                       autocomplete="new-password" minlength="12" maxlength="72" required>
                <label class="col-md-3" for="user-role">Role</label>
                <select class="form-control col-md-8 mb-2" id="user-role" required></select>
                <label class="col-md-3" for="user-storefront">Assigned store front</label>
                <select class="form-control col-md-8 mb-2" id="user-storefront"></select>
                <label class="col-md-3" for="user-phone">Phone (optional)</label>
                <input class="form-control col-md-8 mb-2" id="user-phone" maxlength="40">
                <label class="col-md-3" for="user-address">Address (optional)</label>
                <input class="form-control col-md-8 mb-2" id="user-address" maxlength="250">
                <button class="btn btn-primary mt-2" type="submit">Create user</button>
            </form>
        </div>
    </section>

    <section class="card">
        <div class="card-header"><h2>Company users</h2></div>
        <div class="card-body table-responsive">
            <table class="table table-striped">
                <thead><tr><th>Username</th><th>Company</th><th>Role</th><th>Status</th><th></th></tr></thead>
                <tbody id="users-list"></tbody>
            </table>
        </div>
    </section>
    <p id="admin-message" role="status" aria-live="polite"></p>
</main>

<script src="${pageContext.request.contextPath}/JS/Controllers/UserAdministration.js"></script>
<%@include file="../jspf/Footer.jspf"%>
</body>
</html>
