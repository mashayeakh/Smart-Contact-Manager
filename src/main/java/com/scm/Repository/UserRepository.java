package com.scm.Repository;

import com.scm.Entities.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <RegisteredUser, Long> {

    //getting email for login
//    @Query("select email from RegisteredUser where email=: email ")
//    RegisteredUser findByEmail(@Param("email")String email);

    RegisteredUser findByEmail(String email);

}
