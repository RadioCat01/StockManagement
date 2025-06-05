angular.module('Stock').controller('JobController', function ($scope, $http,$timeout) {
    $scope.jobTypes=["IN_WARRANTY","NON_WARRANTY","CHARGEABLE"];
    $scope.selectedJobType = null;
    let jobTable;
    $scope.jobDTO={
        jobType:null,
        invoiceNumber:'',
        invoiceDate:'',
        customerName:'',
        customerPhone:'',

        jobItems:[],
        customFields:[]
    }
    $scope.jobItem={
        description:'',
        serial:'',
        defectiveDetails:'',
        remainingWarranty:'',
    }
    $scope.searchedInvoice=null;

    $scope.addCustomJobItem = function() {
        if (!$scope.jobItem.description || !$scope.jobItem.serial) {
            toastr.warning('Please enter both Item Description and Item Serial.', 'Warning');
            return;
        }

        const exists = $scope.jobDTO.jobItems.some(item => item.serial === $scope.jobItem.serial);
        if (exists) {
            toastr.warning('This serial number is already added.', 'Warning');
            return;
        }

        const newJobItem = {
            description: $scope.jobItem.description,
            serial: $scope.jobItem.serial,
            defectiveDetails: $scope.jobItem.defectiveDetails || '',
            remainingWarranty: $scope.jobItem.remainingWarranty || '',
        };

        $scope.jobDTO.jobItems.push(newJobItem);

        $scope.jobItem.description = '';
        $scope.jobItem.serial = '';
        $scope.jobItem.defectiveDetails = '';
        $scope.jobItem.remainingWarranty = '';

        toastr.success('Job item added successfully.', 'Success');
    };

    $scope.selectSearched=function (product){
        $scope.jobItem.description=product.productDescription;
        $scope.jobItem.serial=product.serials;
    }

    $scope.removeJobItem = function(index) {
        if (index >= 0 && index < $scope.jobDTO.jobItems.length) {
            $scope.jobDTO.jobItems.splice(index, 1);
        }
    };


    $scope.searchInvoice = function (){
        $http.get(APP_CONFIG.apiBase + '/invoiceCont/invoiceByNumber',{
            params: {number: $scope.jobDTO.invoiceNumber}
        }).then(function (res){
            toastr.success('Invoice Fetched!', 'Success');
            $scope.searchedInvoice=res.data;
            $scope.jobDTO.customerName = $scope.searchedInvoice.customerName;
            $scope.jobDTO.customerPhone = $scope.searchedInvoice.customerPhone;
            $scope.jobDTO.invoiceDate = new Date($scope.searchedInvoice.invoiceDate);
        },function (err){
            toastr.warning('Error Fetching Invoices!', 'Error');
        });
    }

    $scope.addJob = function (){
        if(validateInputs()){
            $http.post(APP_CONFIG.apiBase + `/job/add`,$scope.jobDTO)
                .then(function (res){
                    clearFields();
                    getJobs();
                    toastr.success('Job Saved!', 'Success');
                },function (err){
                    toastr.warning('Error Saving Jobs!', 'Error');
                })
        }
    }
    function validateInputs (){
        if($scope.jobDTO.customerPhone != null && $scope.jobDTO.customerName !=null &&
            $scope.jobDTO.jobItems.length >0){
            return true;
        }
    }
    function clearFields(){
        $scope.jobDTO.jobItems=[];
        $scope.jobItem={
            description:'',
            serial:'',
            defectiveDetails:'',
            remainingWarranty:'',
        };
    }

    function initializeDataTable() {
        if ($.fn.DataTable.isDataTable('#jobTable')) {
            $('#jobTable').DataTable().destroy();
        }

        jobTable = $('#jobTable').DataTable({
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
        return jobTable;
    }

    function getJobs(){
        $http.get(APP_CONFIG.apiBase + `/job/get`)
            .then(function (res) {
                const data = res.data;

                if (!jobTable || !$.fn.DataTable.isDataTable('#jobTable')) {
                    jobTable = initializeDataTable();
                } else {
                    jobTable.clear();
                }

                if (Array.isArray(data)) {
                    data.forEach(function (job) {
                        const itemsHtml = Array.isArray(job.jobItems) && job.jobItems.length > 0
                            ? job.jobItems.map(item => `
                            <div style="display: flex; justify-content: space-between; align-items: flex-start;">
                                <div class="mb-1" style="flex: 1; max-width: 500px">
                                    <div><strong>Description:</strong> ${item.description || ''}</div>
                                    <div><strong>Serial:</strong> ${item.serial || ''}</div>
                                    <div style="max-width: 500px; white-space: normal; overflow-wrap: break-word; word-break: break-word;">
                                        <strong>Defective Details:</strong> ${item.defectiveDetails || ''}
                                    </div>
                                    <div><strong>Remaining Warranty:</strong> ${item.remainingWarranty || ''}</div>
                                </div>
                                <div style="flex-shrink: 0; width: 110px; text-align: right;">
                                    <img src="data:image/png;base64,${item.barcodeImage || ''}" 
                                         alt="Barcode" 
                                         style="height:40px; max-width: 100%;"/>
                                </div>
                            </div>

                        `).join('')
                            : 'No items';

                        const actionDropdown = `
                        <span>
                            <button type="button" class="btn btn-light btn-xs open-invoice" data-invoice-id="${job.invoiceId || ''}" title="Open">
                                <i class="la la-eye action-icons"></i>
                            </button>
                            <button type="button" class="btn btn-light btn-xs edit-invoice" data-invoice-id="${job.invoiceId || ''}" title="Edit">
                                <i class="la la-pencil action-icons"></i>
                            </button>
                        </span>`;

                        jobTable.row.add([
                            job.jobNumber || '',
                            job.jobDate || '',
                            job.jobType || '',
                            job.invoiceNumber || '',
                            job.invoiceDate || '',
                            itemsHtml,
                            job.status || 'N/A',
                            actionDropdown
                        ]);
                    });
                    jobTable.draw();
                }
            }, function (err) {
                toastr.warning('Error Fetching Jobs!', 'Error');
            });
    }

    initializeDataTable();
    getJobs();
});