package com.wahak.controller;

import com.wahak.dto.ChalakDto;
import com.wahak.service.ChalakService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author krishna.meena
 */
@RestController
@RequestMapping("/chalak")
public class ChalakController {

    private final ChalakService chalakService;

    public ChalakController(ChalakService chalakService) {
        this.chalakService = chalakService;
    }

    @PostMapping("/create")
    public ResponseEntity<ChalakDto> createChalak(@RequestBody @Valid ChalakDto chalak) {
        return ResponseEntity.ok(chalakService.create(chalak));
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<Boolean> validateOtp(@RequestBody @Valid ChalakDto optRequest) {
        return ResponseEntity.ok(chalakService.validateRegistrationOtp(optRequest));
    }

    @PostMapping("/enable/{chalakId}")
    public ResponseEntity<Boolean> enableChalak(@PathParam("chalakId") Integer chalakId){
        return ResponseEntity.ok(chalakService.enableChalak(chalakId));
    }

    @PostMapping("/disable/{chalakId}")
    public ResponseEntity<Boolean> disableChalak(@PathParam("chalakId") Integer chalakId){
        return ResponseEntity.ok(chalakService.disableChalak(chalakId));
    }

    @PostMapping("/block/{chalakId}")
    public ResponseEntity<Boolean> blockChalak(@PathParam("chalakId") Integer chalakId){
        return ResponseEntity.ok(chalakService.blockChalak(chalakId));
    }

    @PostMapping("/unblock/{chalakId}")
    public ResponseEntity<Boolean> unBlockChalak(@PathParam("chalakId") Integer chalakId){
        return ResponseEntity.ok(chalakService.unBlockChalak(chalakId));
    }


}
