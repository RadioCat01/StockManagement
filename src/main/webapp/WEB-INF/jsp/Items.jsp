<!DOCTYPE html>
<html class="loading" lang="en" data-textdirection="ltr" ng-app="Stock">
<head>
  <%@include file="../jspf/Headers.jspf"%>
  <title>Stock Management</title>
  <script src="${pageContext.request.contextPath}/JS/Controllers/ItemsManagement.js" type="text/javascript"></script>
</head>
<body ng-controller="ItemsManagement" class="vertical-layout vertical-menu 2-columns   menu-expanded fixed-navbar" data-open="click" data-menu="vertical-menu" data-color="bg-gradient-x-purple-blue" data-col="2-columns">

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
          <li><a class="menu-item" href="#">Stock Info</a>
            <ul class="menu-content">
              <li><a class="menu-item" href="${pageContext.request.contextPath}/category"><span class="menu-title" data-i18n="">Categories</span></a>
              </li>
              <li><a class="menu-item" href="${pageContext.request.contextPath}/brand">Brands</a>
              </li>
              <li class="active"><a class="menu-item" href="${pageContext.request.contextPath}/items">Stock</a>
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
          <li class="active"><a class="menu-item" href="#"><span class="menu-title" data-i18n="">Sales Summery</span></a>
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
        <h3 class="content-header-title">Sales Information</h3>
      </div>
    </div>

    <!-- Stock Section -->
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
                  <table id="itemsTable" class="table table-striped table-bordered zero-configuration">
                    <thead>
                    <tr>
                      <th>Category Name</th>
                      <th>Brand Name</th>
                      <th>Item Code</th>
                      <th>Product Description</th>
                      <th>Stock</th>
                      <th>GRN Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                  </table>
                  <button class="btn btn-outline-info mb-2" data-toggle="modal" data-target="#addItemsModal"><i class="ft-plus"></i>&nbsp; Add Stock</button>
                </div>
              </div>

              <!-- /////////// Add Items Model /////////////// -->
              <div class="modal fade text-left" id="addItemsModal" tabindex="-1" role="dialog"
                   aria-labelledby="addProductModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-xl" role="document">
                  <div class="modal-content">
                    <div class="modal-header btn-bg-gradient-x-purple-blue white">
                      <h4 class="modal-title white" id="addProductModalLabel">Add Stock</h4>
                      <button type="button" class="close white" data-dismiss="modal"
                              aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                      </button>
                    </div>

                    <div class="modal-body">
                      <form class="form needs-validation" novalidate ng-submit="addStock()">
                        <div class="form-body">
                          <div class="form-group d-flex justify-content-end">
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
                                         ng-model="stockCustom.additional.enabled"
                                         ng-change="toggleCustomField('additional')"
                                         id="additionalCheckbox">
                                  <label class="form-check-label" for="additionalCheckbox">Supplier Info</label>
                                </div>
                                <div class="form-check">
                                  <input class="form-check-input" type="checkbox"
                                         ng-model="stockCustom.grnDate.enabled"
                                         ng-change="toggleCustomField('grnDate')"
                                         id="grnDateCheckbox">
                                  <label class="form-check-label" for="grnDateCheckbox">GRN Date</label>
                                </div>
                              </div>
                            </div>
                          </div>

                          <!-- Container for two columns with vertical divider -->
                          <div class="d-flex" style="position: relative; gap: 2rem;">
                            <!-- Left Column: Product Info + Item Details + Pricing -->
                            <div class="col-md-6 flex-fill">

                              <h6 class="mb-2 text-bold-600">Store Information</h6>
                              <hr class="section-divider">
                              <div class="row mb-2">
                                <div class="col-md-12">
                                  <label for="storeId">Select Store:</label>
                                  <select ng-model="itemDTO.store"
                                          ng-options="store.storeId as store.storeName for store in stores"
                                          class="form-control mb-2"
                                          id="storeId"
                                          required>
                                    <option value="" disabled>Select a store</option>
                                  </select>
                                  <div class="invalid-feedback">Please select a store.</div>
                                </div>
                              </div>

                              <h6 class="mb-2 text-bold-600">Supplier Information</h6>
                              <hr class="section-divider">
                              <div class="row mb-2">
                                <div class="col-md-6">
                                  <label for="supplierSelect">Select Supplier:</label>
                                  <select id="supplierSelect" class="form-control"
                                          ng-model="selectedSupplier"
                                          ng-change="selectItemSupplier(selectedSupplier)"
                                          ng-options="supplier.name for supplier in suppliers"
                                          required>
                                    <option value="" disabled>Select a supplier</option>
                                  </select>
                                  <div class="invalid-feedback">Please select a supplier.</div>
                                </div>
                                <div class="col-md-6">
                                  <label for="SuppInvSelect">Supplier Invoice:</label>
                                  <input type="text" id="SuppInvSelect" class="form-control"
                                         ng-model="itemDTO.supplierInvoiceNumber"
                                         placeholder="Supplier invoice Number"/>
                                  <div class="invalid-feedback">Please enter supplier invoice Number</div>
                                </div>
                              </div>
                              <div class="row mb-2">
                                <div class="col-md-6" ng-if="stockCustom.grnDate.enabled">
                                  <label>GRN Date</label>
                                  <div class='input-group'>
                                    <input type='date' ng-model="itemDTO.grnDate" class="form-control" />
                                  </div>
                                </div>
                                <div class="col-md-6">
                                  <div ng-if="stockCustom.additional.enabled">
                                    <label for="additionalInfo">Additional Supplier Info:</label>
                                    <input type="text" id="additionalInfo" class="form-control"
                                           ng-model="stockCustom.additional.value"
                                           placeholder="Additional Info"/>
                                    <div class="invalid-feedback">Please enter additional Information</div>
                                  </div>
                                </div>
                              </div>

                              <h6 class="mb-2 text-bold-600 mt-3">Product Information</h6>
                              <hr class="section-divider">
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
                                  <div class="form-group">
                                    <label for="stockType">Stock Type</label>
                                    <div class="position-relative has-icon-left">
                                      <select id="stockType" class="form-control"
                                              ng-model="itemDTO.stockType"
                                              ng-init="itemDTO.stockType='Goods'">
                                        <option value="Goods">Goods</option>
                                        <option value="Defective">Defective</option>
                                        <option value="Spare Parts">Spare Parts</option>
                                      </select>
                                      <div class="form-control-position">
                                        <i class="la la-archive"></i>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>

                              <h6 class="mb-2 text-bold-600 mt-3">Item Details</h6>
                              <hr class="section-divider">
                              <div class="row mb-2">
                                <div class="col-md-6">
                                  <label for="warranty">Supplier Warranty:</label>
                                  <input type="text" id="warranty" class="form-control"
                                         ng-model="itemDTO.warranty"
                                         placeholder="Warranty Period"/>
                                  <div class="invalid-feedback">Please enter warranty period.</div>
                                </div>
                                <div class="col-md-6">
                                  <label for="warranty">Seller Warranty:</label>
                                  <input type="text" id="warranty" class="form-control"
                                         ng-model="itemDTO.sellerWarranty"
                                         placeholder="Warranty Period"/>
                                  <div class="invalid-feedback">Please enter warranty period.</div>
                                </div>
                              </div>

                              <div class="row mb-2">
                                <div class="col-md-6">
                                  <label for="quantity">Quantity:</label>
                                  <input type="number" id="quantity" class="form-control"
                                         ng-model="itemDTO.quantity"
                                         placeholder="Stock Quantity"/>
                                  <div class="invalid-feedback">Please enter item quantity.</div>
                                </div>
                                <div class="col-md-6">
                                  <label for="cost">Item Cost:</label>
                                  <input type="number" id="cost" class="form-control"
                                         ng-model="itemDTO.itemCost"
                                         placeholder="Item Cost"/>
                                  <div class="invalid-feedback">Please enter item cost.</div>
                                </div>
                              </div>


                            </div>

                            <!-- Right Column: Payment Info + Serial Numbers -->
                            <div class="col-md-6 flex-fill">
                              <h6 class="mb-2 text-bold-600">Pricing Information</h6>
                              <hr class="section-divider">
                              <div class="row mb-2">
                                <div class="col-md-6">
                                  <label for="dealerPrice">Dealer Price:</label>
                                  <input type="number" id="dealerPrice" class="form-control"
                                         ng-model="itemDTO.dealerPrice"
                                         placeholder="Dealer Price"/>
                                  <div class="invalid-feedback">Please enter dealer price.</div>
                                </div>
                                <div class="col-md-6">
                                  <label for="retailPrice">Retail Price:</label>
                                  <input type="number" id="retailPrice" class="form-control"
                                         ng-model="itemDTO.retailPrice"
                                         placeholder="Retail Price"/>
                                  <div class="invalid-feedback">Please enter retail price.</div>
                                </div>
                              </div>

                              <h6 class="mb-2 text-bold-600">Payment Information</h6>
                              <hr class="section-divider">

                              <div class="row mb-2">
                                <div class="col-md-12">
                                  <label for="supplierPayment">Supplier Payment:</label>
                                  <input type="text" id="supplierPayment" class="form-control"
                                         ng-model="itemDTO.supplierPayment"
                                         placeholder="Supplier Payment"/>
                                  <div class="invalid-feedback">Please enter supplier payment.</div>
                                </div>
                              </div>

                              <div class="row mb-2">
                                <div class="col-md-12">
                                  <label for="paymentStatus">Payment Status:</label>
                                  <select id="paymentStatus" class="form-control"
                                          ng-model="itemDTO.paymentStatus"
                                          required>
                                    <option value="">Select Payment Status</option>
                                    <option value="Pending">Pending</option>
                                    <option value="Done">Done</option>
                                  </select>
                                  <div class="invalid-feedback">Please select a payment status.</div>
                                </div>
                              </div>

                              <div class="row mb-2">
                                <div class="col-md-12">
                                  <div class="form-check">
                                    <input type="checkbox" id="hasSerialNumbers"
                                           class="form-check-input"
                                           ng-model="itemDTO.hasSerialNumbers"
                                           ng-change="toggleSerialNumberSection(itemDTO.hasSerialNumbers)">
                                    <label class="form-check-label" for="hasSerialNumbers">
                                      Item Serial Numbers
                                    </label>
                                  </div>
                                </div>
                              </div>

                              <div ng-if="itemDTO.hasSerialNumbers">
                                <h6 class="mb-2 text-bold-600 mt-3">Serial Numbers</h6>
                                <hr class="section-divider">

                                <div class="row mb-2">
                                  <div class="col-md-12">
                                    <label for="serialNumber">Enter Serial Number:</label>
                                    <input type="text" id="serialNumber" class="form-control"
                                           ng-model="formData.serialNumber"
                                           placeholder="Enter serial number"/>
                                  </div>
                                </div>

                                <div class="row mb-2">
                                  <div class="col-md-12">
                                    <button type="button" class="btn btn-success btn-block"
                                            ng-click="addItems()" ng-disabled="!selectedBrand">
                                      <i class="fas fa-plus-circle"></i> Add Serial
                                    </button>
                                  </div>
                                </div>

                                <div class="row mb-2">
                                  <div class="col-md-12">
                                    <label>Added Serials:</label>
                                    <div style="display: flex; flex-wrap: wrap; gap: 8px;">
                                      <span ng-repeat="item in itemDTO.serialNumbers track by $index"
                                            style="padding: 5px 10px; border: 1px solid #ccc; border-radius: 4px; background-color: #f9f9f9; font-size: 14px; white-space: nowrap;">
                                        {{item}}
                                        <button type="button" style="margin-left: 5px; background: none; border: none; cursor: pointer;"
                                                ng-click="removeItem(item)">&times;</button>
                                      </span>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>

                          </div>

                          <!-- Action Buttons -->
                          <div class="row mt-3">
                            <div class="col-md-12 d-flex justify-content-end">
                              <button type="button" class="btn btn-danger mr-2" data-dismiss="modal">
                                <i class="fas fa-times"></i> Cancel
                              </button>
                              <button type="submit" class="btn btn-primary"
                                      ng-disabled="!selectedBrand || (itemDTO.hasSerialNumbers && itemDTO.serialNumbers.length === 0)">
                                <i class="fas fa-save"></i> Add Stock
                              </button>
                            </div>
                          </div>
                        </div>
                      </form>
                    </div>

                  </div>
                </div>
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