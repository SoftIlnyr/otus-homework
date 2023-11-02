package com.softi.repository;

import com.softi.model.Client;
import org.springframework.data.repository.ListCrudRepository;

public interface ClientsRepository extends ListCrudRepository<Client, Long> {

}
