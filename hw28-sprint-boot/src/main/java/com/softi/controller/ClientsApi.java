package com.softi.controller;

import com.softi.dto.UserData;
import com.softi.service.ClientsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class ClientsApi {

    private final ClientsService clientsService;

    @PostMapping(value = "/api/users/create", consumes = "application/json")
    public void createClient(@RequestBody UserData userData) {
        clientsService.save(userData);
    }

    @ResponseBody
    @GetMapping("/api/users")
    public List<UserData> getAllClients() {
        return clientsService.findAll();
    }

}
