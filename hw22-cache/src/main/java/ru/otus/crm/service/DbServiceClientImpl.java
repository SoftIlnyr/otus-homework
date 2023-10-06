package ru.otus.crm.service;

import cachehw.MyCache;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.crm.model.Client;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;

    private final MyCache<Long, Client> clientCache;

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.clientCache = new MyCache<>();
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                var savedClient = clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                return savedClient;
            }
            var savedClient = clientDataTemplate.update(session, clientCloned);
            clientCache.put(savedClient.getId(), savedClient);
            log.info("updated client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        Optional<Client> client = Optional.ofNullable(clientCache.get(id));
        if (client.isEmpty()) {
            client = transactionManager.doInReadOnlyTransaction(session -> {
                var clientOptional = clientDataTemplate.findById(session, id);
                log.info("client: {}", clientOptional);
                return clientOptional;
            });
            client.ifPresent(value -> clientCache.put(value.getId(), value));
        }
        return client;
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            clientList.forEach(client -> clientCache.put(client.getId(), client));
            return clientList;
        });
    }
}
