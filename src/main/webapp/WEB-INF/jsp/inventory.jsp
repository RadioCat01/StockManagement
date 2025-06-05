<!DOCTYPE html>
<html class="loading" lang="en" data-textdirection="ltr" ng-app="Stock">
<head>
    <%@include file="../jspf/Headers.jspf"%>
    <title>Stock Management</title>
    <script src="${pageContext.request.contextPath}/JS/Controllers/InventoryController.js" type="text/javascript"></script>
</head>
<body ng-controller="InventoryCont" class="vertical-layout vertical-menu 2-columns   menu-expanded fixed-navbar" data-open="click" data-menu="vertical-menu" data-color="bg-gradient-x-purple-blue" data-col="2-columns">

<!-- fixed-top-->
<nav class="header-navbar navbar-expand-md navbar navbar-with-menu navbar-without-dd-arrow fixed-top navbar-dark">
    <div class="navbar-wrapper">
        <div class="navbar-header">
            <ul class="nav navbar-nav flex-row">
                <li class="nav-item mobile-menu d-md-none mr-auto"><a class="nav-link nav-menu-main menu-toggle hidden-xs" href="#"><i class="ft-menu font-large-1"></i></a></li>
                <li class="nav-item"><a class="navbar-brand" href="index.html"><img class="brand-logo" alt="Chameleon admin logo" src="${pageContext.request.contextPath}/resources/images/USCOM.png">
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
                            <div class="arrow_box_right"><a class="dropdown-item" href="#"><span class="avatar avatar-online"><img src="${pageContext.request.contextPath}/app-assets/images/portrait/small/avatar-s-19.png" alt="avatar"><span class="user-name text-bold-700 ml-1">John Doe</span></span></a>
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
                    <li  class="active"><a class="menu-item" href="${pageContext.request.contextPath}/inventory"><span class="menu-title" data-i18n="">Inventory</span></a>
                    </li>
                    <li><a class="menu-item" href="${pageContext.request.contextPath}/addCategory">Stock Management</a>
                    </li>
                    <li><a class="menu-item" href="${pageContext.request.contextPath}/customerJobs">Job Management</a>
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
                <h3 class="content-header-title">Inventory Information</h3>
            </div>
        </div>
        <div class="content-body">
            <div class="row">
                <div class="col-12">
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Inventory Details</h4>
                            <a class="heading-elements-toggle"><i class="la la-ellipsis-v font-medium-3"></i></a>
                        </div>
                        <div class="d-flex px-3 align-items-center">
                            <div class="col-md-2 p-0">
                                <label for="storeId">Select Store:</label>
                                <select ng-model="selectedStore"
                                        ng-options="store.storeId as store.storeName for store in stores"
                                        class="form-control mb-2"
                                        ng-change="onStoreSelect()"
                                        id="storeId"
                                        required>
                                    <option value="" disabled>Select a store</option>
                                </select>
                                <div class="invalid-feedback">Please select a store.</div>
                            </div>

                            <div class="flex-grow-1"></div>
                            <div>
                                <button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#stockTransferModel">
                                    <i class="ft-corner-up-right"></i>
                                    Item Transfer</button>
                            </div>
                        </div>
                        <div class="card-content collapse show">
                            <div class="card-body card-dashboard">
                                <table id="inventoryTable" class="table table-striped table-bordered">
                                    <thead>
                                    <tr>
                                        <th>Info</th>
                                        <th>Item Code</th>
                                        <th>Description</th>
                                        <th>Product Serial</th>
                                        <th>GRN Date</th>
                                        <th>Last Update</th>
                                        <th>Stock Type</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <th>Info</th>
                                        <th>Item Code</th>
                                        <th>Description</th>
                                        <th>Product Serial</th>
                                        <th>GRN Date</th>
                                        <th>Last Update</th>
                                        <th>Stock Type</th>
                                    </tr>
                                    </tfoot>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-12">
                    <div class="card">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <h4 class="card-title mb-0">Stock Transfer</h4>
                            <button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#bulkTransferModel">
                                <i class="ft-corner-up-right"></i> Transfer Bulk
                            </button>
                        </div>
                        <div class="card-body card-dashboard">
                            <table id="transfersTable" class="table table-striped table-bordered">
                                <thead>
                                <tr>
                                    <th>Transfer Number</th>
                                    <th>Date</th>
                                    <th>Info</th>
                                    <th>From</th>
                                    <th>To</th>
                                    <th>Reason</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <th>Transfer Number</th>
                                    <th>Date</th>
                                    <th>Info</th>
                                    <th>From</th>
                                    <th>To</th>
                                    <th>Reason</th>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-12">
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Item History</h4>
                            <a class="heading-elements-toggle"><i class="la la-ellipsis-v font-medium-3"></i></a>
                        </div>
                        <div class="card-body card-dashboard">
                            <div class="row mb-2">
                                <input type="text" class="form-control col-md-2 ml-2 mr-1" ng-model="searchSerialNumber" placeholder="Enter Serial Number" />
                                <button class="btn btn-outline-primary" ng-click="getItemHistory(searchSerialNumber)">Search</button>
                            </div>
                            <table id="historyTable" class="table table-striped table-bordered">
                                <thead>
                                <tr>
                                    <th>Brand</th>
                                    <th>Item Code</th>
                                    <th>Serial Number</th>
                                    <th>Log</th>
                                    <th>Date</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <th>Brand</th>
                                    <th>Item Code</th>
                                    <th>Serial Number</th>
                                    <th>Log</th>
                                    <th>Date</th>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade text-left" id="stockTransferModel" tabindex="-1" role="dialog"
             aria-labelledby="addProductModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header btn-bg-gradient-x-purple-blue white">
                        <h4 class="modal-title white" id="addProductModalLabel">Confirm Stock Transfer</h4>
                        <button type="button" class="close white" data-dismiss="modal"
                                aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="col-md-6 p-0">
                            <label for="storeId">Transfer To:</label>
                            <select ng-model="toTransfer.transferTo"
                                    ng-options="store.storeId as store.storeName for store in stores"
                                    class="form-control mb-2"
                                    id="storeId"
                                    required>
                                <option value="" disabled>Select a store</option>
                            </select>
                            <div class="invalid-feedback">Please select a store.</div>
                        </div>
                        <div style="max-height: 400px; overflow-y: auto;">
                            <table id="transfersTable" class="table table-striped table-bordered">
                                <thead>
                                <tr>
                                    <th>Info</th>
                                    <th>ItemCode</th>
                                    <th>Serial</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr ng-repeat="item in selectedItems">
                                    <td>{{ item.category }}<br>{{ item.brand }}</td>
                                    <td>{{ item.itemCode }}</td>
                                    <td>{{ item.productSerial }}</td>
                                </tr>
                                </tbody>
                            </table>
                            <div class="flex-grow-1"></div>
                            <div>
                                <button type="button" class="btn btn-outline-blue-grey" ng-click="clearSelected()">
                                    <i class="ft-x"></i></button>
                            </div>
                        </div>
                        <div class="col-md-12 p-0 mb-1">
                            <label for="transferReason">Reason for Transfer:</label>
                            <textarea id="transferReason"
                                      ng-model="toTransfer.reason"
                                      class="form-control"
                                      rows="3"
                                      placeholder="Enter reason for stock transfer"
                                      required></textarea>
                            <div class="invalid-feedback">Please enter a reason.</div>
                        </div>
                        <div class="text-right">
                            <button class="btn btn-primary" ng-click="transfer()">Transfer</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- ////// Bulk Transfer Model /////////-->
        <div class="modal fade text-left" id="bulkTransferModel" tabindex="-1" role="dialog"
             aria-labelledby="addProductModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header btn-bg-gradient-x-purple-blue white">
                        <h4 class="modal-title white" id="addProductModalLabel">Transfer Stock Batch</h4>
                        <button type="button" class="close white" data-dismiss="modal"
                                aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="row mb-2">
                            <div class="col-md-6">
                                <label for="storeId">Transfer From:</label>
                                <select ng-model="fromSelectedStore"
                                        ng-change="selectBulkTransferTo()"
                                        ng-options="store.storeId as store.storeName for store in stores"
                                        class="form-control mb-2"
                                        id="storeId"
                                        required>
                                    <option value="" disabled>Select a store</option>
                                </select>
                                <div class="invalid-feedback">Please select a store.</div>
                            </div>
                            <div class="col-md-6">
                                <label for="storeId">Transfer To:</label>
                                <select ng-model="bulkTransferDTO.storeId"
                                        ng-options="store.storeId as store.storeName for store in stores"
                                        class="form-control mb-2"
                                        id="storeId"
                                        required>
                                    <option value="" disabled>Select a store</option>
                                </select>
                                <div class="invalid-feedback">Please select a store.</div>
                            </div>
                        </div>

                        <div class="row mb-2">
                            <div class="col-md-6">
                                <label for="categorySelect">Select Category:</label>
                                <select id="categorySelect" class="form-control"
                                        ng-model="selectedCategory"
                                        ng-change="selectCategory(selectedCategory)"
                                        ng-options="category.categoryName for category in categories"
                                        required>
                                    <option value="" disabled>Select a category</option>
                                </select>
                                <div class="invalid-feedback">Please select a category.</div>
                            </div>
                            <div class="col-md-6">
                                <label for="brandSelect">Select Brand:</label>
                                <select id="brandSelect" class="form-control"
                                        ng-model="selectedBrand"
                                        ng-change="selectBrand(selectedBrand)"
                                        ng-options="brand.brandName for brand in selectedCategory.brands"
                                        required>
                                    <option value="" disabled>Select a brand</option>
                                </select>
                                <div class="invalid-feedback">Please select a brand.</div>
                            </div>
                        </div>

                        <div class="row mb-2">
                            <div class="col-md-6">
                                <label for="itemCodeSelect">Select Item Code:</label>
                                <select id="itemCodeSelect" class="form-control"
                                        ng-model="selectedItemInfo"
                                        ng-change="selectItemInfo(selectedItemInfo)"
                                        ng-options="itemInfo as itemInfo.itemCode for itemInfo in selectedBrand.itemInfos"
                                        required>
                                    <option value="" disabled>Select an item code</option>
                                </select>
                                <div class="invalid-feedback">Please select an item code.</div>
                            </div>
                            <div class="col-md-6">
                                <label for="quantity">Quantity Available: {{itemQuantity}}</label>
                                <input type="number" id="quantity" class="form-control"
                                       ng-model="bulkTransferDTO.itemQuantity"
                                       placeholder="Select Quantity to Transfer"/>
                                <div class="invalid-feedback">Please enter item quantity.</div>
                            </div>
                        </div>

                        <div class="col-md-12 p-0 mb-1">
                            <label for="transferReason">Reason for Transfer:</label>
                            <textarea id="transferReason"
                                      ng-model="bulkTransferDTO.reason"
                                      class="form-control"
                                      rows="3"
                                      placeholder="Enter reason for stock transfer"
                                      required></textarea>
                            <div class="invalid-feedback">Please enter a reason.</div>
                        </div>
                        <div class="text-right">
                            <button class="btn btn-primary" ng-click="transferBulk()">Transfer</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
        <%@include file="../jspf/Footer.jspf" %>

</body>
</html>