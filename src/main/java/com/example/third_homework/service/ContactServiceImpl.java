package com.example.third_homework.service;

import com.example.third_homework.Contact;
import com.example.third_homework.repository.ContactsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService{

    private final ContactsRepository contactsRepository;

    @Override
    public List<Contact> findAll() {
        log.debug("Call findAll in ContactServiceImpl");
        return contactsRepository.findAll();
    }

    @Override
    public Contact findById(Long id) {
        log.debug("Call findById in ContactServiceImpl");
        return contactsRepository.findById(id).orElseThrow();
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("Call save in ContactServiceImpl");
        return contactsRepository.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("Call update in ContactServiceImpl");
        return contactsRepository.update(contact);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Call deleteById in ContactServiceImpl");
        contactsRepository.deleteById(id);
    }
}
