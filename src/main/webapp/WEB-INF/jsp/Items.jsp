<!DOCTYPE html>
<html class="loading" lang="en" data-textdirection="ltr" ng-app="Stock">
<head>
    <%@include file="../jspf/Headers.jspf"%>
    <script src="${pageContext.request.contextPath}/JS/Controllers/ItemsController.js" type="text/javascript"></script>
    <title>Stock Management</title>
</head>
<body ng-controller="ItemsMgt" class="vertical-layout vertical-menu 2-columns menu-expanded fixed-navbar" data-open="click" data-menu="vertical-menu" data-color="bg-gradient-x-purple-blue" data-col="2-columns">

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
                    <li class="dropdown dropdown-user nav-item"><a class="dropdown-toggle nav-link dropdown-user-link" href="#" data-toggle="dropdown"><span class="ft ft-user"></span></a>
                        <div class="dropdown-menu dropdown-menu-right">
                            <div class="arrow_box_right"><a class="dropdown-item" href="#"><span class="avatar avatar-online"><img src="../../../app-assets/images/portrait/small/avatar-s-19.png" alt="avatar"><span class="user-name text-bold-700 ml-1">John Doe</span></span></a>
                                <div class="dropdown-divider"></div><a class="dropdown-item" href="login.html"><i class="ft-power"></i> Logout</a>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</nav>

<!-- Nav -->
<div class="main-menu menu-fixed menu-light menu-accordion menu-shadow" data-scroll-to-active="true">
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
                    <li><a class="menu-item" href="${pageContext.request.contextPath}/invoice"><span class="menu-title" data-i18n="">Invoices</span></a></li>
                </ul>
            </li>
            <li class=" nav-item"><a href="#"><i class="ft-briefcase"></i><span class="menu-title" data-i18n="">Management</span></a>
                <ul class="menu-content">
                    <li><a class="menu-item" href="${pageContext.request.contextPath}/inventory"><span class="menu-title" data-i18n="">Inventory</span></a></li>
                    <li><a class="menu-item" href="#">Stock Info</a>
                        <ul class="menu-content">
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/category"><span class="menu-title" data-i18n="">Categories</span></a></li>
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/brand">Brands</a></li>
                            <li class="active"><a class="menu-item" href="${pageContext.request.contextPath}/items">Stock</a></li>
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/suppliers">Suppliers</a></li>
                        </ul>
                    </li>
                    <li><a class="menu-item" href="${pageContext.request.contextPath}/customerJobs">Job Management</a></li>
                    <li><a class="menu-item" href="#">Company Info</a>
                        <ul class="menu-content">
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/company"><span class="menu-title" data-i18n="">Companies</span></a></li>
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/subcompany">SubCompanies</a></li>
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/store">Store/Warehouses</a></li>
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/storefront">StoreFronts</a></li>
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/counter">Counters</a></li>
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/scanner">Scanners</a></li>
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/posTerminal">POS Terminals</a></li>
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/drawer">Cash Drawers</a></li>
                        </ul>
                    </li>
                    <li><a class="menu-item" href="${pageContext.request.contextPath}/formsPage">Forms Info</a></li>
                </ul>
            </li>
            <li class=" nav-item"><a href="#"><i class="ft-printer"></i><span class="menu-title" data-i18n="">Reporting</span></a>
                <ul class="menu-content">
                    <li><a class="menu-item" href="${pageContext.request.contextPath}/grnSummery">GRN Summery</a></li>
                    <li><a class="menu-item" href="${pageContext.request.contextPath}/sales"><span class="menu-title" data-i18n="">Sales Summery</span></a></li>
                </ul>
            </li>
        </ul>
    </div>
    <div class="navigation-background"></div>
</div>

<!-- Content -->
<div class="app-content content">
    <div class="content-wrapper">
        <div class="content-wrapper-before"></div>
        <div class="content-header row">
            <div class="content-header-left col-md-4 col-12 mb-2">
                <h3 class="content-header-title">Stock Management</h3>
            </div>
        </div>
        <div class="content-body">
            <div class="row">
                <div class="col-12">
                    <div class="card">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <h4 class="card-title mb-0">Stock Items</h4>
                            <button class="btn btn-outline-info" data-toggle="modal" data-target="#addStockModal">
                                <i class="ft-plus"></i>&nbsp; Add Stock
                            </button>
                        </div>
                        <div class="card-content collapse show">
                            <div class="card-body card-dashboard">
                                <p class="card-text">View and manage stock items by category and brand.</p>
                                <table id="itemsTable" class="table table-striped table-bordered">
                                    <thead>
                                    <tr>
                                        <th>Category</th>
                                        <th>Brand</th>
                                        <th>Item Code</th>
                                        <th>Description</th>
                                        <th>Quantity</th>
                                        <th>GRN Date</th>
                                    </tr>
                                    </thead>
                                    <tbody></tbody>
                                    <tfoot>
                                    <tr>
                                        <th>Category</th>
                                        <th>Brand</th>
                                        <th>Item Code</th>
                                        <th>Description</th>
                                        <th>Quantity</th>
                                        <th>GRN Date</th>
                                    </tr>
                                    </tfoot>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Add Stock Modal -->
    <div class="modal fade text-left" id="addStockModal" tabindex="-1" role="dialog" aria-labelledby="addStockModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header btn-bg-gradient-x-purple-blue white">
                    <h4 class="modal-title white" id="addStockModalLabel">Add Stock (GRN)</h4>
                    <button type="button" class="close white" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form name="stockForm">
                        <div class="row">
                            <!-- Category Select -->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Category <span class="text-danger">*</span></label>
                                    <select class="form-control"
                                            ng-model="selectedCategory"
                                            ng-options="category.categoryName for category in categories"
                                            ng-change="selectCategory(selectedCategory)"
                                            required>
                                        <option value="" disabled>Select Category</option>
                                    </select>
                                </div>
                            </div>
                            <!-- Brand Select -->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Brand <span class="text-danger">*</span></label>
                                    <select class="form-control"
                                            ng-model="selectedBrand"
                                            ng-options="brand.brandName for brand in selectedCategory.brands"
                                            ng-change="selectBrand(selectedBrand)"
                                            required>
                                        <option value="" disabled>Select Brand</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <!-- Item Info Select -->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Item Code <span class="text-danger">*</span></label>
                                    <select class="form-control"
                                            ng-model="selectedItemInfo"
                                            ng-options="item as item.itemCode for item in selectedBrand.itemInfos"
                                            ng-change="selectItemInfo(selectedItemInfo)"
                                            required>
                                        <option value="" disabled>Select Item</option>
                                    </select>
                                </div>
                            </div>
                            <!-- Supplier Select -->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Supplier <span class="text-danger">*</span></label>
                                    <select class="form-control"
                                            ng-model="selectedSupplier"
                                            ng-options="s as s.name for s in suppliers"
                                            ng-change="selectItemSupplier(selectedSupplier)"
                                            required>
                                        <option value="" disabled>Select Supplier</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Store <span class="text-danger">*</span></label>
                                    <select class="form-control" ng-model="itemDTO.store"
                                            ng-options="store.storeId as store.storeName for store in stores" required>
                                        <option value="" disabled>Select Store</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Quantity <span class="text-danger">*</span></label>
                                    <input type="number" class="form-control" ng-model="itemDTO.quantity" min="1" required>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Cost Price</label>
                                    <input type="number" class="form-control" ng-model="itemDTO.itemCost" step="0.01">
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Dealer Price</label>
                                    <input type="number" class="form-control" ng-model="itemDTO.dealerPrice" step="0.01">
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Retail Price</label>
                                    <input type="number" class="form-control" ng-model="itemDTO.retailPrice" step="0.01">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Warranty</label>
                                    <input type="text" class="form-control" ng-model="itemDTO.warranty" placeholder="e.g. 1 Year">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Seller Warranty</label>
                                    <input type="text" class="form-control" ng-model="itemDTO.sellerWarranty" placeholder="e.g. 6 Months">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Supplier Invoice Number</label>
                                    <input type="text" class="form-control" ng-model="itemDTO.supplierInvoiceNumber">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>GRN Date</label>
                                    <input type="date" class="form-control" ng-model="itemDTO.grnDate">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Payment Status</label>
                                    <select class="form-control" ng-model="itemDTO.paymentStatus">
                                        <option value="">Select</option>
                                        <option value="PAID">PAID</option>
                                        <option value="UNPAID">UNPAID</option>
                                        <option value="PARTIAL">PARTIAL</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Supplier Payment</label>
                                    <input type="text" class="form-control" ng-model="itemDTO.supplierPayment">
                                </div>
                            </div>
                        </div>
                        <!-- Serial Numbers -->
                        <div class="form-group">
                            <label>Has Serial Numbers?</label>
                            <div class="form-check form-check-inline ml-2">
                                <input class="form-check-input" type="radio" ng-model="itemDTO.hasSerialNumbers" ng-value="true"> <label class="form-check-label">Yes</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" ng-model="itemDTO.hasSerialNumbers" ng-value="false"> <label class="form-check-label">No</label>
                            </div>
                        </div>
                        <div ng-if="itemDTO.hasSerialNumbers">
                            <div class="input-group mb-2">
                                <input type="text" class="form-control" ng-model="formData.serialNumber" placeholder="Enter Serial Number">
                                <div class="input-group-append">
                                    <button class="btn btn-outline-secondary" type="button" ng-click="addItems()">Add</button>
                                </div>
                            </div>
                            <ul class="list-group mb-2">
                                <li class="list-group-item d-flex justify-content-between align-items-center" ng-repeat="sn in itemDTO.serialNumbers">
                                    {{ sn }}
                                    <button class="btn btn-sm btn-outline-danger" ng-click="removeItem(sn)"><i class="ft-x"></i></button>
                                </li>
                            </ul>
                        </div>
                    </form>
                    <div class="mt-4">
                        <button class="btn btn-primary" ng-click="addStock()">Submit</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../jspf/Footer.jspf" %>
</body>
</html>