package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactFormService {

    @Autowired
    private ContactFormRepository repository;

    public ContactForm saveContactForm(ContactForm form) {
        return repository.save(form);
    }
}
