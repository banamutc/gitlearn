package com.example.springfile.service;

import com.example.springfile.model.entity.Profile;
import com.example.springfile.model.entity.User;
import com.example.springfile.model.request.ProfileRequest;
import com.example.springfile.model.request.UserRequest;
import com.example.springfile.model.response.UploadFileResponse;
import com.example.springfile.repository.ProfileRepository;
import com.example.springfile.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
public class ProfileServiceImpl implements IProfileService {
    @Autowired
    private ProfileRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Profile getAccount(Long id) {
        return null;
    }

    @Override
    public List<Profile> getAll() {
        return repository.findAll();
    }

    @Override
    public List<User> getAlll() {
        return userRepository.findAll();
    }

    @Override
    public Profile create(ProfileRequest request) {
        Profile profile = new Profile();
        profile.setFullName(request.getFullName());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setAvatarUrl(request.getAvatarUrl());
        profile.setEmail(request.getEmail());
        profile.setUserId(request.getUserId());
        profile.setCreatedAt(LocalDateTime.now());
        profile.setUpdatedAt(LocalDateTime.now());
        repository.save(profile);
        return profile;
    }

    @Override
    public User cre(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

    private  final ObjectMapper mapper = new ObjectMapper();
    @Value("${nbnam.file.url}")
    private String nbnamLocation;
    @Override
    public UploadFileResponse upload(MultipartFile file, Long id) throws Exception {
        if (file == null || file.getOriginalFilename() == null) {
            throw new Exception("file ko null");
        }
        String fileName = StringUtils.cleanPath(Integer.valueOf(LocalDateTime.now().getNano()).toString())
                + ".png";
        try {
            if(fileName.contains("..")) {
                throw new Exception("Sorry! ko tìm thấy loại file");
            }
            Path dir = Paths.get(nbnamLocation);
            Path targetLocation = dir.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            Profile newFile = repository.getReferenceById(id);
            newFile.setAvatarUrl(nbnamLocation + fileName);
            repository.save(newFile);
            return mapper.convertValue(newFile, UploadFileResponse.class);
        }
        catch (IOException ex) {
            throw new Exception("ko thể gì đó" + fileName + ". Làm ơn try again",ex);
        }
    }
}
