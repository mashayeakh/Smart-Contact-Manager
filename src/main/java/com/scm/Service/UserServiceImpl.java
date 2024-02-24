package com.scm.Service;

import com.scm.Entities.Contact;
import com.scm.Entities.RegisteredUser;
import com.scm.Repository.ContactRepository;
import com.scm.Repository.UserRepository;
import com.scm.Service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ContactRepository contactRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           ContactRepository contactRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.contactRepository=contactRepository;
    }

    @Override
    public void saveUser(RegisteredUser rUser) {

//            System.out.println("Existing user"+rUser);
            rUser.setPassword(passwordEncoder.encode(rUser.getPassword()));
            userRepository.save(rUser);

    }

    @Override
    public void saveContact(Contact contact) {
        this.contactRepository.save(contact);
    }

    @Override
    public List<Contact> findAllContact() {
        return this.contactRepository.findAll();
    }

    @Override
    public void deleteUserById(long id) {
        this.contactRepository.deleteById(id);
    }

    @Override
    public void updateUser(Contact contact) {
        this.contactRepository.save(contact);
    }

//    @Override
//    public List<Contact> findContactsOrderedByPhoneNumber() {
//        return this.contactRepository.findAllByOrderByPhoneNumber();
//    }


}
