package com.scm.Service;

import com.scm.Entities.Contact;
import com.scm.Entities.RegisteredUser;

import java.util.List;

public interface UserService {

    void saveUser(RegisteredUser rUser);

    void saveContact(Contact contact);

    List<Contact> findAllContact();

    void deleteUserById(long id);

    void updateUser(Contact contact);

//    List<Contact> findContactsOrderedByPhoneNumber();

}
