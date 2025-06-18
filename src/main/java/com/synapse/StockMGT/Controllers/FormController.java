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

    @GetMapping("/templates")
    public ResponseEntity<?>  getTemplates(){
        return ResponseEntity.ok(formService.getTemplates());
    }

    @GetMapping("/{formType}")
    public ResponseEntity<List<FieldDTO>> getCompanyFields(@PathVariable String formType){
        return ResponseEntity.ok(formService.getGenericFields(formType));
    }

    @PostMapping
    public ResponseEntity<List<FieldDTO>> addCompanyFields(@RequestBody FieldDTO field){
        return ResponseEntity.ok(formService.addGenericFields(field));
    }
}
