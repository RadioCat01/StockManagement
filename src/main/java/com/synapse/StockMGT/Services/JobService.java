package com.synapse.StockMGT.Services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.synapse.StockMGT.DTOs.*;
import com.synapse.StockMGT.Enums.JobStatus;
import com.synapse.StockMGT.Models.*;
import com.synapse.StockMGT.Repos.ItemRepo;
import com.synapse.StockMGT.Repos.JobItemRepo;
import com.synapse.StockMGT.Repos.JobNoteRepo;
import com.synapse.StockMGT.Repos.ReplacementNoteRepo;
import com.synapse.StockMGT.Util.WarrantyCal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobNoteRepo jobNoteRepo;
    private final WarrantyCal warrantyCal;
    private final ItemRepo itemRepo;
    private final JobItemRepo jobItemRepo;
    private final ReplacementNoteRepo replacementNoteRepo;

    public JobResDTO addJob(JobDTO job) throws Exception {
        JobNotes note = JobNotes.builder()
                .jobNumber(generateJobNumber(job))
                .jobDate(LocalDate.now())
                .jobType(job.getJobType())
                .invoicedDate(job.getInvoiceDate())
                .invoiceNumber(job.getInvoiceNumber())
                .status(JobStatus.PENDING)
                .build();
        note.setJobItems(job.getJobItems().stream().map(
                item -> JobItem.builder()
                        .description(item.getDescription())
                        .serial(item.getSerial())
                        .defectiveDetails(item.getDefectiveDetails())
                        .remainingSupplierWarranty(item.getRemainingSupplierWarranty())
                        .remainingSellerWarranty(item.getRemainingSellerWarranty())
                        .barCode(generateBarcodeUUID())
                        .isWarrantyClaimed(false)
                        .build()
        ).toList());
        for (JobItem item : note.getJobItems()) {
            item.setBarCodeImage(convertImageToBase64String(
                    generateBarcodeImage(item.getBarCode(),300,100),"png"));
        }
        JobNotes jobNote = jobNoteRepo.save(note);
        return JobResDTO.builder()
                                    .jobNumber(jobNote.getJobNumber())
                                    .jobDate(jobNote.getJobDate())
                                    .jobType(jobNote.getJobType())
                                    .jobDate(jobNote.getJobDate())
                                    .invoiceDate(jobNote.getInvoicedDate())
                                    .invoiceNumber(jobNote.getInvoiceNumber())
                                    .status(jobNote.getStatus())
                                    .jobItems(jobNote.getJobItems().stream()
                                            .map(item -> JobItemsDTO.builder()
                                                    .description(item.getDescription())
                                                    .serial(item.getSerial())
                                                    .defectiveDetails(item.getDefectiveDetails())
                                                    .barcodeImage(item.getBarCodeImage())
                                                    .build()).toList())
                                    .build();
    }

    private String generateBarcodeUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    private BufferedImage generateBarcodeImage(String barcodeText, int width, int height) throws WriterException {
        Code128Writer barcodeWriter = new Code128Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.CODE_128, width, height);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    private String convertImageToBase64String(BufferedImage image, String format) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        byte[] imageBytes = baos.toByteArray();
        baos.close();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    private String generateJobNumber(JobDTO job) {
        String input = job.getInvoiceNumber() + job.getInvoiceDate() + System.nanoTime();
        int hash = Math.abs(input.hashCode());
        String hashStr = String.valueOf(hash);

        if (hashStr.length() >= 6) {
            return hashStr.substring(hashStr.length() - 6);
        } else {
            return String.format("%06d", hash);
        }
    }

    public List<JobResDTO> getJobs() {
        return jobNoteRepo.findAll().stream().map(job ->
            JobResDTO.builder()
                    .jobNumber(job.getJobNumber())
                    .jobDate(job.getJobDate())
                    .jobType(job.getJobType())
                    .invoiceNumber(job.getInvoiceNumber())
                    .invoiceDate(job.getInvoicedDate())
                    .jobItems(job.getJobItems().stream().map(
                            item -> JobItemsDTO.builder()
                                    .description(item.getDescription())
                                    .serial(item.getSerial())
                                    .defectiveDetails(item.getDefectiveDetails())
                                    .remainingSupplierWarranty(item.getRemainingSupplierWarranty())
                                    .remainingSellerWarranty(item.getRemainingSellerWarranty())
                                    .barcodeImage(item.getBarCodeImage())
                                    .isWarrantyClaimed(item.isWarrantyClaimed())
                                    .replacedItem(ReplacedItemDTO.builder()
                                            .description(
                                                    item.getReplacedItem() != null && item.getReplacedItem().getDescription() != null
                                                            ? item.getReplacedItem().getDescription()
                                                            : null
                                            )
                                            .serial(item.getReplacedItem() != null ? item.getReplacedItem().getSerialNumber() : null)
                                            .build())
                                    .build()
                    ).toList())
                    .status(job.getStatus())
                    .build()).toList();
    }

    @Transactional
    public JobResDTO claimWarranty(JobDTO job) {
        JobNotes note = jobNoteRepo.findByJobNumber(job.getJobNumber())
                .orElseThrow(() -> new RuntimeException("Job number not found"));

        List<String> claimSerials = job.getClaimSerials() != null
                ? new ArrayList<>(job.getClaimSerials())
                : Collections.emptyList();

        List<JobItemsDTO> updatedJobItems = new ArrayList<>();

        for (JobItemsDTO item : job.getJobItems()) {
            if (item.isWarrantyClaimed()) {
                continue;
            }
            String replacingSerial = claimSerials.stream().findAny().orElse(null);
            if (replacingSerial == null) {
                break;
            }

            claimSerials.remove(replacingSerial);

            Item replacingItem = itemRepo.findBySerialNumber(replacingSerial)
                    .orElseThrow(() -> new RuntimeException("No replacing item found with serial number " + replacingSerial));

            JobItem jobItem = note.getJobItems().stream()
                    .filter(j -> j.getSerial().equalsIgnoreCase(item.getSerial()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Job item not found for serial " + item.getSerial()));

            jobItem.setReplacedItem(ReplacedItem.builder()
                    .description(replacingItem.getItemInfo().getItemDescription())
                    .serialNumber(replacingItem.getSerialNumber())
                    .build());
            jobItem.setWarrantyClaimed(true);

            jobItemRepo.save(jobItem);
            itemRepo.delete(replacingItem);

            updatedJobItems.add(JobItemsDTO.builder()
                    .description(item.getDescription())
                    .serial(item.getSerial())
                    .replacedItem(ReplacedItemDTO.builder()
                            .serial(replacingItem.getSerialNumber())
                            .description(replacingItem.getItemInfo().getItemDescription())
                            .build())
                    .build());
        }

        boolean allClaimed = note.getJobItems().stream()
                .allMatch(JobItem::isWarrantyClaimed);

        if (allClaimed) {
            note.setStatus(JobStatus.WARRANTY_CLAIMED);
            jobNoteRepo.save(note);
        }

        createRepNote(job, note);

        return JobResDTO.builder()
                .jobNumber(job.getJobNumber())
                .jobItems(updatedJobItems)
                .invoiceDate(job.getInvoiceDate())
                .invoiceNumber(job.getInvoiceNumber())
                .build();
    }


    private void createRepNote(JobDTO job, JobNotes note) {
        if(note.getReplacementNote() == null){
            ReplacementNote repNote = replacementNoteRepo.save(ReplacementNote.builder()
                            .repNumber(generateJobNumber(job))
                            .replacementDate(LocalDate.now())
                            .jobNotes(note)
                            .build());
            note.setReplacementNote(repNote);
            jobNoteRepo.save(note);
        }else {
            ReplacementNote repNote = replacementNoteRepo.findById(note.getReplacementNote().getReplacementNoteId())
                    .orElseThrow(() -> new RuntimeException("Replacement note id not found"));
            repNote.setJobNotes(note);
            replacementNoteRepo.save(repNote);
        }
    }

    public List<ReplacementNotesDTO> getRepNotes() {
        return replacementNoteRepo.findAll().stream()
                .flatMap(repNote ->
                        repNote.getJobNotes().getJobItems().stream()
                                .map(jobItem -> ReplacementNotesDTO.builder()
                                        .repNumber(repNote.getRepNumber())
                                        .replacementDate(repNote.getReplacementDate())
                                        .defectItemDescription(jobItem.getDescription())
                                        .defectItemSerial(jobItem.getSerial())
                                        .replacedItemDescription(jobItem.getReplacedItem().getDescription())
                                        .replacedItemSerial(jobItem.getReplacedItem().getSerialNumber())
                                        .build()
                                )
                )
                .toList();
    }

    public List<JobItemsDTO> getDefects() {
        return jobItemRepo.findAll().stream().map(jobItem ->JobItemsDTO.builder()
                .description(jobItem.getDescription())
                .serial(jobItem.getSerial())
                .defectiveDetails(jobItem.getDefectiveDetails())
                .barcodeImage(jobItem.getBarCodeImage())
                .isWarrantyClaimed(jobItem.isWarrantyClaimed())
                .remainingSupplierWarranty(jobItem.getRemainingSupplierWarranty())
                .remainingSellerWarranty(jobItem.getRemainingSellerWarranty())
                .build()).toList();
    }
}
