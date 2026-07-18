angular.module('Stock').controller('SupplierMgt', function ($scope, $http, $timeout) {
    $scope.suppliers = [];

    $scope.supplier = {
        name: '',
        contactNumber: '',
        contactName: '',
        address: '',
        period: '',
        paymentTerms: '',
        customFields: []
    };

    $scope.supplierCustom = {
        taxId: { enabled: false, value: '' },
        bankDetails: { enabled: false, value: '' }
    };

    $scope.toggleCustomField = function (field) {
        if (!$scope.supplierCustom[field].enabled) {
            $scope.supplierCustom[field].value = '';
        }
    };

    $scope.saveSupplier = function () {
        if ($scope.supplierCustom.taxId.enabled && $scope.supplierCustom.taxId.value != null) {
            $scope.supplier.customFields.push({
                fieldName: "Vat Number",
                fieldValue: $scope.supplierCustom.taxId.value,
                fieldType: "TEXT",
                enabled: true,
                required: false
            });
        }
        if ($scope.supplierCustom.bankDetails.enabled && $scope.supplierCustom.bankDetails.value != null) {
            $scope.supplier.customFields.push({
                fieldName: "Bank Details",
                fieldValue: $scope.supplierCustom.bankDetails.value,
                fieldType: "TEXT",
                enabled: true,
                required: false
            });
        }

        $http.post(APP_CONFIG.apiBase + '/supplier', $scope.supplier)
            .then(function () {
                toastr.success('Supplier Added!', 'Success');
                resetForm();
                getSuppliers();
                $('#addSupplier').modal('hide');
            })
            .catch(function () {
                toastr.error('Something Went Wrong!', 'Error');
            });
    };

    function resetForm() {
        $scope.supplier = {
            name: '',
            contactNumber: '',
            contactName: '',
            address: '',
            period: '',
            paymentTerms: '',
            customFields: []
        };
        $scope.supplierCustom = {
            taxId: { enabled: false, value: '' },
            bankDetails: { enabled: false, value: '' }
        };
    }

    const supplierTable = $('#supplierTable').DataTable({
        paging: true,
        searching: true,
        ordering: true,
        destroy: true
    });

    function drawSupplierTable() {
        const data = $scope.suppliers;
        if (!Array.isArray(data)) return;

        const hasVatNumber = data.some(s => s.vatNumber != null && s.vatNumber.trim() !== '');
        const hasBankDetails = data.some(s => s.bankDetails != null && s.bankDetails.trim() !== '');

        supplierTable.clear();
        data.forEach(function (s) {
            supplierTable.row.add([
                s.name || '',
                s.address || '',
                s.contactName || '',
                s.contactNumber || '',
                s.paymentTerms || '',
                s.period || '',
                s.vatNumber || 'N/A',
                s.bankDetails || 'N/A'
            ]);
        });
        supplierTable.draw();
        supplierTable.column(6).visible(hasVatNumber);
        supplierTable.column(7).visible(hasBankDetails);
    }

    function getSuppliers() {
        $http.get(APP_CONFIG.apiBase + '/supplier')
            .then(function (res) {
                $scope.suppliers = res.data;
                drawSupplierTable();
            }, function () {
                toastr.warning("Error Fetching Supplier Data");
            });
    }

    getSuppliers();
});
