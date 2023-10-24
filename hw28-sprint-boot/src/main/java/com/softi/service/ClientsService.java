package com.softi.service;

import com.softi.dto.UserData;
import java.util.List;

public interface ClientsService {

    void save(UserData userDataModel);

    List<UserData> findAll();
}
