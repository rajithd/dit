package com.dit;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "staff")
public class Staff extends Person {
}
