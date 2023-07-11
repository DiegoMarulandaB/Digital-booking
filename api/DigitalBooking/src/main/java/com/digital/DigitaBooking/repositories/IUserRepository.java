package com.digital.DigitaBooking.repositories;

import com.digital.DigitaBooking.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    @Query("from User u where u.userName =:userName")
    User getFirstByEmail(@Param("userName") String userName);

    // La consulta busca un objeto User en la base de datos donde el atributo userName
    // sea igual al valor proporcionado como parámetro.

}
