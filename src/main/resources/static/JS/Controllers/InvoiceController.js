angular.module('Stock').controller('InvoiceController', function($scope, $http){
    $scope.invoiceDTOs=[];
    let inventoryTable;

    function initializeDataTable() {
        if ($.fn.DataTable.isDataTable('#inventoryTable')) {
            $('#inventoryTable').DataTable().destroy();
        }

        inventoryTable = $('#inventoryTable').DataTable({
            paging: true,
            searching: true,
            ordering: true,
            destroy: true,
            initComplete: function() {
                this.api().columns().every(function() {
                    let column = this;
                    let title = $(column.footer()).text().trim();

                    let input = $('<input type="text" placeholder="' + title + '" />');

                    $(column.footer()).html(input);

                    input.on('keyup change', function() {
                        if (column.search() !== this.value) {
                            column
                                .search(this.value)
                                .draw();
                        }
                    });
                });
            }
        });

        return inventoryTable;
    }

    function getInvoiceDTOs() {
        $http.get(APP_CONFIG.apiBase + '/invoiceCont/invoice')
            .then(function (res) {
                const data = res.data;

                if (!inventoryTable || !$.fn.DataTable.isDataTable('#inventoryTable')) {
                    inventoryTable = initializeDataTable();
                } else {
                    inventoryTable.clear();
                }

                if (Array.isArray(data)) {
                    data.forEach(function (invoice) {
                        const paymentBadgeClass = invoice.paymentTerms === 'Pending' ? 'badge-warning' : 'badge-success';
                        const paymentBadge = `<span class="badge ${paymentBadgeClass}">${invoice.paymentTerms}</span>`;

                        const actionDropdown = `
                        <span>
                            <button type="button" class="btn btn-light btn-xs open-invoice" data-invoice-id="${invoice.invoiceId}" title="Open">
                                <i class="la la-eye action-icons"></i>
                            </button>
                            <button type="button" class="btn btn-light btn-xs edit-invoice" data-invoice-id="${invoice.invoiceId}" title="Edit">
                                <i class="la la-pencil action-icons"></i>
                            </button>
                        </span>`;

                        inventoryTable.row.add([
                            invoice.invoiceNumber,
                            invoice.poReference,
                            invoice.invoiceDate ? invoice.invoiceDate.substring(0, 10) : '',
                            `${invoice.customerName} <br> ${invoice.customerPhone}`,
                            invoice.saleType,
                            paymentBadge,
                            `${invoice.totalInvoice.toFixed(2)} Rs`,
                            actionDropdown
                        ]);
                    });

                    inventoryTable.draw();
                }
            }, function (err) {
                toastr.warning('Something Went Wrong!', 'Error');
            });
    }

    $(document).on('click', '.open-invoice', function (e) {
        e.preventDefault();
        const invoiceId = $(this).data('invoice-id');
        $scope.viewInvoice(invoiceId);
        $scope.$apply();
    });

    $(document).on('click', '.edit-invoice', function (e) {
        e.preventDefault();
        const invoiceId = $(this).data('invoice-id');
        $scope.editInvoice(invoiceId);
        $scope.$apply();
    });

    $scope.viewInvoice = function (invoiceID) {
        window.location.href = APP_CONFIG.apiBase + "/invoiceTemp?invoiceId=" + invoiceID;
    };

    $scope.editInvoice = function (invoiceID){
        window.location.href = APP_CONFIG.apiBase + "/editInvoice?invoiceId=" + invoiceID;
    };

    initializeDataTable();
    getInvoiceDTOs();
});