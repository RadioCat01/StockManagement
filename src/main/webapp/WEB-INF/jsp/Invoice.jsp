<!DOCTYPE html>
<html class="loading" lang="en" data-textdirection="ltr">
<head>
    <%@include file="../jspf/Headers.jspf"%>
    <script src="${pageContext.request.contextPath}/JS/Controllers/InvoiceController.js" type="text/javascript"></script>
    <title>Invoices Management</title>
    <style>
        .btn-xs {
            padding: 0.2rem 0.4rem;
            font-size: 0.75rem;
            line-height: 1;
            border-radius: 0.2rem;
        }

        .action-icons {
            font-size: 0.85rem; /* Optional: make the icon size smaller as well */
        }
    </style>

</head>
<body ng-app="Stock" ng-controller="InvoiceController" class="vertical-layout vertical-menu 2-columns   menu-expanded fixed-navbar" data-open="click" data-menu="vertical-menu" data-color="bg-gradient-x-purple-blue" data-col="2-columns">

<nav class="header-navbar navbar-expand-md navbar navbar-with-menu navbar-without-dd-arrow fixed-top navbar-dark">
    <div class="navbar-wrapper">
        <div class="navbar-header">
            <ul class="nav navbar-nav flex-row">
                <li class="nav-item mobile-menu d-md-none mr-auto"><a class="nav-link nav-menu-main menu-toggle hidden-xs" href="#"><i class="ft-menu font-large-1"></i></a></li>
                <li class="nav-item"><a class="navbar-brand" href="index.html"><img class="brand-logo" alt="Chameleon admin logo" src="${pageContext.request.contextPath}/resources/images/USCOM.png">
                    <h3 class="brand-text">Chameleon</h3></a></li>
                <li class="nav-item d-md-none"><a class="nav-link open-navbar-container" data-toggle="collapse" data-target="#navbar-mobile"><i class="la la-ellipsis-v"></i></a></li>
            </ul>
        </div>
        <div class="navbar-container content">
            <div class="collapse navbar-collapse show" id="navbar-mobile">
                <ul class="nav navbar-nav mr-auto float-left">
                    <li class="nav-item d-none d-md-block"><a class="nav-link nav-menu-main menu-toggle hidden-xs" href="#"><i class="ft-menu"></i></a></li>
                </ul>
                <ul class="nav navbar-nav float-right">
                    <li class="dropdown dropdown-user nav-item"><a class="dropdown-toggle nav-link dropdown-user-link" href="#" data-toggle="dropdown">             <span class="ft ft-user"></span></a>

                    </li>
                </ul>
            </div>
        </div>
    </div>
</nav>
<!-- ////////////////////////////////////////////////////////////////////////////-->
<!-- Nav -->
<div class="main-menu menu-fixed menu-light menu-accordion menu-shadow" data-scroll-to-active="true" data-img="${pageContext.request.contextPath}/app-assets/images/backgrounds/02.jpg">
    <div class="navbar-header">
        <ul class="nav navbar-nav flex-row">
            <li class="nav-item mr-auto"><a class="navbar-brand" href="index.html">
                <h3 class="brand-text">Stock Management</h3></a></li>
            <li class="nav-item d-md-none"><a class="nav-link close-navbar"><i class="ft-x"></i></a></li>
        </ul>
    </div>
    <div class="main-menu-content">
        <ul class="navigation navigation-main" id="main-menu-navigation" data-menu="menu-navigation">
            <li class=" nav-item"><a href="${pageContext.request.contextPath}/pos"><i class="la la-cart-arrow-down"></i><span class="menu-title" data-i18n="">Point of Sales</span></a>
            <li class=" nav-item"><a href="#"><i class="la la-bank"></i><span class="menu-title" data-i18n="">Finance</span></a>
                <ul class="menu-content">
                    <li class="active" ><a class="menu-item" href="${pageContext.request.contextPath}/invoice"><span class="menu-title" data-i18n="">Invoices</span></a>
                    </li>
                </ul>
            </li>
            <li class=" nav-item"><a href="#"><i class="ft-briefcase"></i><span class="menu-title" data-i18n="">Management</span></a>
                <ul class="menu-content">
                    <li class=" menu-item"><a href="${pageContext.request.contextPath}/inventory"><span class="menu-title" data-i18n="">Inventory</span></a>
                    </li>
                    <li ><a class="menu-item" href="${pageContext.request.contextPath}/addCategory">Stock Management</a>
                    </li>
                </ul>
            </li>
            <li class=" nav-item"><a href="#"><i class="ft-printer"></i><span class="menu-title" data-i18n="">Reporting</span></a>
                <ul class="menu-content">
                    <li><a class="menu-item" href="${pageContext.request.contextPath}/grnSummery">GRN Summery</a>
                    </li>
                    <li ><a class="menu-item" href="${pageContext.request.contextPath}/sales"><span class="menu-title" data-i18n="">Sales Summery</span></a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
    <div class="navigation-background"></div>
</div>


<div class="app-content content">
    <div class="content-wrapper">
        <div class="content-wrapper-before"></div>
        <div class="content-header row">
            <div class="content-header-left col-md-4 col-12 mb-2">
                <h3 class="content-header-title">Invoices List</h3>
            </div>
            <div class="content-header-right col-md-8 col-12">
                <div class="breadcrumbs-top float-md-right">
                    <div class="breadcrumb-wrapper mr-1">

                    </div>
                </div>
            </div>
        </div>
        <div class="content-body"><section class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-head">
                        <div class="card-header">
                            <h4 class="card-title">Invoices</h4>
                            <a class="heading-elements-toggle"><i class="la la-ellipsis-h font-medium-3"></i></a>
                        </div>
                    </div>
                    <div class="card-content">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table id="inventoryTable" class="table table-white-space table-bordered row-grouping display no-wrap icheck table-middle">
                                    <thead>

                                    <tr>
                                        <th>Invoice #</th>
                                        <th>Order #</th>
                                        <th>Date</th>
                                        <th>Customer Info</th>
                                        <th>Sale type</th>
                                        <th>Status</th>
                                        <th>Total Invoice</th>
                                        <th>Actions</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <th>Invoice #</th>
                                        <th>Order #</th>
                                        <th>Date</th>
                                        <th>Customer Info</th>
                                        <th>Sale type</th>
                                        <th>Status</th>
                                        <th>Total Invoice</th>
                                    </tr>
                                    </tfoot>
                                </table>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </section>
        </div>
    </div>
</div>
<!-- ////////////////////////////////////////////////////////////////////////////-->


<script src="${pageContext.request.contextPath}/app-assets/vendors/js/vendors.min.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/app-assets/vendors/js/tables/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/app-assets/vendors/js/tables/datatable/dataTables.bootstrap4.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/app-assets/vendors/js/forms/icheck/icheck.min.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/app-assets/js/core/app-menu.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/app-assets/js/core/app.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/app-assets/js/scripts/pages/invoices-list.js" type="text/javascript"></script>

</body>
</html>