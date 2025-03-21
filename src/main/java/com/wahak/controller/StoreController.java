package com.wahak.controller;

import com.wahak.dto.StoreDto;
import com.wahak.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author krishna.meena
 */

@RestController
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/create")
    public ResponseEntity<StoreDto> createStore(@RequestBody @Valid StoreDto store) {
        return ResponseEntity.ok(storeService.createStore(store));
    }

    @PostMapping("/update")
    public ResponseEntity<StoreDto> updateStore() {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteStore(@RequestParam Integer storeId) {
        return ResponseEntity.ok(storeService.deleteStore(storeId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<StoreDto>> getAllStores() {
        return ResponseEntity.ok(storeService.getAllStores());
    }
}
