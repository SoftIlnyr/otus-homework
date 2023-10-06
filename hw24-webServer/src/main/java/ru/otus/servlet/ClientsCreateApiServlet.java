package ru.otus.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import ru.otus.crm.dao.ClientDao;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

@SuppressWarnings({"squid:S1948"})
public class ClientsCreateApiServlet extends HttpServlet {

    private final ClientDao clientDao;

    private final Gson gson;

    public ClientsCreateApiServlet(ClientDao clientDao) {
        this.clientDao = clientDao;
        this.gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{

        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        UserDataModel userDataModel = gson.fromJson(requestBody, UserDataModel.class);

        Client client = new Client(userDataModel.getUserName());

        Address address = new Address();
        address.setStreet(userDataModel.getUserAddress());
        client.setAddress(address);

        Phone phone = new Phone();
        phone.setNumber(userDataModel.getUserPhoneNumber());
        client.getPhones().add(phone);

        clientDao.saveClient(client);
    }
}
