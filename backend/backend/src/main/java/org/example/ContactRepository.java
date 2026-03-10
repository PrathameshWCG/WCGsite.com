package org.example;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends MongoRepository<ContactRequest, String> {
    // Spring Boot automatically implements save(), findAll(), etc.
}