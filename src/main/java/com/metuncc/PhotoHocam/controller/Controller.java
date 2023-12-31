package com.metuncc.PhotoHocam.controller;


import com.metuncc.PhotoHocam.controller.request.UserRequest;
import com.metuncc.PhotoHocam.controller.response.ImageListDTOListResponse;
import com.metuncc.PhotoHocam.controller.response.LoginResponse;
import com.metuncc.PhotoHocam.controller.response.UserDTOListResponse;
import com.metuncc.PhotoHocam.security.JwtProvider;
import com.metuncc.PhotoHocam.service.PhotoHocamService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

@RestController
@RequestMapping(value ="/api", produces = "application/json;charset=UTF-8")
public class Controller {
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtTokenProvider;
    private PhotoHocamService photoHocamService;

    public Controller(AuthenticationManager authenticationManager, JwtProvider jwtTokenProvider, PhotoHocamService photoHocamService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.photoHocamService = photoHocamService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserRequest userRequest){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userRequest.getUsername(),
                        userRequest.getPass());
        Authentication authentication = authenticationManager
                .authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token =  jwtTokenProvider.generateJwtToken(authentication);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwt(token);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody UserRequest userRequest){
       try{
           photoHocamService.createUser(userRequest);
           return new ResponseEntity<>(true,HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);       }
    }


    @PostMapping("/user/addFriend")
    public ResponseEntity<Boolean> addFriend(@RequestParam Long id){
        photoHocamService.sendFriendRequest(id);
        return new ResponseEntity<>(true,HttpStatus.OK);
    }
    @PostMapping("/user/approveFriendRequest")
    public ResponseEntity<Boolean> approveFriendRequest(@RequestParam Long id){
        photoHocamService.approveFriendRequest(id);
        return new ResponseEntity<>(true,HttpStatus.OK);
    }
    @PostMapping("/user/sendImage")
    public ResponseEntity<Boolean> sendImage(@RequestBody MultipartFile file,
                                             @RequestParam Long userid){
        photoHocamService.sendImage(file, userid);
        return new ResponseEntity<>(true,HttpStatus.OK);
    }
    @GetMapping("/user/getfriends")
    public ResponseEntity<UserDTOListResponse> getfriends(){
        UserDTOListResponse result = new UserDTOListResponse();
        result.setUserDTOList(photoHocamService.getFriendList());
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @GetMapping("/user/getunfriends")
    public ResponseEntity<UserDTOListResponse> getunfriends(){
        UserDTOListResponse result = new UserDTOListResponse();
        result.setUserDTOList(photoHocamService.getUnfriendList());
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @GetMapping("/user/getFriendRequestList")
    public ResponseEntity<UserDTOListResponse> getFriendRequestList(){
        UserDTOListResponse result = new UserDTOListResponse();
        result.setUserDTOList(photoHocamService.getFriendRequestList());
        return new ResponseEntity<>(result,HttpStatus.OK);
    }


    @DeleteMapping("/deletePhoto")
    public ResponseEntity<Boolean> deleteImage(@RequestParam Long id){
        photoHocamService.deleteImage(id);
        return new ResponseEntity<>(true,HttpStatus.OK);
    }

    @GetMapping("/user/getImagelist")
    public ResponseEntity<ImageListDTOListResponse> getImagelist(){
        ImageListDTOListResponse result = new ImageListDTOListResponse();
        result.setList(photoHocamService.getImagelist());
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
