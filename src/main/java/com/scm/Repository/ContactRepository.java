package com.scm.Repository;

import com.scm.Entities.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    //pagination
    @Query("from Contact where rUser.id = :id")
    //pageable have 2  info - current page and no of contacts per page[2]
    Page<Contact> findContactBy_RegisteredUser(@Param("id") long id, Pageable pageable);

//     List<Contact> findAllByOrderByPhoneNumber();



}
