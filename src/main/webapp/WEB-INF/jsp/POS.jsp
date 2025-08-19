<!DOCTYPE html>
<html class="loading" lang="en" data-textdirection="ltr">
<head>
    <%@include file="../jspf/Headers.jspf"%>
    <title>Point of Sales</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
    <meta name="description" content="Chameleon Admin is a modern Bootstrap 4 webapp &amp; admin dashboard html template with a large number of components, elegant design, clean and organized code.">
    <meta name="keywords" content="admin template, Chameleon admin template, dashboard template, gradient admin template, responsive admin template, webapp, eCommerce dashboard, analytic dashboard">
    <meta name="author" content="ThemeSelect">
    <title>Compact Menu - Chameleon Admin - Modern Bootstrap 4 WebApp & Dashboard HTML Template + UI Kit</title>
    <link rel="apple-touch-icon" href="../app-assets/images/ico/apple-icon-120.png">
    <link rel="shortcut icon" type="image/x-icon" href="../app-assets/images/ico/favicon.ico">
    <link href="https://fonts.googleapis.com/css?family=Muli:300,300i,400,400i,600,600i,700,700i%7CComfortaa:300,400,700" rel="stylesheet">
    <link href="https://maxcdn.icons8.com/fonts/line-awesome/1.1/css/line-awesome.min.css" rel="stylesheet">
    <!-- BEGIN VENDOR CSS-->
    <link rel="stylesheet" type="text/css" href="../app-assets/css/vendors.css">
    <link rel="stylesheet" type="text/css" href="../app-assets/vendors/css/ui/prism.min.css">
    <!-- END VENDOR CSS-->
    <!-- BEGIN CHAMELEON  CSS-->
    <link rel="stylesheet" type="text/css" href="../app-assets/css/app.css">
    <!-- END CHAMELEON  CSS-->
    <!-- BEGIN Page Level CSS-->
    <link rel="stylesheet" type="text/css" href="../app-assets/css/core/menu/menu-types/vertical-menu.css">
    <link rel="stylesheet" type="text/css" href="../app-assets/css/core/colors/palette-gradient.css">
    <!-- END Page Level CSS-->
    <!-- BEGIN Custom CSS-->
    <link rel="stylesheet" type="text/css" href="../assets/css/style.css">

    <script src="${pageContext.request.contextPath}/JS/Controllers/POSController.js" type="text/javascript"></script>

    <style>
        .products-container {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 10px 20px;
            min-height: 200px;
            max-height: 600px;
            overflow-y: auto;
            padding: 8px;
        }
        .addService{
            border: 2px dashed #5e5bca;
        }

        .product-item {
            cursor: pointer;
            border-radius: 6px;
            padding: 6px;
            height: auto;
            width: auto;
            background-color: #fff;
        }

        .product-title {
            font-size: 12px;
            font-weight: 600;
            margin-bottom: 2px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .product-code {
            font-size: 10px;
            color: #888;
            margin-bottom: 2px;
        }

        .product-brand {
            font-size: 11px;
            color: #555;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
            height: 14px;
        }

        .quantity-badge {
            font-size: 10px;
        }
        .form-check{
            margin: 10px;
        }
    </style>
</head>
<body ng-controller="POSController" class="vertical-layout vertical-menu" data-open="click" data-menu="vertical-menu" data-color="bg-gradient-x-purple-blue" data-col="2-columns" ng-app="Stock">
<!-- Nav -->
<div class="main-menu menu-fixed menu-light menu-accordion menu-shadow" data-scroll-to-active="true" data-img="../../../app-assets/images/backgrounds/02.jpg">
    <div class="navbar-header">
    </div>
    <div class="main-menu-content">
        <ul class="navigation navigation-main" id="main-menu-navigation" data-menu="menu-navigation">
            <li  class="active"><a href="${pageContext.request.contextPath}/pos"><i class="la la-cart-arrow-down">&nbsp;</i><span class="menu-title" data-i18n="">Point of Sales</span></a>
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
                            <li><a class="menu-item" href="${pageContext.request.contextPath}/category"><span class="menu-title" data-i18n="">Categories</span></a>
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

<!-- /////////////////////////////////////////////////////////////// -->
<!-- Cont-->
<div class="app-content content">
    <div class="content-wrapper">
        <div class="content-wrapper-before"></div>
        <div class="content-header row">
            <div class="content-header-left col-md-4 col-12 mb-2">
                <h3 class="content-header-title">Sales Terminal</h3>
            </div>
        </div>
        <div id="switchable-sections">


                <section class="row">

                </section>

            <!-- Items Section -->
                <section class="row">

                    <div class="col-md-3 col-sm-12">
                        <div id="with-header" class="card">
                            <div class="card-header">
                                <h4 class="card-title">Customer Information</h4>
                            </div>
                            <div class="card-content collapse show">
                                <div class="form-group d-flex justify-content-end mr-lg-1">
                                    <div class="dropdown">
                                        <button class="btn btn-outline-info dropdown-toggle" type="button"
                                                id="customFieldsDropdown" data-toggle="dropdown"
                                                aria-haspopup="true" aria-expanded="false">
                                            Fields
                                        </button>

                                        <!-- Dropdown Menu with Checkboxes -->
                                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="customFieldsDropdown">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox"
                                                       ng-model="posCustom.poNumber.enabled"
                                                       ng-change="toggleCustomField('poNumber')"
                                                       id="poNumberCheckbox">
                                                <label class="form-check-label" for="poNumberCheckbox">PO Number</label>
                                            </div>
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox"
                                                       ng-model="posCustom.additionalDetails.enabled"
                                                       ng-change="toggleCustomField('additionalDetails')"
                                                       id="additionalDetailsCheckbox">
                                                <label class="form-check-label" for="additionalDetailsCheckbox">Additional Details</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <form class="form needs-validation" novalidate>
                                    <div class="col-md-12">
                                        <div class="mb-3">
                                            <label for="customerSearch">Search Customer</label>
                                            <input type="text"
                                                   class="form-control"
                                                   id="customerSearch"
                                                   placeholder="Search by name or phone"
                                                   ng-model="customerSearchQuery"
                                                   ng-change="updateFilteredCustomers(customerSearchQuery)"
                                                   ng-model-options="{ debounce: 300 }"
                                                   autocomplete="off">
                                        </div>
                                        <!-- Suggested Customers Dropdown -->
                                        <div class="mb-3" ng-if="customerSearchQuery && filteredCustomersList.length > 0 && !soldDTO.customerName">
                                            <ul class="list-group">
                                                <li class="list-group-item list-group-item-action"
                                                    ng-repeat="cust in filteredCustomersList track by cust.customerId"
                                                    ng-click="selectCustomer(cust)">
                                                    <strong>{{ cust.name }}</strong><br/>
                                                    <small>{{ cust.phone }}</small>
                                                </li>
                                            </ul>
                                        </div>
                                        <div class="mb-3 text-muted"
                                             ng-if="customerSearchQuery && filteredCustomersList.length === 0">
                                            No matching customers found.
                                        </div>

                                        <!-- New Customer Form if none selected -->
                                        <div ng-if="!selectedCustomer">
                                            <hr>
                                            <h5>New Customer</h5>

                                            <div class="form-row">
                                                <div class="form-group col-md-6">
                                                    <label for="customerName">Name <span class="text-danger">*</span></label>
                                                    <input type="text"
                                                           class="form-control"
                                                           id="customerName"
                                                           placeholder="Enter Name"
                                                           ng-model="soldDTO.customerName"
                                                           required>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="customerPhone">Phone <span class="text-danger">*</span></label>
                                                    <input type="text"
                                                           class="form-control"
                                                           id="customerPhone"
                                                           placeholder="Enter Phone Number"
                                                           ng-model="soldDTO.customerPhone"
                                                           required>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label for="customerAddress">Address</label>
                                                <textarea class="form-control"
                                                          id="customerAddress"
                                                          placeholder="Enter Address"
                                                          rows="2"
                                                          ng-model="soldDTO.customerAddress">
                                                </textarea>
                                            </div>
                                        </div>
                                        <!-- PO Details Section -->
                                        <hr>
                                        <div class="form-row">
                                            <div class="form-group col-md-6">
                                              <div class="form-group">
                                                    <label for="poReference">PO Reference</label>
                                                    <input type="text"
                                                           class="form-control"
                                                         id="poReference"
                                                           placeholder="Enter PO Reference"
                                                           ng-model="soldDTO.poReference">
                                              </div>
                                            </div>
                                            <div class="form-group col-md-6" ng-if="posCustom.poNumber.enabled">
                                                <div class="form-group">
                                                    <label for="poNumber">PO Number</label>
                                                    <input type="text"
                                                           class="form-control"
                                                           id="poNumber"
                                                           placeholder="Enter PO Number"
                                                           ng-model="posCustom.poNumber.value">
                                                </div>
                                            </div>
                                        </div>


                                        <!-- Payment Method -->
                                        <div class="form-group">
                                            <label for="paymentTerms">Payment Method</label>
                                            <select class="form-control mb-3" id="paymentTerms"
                                                    ng-model="soldDTO.paymentTerms"
                                                    ng-options="option for option in paymentOptions">
                                            </select>
                                            <div class="form-group" ng-if="posCustom.additionalDetails.enabled">
                                                <div class="form-group">
                                                    <label for="additionalDetails">Additional Details</label>
                                                    <input type="text"
                                                           class="form-control"
                                                           id="additionalDetails"
                                                           placeholder="Additional Details"
                                                           ng-model="posCustom.additionalDetails.value">
                                                </div>
                                            </div>
                                            <hr>
                                            <!-- Show Selected Customer -->
                                            <div ng-if="soldDTO.customerName">
                                                <button class="btn btn-sm btn-secondary" ng-click="clearCustomer()">Clear</button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>


                    <!-- /////////// Product Section ////////// -->
                    <div class="col-md-6 col-sm-12">
                        <div id="with-header-border-0" class="card">
                            <div class="card-header">
                                <h4 class="card-title">Product Details</h4>
                            </div>
                            <div class="card-content collapse show">
                                <div class="card-body">

                                    <div class="row mb-2">
                                        <div class="col-md-8 col-sm-7">
                                            <div class="input-group">
                                                <input
                                                        type="text"
                                                        class="form-control"
                                                        placeholder="Search products..."
                                                        ng-model="searchQuery"
                                                />
                                                <div class="input-group-append">
                                                    <button class="btn btn-primary" type="button" ng-click="search()">
                                                        <i class="ft-search"></i> Search
                                                    </button>
                                                    <button
                                                            class="btn btn-secondary ml-2"
                                                            ng-click="resetSearch()"
                                                    >
                                                        Clear
                                                    </button>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- Barcode Button + Retail Checkbox -->
                                        <div class="col-md-4 col-sm-5 d-flex flex-row flex-wrap align-items-center justify-content-between">

                                            <!-- Barcode Scan Button -->
                                            <button
                                                    class="btn btn-primary mr-2 mb-2"
                                                    ng-class="barcodeReady ? 'btn-primary' : 'btn-outline-secondary'"
                                                    ng-click="seBarcodeReady()"
                                                    data-action="br"
                                            >
                                                <i class="ft-maximize"></i>
                                            </button>

                                            <!-- Retail Price Checkbox -->
                                            <div class="form-check mb-2">
                                                <input
                                                        type="checkbox"
                                                        id="isRetail"
                                                        class="form-check-input"
                                                        ng-model="soldDTO.retail"
                                                        ng-change="toggleSerialNumberSection(soldDTO.retail)"
                                                />
                                                <label class="form-check-label" for="isRetail">
                                                    Retail Price
                                                </label>
                                            </div>

                                        </div>
                                    </div>

                                    <!-- Products Display -->
                                    <div class="products-container">
                                        <!-- Add Service Button -->
                                        <div class="product-item d-flex flex-column justify-content-center align-items-center addService"
                                             data-toggle="modal"
                                             data-target="#serviceModal">
                                            <i class="ft-plus-circle text-primary" style="font-size: 18px;"></i>
                                            <h6 class="mt-1 mb-0 text-center" style="font-size: 11px; font-weight: 600;">Add Service Charge</h6>
                                        </div>

                                        <!-- Product Items -->
                                        <div ng-repeat="product in (isSearchActive ? filteredProducts : productDTOs)"
                                             class="product-item shadow-sm rounded position-relative"
                                             ng-click="selectProduct(product)">

                                            <!-- Item Code and Price Inline -->
                                            <div class="d-flex justify-content-between align-items-center">
                                                <h5 class="product-title m-0">{{ product.itemCode }}</h5>

                                                <!-- Price in Top Right (now inline with item code) -->
                                                <div>
                                                    <p class="price m-0 small fw-bold">
                                                        {{ soldDTO.retail ? product.retailPrice : product.dealerPrice | currency }}
                                                    </p>
                                                </div>
                                            </div>

                                            <!-- Product Content -->
                                            <h6 class="product-code mb-1">{{ product.description }}</h6>
                                            <p class="product-brand mb-1">{{ product.brandName }}</p>

                                            <div class="d-flex justify-content-between align-items-center mt-1">
                                                <span class="badge bg-light rounded-pill quantity-badge">Qty: {{ product.quantity }}</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- //////////// Checkout Section /////////// -->
                    <div class="col-md-3 col-sm-12">
                        <div id="with-header" class="card">
                            <div class="card-header">
                                <h4 class="card-title">Order Details</h4>
                            </div>
                            <div class="card-content collapse show">

                                <div class="card-body pr-1 pl-1 pb-0">
                                    <div class="selected-products-container "
                                         style="border-bottom: 1px solid rgba(44, 71, 98, 0.4); min-height: 320px; max-height: 320px; overflow-y: auto;">
                                        <div id="selected-products">
                                            <div class="selected-product-item pr-2 pl-2 pb-1"
                                                 ng-repeat="item in getAllItems() track by $index">
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <!-- Left side: Description + Unit price -->
                                                    <div>
                                                        <strong>{{item.description}}</strong>
                                                        <div ng-if="item.itemType === 'product'">
                                                            <small class="text-muted">
                                                                {{ soldDTO.retail ?
                                                                item.retailPrice.toFixed(2) :
                                                                item.dealerPrice.toFixed(2) }}
                                                            </small>
                                                            <strong> x {{item.selectedQuantity}}</strong>
                                                        </div>
                                                        <div ng-if="item.itemType === 'service'">
                                                            <small class="text-muted">Service charge</small>
                                                        </div>
                                                    </div>

                                                    <!-- Right side: Total price + Delete button -->
                                                    <div class="text-right">
                                                        <strong>
                                                        <span ng-if="item.itemType === 'product'">
                                                            {{soldDTO.retail
                                                            ? (item.retailPrice * item.selectedQuantity).toFixed(2)
                                                            : (item.dealerPrice * item.selectedQuantity).toFixed(2)}}
                                                        </span>

                                                        <span ng-if="item.itemType === 'service'">
                                                                {{item.chargeAmount.toFixed(2)}}
                                                        </span>
                                                        </strong>
                                                        <button class="btn btn-sm btn-outline-danger ml-2"
                                                                ng-click="removeItem(item, $index)">
                                                            <i class="ft-trash-2"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Order Summary -->
                                    <div class="order-summary p-2 bg-lighter ">
                                        <div class="d-flex justify-content-between mb-1">
                                            <span class="font-weight-bold">Subtotal:</span>
                                            <span id="subtotal">{{subtotal.toFixed(2)}} Rs</span>
                                        </div>
                                        <div class="d-flex justify-content-between mb-1">
                                            <span class="font-weight-bold">Vat ({{tax}}%):</span>
                                            <span id="tax">{{calculatedTax}} Rs</span>
                                        </div>
                                        <div class="d-flex justify-content-between">
                                            <span class="font-weight-bold">Total:</span>
                                            <span id="total" class="font-weight-bold">{{total}} Rs</span>
                                        </div>
                                    </div>

                                    <!-- Calculator Panel -->
                                    <div class="calculator-pad">
                                        <!-- First Row -->
                                        <div class="row mb-1 gx-1"> <!-- Reduced gutter -->
                                            <div class="col-3 px-1">
                                                <button class="btn btn-light w-100 calc-btn" data-value="7" ng-click="handleNumberInput(7)">7</button>
                                            </div>
                                            <div class="col-3 px-1">
                                                <button class="btn btn-light w-100 calc-btn" data-value="8" ng-click="handleNumberInput(8)">8</button>
                                            </div>
                                            <div class="col-3 px-1">
                                                <button class="btn btn-light w-100 calc-btn" data-value="9" ng-click="handleNumberInput(9)">9</button>
                                            </div>
                                            <div class="col-3 px-1">
                                                <button class="btn w-100 calc-btn"
                                                        ng-class="inputType === 'qty' ? 'btn-primary' : 'btn-outline-secondary'"
                                                        ng-click="setInputType('qty')" data-action="qty">Qty</button>
                                            </div>
                                        </div>

                                        <!-- Second Row -->
                                        <div class="row mb-1 gx-1">
                                            <div class="col-3 px-1">
                                                <button class="btn btn-light w-100 calc-btn" data-value="4" ng-click="handleNumberInput(4)">4</button>
                                            </div>
                                            <div class="col-3 px-1">
                                                <button class="btn btn-light w-100 calc-btn" data-value="5" ng-click="handleNumberInput(5)">5</button>
                                            </div>
                                            <div class="col-3 px-1">
                                                <button class="btn btn-light w-100 calc-btn" data-value="6" ng-click="handleNumberInput(6)">6</button>
                                            </div>
                                            <div class="col-3 px-1">
                                                <button class="btn w-100 calc-btn"
                                                        ng-class="inputType === 'percentage' ? 'btn-primary' : 'btn-outline-secondary'"
                                                        ng-click="setInputType('percentage')" data-action="percentage">%</button>
                                            </div>
                                        </div>

                                        <!-- Third Row -->
                                        <div class="row mb-1 gx-1">
                                            <div class="col-3 px-1">
                                                <button class="btn btn-light w-100 calc-btn" data-value="1" ng-click="handleNumberInput(1)">1</button>
                                            </div>
                                            <div class="col-3 px-1">
                                                <button class="btn btn-light w-100 calc-btn" data-value="2" ng-click="handleNumberInput(2)">2</button>
                                            </div>
                                            <div class="col-3 px-1">
                                                <button class="btn btn-light w-100 calc-btn" data-value="3" ng-click="handleNumberInput(3)">3</button>
                                            </div>
                                            <div class="col-3 px-1">
                                                <button class="btn btn-outline-secondary w-100 calc-btn" data-action="delete">?</button>
                                            </div>
                                        </div>

                                        <!-- Fourth Row -->
                                        <div class="row mb-1 gx-1">
                                            <div class="col-3 px-1">
                                                <button class="btn btn-light w-100 calc-btn" data-value=".">.</button>
                                            </div>
                                            <div class="col-3 px-1">
                                                <button class="btn btn-light w-100 calc-btn" data-value="0">0</button>
                                            </div>
                                            <div class="col-3 px-1">
                                                <button class="btn btn-outline-secondary w-100 calc-btn" data-action="plusminus">+/-</button>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Action Buttons -->
                                    <div class="action-buttons pl-2 pr-2 pb-2">
                                        <div class="row">
                                            <div class="col-6">
                                                <button class="btn btn-light w-100" ng-click="#">Hold</button>
                                            </div>
                                            <div class="col-6">
                                                <button class="btn btn-primary w-100"
                                                        ng-click="createSale()"
                                                        ng-disabled="selectedProducts.length === 0">
                                                    Checkout
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div>

        <div class="modal fade text-left" id="serviceModal" tabindex="-1" role="dialog"
             aria-labelledby="addProductModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header btn-bg-gradient-x-purple-blue white">
                        <h4 class="modal-title white" id="addProductModalLabel">Service Charge</h4>
                        <button type="button" class="close white" data-dismiss="modal"
                                aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form name="serviceChargeForm" novalidate>
                            <div class="form-group">
                                <label for="serviceType">Service Type</label>
                                <!-- HTML5 datalist for autocomplete suggestions -->
                                <input type="text" class="form-control" id="serviceType"
                                       ng-model="serviceCharge.serviceChargeType"
                                       list="serviceTypeOptions"
                                       placeholder="Select or type new service type"
                                       required>
                                <datalist id="serviceTypeOptions">
                                    <option ng-repeat="type in serviceChargeType" value="{{type}}">
                                </datalist>
                            </div>

                            <div class="form-group">
                                <label for="chargeAmount">Charge Amount</label>
                                <input type="number" class="form-control" id="chargeAmount"
                                       ng-model="serviceCharge.chargeAmount" min="0" step="0.01"/>
                            </div>

                            <button type="button" class="btn btn-primary"
                                    ng-click="addServiceCharge()"
                                    ng-disabled="!serviceCharge.serviceChargeType || serviceCharge.chargeAmount <= 0">
                                Add Service Charge
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
</div>




<%@include file="../jspf/Footer.jspf" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/app-assets/js/scripts/ui/compact-menu.js"></script>

</body>
</html>