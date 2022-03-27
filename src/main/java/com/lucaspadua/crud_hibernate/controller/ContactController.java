package com.lucaspadua.crud_hibernate.controller;

import com.lucaspadua.crud_hibernate.entities.Contact;
import com.lucaspadua.crud_hibernate.entities.ContactRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/contact"})
public class ContactController {
    private ContactRepository repository;

    ContactController(ContactRepository contactRepository){
        this.repository = contactRepository;
    }

    //Métodos do CRUD

    @GetMapping("")
    public List contactFindAll(){
        return repository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity contactFindById(@PathVariable Long id){
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public Contact contactCreate(@RequestBody Contact contact){
        return repository.save(contact);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity contactUpdate(@PathVariable("id") Long id,
                                        @RequestBody Contact contact){
        return repository.findById(id)
                .map(record -> {record.setName(contact.getName());
                                record.setEmail(contact.getEmail());
                                record.setPhone(contact.getPhone());
                                Contact update = repository.save(record);
                                return ResponseEntity.ok().body(update);}).
                orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> contactDelete(@PathVariable Long id){
        return repository.findById(id)
                .map(record -> {repository.deleteById(id);
                                return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
