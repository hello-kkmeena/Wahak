package com.wahak.service;

import com.wahak.dto.ItemDto;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author krishna.meena
 */
public interface ItemService {

    ItemDto createItem(ItemDto item, MultipartFile image) throws IOException;

    ItemDto createItemStore(@Valid ItemDto item);
}
