package com.alexxwhite.jdscoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/v1/")
public class RestController {

    @Autowired
    private FileController fileController;

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable String id) {
        fileController.openLocalFile(id);
        return ResponseEntity.ok().build();
    }

}

