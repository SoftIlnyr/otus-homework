package com.softi.service;

import com.softi.dto.UserData;
import com.softi.model.Address;
import com.softi.model.Client;
import com.softi.model.Phone;
import com.softi.repository.AddressRepository;
import com.softi.repository.ClientsRepository;
import com.softi.repository.PhoneRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientsServiceImpl implements ClientsService {

    private final ClientsRepository clientsRepository;
    private final PhoneRepository phoneRepository;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public void save(UserData userDataModel) {
        Client client = new Client(userDataModel.getUserName());
        Client savedClient = clientsRepository.save(client);
        Address address = new Address(userDataModel.getUserAddress(), savedClient.getId());
        addressRepository.save(address);
        Phone phone = new Phone(userDataModel.getUserPhoneNumber(), savedClient.getId());
        phoneRepository.save(phone);
    }

    @Override
    public List<UserData> findAll() {
        List<UserData> userDataModelList = new ArrayList<>();
        for (Client client : clientsRepository.findAll()) {
            UserData userDataModel = new UserData();
            userDataModelList.add(userDataModel);

            userDataModel.setUserId(client.getId());
            userDataModel.setUserName(client.getName());
            Optional.ofNullable(client.getAddress())
                    .ifPresent(address -> userDataModel.setUserAddress(address.getStreet()));
            Optional.ofNullable(client.getPhones()).map(phones -> phones.get(0))
                    .ifPresent(phone -> userDataModel.setUserPhoneNumber(phone.getNumber()));
        }
        return userDataModelList;
    }
}
