<!DOCTYPE html>
<html class="loading" lang="en" data-textdirection="ltr" ng-app="Stock">
<head>
  <%@include file="../jspf/Headers.jspf"%>
  <title>Stock Management</title>
  <script src="${pageContext.request.contextPath}/JS/Controllers/CompanyController.js" type="text/javascript"></script>
</head>
<body ng-controller="CompanyCont" class="vertical-layout vertical-menu 2-columns   menu-expanded fixed-navbar" data-open="click" data-menu="vertical-menu" data-color="bg-gradient-x-purple-blue" data-col="2-columns">

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
              <li ><a class="menu-item" href="${pageContext.request.contextPath}/suppliers">Suppliers</a>
              </li>
            </ul>
          </li>
          <li><a class="menu-item" href="${pageContext.request.contextPath}/customerJobs">Job Management</a>
          </li>
          <li><a class="menu-item" href="#">Company Info</a>
            <ul class="menu-content">
              <li class="active"><a class="menu-item" href="${pageContext.request.contextPath}/company"><span class="menu-title" data-i18n="">Companies</span></a>
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
          <li><a class="menu-item" href="#"><span class="menu-title" data-i18n="">Sales Summery</span></a>
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
        <h3 class="content-header-title">Company Management</h3>
      </div>
    </div>
    <div class="content-body">
      <div class="row">
        <div class="col-12">
          <div class="card">
            <div class="card-header">
              <h4 class="card-title">Company Info</h4>
              <a class="heading-elements-toggle"><i class="la la-ellipsis-v font-medium-3"></i></a>
            </div>
            <div class="card-content collapse show">
              <div class="card-body card-dashboard">
                <button class="btn btn-outline-info mb-2" data-toggle="modal" data-target="#addGenericDataModal" ng-click="openForm('company')">Add Company<i class="ft-plus"></i></button>
                <table class="table table-bordered table-striped mt-4">
                  <thead>
                  <tr>
                    <th>Company Name</th>
                    <th ng-repeat="field in companyHeaders">{{ field }}</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr ng-repeat="entity in companies">
                    <td>{{ entity.displayName }}</td>
                    <td ng-repeat="field in companyHeaders">
                      {{ entity.customFields[field] || '' }}
                    </td>
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


  <!--//////// Generic Modal ////// -->
  <div class="modal fade text-left" id="addGenericDataModal" tabindex="-1" role="dialog" aria-labelledby="addCompanyDataLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm" role="document">
      <div class="modal-content">
        <div class="modal-header btn-bg-gradient-x-purple-blue white">
          <h4 class="modal-title white" id="addCompanyDataLabel">Add Company</h4>
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


</div>
<%@include file="../jspf/Footer.jspf" %>

</body>
</html>