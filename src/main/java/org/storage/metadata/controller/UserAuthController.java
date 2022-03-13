package org.storage.metadata.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.storage.metadata.service.UserAuthService;
import org.storage.metadata.util.ResponseUtil;
import org.storage.metadata.model.orchestrator.dto.LoginDTO;
import org.storage.metadata.model.orchestrator.dto.SignUpDTO;

@RestController
@RequestMapping("/api/auth")
public class UserAuthController {
    private final UserAuthService userAuthService;

    private final ResponseUtil responseUtil;

    UserAuthController(UserAuthService userAuthService, ResponseUtil responseUtil) {
        this.userAuthService = userAuthService;
        this.responseUtil = responseUtil;
    }

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDto){
        UserAuthService.USER_AUTH_ACTION action = userAuthService.authenticateUser(loginDto);
        String message = responseUtil.getResponseMessage(action);
        HttpStatus httpStatus = action == UserAuthService.USER_AUTH_ACTION.SIGNIN_SUCCESS ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(message, httpStatus);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDto) {
        UserAuthService.USER_AUTH_ACTION action = userAuthService.registerUser(signUpDto);
        String message = responseUtil.getResponseMessage(action);
        HttpStatus httpStatus = action != UserAuthService.USER_AUTH_ACTION.REGISTER_SUCCESSFUL ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
        return new ResponseEntity<>(message, httpStatus);
    }
}
