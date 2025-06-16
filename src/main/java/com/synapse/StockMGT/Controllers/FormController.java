package com.synapse.StockMGT.Controllers;

import com.synapse.StockMGT.DTOs.FormDTOs.FieldDTO;
import com.synapse.StockMGT.Configs.TemplatesInitializerConfig;
import com.synapse.StockMGT.Services.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
public class FormController {
    private final FormService formService;

    @GetMapping("/company")
    public ResponseEntity<List<FieldDTO>> getCompanyFields(){
        return ResponseEntity.ok(formService.getCompanyForm());
    }

    @PostMapping("/company")
    public ResponseEntity<List<FieldDTO>> getCompanyFields(@RequestBody FieldDTO field){
        return ResponseEntity.ok(formService.addCompanyField(field));
    }
}
