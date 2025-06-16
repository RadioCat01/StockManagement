<!DOCTYPE html>
<html class="loading" lang="en" data-textdirection="ltr" ng-app="Stock">
<head>
  <%@include file="../jspf/Headers.jspf"%>
  <title>Stock Management</title>
  <script src="${pageContext.request.contextPath}/JS/Controllers/JobController.js" type="text/javascript"></script>
  <style>
    .form-check{
      margin: 10px;
    }
    .product-list-scrollable {
      max-height: 300px;
      overflow-y: auto;
      padding-right: 10px;
      border: 1px solid #ddd;
      border-radius: 4px;
      background-color: #fafafa;
    }
    .job-items-container {
      height: 400px;
      overflow-y: auto;
      margin: 15px 0;
    }
    .btn-xs {
      padding: 0.2rem 0.4rem;
      font-size: 0.75rem;
      line-height: 1;
      border-radius: 0.2rem;
    }

    .action-icons {
      font-size: 15rem;
    }

  </style>
</head>
<body ng-controller="JobController" class="vertical-layout vertical-menu 2-columns   menu-expanded fixed-navbar" data-open="click" data-menu="vertical-menu" data-color="bg-gradient-x-purple-blue" data-col="2-columns">

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
              <div class="${pageContext.request.contextPath}/arrow_box_right"><a class="dropdown-item" href="#"><span class="${pageContext.request.contextPath}/avatar avatar-online"><img src="../../../app-assets/images/portrait/small/avatar-s-19.png" alt="${pageContext.request.contextPath}/avatar"><span class="user-name text-bold-700 ml-1">John Doe</span></span></a>
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
          <li class="active"><a class="menu-item" href="${pageContext.request.contextPath}/customerJobs">Job Management</a>
          </li>
          <li><a class="menu-item" href="${pageContext.request.contextPath}/company">Company Info</a>
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
  <div class="navigation-background"></div>
</div>

<!-- //////////////////////////////////////////////////////////////////////////// -->
<!-- Cont -->
<div class="${pageContext.request.contextPath}/app-content content">
  <div class="content-wrapper">
    <div class="content-wrapper-before"></div>
    <div class="content-header row">
      <div class="content-header-left col-md-4 col-12 mb-2">
        <h3 class="content-header-title">Customer Jobs</h3>
      </div>
    </div>
    <div class="content-body">
      <div class="row">
        <div class="col-12">

          <div class="content-body">
            <div class="row">
              <div class="col-12">
                <div class="card">
                  <div class="card-header">
                    <h4 class="card-title">Customer Job Management</h4>
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
                      <p class="card-text">Add customer job notes, inspect and manage them from here.</p>
                      <button class="btn btn-outline-info mb-2" data-toggle="modal" data-target="#addJob"><i class="ft-plus"></i>&nbsp; Add Job</button>
                      <table id="jobTable" class="table table-white-space table-bordered row-grouping display no-wrap icheck table-middle">
                        <thead>

                        <tr>
                          <th>Job #</th>
                          <th>Date</th>
                          <th>Type</th>
                          <th>Invoice Info</th>
                          <th>Customer Info</th>
                          <th>Item Details</th>
                          <th>Status</th>
                          <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                        <tfoot>
                        <tr>
                          <th>Job #</th>
                          <th>Date</th>
                          <th>Type</th>
                          <th>Invoice Info</th>
                          <th>Customer Info</th>
                          <th>Item Details</th>
                          <th>Status</th>
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
                  <div class="card-header">
                    <h4 class="card-title">Warranty Replacement Notes</h4>
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
                      <p class="card-text">Contains details about warranty replacements.</p>
                      <table id="repTable" class="table table-white-space table-bordered">
                        <thead>
                        <tr>
                          <th>Replacement #</th>
                          <th>Replaced Date</th>
                          <th>Defective Item</th>
                          <th>Replacement</th>
                          <th>Customer Info</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                        <tfoot>
                        <tr>
                          <th>Replacement #</th>
                          <th>Replaced Date</th>
                          <th>Defective Item</th>
                          <th>Replacement</th>
                          <th>Customer Info</th>
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
                  <div class="card-header">
                    <h4 class="card-title">Defect Items in Inventory</h4>
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
                      <p class="card-text">Contains details about warranty replacements.</p>
                      <table id="defTable" class="table table-white-space table-bordered">
                        <thead>
                        <tr>
                          <th>Item Info</th>
                          <th>Defective Details</th>
                          <th>Customer Info</th>
                          <th>Returned Date</th>
                          <th>Remaining Supplier Warranty</th>
                          <th>Remaining Seller Warranty</th>
                          <th>Replacement Status</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                        <tfoot>
                        <tr>
                          <th>Item Info</th>
                          <th>Defective Details</th>
                          <th>Customer Info</th>
                          <th>Returned Date</th>
                          <th>Remaining Supplier Warranty</th>
                          <th>Remaining Seller Warranty</th>
                          <th>Replacement Status</th>
                        </tr>
                        </tfoot>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
            </div>


            <!-- AddJob Model -->
            <div class="modal fade text-left" id="addJob" tabindex="-1" role="dialog" aria-labelledby="addJobModalLabel" aria-hidden="true">
              <div class="modal-dialog modal-xl" role="document">
                <div class="modal-content">

                  <div class="modal-header btn-bg-gradient-x-purple-blue white">
                    <h4 class="modal-title white" id="addProductModalLabel">Add Customer Job</h4>
                    <button type="button" class="close white" data-dismiss="modal" aria-label="Close">
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>


                  <div class="modal-body">
                    <form class="form needs-validation" ng-submit="addJob()" novalidate>
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

                        <div class="d-flex" style="position: relative; gap: 2rem;">
                          <div class="col-md-6 flex-fill">

                            <div class="form-group">
                              <h5>Job Type</h5>
                              <select id="jobType" class="form-control" ng-model="jobDTO.jobType" ng-options="type for type in jobTypes" required>
                                <option value="" disabled selected>Select Job Type</option>
                              </select>
                            </div>


                            <!-- invoice number -->
                              <h5>Invoice Details</h5>
                              <div class="form-group">
                                <label for="invoiceNumber">Invoice Number</label>
                                <div class="d-flex align-items-center">
                                  <div class="position-relative has-icon-left flex-grow-1">
                                    <input type="text" id="invoiceNumber" class="form-control"
                                           ng-model="jobDTO.invoiceNumber"
                                           placeholder="Enter Invoice Number"
                                           name="invoiceNumber" required>
                                    <div class="form-control-position">
                                      <i class="ft-user"></i>
                                    </div>
                                  </div>
                                  <button type="button" ng-click="searchInvoice()" class="btn btn-secondary ml-2">
                                    Search
                                  </button>
                                </div>
                              </div>

                            <div class="row">
                              <div class="col-md-6">
                              <div class="form-group">
                                <label for="invoiceDate">Invoice Date</label>
                                  <div class='input-group'>
                                    <input type='date' id="invoiceDate" ng-model="jobDTO.invoiceDate" class="form-control" />
                                  </div>
                              </div>
                              </div>
                            </div>


                            <!-- Customer Details Section -->
                            <h5 class="mt-1">Customer Details</h5>
                            <div class="row">
                              <div class="col-md-6">
                                <div class="form-group">
                                  <label for="customerName">Customer Name</label>
                                  <div class="position-relative has-icon-left">
                                    <input type="text" id="customerName" class="form-control" ng-model="jobDTO.customerName" placeholder="Enter Customer Name" name="customerName" required>
                                    <div class="form-control-position">
                                      <i class="ft-user"></i>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="col-md-6">
                                <div class="form-group">
                                  <label for="customerPhoneNumber">Customer Phone</label>
                                  <div class="position-relative has-icon-left">
                                    <input type="text" id="customerPhoneNumber" class="form-control" ng-model="jobDTO.customerPhone" placeholder="Enter Customer Phone Number" name="customerPhoneNumber" required>
                                    <div class="form-control-position">
                                      <i class="la la-phone"></i>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>

                            <h5>Add Job Items</h5>
                            <div class="row">
                              <div class="col-md-6">
                                <div class="form-group">
                                  <label for="customerName">Item Description</label>
                                  <div class="position-relative has-icon-left">
                                    <input type="text" id="customerName" class="form-control" ng-model="jobItem.description" placeholder="Enter Customer Name" name="customerName" required>
                                    <div class="form-control-position">
                                      <i class="la la-comment"></i>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="col-md-6">
                                <div class="form-group">
                                  <label for="customerPhoneNumber">Item Serial</label>
                                  <div class="position-relative has-icon-left">
                                    <input type="text" id="customerPhoneNumber" class="form-control" ng-model="jobItem.serial" placeholder="Enter Customer Phone Number" name="customerPhoneNumber" required>
                                    <div class="form-control-position">
                                      <i class="la la-barcode"></i>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>

                            <div class="form-group">
                              <label for="defectiveDetails">Defective Details</label>
                              <div class="d-flex align-items-center">
                                <div class="position-relative has-icon-left flex-grow-1">
                                  <textarea id="defectiveDetails" class="form-control"
                                            ng-model="jobItem.defectiveDetails"
                                            placeholder="Enter Defective Details"
                                            name="defectiveDetails" required rows="4"></textarea>
                                  <div class="form-control-position">
                                    <i class="ft-user"></i>
                                  </div>
                                </div>
                              </div>
                            </div>


                            <div class="d-flex align-items-end" style="gap: 1rem; flex-wrap: wrap;">

                              <!-- Remaining Seller Warranty -->
                              <div style="display: flex; flex-direction: column; flex: 1; min-width: 180px;">
                                <label for="remainingSellerWarranty" style="margin-bottom: 0.25rem;">
                                  Remaining Seller Warranty
                                </label>
                                <div class="position-relative has-icon-left">
                                  <input type="text" id="remainingSellerWarranty" class="form-control"
                                         ng-model="jobItem.remainingSellerWarranty"
                                         placeholder="Remaining Seller Warranty">
                                  <div class="form-control-position">
                                    <i class="la la-calendar"></i>
                                  </div>
                                </div>
                              </div>

                              <!-- Remaining Supplier Warranty -->
                              <div style="display: flex; flex-direction: column; flex: 1; min-width: 180px;">
                                <label for="remainingSupplierWarranty" style="margin-bottom: 0.25rem;">
                                  Remaining Supplier Warranty
                                </label>
                                <div class="position-relative has-icon-left">
                                  <input type="text" id="remainingSupplierWarranty" class="form-control"
                                         ng-model="jobItem.remainingSupplierWarranty"
                                         placeholder="Remaining Supplier Warranty">
                                  <div class="form-control-position">
                                    <i class="la la-calendar"></i>
                                  </div>
                                </div>
                              </div>

                              <!-- Add Button -->
                              <button type="button" ng-click="addCustomJobItem()" class="btn btn-secondary" style="height: 38px; white-space: nowrap;">
                                Add
                              </button>

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
                          </div>

                          <div class="col-md-6 flex-fill" >
                            <h5>Products in Invoice</h5>
                              <div ng-if="searchedInvoice.products && searchedInvoice.products.length > 0"
                                   class="product-list-scrollable">
                                <div ng-click="selectSearched(product)" class="product-card p-1" ng-repeat="product in searchedInvoice.products"
                                     style="cursor: pointer;">
                                  <div><strong>Products:</strong> {{ product.productDescription || 'N/A' }}</div>
                                  <div><strong>Serials:</strong> {{ product.serials || 'N/A' }}</div>
                                  <div><strong>Supplier Warranty:</strong> Until {{ product.supplierWarrantyUntil || 'N/A' }}</div>
                                  <div><strong>Seller Warranty:</strong> Until {{ product.sellerWarrantyUntil || 'N/A'}}</div>
                                </div>
                              </div>

                              <div ng-if="!searchedInvoice.products || searchedInvoice.products.length === 0" class="text-muted">
                                No products found for this invoice.
                            </div>

                            <h5 class="mt-1">Added Job Items</h5>
                            <div ng-if="jobDTO.jobItems.length > 0"
                                 class="mt-1 job-items-container"
                                 style="height: 400px; overflow-y: auto;">
                              <ul class="list-unstyled">
                                <li ng-repeat="item in jobDTO.jobItems track by $index" class="job-item p-1 mb-1 border rounded d-flex justify-content-between align-items-start">
                                  <div>
                                    <strong>Description:</strong> {{ item.description }} <br>
                                    <strong>Serial:</strong> {{ item.serial }} <br>
                                    <strong>Remaining Warranty:</strong> {{ item.remainingWarranty }}<br>
                                    <strong>Defective Details:</strong> {{item.defectiveDetails}}
                                  </div>
                                  <button type="button" class="btn btn-sm btn-danger ml-3" ng-click="removeJobItem($index)" title="Remove Item">
                                    &times;
                                  </button>
                                </li>
                              </ul>
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

            <!-- /////// Barcode Modal /////// -->
            <div id="barcodeModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="barcodeModalLabel" aria-hidden="true">
              <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 400px;">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="barcodeModalLabel">Barcodes</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" ng-click="closeBarcodeModal()">
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>
                  <div class="modal-body" style="display: flex; flex-wrap: wrap; gap: 20px; justify-content: center;">
                    <div ng-repeat="item in barcodeItems"
                         class="barcode-item-card"
                         style="display: flex; flex-direction: column; align-items: center; border: 1px solid #ddd; border-radius: 8px; padding: 16px; min-width: 220px; background: #fafbfc;">
                      <img ng-src="data:image/png;base64,{{item.barcodeImage}}"
                           alt="Barcode"
                           class="barcode-printable"
                           style="height: 80px; margin-bottom: 10px; cursor: pointer;"
                           ng-click="printBarcode(item.barcodeImage)" />
                      <div class="barcode-details" style="text-align: left; width: 100%;">
                        <div><strong>Description:</strong> {{item.description}}</div>
                        <div><strong>Serial:</strong> {{item.serial}}</div>
                        <div ng-if="item.defectiveDetails"><strong>Defective Details:</strong> {{item.defectiveDetails}}</div>
                        <div><strong>Seller Warranty:</strong> {{item.remainingSellerWarranty}}</div>
                        <div><strong>Supplier Warranty:</strong> {{item.remainingSupplierWarranty}}</div>
                      </div>
                    </div>
                  </div>

                </div>
              </div>
            </div>


            <!-- /////// Warranty Modal ///////-->
            <div class="modal fade text-left" id="warrantyModal" tabindex="-1" role="dialog" aria-labelledby="warrantyModalLabel" aria-hidden="true">
              <div class="modal-dialog modal-xl" role="document">
                <div class="modal-content">

                  <div class="modal-header btn-bg-gradient-x-purple-blue white">
                    <h4 class="modal-title white" id="warrantyModalLabel">Manage Customer Job</h4>
                    <button type="button" class="close white" data-dismiss="modal" aria-label="Close">
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>

                  <div class="modal-body">
                    <form class="form needs-validation" ng-submit="addJob()" novalidate>
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

                        <div class="d-flex" style="position: relative; gap: 2rem;">
                          <div class="col-md-6 flex-fill">
                            <h5>Warranty Replacement</h5>
                            <div class="form-group">
                              <label for="serialNumber">Serial Number for Warranty Replacement</label>
                              <div class="d-flex align-items-center">
                                <div class="position-relative has-icon-left flex-grow-1">
                                  <input type="text" id="serialNumber" class="form-control"
                                         ng-model="addSerial"
                                         placeholder="Enter Invoice Number"
                                         name="invoiceNumber" required>
                                  <div class="form-control-position">
                                    <i class="ft-user"></i>
                                  </div>
                                </div>
                                <button type="button" ng-click="addClaimSerial()" class="btn btn-outline-secondary ml-2">
                                  Add Item
                                </button>
                                <button type="button" ng-click="clearClaimSerials()" class="btn btn-outline-danger ml-2">
                                  <i class="la la-remove"></i>
                                </button>
                              </div>
                            </div>
                            <div class="serials-wrapper" style="display: flex; align-items: center; gap: 12px; margin: 12px 0;">
                              <div class="serials-container"
                                   style="display: flex; gap: 8px; overflow-x: auto; padding-bottom: 4px; max-width: 70vw;">

                                <div ng-repeat="serial in claimSerials"
                                     class="serial-chip"
                                     style="background: #f3f4f6; border: 1px solid #d1d5db; border-radius: 16px; padding: 6px 14px; font-size: 15px; color: #222; box-shadow: 0 1px 3px rgba(0,0,0,0.05); white-space: nowrap; flex-shrink: 0;">
                                  {{serial}}
                                </div>
                              </div>
                              <div ng-if="claimSerials.length > 0">
                                <button type="button" ng-click="claimWarranty()" class="btn btn-outline-info" style="white-space: nowrap;">
                                  Confirm Warranty Claim <i class="la la-clipboard"></i>
                                </button>
                              </div>
                            </div>

                            <div ng-repeat="item in claimedWarrantyJob.jobItems">
                              <div ng-if="item.replacedItem.serial" style="display: flex;align-items: flex-start; border: 1px solid #3c4244; border-radius: 2px; padding:10px; margin-bottom: 10px;">
                                <div style="flex: 1;">
                                  <h6 style="margin-top: 0; margin-bottom: 12px; color: #333;">Faulty Item</h6>
                                  <p><strong>Description:</strong> {{item.description}}</p>
                                  <p><strong>Serial:</strong> {{item.serial}}</p>
                                </div>

                                <div style="flex: 1;">
                                  <h6 style="margin-top: 0; margin-bottom: 12px; color: #333;">Replaced Item</h6>
                                  <p><strong>Description:</strong> {{item.replacedItem.description}}</p>
                                  <p><strong>Serial:</strong> {{item.replacedItem.serial}}</p>
                                </div>
                              </div>
                            </div>

                          </div>



                          <div class="col-md-6 flex-fill">
                            <h5 style="margin-bottom: 0.5rem;">Job Details</h5>
                            <div style="margin-bottom: 1rem; padding: 0.5rem;">
                              <p style="margin: 0.15rem 0;"><strong>Job Number:</strong> {{ selectedWarrantyJob.jobNumber }}</p>
                              <p style="margin: 0.15rem 0;"><strong>Job Date:</strong> {{ selectedWarrantyJob.jobDate | date:'mediumDate' }}</p>
                              <p style="margin: 0.15rem 0;"><strong>Job Type:</strong> {{ selectedWarrantyJob.jobType || 'N/A' }}</p>
                              <p style="margin: 0.15rem 0;">
                                <strong>Status:</strong>
                                <span ng-style="selectedWarrantyJob.status === 'WARRANTY_CLAIMED' ? {'background-color': 'green', 'color': 'white', 'padding': '2px 6px', 'border-radius': '4px'} : {}">
                                  {{ selectedWarrantyJob.status }}
                                </span>
                              </p>
                              <p style="margin: 0.15rem 0;"><strong>Invoice Number:</strong> {{ selectedWarrantyJob.invoiceNumber }}</p>
                              <p style="margin: 0.15rem 0;"><strong>Invoice Date:</strong> {{ selectedWarrantyJob.invoiceDate | date:'mediumDate' }}</p>
                            </div>

                            <h5 style="margin-bottom: 0.5rem;">Job Items and Defective Details</h5>
                            <div ng-if="selectedWarrantyJob.jobItems && selectedWarrantyJob.jobItems.length > 0"
                                 style="max-height: 400px; overflow-y: auto; border: 1px solid #ddd; border-radius: 4px; padding: 0.5rem;">
                              <div ng-repeat="item in selectedWarrantyJob.jobItems"
                                   style="margin-bottom: 0.5rem; padding: 0.3rem; border-bottom: 1px solid #eee; display: flex; align-items: center; gap: 0.5rem;">
                                <div style="flex: 1; min-width: 0;">
                                  <p style="margin: 0; font-weight: 600; font-size: 1rem;">{{ item.description }}</p>
                                  <p style="margin: 0; font-size: 1rem; color: #555;">Serial: {{ item.serial }}</p>
                                  <p style="margin: 0; font-size: 1rem; color: #555;">Defective: {{ item.defectiveDetails }}</p>
                                  <p style="margin: 0; font-size: 1rem; color: #555;">Remaining Seller Warranty: {{ item.remainingSellerWarranty }}</p>
                                  <p style="margin: 0; font-size: 1rem; color: #555;">Remaining Supplier Warranty: {{ item.remainingSupplierWarranty }}</p>
                                  <p style="margin: 0; font-size: 1rem; color: #555; display: flex; align-items: center; gap: 6px;">
                                    Warranty Claim Status:
                                      <i ng-if="item.warrantyClaimed" class="ft-check-circle" style="color: green;"></i>
                                      <i ng-if="!item.warrantyClaimed" class="ft-alert-circle" style="color: orangered;"></i>
                                  </p>

                                </div>
                                <div style="flex-shrink: 0;">
                                  <img ng-src="data:image/png;base64,{{ item.barcodeImage }}" alt="Barcode"
                                       style="height: 30px; max-width: 100%; display: block;">
                                </div>
                              </div>
                            </div>
                            <div ng-if="!selectedWarrantyJob.jobItems || selectedWarrantyJob.jobItems.length === 0" style="font-size: 15rem; color: #888;">
                              <p>No job items available.</p>
                            </div>
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
<%--<%@include file="../jspf/Footer.jspf" %>--%>
<script src="${pageContext.request.contextPath}/app-assets/vendors/js/vendors.min.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/app-assets/vendors/js/tables/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/app-assets/vendors/js/tables/datatable/dataTables.bootstrap4.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/app-assets/vendors/js/forms/icheck/icheck.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/app-assets/vendors/js/tables/datatable/datatables.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/app-assets/vendors/js/tables/datatable/dataTables.buttons.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/app-assets/vendors/js/tables/buttons.flash.min.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/app-assets/js/core/app-menu.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/app-assets/js/core/app.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/app-assets/js/scripts/pages/invoices-list.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/app-assets/vendors/js/extensions/toastr.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/app-assets/js/scripts/extensions/toastr.js" type="text/javascript"></script>

</body>
</html>