package org.example;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "contact_requests")
public class ContactRequest {
    @Id
    private String id;
    private String name;
    private String email;
    private String contactNo;
    private String services;
    private String companyName;
    private String message;
}