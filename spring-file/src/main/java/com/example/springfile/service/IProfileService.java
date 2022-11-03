package com.example.springfile.service;

import com.example.springfile.model.entity.Profile;
import com.example.springfile.model.entity.User;
import com.example.springfile.model.request.ProfileRequest;
import com.example.springfile.model.request.UserRequest;
import com.example.springfile.model.response.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface IProfileService {
    Profile getAccount(Long id);

    List<Profile> getAll();

    List<User> getAlll();

    Profile create(ProfileRequest request);

    User cre(UserRequest userRequest);

    UploadFileResponse upload(MultipartFile file, Long id) throws  Exception;



}
