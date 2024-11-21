package org.limir.utility;

import com.google.gson.Gson;
import org.limir.enums.ResponseStatus;
import org.limir.models.dto.CarDTO;
import org.limir.models.dto.CompanyDTO;
import org.limir.models.dto.UserDTO;
import org.limir.models.entities.Car;
import org.limir.models.entities.Company;
import org.limir.models.entities.Person;
import org.limir.models.entities.User;
import org.limir.models.tcp.Request;
import org.limir.models.tcp.Response;
import org.limir.services.CarService;
import org.limir.services.CompanyService;
import org.limir.services.PersonService;
import org.limir.services.UserService;
import org.limir.services.servicesImpl.CarServiceImpl;
import org.limir.services.servicesImpl.CompanyServiceImpl;
import org.limir.services.servicesImpl.PersonServiceImpl;
import org.limir.services.servicesImpl.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientThread implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Gson gson;
    private Request request;
    private Response response;

    private PersonService personService = new PersonServiceImpl();
    private UserService userService = new UserServiceImpl();
    private CarService carService = new CarServiceImpl();
    private CompanyService companyService = new CompanyServiceImpl();

    public ClientThread(Socket clientSocket) throws IOException {
        response = new Response();
        request = new Request();
        this.clientSocket = clientSocket;
        gson = new Gson();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            while (clientSocket.isConnected()) {
                String message = in.readLine();

                request = gson.fromJson(message, Request.class);

                switch (request.getRequestType()) {
                    case REGISTER: {
                        Person person = gson.fromJson(request.getRequestMessage(), Person.class);
                        person.getUsers().forEach(user -> user.setPerson(person));
                        personService.addPerson(person);
                        break;
                    }
                    case LOGIN: {
                        User user = gson.fromJson(request.getRequestMessage(), User.class);

                        User foundUser = userService
                                .showUsers()
                                .stream()
                                .filter(x -> x.getUsername().equalsIgnoreCase(user.getUsername()) &&
                                        x.getPassword().equals(user.getPassword()))
                                .findFirst()
                                .orElse(null);

                        if (foundUser != null) {
                            UserDTO userDTO = foundUser.toDTO();
                            response = new Response(
                                    ResponseStatus.OK,
                                    "Вход выполнен успешно!",
                                    gson.toJson(userDTO)
                            );
                        } else {
                            response = new Response(
                                    ResponseStatus.ERROR,
                                    "Такого пользователя не существует или неправильный пароль!",
                                    ""
                            );
                        }
                        break;
                    }
                    case ADD_CAR: {
                        Car car = gson.fromJson(request.getRequestMessage(), Car.class);
                        carService.addCar(car);
                        response = new Response(
                                ResponseStatus.OK,
                                "Компания добавлена в бд"
                        );
                        break;
                    }
                    case READ_CARS: {
                        List<Car> cars = carService.showCars();
                        List<CarDTO> carDTOs = cars.stream().map(CarDTO::new).collect(Collectors.toList());
                        response = new Response(
                                ResponseStatus.OK,
                                "Список машин из бд",
                                gson.toJson(carDTOs)
                        );
                        break;
                    }
                    case ADD_COMPANY: {
                        Company company = gson.fromJson(request.getRequestMessage(), Company.class);
                        companyService.addCompany(company);
                        response = new Response(
                                ResponseStatus.OK,
                                "Компания добавлена в бд"
                        );
                        break;
                    }
                    case READ_COMPANY: {
                        List<Company> companies = companyService.showCompanies();
                        List<CompanyDTO> companyDTOS = new ArrayList<>();
                        for (Company company : companies) {
                            CompanyDTO dto = new CompanyDTO();
                            dto.setCompanyId(company.getCompany_id());
                            dto.setName(company.getName());
                            dto.setAddress(company.getAddress());
                            dto.setPhone(company.getPhone());
                            dto.setEmail(company.getEmail());
                            dto.setWebsite(company.getWebsite());

                            companyDTOS.add(dto);
                        }
                        response = new Response(
                                ResponseStatus.OK,
                                "Список машин из бд",
                                gson.toJson(companyDTOS)
                        );
                        break;
                    }
                }
                out.println(gson.toJson(response));
                out.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
