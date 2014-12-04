package com.dit;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "owners")
public class Owner extends Person {
}
