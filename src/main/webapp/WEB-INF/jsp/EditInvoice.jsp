<!DOCTYPE html>
<html class="loading" lang="en" data-textdirection="ltr">
<head>
    <%@include file="../jspf/Headers.jspf"%>
    <title>Stock Management</title>
    <script src="${pageContext.request.contextPath}/JS/Controllers/EditInvoiceController.js" type="text/javascript"></script>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-size: 12px;
        }
        .invoice-container {
            font-family: "Times New Roman", Time, serif;
            width: 100%;
            max-width: 800px;
            margin: 10px auto 10px auto;
            border: 2px solid black;
        }
        .header {
            display: flex;
            justify-content: space-between;
            padding: 15px;
            border-bottom: 2px solid black;
        }
        .company-info {
            width: 70%;
        }
        .logo {
            width: 30%;
            text-align: right;
        }
        .logo img {
            height: 110px;
            margin-top: -10px;
        }
        .company-title {
            font-size: 16px;
            font-weight: bold;
            margin-bottom: 5px;
        }
        .company-info-rows{
            font-weight: bold;
        }
        .invoice-title {
            text-align: center;
            font-size: 18px;
            font-weight: bolder;
            padding: 10px 0;
            border-bottom: 2px solid black;
        }
        .bill-info {
            display: flex;
            border-bottom: 2px solid black;
        }
        .bill-to {
            width: 50%;
            padding: 10px;
            border-right: 2px solid black;
        }
        .invoice-details {
            width: 50%;
            padding: 10px;
        }
        .invoice-details table {
            width: 100%;
            border-collapse: collapse;
        }
        .invoice-details td {
            padding: 2px 0;
            margin: 0;
            line-height: 1.2;
        }
        .invoice-details td:first-child {
            width: 40%;
        }
        .items-table {
            width: 100%;
            border-collapse: collapse;
        }
        .items-table th {
            background-color: #2e74b4;
            color: white;
            text-align: left;
            padding: 8px;
            border-top: 2px solid black;
        }
        .items-table td {
            padding: 8px;
            border: 2px solid black;
            vertical-align: top;
        }
        .items-table .item-no {
            border-left: none;
            border-right: 2px solid black;
            width: 8%;
        }
        .items-table .description {
            width: 52%;
            border-right: 2px solid black;
        }
        .items-table .qty {
            width: 10%;
            text-align: center;
            border-right: 2px solid black;
        }
        .items-table .unit-price {
            width: 15%;
            text-align: right;
            border-right: 2px solid black;
        }
        .items-table .total {
            border-right: none;
            width: 15%;
            text-align: right;
        }
        .totals-row td {
            text-align: right;
            padding-right: 10px;
            border-right: none;
        }
        .totals-row td:first-child {
            text-align: right;
            font-weight: bold;
        }
        .totals-row td:last-child {
            border-bottom: 2px solid black;
            border-right: none;
        }
        .total-invoice{
            border-bottom: 2px solid black;
        }
        .terms {
            padding: 10px;
            margin-bottom: 10px;
        }

        .terms p {
            font-size: 11px;
            margin-bottom: 4px;
            line-height: 1.2;
            padding: 0;
        }
        .signatures {
            display: flex;
            justify-content: space-between;
            margin-top: 50px;
            padding: 0 10px;
        }
        .signature-box {
            text-align: center;
            width: 22%;
        }
        .dotted-line {
            border-top: 2px dotted black;
            margin-bottom: 5px;
        }
        .thank-you {
            text-align: center;
            font-style: italic;
            margin: 30px 0 5px 0;
            font-size: 12px;
        }
        .footer-note {
            text-align: center;
            font-size: 10px;
            margin-bottom: 10px;
        }
        @media print {
            body * {
                visibility: hidden;
            }
            #invoice-container, #invoice-container * {
                visibility: visible;
            }
            #invoice-container {
                position: absolute;
                left: 0;
                top: 0;
            }
        }
    </style>
</head>
<body ng-controller="EditInvoice" class="vertical-layout vertical-menu 2-columns   menu-expanded fixed-navbar" data-open="click" data-menu="vertical-menu" data-color="bg-gradient-x-purple-blue" data-col="2-columns" ng-app="Stock">

<!-- fixed-top-->
<nav class="header-navbar navbar-expand-md navbar navbar-with-menu navbar-without-dd-arrow fixed-top navbar-dark">
    <div class="navbar-wrapper">
        <div class="navbar-header">
            <ul class="nav navbar-nav flex-row">
                <li class="nav-item mobile-menu d-md-none mr-auto"><a class="nav-link nav-menu-main menu-toggle hidden-xs" href="#"><i class="ft-menu font-large-1"></i></a></li>
                <li class="nav-item"><a class="navbar-brand" href="index.html"><img class="brand-logo" alt="Chameleon admin logo" src="${pageContext.request.contextPath}/app-assets/images/logo/logo.png">
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
                    <li ><a class="menu-item" href="${pageContext.request.contextPath}/invoice"><span class="menu-title" data-i18n="">Invoices</span></a>
                    </li>
                </ul>
            </li>
            <li class=" nav-item"><a href="#"><i class="ft-briefcase"></i><span class="menu-title" data-i18n="">Management</span></a>
                <ul class="menu-content">
                    <li class=" menu-item"><a href="${pageContext.request.contextPath}/inventory"><span class="menu-title" data-i18n="">Inventory</span></a>
                    </li>
                    <li ><a class="menu-item" href="${pageContext.request.contextPath}/addCategory">Stock Management</a>
                    </li>
                    <li><a class="menu-item" href="${pageContext.request.contextPath}/customerJobs">Job Management</a>
                    </li>
                    <li><a class="menu-item" href="${pageContext.request.contextPath}/company">Company Info</a>
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
                <h3 class="content-header-title">Edit Invoice Details</h3>
            </div>
        </div>
        <div class="content-body">
            <div class="content-body">



                <section id="form-inputs">
                    <div class="row">
                        <div class="col-6">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Update Invoice</h4>
                                </div>
                                <div class="card-content collapse show">
                                    <div class="card-body">
                                        <p class="card-text">Change invoice details to save the new invoice.</p>
                                        <br/>
                                        <table class="table table-striped table-bordered">
                                            <thead>
                                            <tr>
                                                <th>Field Name</th>
                                                <th>Value</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td>Customer Name</td>
                                                <td>
                                                    <input type="text" id="customerName" name="customerName" ng-model="invoice.customerName">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Customer Address</td>
                                                <td>
                                                    <input type="text" id="customerAddress" name="customerAddress" ng-model="invoice.customerAddress">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Customer Phone</td>
                                                <td>
                                                    <input type="text" id="customerPhone" name="customerPhone" ng-model="invoice.customerPhone">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Payment Terms</td>
                                                <td>
                                                    <select id="paymentTerms" name="paymentTerms" ng-model="invoice.paymentTerms">
                                                        <option value="Cash">Cash</option>
                                                        <option value="Card">Card</option>
                                                        <option value="Pending">Pending</option>
                                                        <option value="30% Advanced">30% Advance</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Sales Type</td>
                                                <td>
                                                    <select id="saleType" name="saleType" ng-model="invoice.saleType">
                                                        <option value="Retail">Retail</option>
                                                        <option value="Dealer">Dealer</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Sub Total</td>
                                                <td>
                                                    <input type="number" id="subTotal" name="subTotal" ng-model="invoice.subTotal" step="0.01">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>VAT</td>
                                                <td>
                                                    <input type="number" id="vat" name="vat" ng-model="invoice.vat" step="0.01">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Total Invoice</td>
                                                <td>
                                                    <input type="number" id="totalInvoice" name="totalInvoice" ng-model="invoice.totalInvoice" step="0.01">
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        <div class="d-flex justify-content-end">
                                            <button class="btn btn-primary mr-1" type="button" ng-click="updateInvoice()">
                                                <i class="ft-file"></i> Update
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-6">
                            <div class="card">
                                <div class="card-content collapse show">
                                    <div class="card-body">
                                        <div id="invoice-container" class="invoice-container">
                                            <div class="header">
                                                <div class="company-info">
                                                    <div class="company-title">US Computer Technologies (Pvt) Ltd</div>
                                                    <div class="company-info-rows">No.325, High Level Road, Pitipana,
                                                    </div>
                                                    <div class="company-info-rows">Homagama, Sri Lanka</div>
                                                    <div class="company-info-rows"><a
                                                            href="mailto:uscomtechinfo@gmail.com">uscomtechinfo@gmail.com</a>
                                                    </div>
                                                    <div class="company-info-rows">Tel: 0112 892 163 | Mobile: 0772 351
                                                        187
                                                    </div>
                                                    <div class="company-info-rows">Business Reg No: PV 00303856</div>
                                                </div>
                                                <div class="logo">
                                                    <img src="${pageContext.request.contextPath}/resources/images/USCOM.png"
                                                         alt="US COMTECH Logo">
                                                </div>
                                            </div>

                                            <!-- Invoice Title -->
                                            <div class="invoice-title">INVOICE</div>

                                            <!-- Bill Info -->
                                            <div class="bill-info">
                                                <div class="bill-to">
                                                    <strong>BILL TO :</strong><br>
                                                    ${invoice.customerName}<br>
                                                    ${invoice.customerAddress}<br>
                                                    ${invoice.customerPhone}<br>
                                                </div>
                                                <div class="invoice-details">
                                                    <table>
                                                        <tr>
                                                            <td><strong>Invoice No</strong></td>
                                                            <td>${invoice.invoiceNumber}</td>
                                                        </tr>
                                                        <tr>
                                                            <td><strong>Invoice Date</strong></td>
                                                            <td>${invoice.invoiceDate}</td>
                                                        </tr>
                                                        <tr>
                                                            <td><strong>PO Ref#</strong></td>
                                                            <td>${invoice.poReference}</td>
                                                        </tr>
                                                        <tr>
                                                            <td><strong>PO Date</strong></td>
                                                            <td>${invoice.poDate}</td>
                                                        </tr>
                                                        <tr>
                                                            <td><strong>Payment Terms</strong></td>
                                                            <td>${invoice.paymentTerms}</td>
                                                        </tr>
                                                        <tr>
                                                            <td><strong>Sales Person</strong></td>
                                                            <td>${invoice.salesPerson}</td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </div>


                                            <!-- Items Table with integrated totals -->
                                            <table class="items-table">
                                                <thead>
                                                <tr>
                                                    <th class="item-no">Item No</th>
                                                    <th class="description">DESCRIPTION</th>
                                                    <th class="qty">QTY</th>
                                                    <th class="unit-price">UNIT PRICE</th>
                                                    <th class="total">TOTAL</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${invoice.products}" var="product" varStatus="loop">
                                                    <tr>
                                                        <td class="item-no">${loop.index + 1}</td>
                                                        <td class="description">
                                                                ${product.productDescription}<br>
                                                            S/N: ${product.serials}<br>
                                                            Warranty: ${product.warranty}
                                                        </td>
                                                        <td class="qty">${product.quantity}</td>
                                                        <td class="unit-price">${product.unitPrice}</td>
                                                        <td class="total">${product.total}</td>
                                                    </tr>
                                                </c:forEach>

                                                <!-- Combined totals row -->
                                                <tr class="totals-row">
                                                    <td colspan="4" style="text-align: right; border: none;">
                                                        <strong>SUB Total</strong>
                                                    </td>
                                                    <td><strong>${invoice.subTotal}</strong></td>
                                                </tr>
                                                <tr class="totals-row">
                                                    <td colspan="4" style="text-align: right; border: none;">
                                                        Vat
                                                    </td>
                                                    <td><strong>${invoice.vat}</strong></td>
                                                </tr>
                                                <tr class="totals-row total-invoice">
                                                    <td colspan="4" style="text-align: right; border: none;">
                                                        <strong>Total Invoice</strong>
                                                    </td>
                                                    <td><strong>${invoice.totalInvoice}</strong></td>
                                                </tr>
                                                </tbody>
                                            </table>


                                            <!-- Terms with more compact design -->
                                            <div class="terms">
                                                <p>Please write an A/C Payee Cheque in favour of US Computer
                                                    Technologies
                                                    (Pvt)
                                                    Ltd</p>
                                                <p>Bank Details: Bank of Ceylon Mattegoda Branch - Account
                                                    No.83170476</p>
                                                <p>Only manufacturer's defect are covered by warranty.</p>
                                                <p>Damages or defects due to other causes such as
                                                    negligence,misuse,physical
                                                    damages,corrosion,burn marks, power fluctuation, lightening accident
                                                    etc. or
                                                    other natural disasters, whatever are NOT included under this
                                                    warranty.</p>
                                                <p>Repair or replacement necessities by causes not covered by the
                                                    warranty
                                                    are
                                                    subject to charges for labour time and material.</p>
                                                <p>Notebook Batteries and Power adaptor cover only One year warranty.
                                                    Above
                                                    3+
                                                    dual
                                                    pixels will be covered under warranty for Monitor.</p>
                                            </div>

                                            <!-- Signatures -->
                                            <div class="signatures">
                                                <div class="signature-box">
                                                    <div class="dotted-line"></div>
                                                    <div>Authorized Signature</div>
                                                </div>
                                                <div class="signature-box">
                                                    <div class="dotted-line"></div>
                                                    <div>Customer Name</div>
                                                </div>
                                                <div class="signature-box">
                                                    <div class="dotted-line"></div>
                                                    <div>Customer Signature</div>
                                                </div>
                                                <div class="signature-box">
                                                    <div class="dotted-line"></div>
                                                    <div>ID No</div>
                                                </div>
                                            </div>

                                            <!-- Footer -->
                                            <div class="thank-you">Thank you for doing business with us!</div>
                                            <div class="footer-note">This is a computer generated invoice and does not
                                                need
                                                a
                                                Signature
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
    </div>
</div>

                <%@include file="../jspf/Footer.jspf" %>
</body>
</html>