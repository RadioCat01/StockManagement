angular.module('Stock').controller('EditInvoice', function($scope, $http){
    $scope.invoice={
        customerName:'',
        customerAddress:'',
        customerPhone:'',
        paymentTerms:'',
        saleType:'',
        subTotal:0.00,
        vat:0.00,
        totalInvoice:0.00
    }

    const urlParams = new URLSearchParams(window.location.search);
    const invoiceId = urlParams.get('invoiceId');

    function getInvoiceData() {
        $http.get(APP_CONFIG.apiBase +'/invoiceCont/invoice/' + invoiceId)
            .then(function success(response) {
                $scope.invoice = response.data;
            })
            .catch(function error(errorResponse) {
                toastr.warning('Something Went Wrong!', 'Error');
            });
    }

    $scope.updateInvoice = function (){
        $http.post(APP_CONFIG.apiBase +`/invoiceCont/updateInvoice`, $scope.invoice)
            .then(function (res){
                toastr.success('Operation Completed!', 'Success');
                window.location.href = APP_CONFIG.apiBase + "/editInvoice?invoiceId=" + invoiceId;
            },function (err){
                toastr.warning('Something Went Wrong!', 'Error');
            })
    }

    getInvoiceData();

});