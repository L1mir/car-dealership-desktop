package org.limir.utility;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.limir.models.dto.CarDTO;
import org.limir.models.dto.CompanyDTO;
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
import org.limir.sessionFactory.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RequestProcessor {
    private PersonService personService;
    private UserService userService;
    private CarService carService;
    private CompanyService companyService;
    private ResponseBuilder responseBuilder;

    public RequestProcessor() {
        personService = new PersonServiceImpl();
        userService = new UserServiceImpl();
        carService = new CarServiceImpl();
        companyService = new CompanyServiceImpl();
        responseBuilder = new ResponseBuilder();
    }

    public Response processRequest(Request request) {
        switch (request.getRequestType()) {
            case REGISTER:
                return handleRegister(request);
            case LOGIN:
                return handleLogin(request);
            case ADD_CAR:
                return handleAddCar(request);
            case READ_CARS:
                return handleReadCars();
            case ADD_COMPANY:
                return handleAddCompany(request);
            case READ_COMPANIES:
                return handleReadCompanies();
            case DELETE_CAR:
                return handleDeleteCar(request);
            case UPDATE_CAR:
                return handleUpdateCar(request);
            default:
                return responseBuilder.createErrorResponse("Unknown request type");
        }
    }

    private Response handleRegister(Request request) {
        Person person = RequestDeserializer.deserializePerson(request);
        person.getUsers().forEach(user -> user.setPerson(person));
        personService.addPerson(person);
        return responseBuilder.createSuccessResponse("Person registered successfully");
    }


    private Response handleLogin(Request request) {
        User user = RequestDeserializer.deserializeUser(request);
        User foundUser = userService
                .showUsers()
                .stream()
                .filter(x -> x.getUsername().equalsIgnoreCase(user.getUsername()) &&
                        x.getPassword().equals(user.getPassword()))
                .findFirst()
                .orElse(null);

        if (foundUser != null) {
            return responseBuilder.createSuccessResponse("Login successful", foundUser.toDTO());
        } else {
            return responseBuilder.createErrorResponse("Invalid username or password");
        }
    }

    private Response handleAddCar(Request request) {
        Car car = RequestDeserializer.deserializeCar(request);
        carService.addCar(car);
        return responseBuilder.createSuccessResponse("Car added successfully");
    }

    private Response handleReadCars() {
        List<Car> cars = carService.showCars();
        List<CarDTO> carDTOs = cars.stream().map(CarDTO::new).collect(Collectors.toList());
        return responseBuilder.createSuccessResponse("List of cars", carDTOs);
    }

    private Response handleAddCompany(Request request) {
        Company company = RequestDeserializer.deserializeCompany(request);
        companyService.addCompany(company);
        return responseBuilder.createSuccessResponse("Company added successfully");
    }

    private Response handleReadCompanies() {
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

        return responseBuilder.createSuccessResponse("List of companies", companyDTOS);
    }

    private Response handleDeleteCar(Request request) {
        String model = RequestDeserializer.deserializeModel(request);
        Car car = carService.findCarByModel(model);
        carService.deleteCar(car.getCar_id());
        return responseBuilder.createSuccessResponse("Car deleted successfully");
    }

    private Response handleUpdateCar(Request request) {
        Car car = RequestDeserializer.deserializeCar(request);
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Car existingCar = session.get(Car.class, car.getCar_id());

            if (existingCar != null) {
                existingCar.setModel(car.getModel());
                existingCar.setYear(car.getYear());
                existingCar.setPrice(car.getPrice());
                existingCar.setCar_status(car.getCar_status());
                session.update(existingCar);
                transaction.commit();
                return responseBuilder.createSuccessResponse("Car updated successfully", new CarDTO(existingCar));
            } else {
                return responseBuilder.createErrorResponse("Car not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return responseBuilder.createErrorResponse("Error updating car");
        }
    }

}

