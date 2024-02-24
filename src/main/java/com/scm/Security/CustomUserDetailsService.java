package com.scm.Security;

import com.scm.Entities.Contact;
import com.scm.Entities.RegisteredUser;
import com.scm.Repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println("CustomUserDetailsService is called with email\n\n: " + email);
        RegisteredUser user = this.userRepository.findByEmail(email);

        if (user != null) {
            System.out.println("User found: " + user.getEmail());
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    getAuthority(user.getContacts()));
        } else {
            System.out.println("User not found for email\n\n\n: " + email);
            throw new UsernameNotFoundException("Invalid email and password");
        }
        //return null;
    }

    //converting roles into collection
    private Collection<? extends GrantedAuthority> getAuthority(Collection<Contact> contacts) {
        return contacts.stream()
                .map(names -> new SimpleGrantedAuthority(names.getName()))
                .collect(Collectors.toList());
    }


}

