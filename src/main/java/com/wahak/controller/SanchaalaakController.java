package com.wahak.controller;

import com.wahak.dto.ChalakDto;
import com.wahak.dto.SanchaalaakDTO;
import com.wahak.entity.Sanchaalaak;
import com.wahak.service.ChalakService;
import com.wahak.service.SanchaalaakService;
import com.wahak.service.StoreService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Bool;
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

    @GetMapping("/get-chalak")
    public ResponseEntity<List<ChalakDto>> getChalak(@RequestParam(required = false) String mobile,
                                                     @RequestParam(required = false, defaultValue = "0") int pageNo,
                                                     @RequestParam(required = false) Boolean isActive,
                                                     @RequestParam(required = false) Boolean isBlocked) {
        return ResponseEntity.ok(sanchaalaakService.getChalak(mobile,pageNo,isActive,isBlocked));
    }

    @PostMapping("/createChalak")
    public ResponseEntity<ChalakDto> createChalak(@RequestBody @Valid ChalakDto chalak) {
        return ResponseEntity.ok(chalakService.create(chalak));
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<Boolean> validateOtp(@RequestBody ChalakDto optRequest) {
        return ResponseEntity.ok(chalakService.validateRegistrationOtp(optRequest));
    }




    @PostMapping("/block/{chalakId}")
    public ResponseEntity<Boolean> blockChalak(@PathVariable("chalakId") Integer chalakId){
        return ResponseEntity.ok(chalakService.blockChalak(chalakId));
    }


    @PostMapping("/unblock/{chalakId}")
    public ResponseEntity<Boolean> unBlockChalak(@PathVariable("chalakId") Integer chalakId){
        return ResponseEntity.ok(chalakService.unBlockChalak(chalakId));
    }

    @PostMapping("/disable/{chalakId}")
    public ResponseEntity<Boolean> disableChalak(@PathVariable("chalakId") Integer chalakId){
        return ResponseEntity.ok(chalakService.disableChalak(chalakId));
    }

    @PostMapping("/enable/{id}")
    public ResponseEntity<Boolean> enableChalak(@PathVariable("id") Integer id){
        return ResponseEntity.ok(chalakService.enableChalak(id));
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
