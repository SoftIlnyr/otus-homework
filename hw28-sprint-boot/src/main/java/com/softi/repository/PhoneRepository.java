package com.softi.repository;

import com.softi.model.Phone;
import org.springframework.data.repository.ListCrudRepository;

public interface PhoneRepository extends ListCrudRepository<Phone, Long> {

}
