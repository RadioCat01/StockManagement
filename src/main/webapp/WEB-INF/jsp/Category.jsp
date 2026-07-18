<!DOCTYPE html>
<html class="loading" lang="en" data-textdirection="ltr">
<head>
    <%@include file="../jspf/Headers.jspf"%>
    <script src="${pageContext.request.contextPath}/JS/Controllers/CategoryController.js" type="text/javascript"></script>
    <title>Stock Management</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.1/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <style>
        .form-check{
            margin: 10px;
        }
        .d-flex {
            position: relative;
        }

        .d-flex::after {
            content: '';
            position: absolute;
            top: 0;
            bottom: 0;
            left: 50%;
            width: 1px;
            background-color: #ccc;
            transform: translateX(-50%);
            z-index: 0;
        }

        .section-divider {
            border-top: 1px solid #ddd;
            margin-top: -20px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body ng-app="Stock" ng-controller="CategoryMgt" class="vertical-layout vertical-menu 2-columns   menu-expanded fixed-navbar" data-open="click" data-menu="vertical-menu" data-color="bg-gradient-x-purple-blue" data-col="2-columns" >

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
                    <li class=" nav-item"><a href="${pageContext.request.contextPath}/inventory"><span class="menu-title" data-i18n="">Inventory</span></a>
                    </li>
                    <li><a class="menu-item" href="#">Stock Info</a>
                        <ul class="menu-content">
                            <li class="active"><a class="menu-item" href="${pageContext.request.contextPath}/category"><span class="menu-title" data-i18n="">Categories</span></a>
                            </li>
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/brand">Brands</a>
                            </li>
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/items">Stock</a>
                            </li>
                            <li ><a class="menu-item" href="${pageContext.request.contextPath}/suppliers">Suppliers</a>
                            </li>
                        </ul>
                    </li>
                    <li><a class="menu-item" href="${pageContext.request.contextPath}/customerJobs">Job Management</a>
                    </li>
                    <li><a class="menu-item" href="#">Company Info</a>
                        <ul class="menu-content">
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/company"><span class="menu-title" data-i18n="">Companies</span></a>
                            </li>
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/subcompany">SubCompanies</a>
                            </li>
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/store">Store/Warehouses</a>
                            </li>
                            <li ><a class="menu-item" href="${pageContext.request.contextPath}/storefront">StoreFronts</a>
                            </li>
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/counter">Counters</a>
                            </li>
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/scanner">Scanners</a>
                            </li>
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/posTerminal">POS Terminals</a>
                            </li>
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/drawer">Cash Drawers</a>
                            </li>
                        </ul>
                    </li>
                    <li><a class="menu-item" href="${pageContext.request.contextPath}/formsPage">Forms Info</a>
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
    <div class="navigation-background">   </div>
</div>

<!-- //////////////////////////////////////////////////////////////////////////// -->
<!-- Cont -->
<div class="app-content content">
    <div class="content-wrapper">
        <div class="content-wrapper-before"></div>
        <div class="content-header row">
            <div class="content-header-left col-md-4 col-12 mb-2">
                <h3 class="content-header-title">Stock Management</h3>
            </div>
        </div>
        <div class="content-body">
            <div class="content-body">
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Stock Information</h4>
                            </div>
                            <div class="card-content collapse show">
                                <div class="card-body">
                                    <p class="card-text">Add Categories, SubCategories, And Item Stocks</p>
                                    <table class="table table-bordered table-striped mt-4">
                                        <thead>
                                        <tr>
                                            <th>Category Name</th>
                                            <th ng-repeat="field in categoryHeaders">{{ field }}</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr ng-repeat="entity in categories">
                                            <td>{{ entity.displayName }}</td>
                                            <td ng-repeat="field in categoryHeaders">
                                                {{ entity.customFields[field] || '' }}
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <button class="btn btn-outline-info mb-2" data-toggle="modal" data-target="#addGenericDataModal" ng-click="openForm('category')"><i class="ft-plus"></i>&nbsp; Add Category</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--//////// Generic Modal ////// -->
    <div class="modal fade text-left" id="addGenericDataModal" tabindex="-1" role="dialog" aria-labelledby="addCategoryDataLabel" aria-hidden="true">
        <div class="modal-dialog modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-header btn-bg-gradient-x-purple-blue white">
                    <h4 class="modal-title white" id="addCategoryDataLabel">Add Company</h4>
                    <button type="button" class="close white" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">
                    <form name="companyForm">
                        <div class="form-row" ng-repeat="field in genericFields">

                            <div class="form-group col-md-12">
                                <label>{{field.fieldQuestion}} <span class="text-danger" ng-if="field.mandatory">*</span></label>

                                <!-- Text / Number / Date -->
                                <input ng-if="field.fieldType === 'text' || field.fieldType === 'number' || field.fieldType === 'date'"
                                       type="{{field.fieldType}}"
                                       class="form-control"
                                       ng-model="inputData[field.fieldName]"
                                       ng-required="field.mandatory">

                                <!-- Textarea -->
                                <textarea ng-if="field.fieldType === 'textarea'"
                                          class="form-control"
                                          ng-model="inputData[field.fieldName]"
                                          ng-required="field.mandatory"></textarea>

                                <!-- Checkbox -->
                                <input ng-if="field.fieldType === 'checkbox'"
                                       type="checkbox"
                                       class="form-check-input"
                                       ng-model="inputData[field.fieldName]">

                                <select ng-if="field.fieldType === 'selectCompany'"
                                        class="form-control"
                                        ng-model="inputData[field.fieldName]"
                                        ng-options="company.entityId as company.displayName for company in companies"
                                        ng-change="selectCompany(inputData[field.fieldName])"
                                        ng-required="field.mandatory">
                                    <option value="" disabled selected>Select</option>
                                </select>

                                <select ng-if="field.fieldType === 'selectSubCompany'"
                                        class="form-control"
                                        ng-model="inputData[field.fieldName]"
                                        ng-options="subCompany.entityId as subCompany.displayName for subCompany in selectedSubCompanies"
                                        ng-change="selectSubCompany(inputData[field.fieldName])"
                                        ng-required="field.mandatory">
                                    <option value="" disabled>Select</option>
                                </select>

                                <div ng-if="field.fieldType === 'selectStores'">
                                    <select multiple size="5"
                                            class="form-control"
                                            ng-model="inputData[field.fieldName]"
                                            ng-options="store.entityId as store.displayName for store in selectedStores"
                                            ng-required="field.mandatory">
                                    </select>
                                </div>

                                <select ng-if="field.fieldType === 'selectStore'"
                                        class="form-control"
                                        ng-model="inputData[field.fieldName]"
                                        ng-options="store.entityId as store.displayName for store in selectedStores"
                                        ng-change="selectStore(inputData[field.fieldName])"
                                        ng-required="field.mandatory">
                                    <option value="" disabled>Select</option>
                                </select>

                                <select ng-if="field.fieldType === 'selectStoreFront'"
                                        class="form-control"
                                        ng-model="inputData[field.fieldName]"
                                        ng-options="storeFront.entityId as storeFront.displayName for storeFront in selectedStoreFronts"
                                        ng-change="selectStoreFront(inputData[field.fieldName])"
                                        ng-required="field.mandatory">
                                    <option value="" disabled>Select</option>
                                </select>

                                <select ng-if="field.fieldType === 'selectStoreCounter'"
                                        class="form-control"
                                        ng-model="inputData[field.fieldName]"
                                        ng-options="counter.entityId as counter.displayName for counter in selectedCounters"
                                        ng-required="field.mandatory">
                                    <option value="" disabled>Select</option>
                                </select>

                                <!-- Radio Button (example with Yes/No) -->
                                <div ng-if="field.fieldType === 'radio'">
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" ng-model="inputData[field.fieldName]" value="Yes">
                                        <label class="form-check-label">Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" ng-model="inputData[field.fieldName]" value="No">
                                        <label class="form-check-label">No</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <div class="mt-4">
                        <button class="btn btn-primary" ng-click="saveData(type)">Submit</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--///////// Add Brand Modal ///////////////-->
<%--    <div class="modal fade text-left" id="addBrandModal" tabindex="-1" role="dialog"--%>
<%--         aria-labelledby="addProductModalLabel" aria-hidden="true">--%>
<%--        <div class="modal-dialog modal-lg" role="document">--%>
<%--            <div class="modal-content">--%>

<%--                <div class="modal-header btn-bg-gradient-x-purple-blue white">--%>
<%--                    <h4 class="modal-title white" id="addProductModalLabel">Create Item</h4>--%>
<%--                    <button type="button" class="close white" data-dismiss="modal"--%>
<%--                            aria-label="Close">--%>
<%--                        <span aria-hidden="true">&times;</span>--%>
<%--                    </button>--%>
<%--                </div>--%>

<%--                <div class="modal-body">--%>
<%--                    <form class="form needs-validation" ng-submit="addCategory()">--%>
<%--                        <div class="form-body">--%>
<%--                            <div class="form-group d-flex justify-content-end">--%>
<%--                                <div class="dropdown">--%>
<%--                                    <button class="btn btn-outline-info dropdown-toggle" type="button"--%>
<%--                                            id="customFieldsDropdown" data-toggle="dropdown"--%>
<%--                                            aria-haspopup="true" aria-expanded="false">--%>
<%--                                        Fields--%>
<%--                                    </button>--%>

<%--                                    <!-- Dropdown Menu with Checkboxes -->--%>
<%--                                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="customFieldsDropdown">--%>
<%--                                        <div class="form-check">--%>
<%--                                            <input class="form-check-input" type="checkbox"--%>
<%--                                                   ng-model="itemCustom.stockNumber.enabled"--%>
<%--                                                   ng-change="toggleCustomField('stockNumber')"--%>
<%--                                                   id="stockNumberCheckbox">--%>
<%--                                            <label class="form-check-label" for="stockNumberCheckbox">Stock Number</label>--%>
<%--                                        </div>--%>
<%--                                        <div class="form-check">--%>
<%--                                            <input class="form-check-input" type="checkbox"--%>
<%--                                                   ng-model="itemCustom.additional.enabled"--%>
<%--                                                   ng-change="toggleCustomField('additional')"--%>
<%--                                                   id="additionalCheckbox">--%>
<%--                                            <label class="form-check-label" for="additionalCheckbox">Additional Details</label>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                            <div class="row">--%>
<%--                                <div class="col-md-12">--%>
<%--                                    <div class="form-group">--%>
<%--                                        <label for="timesheetinput1">Category Name</label>--%>
<%--                                        <div class="position-relative has-icon-left d-flex align-items-center">--%>
<%--                                            <div class="input-group">--%>
<%--                                                <input type="text" id="timesheetinput1"--%>
<%--                                                       class="form-control"--%>
<%--                                                       ng-model="categoryName"--%>
<%--                                                       placeholder="Category Name"--%>
<%--                                                       name="categoryName">--%>
<%--                                                <div class="form-control-position">--%>
<%--                                                    <i class="ft-codepen"></i>--%>
<%--                                                </div>--%>
<%--                                            </div>--%>

<%--                                            <button type="submit" class="btn btn-primary ml-2">--%>
<%--                                                <i class="la la-check-square-o"></i> Save--%>
<%--                                            </button>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </form>--%>
<%--                    <form class="form needs-validation" ng-submit="addBrand()" novalidate>--%>
<%--                        <div class="form-body">--%>
<%--                            <!-- Category and Supplier Selection -->--%>
<%--                            <div class="row">--%>
<%--                                <div class="col-md-6">--%>
<%--                                    <div class="form-group">--%>
<%--                                        <label for="categorySelect">Select Category</label>--%>
<%--                                        <select class="select2 form-control" id="categorySelect"--%>
<%--                                                ng-model="brandDTO.categoryId"--%>
<%--                                                ng-options="category.categoryId as category.categoryName for category in categories"--%>
<%--                                                required>--%>
<%--                                        </select>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                                <div class="col-md-6">--%>
<%--                                    <div class="form-group">--%>
<%--                                        <label for="brandName">Brand Name</label>--%>
<%--                                        <div class="position-relative has-icon-left">--%>
<%--                                            <input type="text" id="brandName"--%>
<%--                                                   class="form-control"--%>
<%--                                                   ng-model="brandDTO.brandName"--%>
<%--                                                   placeholder="Enter Brand Name"--%>
<%--                                                   name="brandName" required>--%>
<%--                                            <div class="form-control-position">--%>
<%--                                                <i class="ft-tag"></i>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </div>--%>

<%--                            <div class="row">--%>
<%--                                <div class="col-md-6">--%>
<%--                                    <div class="form-group">--%>
<%--                                        <label for="itemCode">Item Code</label>--%>
<%--                                        <div class="position-relative has-icon-left">--%>
<%--                                            <input type="text" id="itemCode" class="form-control"--%>
<%--                                                   ng-model="brandDTO.itemCode"--%>
<%--                                                   placeholder="Enter Item Code" name="itemCode"--%>
<%--                                                   required>--%>
<%--                                            <div class="form-control-position">--%>
<%--                                                <i class="la la-barcode"></i>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </div>--%>

<%--                            <!-- Description -->--%>
<%--                            <div class="row">--%>
<%--                                <div class="col-md-12">--%>
<%--                                    <div class="form-group">--%>
<%--                                        <label for="descTextarea">Product Description</label>--%>
<%--                                        <textarea class="form-control" id="descTextarea"--%>
<%--                                                  rows="3" ng-model="brandDTO.productDescription"--%>
<%--                                                  placeholder="Enter Product Description"--%>
<%--                                                  required></textarea>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </div>--%>

<%--                            <div class="row">--%>
<%--                                <div class="col-md-6" ng-if="itemCustom.stockNumber.enabled">--%>
<%--                                    <div class="form-group">--%>
<%--                                        <label for="taxId">Stock Number</label>--%>
<%--                                        <div class="position-relative has-icon-left">--%>
<%--                                            <input type="text" id="stockNumber" class="form-control"--%>
<%--                                                   ng-model="itemCustom.stockNumber.value"--%>
<%--                                                   placeholder="Stock Number">--%>
<%--                                            <div class="form-control-position">--%>
<%--                                                <i class="la la-id-card"></i>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
<%--                                </div>--%>

<%--                                <div class="col-md-6" ng-if="itemCustom.additional.enabled">--%>
<%--                                    <div class="form-group">--%>
<%--                                        <label for="Bank Details">Additional Details</label>--%>
<%--                                        <div class="position-relative has-icon-left">--%>
<%--                                            <input type="text" id="additional" class="form-control"--%>
<%--                                                   ng-model="itemCustom.additional.value"--%>
<%--                                                   placeholder="Additional Details">--%>
<%--                                            <div class="form-control-position">--%>
<%--                                                <i class="la la-id-card"></i>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </div>--%>

<%--                            <!-- Form Actions -->--%>
<%--                            <div class="form-actions right">--%>
<%--                                <button type="button" class="btn btn-danger mr-1"--%>
<%--                                        data-dismiss="modal">--%>
<%--                                    <i class="ft-x"></i> Cancel--%>
<%--                                </button>--%>
<%--                                <button type="submit" class="btn btn-primary">--%>
<%--                                    <i class="la la-check-square-o"></i> Save--%>
<%--                                </button>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </form>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>

</div>
<%@include file="../jspf/Footer.jspf" %>
</body>
</html>