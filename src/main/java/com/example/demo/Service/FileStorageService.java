package com.example.demo.Service;

import com.example.demo.Entity.FileStorage;
import com.example.demo.Entity.FileStorageStatus;
import com.example.demo.Repository.FileStorageRepository;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class FileStorageService {
    private final FileStorageRepository fileStorageRepository;

    @Value("${upload.folder}")
    private String uploadFolder;

    private final Hashids hashids;

    public FileStorageService(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
        this.hashids = new Hashids("demo", 6);
    }

    public void save(MultipartFile multipartFile){
        FileStorage fileStorage = new FileStorage();
        fileStorage.setName(multipartFile.getOriginalFilename());
        fileStorage.setExtension(getExt(multipartFile.getOriginalFilename()));
        fileStorage.setFileSize(multipartFile.getSize());
        fileStorage.setContentType(multipartFile.getContentType());
        fileStorage.setFileStorageStatus(FileStorageStatus.DRAFT);
        fileStorageRepository.save(fileStorage);
        fileStorage.setHashId(hashids.encode(fileStorage.getId()));
        Date now = new Date();
        String path = String.format("uploads/%d/%d/%d",
                                    1900 + now.getYear(),
                                    now.getMonth() + 1,
                                    now.getDate());
        File dir = new File(String.format("%s/%s", uploadFolder, path));
        if(!dir.exists()) dir.mkdirs();
        String name = String.format("%s.%s", fileStorage.getHashId(), fileStorage.getExtension());
        fileStorage.setUploadPath(String.format("%s/%s", path, name));
        fileStorageRepository.save(fileStorage);
        File file = new File(dir.getAbsoluteFile(), name);
        try{
            multipartFile.transferTo(file);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private String getExt(String fileName){
        String ext = null;
        if(fileName != null && !fileName.isEmpty()){
            int dot = fileName.lastIndexOf('.');
            if(dot > 0 && dot <= fileName.length() - 2){
                ext = fileName.substring(dot + 1);
            }
        }
        return ext;
    }

    @Transactional(readOnly = true)
    public FileStorage findByHashId(String hashId){
        return fileStorageRepository.findByHashId(hashId);
    }

    public void delete(String hashId){
        FileStorage fileStorage = findByHashId(hashId);
        File file = new File(String.format("%s/%s", uploadFolder, fileStorage.getUploadPath()));
        if(file.delete()){
            fileStorageRepository.delete(fileStorage);
        }
    }

    @Scheduled(cron = "0 10 14 * * *")
    public void cleanDruft(){
        List<FileStorage> fileStorageList = fileStorageRepository.findAllByFileStorageStatus(FileStorageStatus.DRAFT);
        for(FileStorage fileStorage: fileStorageList){
            delete(fileStorage.getHashId());
        }
    }
}
