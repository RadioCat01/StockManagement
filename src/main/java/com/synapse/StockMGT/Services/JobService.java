package com.synapse.StockMGT.Services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.synapse.StockMGT.DTOs.JobDTO;
import com.synapse.StockMGT.DTOs.JobItemsDTO;
import com.synapse.StockMGT.DTOs.JobResDTO;
import com.synapse.StockMGT.Models.JobItem;
import com.synapse.StockMGT.Models.JobNotes;
import com.synapse.StockMGT.Repos.JobNoteRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobNoteRepo jobNoteRepo;

    public String addJob(JobDTO job) throws Exception {
        JobNotes note = JobNotes.builder()
                .jobNumber(generateJobNumber(job))
                .jobDate(LocalDate.now())
                .jobType(job.getJobType())
                .invoicedDate(job.getInvoiceDate())
                .invoiceNumber(job.getInvoiceNumber())
                .build();

        note.setJobItems(job.getJobItems().stream().map(
                item -> JobItem.builder()
                        .description(item.getDescription())
                        .serial(item.getSerial())
                        .defectiveDetails(item.getDefectiveDetails())
                        .remainingWarranty(item.getRemainingWarranty())
                        .barCode(generateBarcodeUUID())
                        .build()
        ).toList());
        for (JobItem item : note.getJobItems()) {
            item.setBarCodeImage(convertImageToBase64String(
                    generateBarcodeImage(item.getBarCode(),300,100),"png"));
        }
        jobNoteRepo.save(note);
        return note.getJobNumber();
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
        String input = job.getInvoiceNumber() + job.getInvoiceDate();
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
                                    .remainingWarranty(item.getRemainingWarranty())
                                    .barcodeImage(item.getBarCodeImage())
                                    .build()
                    ).toList())
                    .build()).toList();
    }
}
