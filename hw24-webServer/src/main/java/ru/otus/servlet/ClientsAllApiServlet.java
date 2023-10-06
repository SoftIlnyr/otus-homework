package ru.otus.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ru.otus.crm.dao.ClientDao;
import ru.otus.crm.model.Client;

public class ClientsAllApiServlet extends HttpServlet {

    private final ClientDao clientDao;

    private final Gson gson;

    public ClientsAllApiServlet(ClientDao clientDao) {
        this.clientDao = clientDao;
        this.gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Client> clients = clientDao.findAll();

        List<UserDataModel> userDataModelList = new ArrayList<>();

        for (Client client : clients) {
            UserDataModel userDataModel = new UserDataModel();
            userDataModel.setUserName(client.getName());
            if (client.getAddress() != null) {
                userDataModel.setUserAddress(client.getAddress().getStreet());
            }
            if (!client.getPhones().isEmpty()) {
                userDataModel.setUserPhoneNumber(String.valueOf(client.getPhones().get(0).getNumber()));
            }
            userDataModelList.add(userDataModel);
        }

        resp.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = resp.getOutputStream();
        out.print(gson.toJson(userDataModelList));
    }
}
