package com.example.demo.Repository;

import com.example.demo.Entity.FileStorage;
import com.example.demo.Entity.FileStorageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileStorageRepository extends JpaRepository<FileStorage, Long> {
    FileStorage findByHashId(String hashId);

    List<FileStorage> findAllByFileStorageStatus(FileStorageStatus status);
}
