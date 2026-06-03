package service;

import model.Client;
import repository.ClientRepository;

import java.util.List;

public class ClientService {
    private ClientRepository clientRepo = ClientRepository.getInstance();

    public void registerClient(Client client) {
        List<Client> existing = clientRepo.readAll();
        if (existing.stream().noneMatch(c -> c.getCnp().equals(client.getCnp()))) {
            clientRepo.create(client);
            AuditService.getInstance().logAction("registerClient");
            System.out.println("Inregistrat: " + client.getName());
        } else {
            System.out.println("Exista deja: " + client.getName());
        }
    }

    public List<Client> getAllClients() {
        return clientRepo.readAll();
    }
}
