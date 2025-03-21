package com.wahak.controller;

import com.wahak.dto.ChalakDto;
import com.wahak.dto.ItemDto;
import com.wahak.dto.SanchaalaakDTO;
import com.wahak.dto.StoreDto;
import com.wahak.entity.Sanchaalaak;
import com.wahak.service.ChalakService;
import com.wahak.service.SanchaalaakService;
import com.wahak.service.StoreService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author krishna.meena
 */

@RestController
@RequestMapping("/sanchaalaak")
public class SanchaalaakController {

    @Autowired private StoreService storeService;

    @Autowired private SanchaalaakService sanchaalaakService;

    @Autowired private ChalakService chalakService;


    @PostMapping("/createChalak")
    public ResponseEntity<ChalakDto> createChalak(@RequestBody @Valid ChalakDto chalak) {
        return ResponseEntity.ok(chalakService.create(chalak));
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<Boolean> validateOtp(@RequestBody ChalakDto optRequest) {
        return ResponseEntity.ok(chalakService.validateRegistrationOtp(optRequest));
    }


    @PostMapping("/disable/{chalakId}")
    public ResponseEntity<Boolean> disableChalak(@PathParam("chalakId") Integer chalakId){
        return ResponseEntity.ok(chalakService.disableChalak(chalakId));
    }

    @PostMapping("/block/{chalakId}")
    public ResponseEntity<Boolean> blockChalak(@PathParam("chalakId") Integer chalakId){
        return ResponseEntity.ok(chalakService.blockChalak(chalakId));
    }

    // TODO
    // move this to Sanchaalaak Controller
    @PostMapping("/unblock/{chalakId}")
    public ResponseEntity<Boolean> unBlockChalak(@PathParam("chalakId") Integer chalakId){
        return ResponseEntity.ok(chalakService.unBlockChalak(chalakId));
    }

    // TODO
    // move this to Sanchaalaak Controller
    @PostMapping("/enable/{chalakId}")
    public ResponseEntity<Boolean> enableChalak(@PathParam("chalakId") Integer chalakId){
        return ResponseEntity.ok(chalakService.enableChalak(chalakId));
    }

    @GetMapping("/get-details")
    public ResponseEntity<Sanchaalaak> getSanchalak() {
        return new ResponseEntity<>(sanchaalaakService.fetchSanchalak(), null, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Sanchaalaak> createSanchalak(@RequestBody SanchaalaakDTO sanchaalaak) {

        Sanchaalaak sanchaalaak1=new Sanchaalaak();
        BeanUtils.copyProperties(sanchaalaak, sanchaalaak1);
        return new ResponseEntity<>(sanchaalaakService.createSanchalak(sanchaalaak1), null, HttpStatus.OK);
    }





}
