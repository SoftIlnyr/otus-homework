package com.softi.controller;

import com.softi.dto.UserData;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class AdminController {

    private final ClientsApi clientsApi;

    @GetMapping("/admin/users")
    public String adminPage() {
        return "admin-page";
    }

    @GetMapping("/admin/users/getAll")
    public String getAllClients(Model model) {
        List<UserData> clients = clientsApi.getAllClients();
        model.addAttribute("clients", clients);

        return "table-fragment.xml";
    }

}
