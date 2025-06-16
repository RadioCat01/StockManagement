<!DOCTYPE html>
<html class="loading" lang="en" data-textdirection="ltr" ng-app="Stock">
<head>
    <%@include file="../jspf/Headers.jspf"%>
    <title>Stock Management</title>
    <script src="${pageContext.request.contextPath}/JS/Controllers/FormsController.js" type="text/javascript"></script>
</head>
<body ng-controller="FormsCont" class="vertical-layout vertical-menu 2-columns   menu-expanded fixed-navbar" data-open="click" data-menu="vertical-menu" data-color="bg-gradient-x-purple-blue" data-col="2-columns">

<!-- fixed-top-->
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
                        <div class="dropdown-menu dropdown-menu-right">
                            <div class="arrow_box_right"><a class="dropdown-item" href="#"><span class="avatar avatar-online"><img src="../../../app-assets/images/portrait/small/avatar-s-19.png" alt="avatar"><span class="user-name text-bold-700 ml-1">John Doe</span></span></a>
                                <div class="dropdown-divider"></div><a class="dropdown-item" href="user-profile.html"><i class="ft-user"></i> Edit Profile</a><a class="dropdown-item" href="email-application.html"><i class="ft-mail"></i> My Inbox</a><a class="dropdown-item" href="project-summary.html"><i class="ft-check-square"></i> Task</a><a class="dropdown-item" href="chat-application.html"><i class="ft-message-square"></i> Chats</a>
                                <div class="dropdown-divider"></div><a class="dropdown-item" href="login.html"><i class="ft-power"></i> Logout</a>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</nav>
<!-- ////////////////////////////////////////////////////////////////////////////-->
<!-- Nav -->
<div class="main-menu menu-fixed menu-light menu-accordion menu-shadow" data-scroll-to-active="true" data-img="../../../app-assets/images/backgrounds/02.jpg">
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
                    <li ><a class="menu-item" href="${pageContext.request.contextPath}/invoice"><span class="menu-title" data-i18n="">Invoices</span></a>
                    </li>
                </ul>
            </li>
            <li class=" nav-item"><a href="#"><i class="ft-briefcase"></i><span class="menu-title" data-i18n="">Management</span></a>
                <ul class="menu-content">
                    <li ><a class="menu-item" href="${pageContext.request.contextPath}/inventory"><span class="menu-title" data-i18n="">Inventory</span></a>
                    </li>
                    <li><a class="menu-item" href="${pageContext.request.contextPath}/addCategory">Stock Management</a>
                    </li>
                    <li><a class="menu-item" href="${pageContext.request.contextPath}/customerJobs">Job Management</a>
                    </li>
                    <li><a class="menu-item" href="${pageContext.request.contextPath}/company">Company Info</a>
                    </li>
                    <li class="active"><a class="menu-item" href="${pageContext.request.contextPath}/formsPage">Forms Info</a>
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

<!-- //////////////////////////////////////////////////////////////////////////// -->
<!-- Cont -->
<div class="app-content content">
    <div class="content-wrapper">
        <div class="content-wrapper-before"></div>
        <div class="content-header row">
            <div class="content-header-left col-md-4 col-12 mb-2">
                <h3 class="content-header-title">Forms Information</h3>
            </div>
        </div>
        <div class="content-body">
            <div class="row">
                <div class="col-12">
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Forms Management</h4>
                            <a class="heading-elements-toggle"><i class="la la-ellipsis-v font-medium-3"></i></a>
                        </div>
                        <div class="card-content collapse show">
                            <div class="card-body card-dashboard">
                                <table class="table table-striped table-bordered">
                                    <thead>
                                    <tr>
                                        <th>Form</th>
                                        <th>Actions</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>Company</td>
                                        <td><button class="btn btn-outline-info mb-2" data-toggle="modal" data-target="#formsModel"><i class="ft-plus"></i> </button></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- /////////// Company Model /////////// -->
    <div class="modal fade text-left" id="formsModel" tabindex="-1" role="dialog" aria-labelledby="formsModelLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header btn-bg-gradient-x-purple-blue white">
                    <h4 class="modal-title white" id="formsModelLabel">Manage Company Form</h4>
                    <button type="button" class="close white" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <!-- Existing Fields Table -->
                    <table class="table table-bordered table-striped mt-4">
                        <thead>
                        <tr>
                            <th>Field Name</th>
                            <th>Field Type</th>
                            <th>Is Mandatory</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="field in companyFields">
                            <td>{{field.fieldName}}</td>
                            <td>{{field.fieldType}}</td>
                            <td>{{field.mandatory ? 'Yes' : 'No'}}</td>
                        </tr>
                        </tbody>
                    </table>

                    <div class="mb-3 p-3">
                        <h5>Add New Field</h5>
                        <div class="form-row">
                            <div class="form-group col-md-4">
                                <label>Field Name</label>
                                <input type="text" class="form-control" ng-model="newField.fieldName" placeholder="Enter field name">
                            </div>
                            <div class="form-group col-md-4">
                                <label>Field Question</label>
                                <input type="text" class="form-control" ng-model="newField.fieldQuestion" placeholder="Enter field name">
                            </div>
                            <div class="form-group col-md-4">
                                <label>Field Type</label>
                                <select class="form-control" ng-model="newField.fieldType">
                                    <option value="text">Text</option>
                                    <option value="textarea">Textarea</option>
                                    <option value="number">Number</option>
                                    <option value="checkbox">Checkbox</option>
                                    <option value="radio">Radio Button</option>
                                    <option value="date">Date</option>
                                </select>
                            </div>
                            <div class="form-group col-md-2 d-flex align-items-center">
                                <label class="mr-2 mb-0">Mandatory</label>
                                <input type="checkbox" ng-model="newField.mandatory">
                            </div>
                            <div class="form-group col-md-2 d-flex align-items-end">
                                <button class="btn btn-primary" ng-click="addCompanyField()">Add</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>




</div>
<%@include file="../jspf/Footer.jspf" %>

</body>
</html>