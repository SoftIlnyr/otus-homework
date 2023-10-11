package ru.otus.crm.dao;

import java.util.List;
import java.util.Optional;
import ru.otus.crm.model.Client;

public interface ClientDao {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();
}
