package com.dit;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "managers")
public class Manager extends Person {
}
