package com.wahak.controller;

import com.wahak.dto.ItemDto;
import com.wahak.service.StoreManagerService;
import com.wahak.service.StoreService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author krishna.meena
 */

@RestController
@RequestMapping("/store-manager")
public class StoreManagerController {

    @Autowired private StoreManagerService storeManagerService;

    @Autowired private StoreService storeService;


    @GetMapping("/common-items")
    public ResponseEntity<List<ItemDto>> getCommonItemItems(@RequestParam(required = false) String name,
                                                            @RequestParam(required = false, defaultValue = "0") int pageNo) {
        return ResponseEntity.ok(storeManagerService.getItems(name,pageNo));
    }

    @GetMapping("/store-items")
    public ResponseEntity<List<ItemDto>> getStoreItems(@RequestParam(required = false) String name,
                                                       @RequestParam(required = false,defaultValue = "0") int pageNo) {
        return ResponseEntity.ok(storeManagerService.getStoreItems(name,pageNo));
    }

    @PostMapping("/add-store-item/{storeId}")
    public ResponseEntity<ItemDto> createItemStore(@RequestBody ItemDto item,
                                                   @PathVariable("storeId") Integer storeId) {
        return ResponseEntity.ok(storeManagerService.createStoreItem(item,storeId));
    }

    @DeleteMapping("store-item/delete")
    public ResponseEntity<Boolean> deleteStoreItem(
            @RequestParam Integer itemId) {
        return ResponseEntity.ok(storeManagerService.deleteStoreItem(itemId));
    }

    @PatchMapping("store-item/update/{itemId}")
    public ResponseEntity<ItemDto> updateStoreItem(@RequestBody ItemDto item,
                                                   @PathVariable("itemId") Integer itemId) {
        return ResponseEntity.ok(storeManagerService.updateStoreItem(item,itemId));
    }


    @GetMapping("store/get-items")
    public ResponseEntity<List<ItemDto>> getStoreItem(@RequestParam Integer storeId) {
        return ResponseEntity.ok(storeService.getStoreItem(storeId));
    }



}
