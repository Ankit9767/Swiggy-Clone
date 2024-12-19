package com.example.Swiggy.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Swiggy.Domain.OrgUser;
import com.example.Swiggy.Repository.OrgUserRepository;

@Service
public class OrgUserService {
		
    private final Logger log = LoggerFactory.getLogger(OrgUserService.class);

	    private OrgUserRepository orgUserRepository;
	    
	    private final PasswordEncoder passwordEncoder;
	    
	    public OrgUserService(OrgUserRepository orgUserRepository, PasswordEncoder passwordEncoder) {

	        this.orgUserRepository = orgUserRepository;
	        this.passwordEncoder = passwordEncoder;
	    }
	    
	    public OrgUser registerUser(OrgUser user) {
	        log.info("Attempting to register user with username: {}", user.getUserName());

	        if (orgUserRepository.findByUserName(user.getUserName()).isPresent()) {
	            log.warn("Registration failed: Username '{}' already exists", user.getUserName());
	            throw new RuntimeException("Username already exists: " + user.getUserName());
	        }

	        if (orgUserRepository.findByEmail(user.getEmail()).isPresent()) {
	            log.warn("Registration failed: Email '{}' already exists", user.getEmail());
	            throw new RuntimeException("Email already exists: " + user.getEmail());
	        }

	        String encodedPassword = passwordEncoder.encode(user.getPassword());
	        user.setPassword(encodedPassword);

	        OrgUser savedUser = orgUserRepository.save(user);
	        log.info("User with username '{}' registered successfully.", user.getUserName());

	        return savedUser;
	    }

	    public OrgUser saveUser(OrgUser user) {
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        
	        log.info("Saving user with encrypted password {}",passwordEncoder.encode(user.getPassword()));
	        return orgUserRepository.save(user);
	    }

	    public List<OrgUser> getAllUsers() {
	        return orgUserRepository.findAll();
	    }

	    public Optional<OrgUser> getUserById(Long id) {
	        log.info("Fetching user with ID: {}", id);
	        return orgUserRepository.findById(id);
	    }

	    public Optional<OrgUser> getUserByUserName(String userName) {
	        log.info("Fetching user with username: {}", userName);
	        return orgUserRepository.findByUserName(userName);
	    }	
	    
	    public Optional<OrgUser> getUserByEmail(String email) {
	        log.info("Fetching user with email: {}", email);
	        return orgUserRepository.findByEmail(email);
	    }

	    public OrgUser updateUser(Long id, OrgUser updatedUser) {
	        return orgUserRepository.findById(id).map(user -> {
	            user.setFirstName(updatedUser.getFirstName());
	            user.setLastName(updatedUser.getLastName());
	            user.setUserName(updatedUser.getUserName());
	            user.setEmail(updatedUser.getEmail());
	            user.setPhoneNumber(updatedUser.getPhoneNumber());
	            user.setAddress(updatedUser.getAddress());
	            user.setCity(updatedUser.getCity());
	            return orgUserRepository.save(user);
	        }).orElseThrow(() -> new RuntimeException("User not found"));
	    }

	    public void deleteUser(Long id) {
	    	orgUserRepository.deleteById(id);
	    }
	}

