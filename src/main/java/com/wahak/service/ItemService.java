package com.wahak.service;

import com.wahak.dto.ItemDto;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author krishna.meena
 */
public interface ItemService {

    ItemDto createItem(ItemDto item, MultipartFile image) throws IOException;

    ItemDto createItemStore(@Valid ItemDto item);

    List<ItemDto> getItemStore(@Valid String name, Integer cityId, Integer addressStoreId, Integer pageNo);
}
