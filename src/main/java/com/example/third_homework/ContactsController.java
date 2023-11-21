package com.example.third_homework;

import com.example.third_homework.repository.ContactsRepository;
import com.example.third_homework.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class ContactsController {


    private final ContactService contactService;


    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("contacts", contactService.findAll());
        return "index";
    }


    @GetMapping("/contact/form")
    public String showContactForm(Model model){
        model.addAttribute("contact", new Contact());
        return "form";
    }

    @GetMapping("/contact/form/{contactId}")
    public String showEditForm(@PathVariable Long contactId, Model model){
        Contact contact = contactService.findById(contactId);
        model.addAttribute("contact", contact);
        return "form";
    }

    @GetMapping("/contact/delete/{contactId}")
    public String deleteContact(@PathVariable Long contactId) {
        contactService.deleteById(contactId);
        return "redirect:/";
    }

    @PostMapping("/contact/form")
    public String saveContact(@ModelAttribute Contact contact) {
        if (contact.getId() != null) {
            contactService.update(contact);
        } else {
            contactService.save(contact);
        }
        return "redirect:/";
    }
}
