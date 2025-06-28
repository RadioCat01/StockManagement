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
              <li><a class="menu-item" href="${pageContext.request.contextPath}/items">Stock</a>
              </li>
              <li class="active"><a class="menu-item" href="${pageContext.request.contextPath}/suppliers">Suppliers</a>
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
    <!-- Supplier Section -->
    <div class="content-body">
      <div class="row">
        <div class="col-12">
          <div class="card">
            <div class="card-header">
              <h4 class="card-title">Supplier Management</h4>
              <a class="heading-elements-toggle"><i class="la la-ellipsis-v font-medium-3"></i></a>
              <div class="heading-elements">
                <ul class="list-inline mb-0">
                  <li><a data-action="collapse"><i class="ft-minus"></i></a></li>
                  <li><a data-action="reload"><i class="ft-rotate-cw"></i></a></li>
                  <li><a data-action="expand"><i class="ft-maximize"></i></a></li>
                  <li><a data-action="close"><i class="ft-x"></i></a></li>
                </ul>
              </div>
            </div>
            <div class="card-content collapse show">
              <div class="card-body">
                <p class="card-text">This section contains the details about suppliers relating to their company. Inspect the product details add or remove products.</p>
                <button class="btn btn-outline-info mb-2" data-toggle="modal" data-target="#addSupplier"><i class="ft-plus"></i>&nbsp; Add Supplier</button>
                <table id="supplierTable" class="table table-striped table-bordered scroll-horizontal " >
                  <thead>
                  <tr>
                    <th>Supplier Name</th>
                    <th>Address</th>
                    <th>Contact Name</th>
                    <th>Contact Number</th>
                    <th>Payment Terms</th>
                    <th>Period</th>
                    <th>Vat Number</th>
                    <th>Bank Details</th>
                  </tr>
                  </thead>
                  <tbody>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>


      <!-- Supplier Model -->
      <div class="modal fade text-left" id="addSupplier" tabindex="-1" role="dialog" aria-labelledby="addSupplierModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
          <div class="modal-content">

            <div class="modal-header btn-bg-gradient-x-purple-blue white">
              <h4 class="modal-title white" id="addProductModalLabel">Add Supplier</h4>
              <button type="button" class="close white" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>


            <div class="modal-body">
              <form class="form needs-validation" ng-submit="saveSupplier()" novalidate>
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
                                 ng-model="supplierCustom.taxId.enabled"
                                 ng-change="toggleCustomField('taxId')"
                                 id="taxIdCheckbox">
                          <label class="form-check-label" for="taxIdCheckbox">Vat Number</label>
                        </div>
                        <div class="form-check">
                          <input class="form-check-input" type="checkbox"
                                 ng-model="supplierCustom.bankDetails.enabled"
                                 ng-change="toggleCustomField('bankDetails')"
                                 id="BankDetailsCheckbox">
                          <label class="form-check-label" for="bankDetailsCheckbox">Bank Details</label>
                        </div>
                      </div>
                    </div>
                  </div>

                  <!-- Supplier Name -->
                  <div class="form-group">
                    <label for="supplierName">Supplier Name</label>
                    <div class="position-relative has-icon-left">
                      <input type="text" id="supplierName" class="form-control" ng-model="supplier.name" placeholder="Enter Supplier Name" name="supplierName" required>
                      <div class="form-control-position">
                        <i class="ft-user"></i>
                      </div>
                    </div>
                  </div>

                  <!-- Contact Information -->
                  <div class="row">
                    <div class="col-md-6">
                      <div class="form-group">
                        <label for="contactNumber">Contact Number</label>
                        <div class="position-relative has-icon-left">
                          <input type="text" id="contactNumber" class="form-control" ng-model="supplier.contactNumber" placeholder="Enter Contact Number" name="contactNumber" required>
                          <div class="form-control-position">
                            <i class="la la-phone"></i>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="form-group">
                        <label for="contactName">Contact Person</label>
                        <div class="position-relative has-icon-left">
                          <input type="text" id="contactName" class="form-control" ng-model="supplier.contactName" placeholder="Enter Contact Person Name" name="contactName" required>
                          <div class="form-control-position">
                            <i class="ft-user"></i>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>

                  <!-- Address -->
                  <div class="form-group">
                    <label for="address">Address</label>
                    <div class="position-relative has-icon-left">
                      <input type="text" id="address" class="form-control" ng-model="supplier.address" placeholder="Enter Address" name="address" required>
                      <div class="form-control-position">
                        <i class="la la-map-marker"></i>
                      </div>
                    </div>
                  </div>

                  <div class="row">
                    <!-- Payment Period -->
                    <div class="col-md-6">
                      <div class="form-group">
                        <label for="paymentPeriod">Payment Period</label>
                        <div class="position-relative has-icon-left">
                          <input type="text" id="paymentPeriod" class="form-control" ng-model="supplier.period" placeholder="Enter Payment Period (e.g., Net 30)" name="paymentPeriod" required>
                          <div class="form-control-position">
                            <i class="la la-calendar"></i> <!-- Icon for Payment Period -->
                          </div>
                        </div>
                      </div>
                    </div>

                    <!-- Payment Method -->
                    <div class="col-md-6">
                      <div class="form-group">
                        <label for="paymentMethod">Payment Method</label>
                        <select class="form-control" id="paymentMethod" ng-model="supplier.paymentTerms" name="paymentMethod" required>
                          <option value="" disabled selected>Select Payment Method</option>
                          <option value="CREDIT">CREDIT</option>
                          `                      <option value="CASH">CASH</option>
                        </select>
                        <div class="form-control-position">

                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-6" ng-if="supplierCustom.taxId.enabled">
                      <div class="form-group">
                        <label for="taxId">Vat Number</label>
                        <div class="position-relative has-icon-left">
                          <input type="text" id="taxId" class="form-control"
                                 ng-model="supplierCustom.taxId.value"
                                 placeholder="Supplier Vat Number">
                          <div class="form-control-position">
                            <i class="la la-id-card"></i>
                          </div>
                        </div>
                      </div>
                    </div>

                    <div class="col-md-6" ng-if="supplierCustom.bankDetails.enabled">
                      <div class="form-group">
                        <label for="Bank Details">Bank Details</label>
                        <div class="position-relative has-icon-left">
                          <input type="text" id="bankDetails" class="form-control"
                                 ng-model="supplierCustom.bankDetails.value"
                                 placeholder="Supplier Bank Details">
                          <div class="form-control-position">
                            <i class="la la-id-card"></i>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>



                  <!-- Form Actions -->
                  <div class="form-actions right">
                    <button type="button" class="btn btn-danger mr-1" data-dismiss="modal">
                      <i class="ft-x"></i> Cancel
                    </button>
                    <button type="submit" class="btn btn-primary">
                      <i class="la la-check-square-o"></i> Save
                    </button>
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
<%@include file="../jspf/Footer.jspf" %>

</body>
</html>