package com.wahak.controller;

import com.wahak.dto.ItemDto;
import com.wahak.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author krishna.meena
 */
@RestController
@RequestMapping("item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping(value="create", consumes = "multipart/form-data")
    public ResponseEntity<ItemDto> createItem(@RequestPart("data")ItemDto item,
                                              @RequestPart("image") MultipartFile image)
            throws IOException {
        return ResponseEntity.ok(itemService.createItem(item,image));
    }

   @PostMapping("/add-store-item")
    public ResponseEntity<ItemDto> createItemStore(@RequestBody @Valid ItemDto item) {
       return ResponseEntity.ok(itemService.createItemStore(item));
   }


   @GetMapping("/store-items")
    public ResponseEntity<List<ItemDto>> getItemStore(@RequestParam @Valid String name,
                                     @RequestParam(required = false,defaultValue = "0") Integer pageNo,
                                     @RequestParam(required = false) Integer cityId,
                                     @RequestParam(required = false) Integer addressStoreId // store Id
   ) {
       return ResponseEntity.ok(itemService.getItemStore(name,cityId,addressStoreId,pageNo));
   }
}
