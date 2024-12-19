package com.example.Swiggy.Resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Swiggy.Domain.OrgUser;
import com.example.Swiggy.Service.OrgUserService;

@RestController
@RequestMapping("/api/users")
public class OrgUserController {
    @Autowired
    private OrgUserService orgUserService;

    @PostMapping
    public ResponseEntity<OrgUser> addUser(@RequestBody OrgUser user) {
        return new ResponseEntity<>(orgUserService.saveUser(user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrgUser>> getAllUsers() {
        return new ResponseEntity<>(orgUserService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrgUser> getUserById(@PathVariable Long id) {
        return orgUserService.getUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrgUser> updateUser(@PathVariable Long id, @RequestBody OrgUser user) {
        return new ResponseEntity<>(orgUserService.updateUser(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    	orgUserService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

