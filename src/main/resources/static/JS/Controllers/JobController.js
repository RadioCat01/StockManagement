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
        customFields:[],
        claimSerials:[]
    }
    $scope.jobItem={
        description:'',
        serial:'',
        defectiveDetails:'',
        remainingSellerWarranty:'',
        remainingSupplierWarranty:''
    }
    $scope.searchedInvoice=null;
    $scope.barcodeItems = [];
    $scope.selectedWarrantyJob=null;
    $scope.claimSerials=[];
    $scope.addSerial='';

    $scope.claimedWarrantyJob={
        jobItems:[],
    };

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
            remainingSellerWarranty : $scope.jobItem.remainingSellerWarranty || '',
            remainingSupplierWarranty: $scope.jobItem.remainingSupplierWarranty || '',        };

        $scope.jobDTO.jobItems.push(newJobItem);

        $scope.jobItem.description = '';
        $scope.jobItem.serial = '';
        $scope.jobItem.defectiveDetails = '';
        $scope.jobItem.remainingSellerWarranty = '';
        $scope.jobItem.remainingSupplierWarranty = '';

        toastr.success('Job item added successfully.', 'Success');
    };

    $scope.selectSearched=function (product){
        $scope.jobItem.description=product.productDescription;
        $scope.jobItem.serial=product.serials;
        $scope.jobItem.remainingSellerWarranty = product.remainingSellerWarranty;
        $scope.jobItem.remainingSupplierWarranty = product.remainingSupplierWarranty;
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
                    if (res.data && res.data.jobItems && res.data.jobItems.length > 0) {
                        $scope.barcodeItems = res.data.jobItems;
                        $('#barcodeModal').modal('show');
                        clearFields();
                        getJobs();
                    }
                    toastr.success('Job Saved!', 'Success');
                },function (err){
                    toastr.warning('Error Saving Jobs!', 'Error');
                })
        }
    }

    $scope.printBarcode = function (base64Image) {
        const imageSrc = `data:image/png;base64,${base64Image}`;
        printBarcodeImage(imageSrc);
    };

    $scope.openWarrantyModal = function(job) {
        $scope.selectedWarrantyJob = job;
        $scope.claimedWarrantyJob =job;
        console.log(job);
        $('#warrantyModal').modal('show');
        $scope.claimSerials = [];
        $scope.$applyAsync();
    };

    $scope.addClaimSerial = function (){
        $scope.claimSerials.push($scope.addSerial);
        $scope.addSerial='';
    }
    $scope.clearClaimSerials = function (){
        $scope.claimSerials = [];
    }

    $scope.claimWarranty = function (){
        $scope.selectedWarrantyJob.claimSerials= $scope.claimSerials;
        $http.post(APP_CONFIG.apiBase + `/job/warranty`, $scope.selectedWarrantyJob)
            .then(function (res){
                $scope.claimedWarrantyJob=res.data;
                toastr.success('Warranty Claim Success!', 'Success');
                clearFields();
                getJobs();
            },function (err){});
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
            remainingSellerWarranty:'',
            remainingSupplierWarranty:''
        }
        $scope.jobDTO={
            jobType:null,
            invoiceNumber:'',
            invoiceDate:'',
            customerName:'',
            customerPhone:'',
            jobItems:[],
            customFields:[],
            claimSerials:[]
        }
        $scope.searchedInvoice=null;
        $scope.addSerial='';
        $scope.claimSerials=[];
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
                                </div>                            
                            </div>

                        `).join('')
                            : 'No items';

                        const actionDropdown = `
                            <span>
                                <button type="button" class="btn btn-outline-blue btn-xs edit-invoice" data-job-number="${job.jobNumber || ''}" title="Edit">
                                    <i class="la la-pencil action-icons"></i>
                                </button>
                            </span>`;


                        const getStatusBadge = (status) => {
                            const statusMap = {
                                'PENDING': 'badge-warning',
                                'WARRANTY_CLAIMED': 'badge-success',
                                'DISPATCHED': 'badge-info',
                                'DELIVERED': 'badge-secondary'
                            };

                            const badgeClass = statusMap[status] || 'badge-secondary';
                            return `<span class="badge ${badgeClass}">${status || 'N/A'}</span>`;
                        };

                        jobTable.row.add([
                            job.jobNumber || '',
                            job.jobDate || '',
                            job.jobType || '',
                            job.invoiceNumber || '',
                            job.invoiceDate || '',
                            itemsHtml,
                            getStatusBadge(job.status),
                            actionDropdown
                        ]);
                    });
                    jobTable.draw();

                    $('#jobTable tbody').off('click', '.edit-invoice').on('click', '.edit-invoice', function () {
                        const jobNumber = $(this).data('job-number');
                        const selectedJob = data.find(j => String(j.jobNumber) === String(jobNumber));
                        if (selectedJob) {
                            $scope.openWarrantyModal(selectedJob);
                        } else {
                            toastr.warning('Job not found for job number: ' + jobNumber);
                        }
                    });
                }
            }, function (err) {
                toastr.warning('Error Fetching Jobs!', 'Error');
            });
    }

    $(document).on('click', '.barcode-printable', function (e) {
        e.preventDefault();
        const imgSrc = $(this).attr('src');
        printBarcodeImage(imgSrc);
    });

    function printBarcodeImage(imageSrc) {
        const printWindow = window.open('', '_blank', 'width=400,height=300');
        printWindow.document.write(`
        <html>
            <head>
                <title>Print Barcode</title>
                <style>
                    body {
                        margin: 0;
                        padding: 10px;
                        position: relative;
                        height: 100vh;
                    }
                    img {
                        width: 100px;
                        height: auto;
                        position: absolute;
                        top: 10px;
                        left: 10px;
                    }
                    @media print {
                        body {
                            margin: 0;
                            padding: 0;
                        }
                        img {
                            width: 100px;
                            height: auto;
                            position: absolute;
                            top: 10px;
                            left: 10px;
                        }
                    }
                </style>
            </head>
            <body>
                <img src="${imageSrc}" alt="Barcode" />
                <script>
                    window.onload = function() {
                        window.print();
                        window.onafterprint = function() { window.close(); }
                    }
                <\/script>
            </body>
        </html>
    `);
        printWindow.document.close();
    }



    initializeDataTable();
    getJobs();
});