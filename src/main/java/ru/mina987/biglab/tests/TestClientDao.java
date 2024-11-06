package ru.mina987.biglab.tests;

import ru.mina987.biglab.dao.ClientDao;
import ru.mina987.biglab.domains.Client;

import java.util.Collection;

public class TestClientDao {
    public static void main(String[] args) {
        ClientDao clientDao = null;
        try {
            clientDao = new ClientDao();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Client client = clientDao.findById(1L);
        System.out.println(client);
//        delete(clientDao);
//        Collection<Client> al = selectall(clientDao);
//        for (Client cl :al
//             ) {
//            System.out.println(cl);
//        }
    }
    static void insert(ClientDao clientDao){
        clientDao.save(new Client("Валуев","Георгий","Леонидович","8(444)456-78-90"));
    }
    static void update(ClientDao clientDao){clientDao.update(new Client(16,"Бабуев","Георгий","Леонидович","8(444)456-78-90"));}
    static void delete(ClientDao clientDao){long j = 16; clientDao.deleteById(j);}
    static Collection<Client> selectall(ClientDao clientDao){return  clientDao.findALl();}

}
