package com.wahak.controller;

import com.wahak.dto.ConstantDto;
import com.wahak.service.AppService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author krishna.meena
 */

@RestController
@RequestMapping("/app")
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/get-category/{cityId}")
    public ResponseEntity<List<ConstantDto>> getCategory(@PathParam("cityId") Integer cityId) {
        return ResponseEntity.ok(appService.getAllCategory(cityId));
    }
}
