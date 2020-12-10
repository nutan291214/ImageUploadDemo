package com.example.demo.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.model.*;
public interface FileRepository  extends JpaRepository<FileModel, Long>{
	
	public Optional<FileModel> findByName(String name);

}
