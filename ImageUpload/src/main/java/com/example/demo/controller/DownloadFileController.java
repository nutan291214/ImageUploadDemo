package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.FileModel;
import com.example.demo.model.View;
import com.example.demo.repository.FileRepository;
import com.fasterxml.jackson.annotation.JsonView;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class DownloadFileController {
	@Autowired
	  FileRepository fileRepository;
	 
	  /*
	   * List All Files
	   */
	    @JsonView(View.FileInfo.class)
	  @GetMapping("/api/file/all")
	  public List<FileModel> getListFiles() {
	    return fileRepository.findAll();
	  }
	  
	    /*
	     * Download Files
	     */
	  @GetMapping("/api/files/{id}")
	  public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
	    Optional<FileModel> fileOptional = fileRepository.findById(id);
	    
	    if(fileOptional.isPresent()) {
	      FileModel file = fileOptional.get();
	      return ResponseEntity.ok()
	          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
	          .body(file.getPic());  
	    }
	    
	    return ResponseEntity.status(404).body(null);
	  }
	  @GetMapping("/api/file/{name}")
	  public ResponseEntity<byte[]> getFileByName(@PathVariable String name) {
		    Optional<FileModel> fileOptional = fileRepository.findByName(name);
		    
		    if(fileOptional.isPresent()) {
		      FileModel file = fileOptional.get();
		      return ResponseEntity.ok()
		          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
		          .body(file.getPic());  
		    }
		    
		    return ResponseEntity.status(404).body(null);
		  }

}
