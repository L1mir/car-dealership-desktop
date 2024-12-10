package org.limir.utility;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.limir.enums.OrderStatus;
import org.limir.enums.PaymentStatus;
import org.limir.models.dto.*;
import org.limir.models.entities.*;
import org.limir.models.tcp.Request;
import org.limir.models.tcp.Response;
import org.limir.services.*;
import org.limir.services.servicesImpl.*;
import org.limir.sessionFactory.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RequestProcessor {
    private ResponseBuilder responseBuilder;

    private PersonService personService;
    private UserService userService;
    private CarService carService;
    private CompanyService companyService;
    private OrderService orderService;
    private PaymentService paymentService;
    private EmployeeService employeeService;

    public RequestProcessor() {
        personService = new PersonServiceImpl();
        userService = new UserServiceImpl();
        carService = new CarServiceImpl();
        companyService = new CompanyServiceImpl();
        responseBuilder = new ResponseBuilder();
        orderService = new OrderServiceImpl();
        paymentService = new PaymentServiceImpl();
        employeeService = new EmployeeServiceImpl();
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
                return handleReadCars(request);
            case ADD_COMPANY:
                return handleAddCompany(request);
            case READ_COMPANIES:
                return handleReadCompanies();
            case DELETE_CAR:
                return handleDeleteCar(request);
            case UPDATE_CAR:
                return handleUpdateCar(request);
            case PURCHASE_ORDER:
                return handlePurchaseOrder(request);
            case READ_USER_ORDERS:
                return handleReadUserOrders(request);
            case READ_CAR_BY_MODEL:
                return handleReadCarByModel(request);
            case READ_ORDERS:
                return handleReadOrders(request);
            case FIND_COMPANY_BY_NAME:
                return handleFindCompanyByName(request);
            case FIND_PERSON_BY_SURNAME:
                return handleFindPersonByName(request);
            case ADD_EMPLOYEE:
                return handleAddEmployee(request);
            case READ_EMPLOYEES:
                return handleReadEmployees(request);
            case DELETE_COMPANY_BY_NAME:
                return handleDeleteCompanyByName(request);
            case DELETE_EMPLOYEE:
                return handleDeleteEmployee(request);
            case READ_USERS:
                return handleReadUsers(request);
            case UPDATE_COMPANY:
                return handleUpdateCompany(request);
            case UPDATE_EMPLOYEE:
                return handleUpdateEmployee(request);
            case FIND_EMPLOYEE_BY_ID:
                return handleFindEmployeeById(request);
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

    private Response handleReadCars(Request request) {
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

    private Response handlePurchaseOrder(Request request) {
        OrderDTO orderDTO = RequestDeserializer.deserializeOrderDto(request);

        try {
            User user = userService.findUserByUsername(orderDTO.getUserName());
            if (user == null) {
                return responseBuilder.createErrorResponse("User not found");
            }

            Car car = carService.findCarById(orderDTO.getCarId());
            if (car == null) {
                return responseBuilder.createErrorResponse("Car not found");
            }

            Company company = companyService.findCompanyByName(orderDTO.getCompanyName());
            if (company == null) {
                return responseBuilder.createErrorResponse("Company not found");
            }


            if (!car.getCompany().getCompany_id().equals(company.getCompany_id())) {
                return responseBuilder.createErrorResponse("The car does not belong to the specified company");
            }

            Order order = new Order();
            order.setUser(user);
            order.setCompany(company);
            order.setTotal_price(orderDTO.getTotalPrice());
            order.setDate(orderDTO.getDate());
            order.setOrder_status(OrderStatus.PROCESSING);
            orderService.addOrder(order);

            Payment payment = new Payment();
            payment.setUser(user);
            payment.setCompany(company);
            payment.setAmount(orderDTO.getTotalPrice());
            payment.setDate(orderDTO.getDate());
            payment.setPayment_method(orderDTO.getPaymentMethod());
            payment.setPayment_status(PaymentStatus.PROCESSED);
            payment.setOrder(order);
            paymentService.addPayment(payment);

            return responseBuilder.createSuccessResponse("Order created successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return responseBuilder.createErrorResponse("Error while creating order");
        }
    }

    private Response handleReadUserOrders(Request request) {
        UserDTO userDTO = RequestDeserializer.deserializeUserDto(request);
        List<Order> orders = orderService.findOrdersByUsername(userDTO.getUsername());
        List<OrderDTO> orderDTOS = orders.stream().map(OrderDTO::new).collect(Collectors.toList());
        return responseBuilder.createSuccessResponse("List of cars", orderDTOS);
    }

    private Response handleReadCarByModel(Request request) {
        String model = RequestDeserializer.deserializeModel(request);
        Car car = carService.findCarByModel(model);
        CarDTO carDTO = new CarDTO(car);
        return responseBuilder.createSuccessResponse("Car read successfully", carDTO);
    }

    private Response handleReadOrders(Request request) {
        List<Order> orders = orderService.showOrders();
        List<OrderDTO> orderDTOS = orders.stream().map(OrderDTO::new).collect(Collectors.toList());
        return responseBuilder.createSuccessResponse("List of cars", orderDTOS);
    }

    private Response handleFindCompanyByName(Request request) {
        String name = RequestDeserializer.deserializeName(request);
        Company company = companyService.findCompanyByName(name);
        CompanyDTO companyDTO = new CompanyDTO(company);
        return responseBuilder.createSuccessResponse("Company read successfully", companyDTO);
    }

    private Response handleFindPersonByName(Request request) {
        String name = RequestDeserializer.deserializeName(request);
        Person person = personService.findPersonByName(name);
        PersonDTO personDTO = new PersonDTO(person);
        return responseBuilder.createSuccessResponse("Person read successfully", personDTO);
    }

    private Response handleAddEmployee(Request request) {
        Employee employee = RequestDeserializer.deserializeEmployee(request);
        employeeService.addEmployee(employee);
        return responseBuilder.createSuccessResponse("Employee added successfully");
    }

    private Response handleReadEmployees(Request request) {
        List<Employee> employees = employeeService.showEmployee();
        List<EmployeeDTO> employeeDTOS = employees.stream().map(EmployeeDTO::new).collect(Collectors.toList());
        return responseBuilder.createSuccessResponse("List of cars", employeeDTOS);
    }

    private Response handleDeleteCompanyByName(Request request) {
        String name = RequestDeserializer.deserializeName(request);
        Company company = companyService.findCompanyByName(name);
        companyService.deleteCompany(company.getCompany_id());
        return responseBuilder.createSuccessResponse("Car deleted successfully");
    }

    private Response handleDeleteEmployee(Request request) {
        Long employeeId = RequestDeserializer.deserializeEmployeeId(request);
        Employee employee = employeeService.findEmployeeById(employeeId);
        employeeService.deleteEmployee(employee.getEmployee_id());
        return responseBuilder.createSuccessResponse("Employee deleted successfully");
    }

    private Response handleReadUsers(Request request) {
        List<User> users = userService.showUsers();
        List<UserDTO> userDTOS = users.stream().map(UserDTO::new).collect(Collectors.toList());
        return responseBuilder.createSuccessResponse("List of cars", userDTOS);
    }

    private Response handleUpdateCompany(Request request) {
        Company company = RequestDeserializer.deserializeCompany(request);
        companyService.updateCompany(company);
        return responseBuilder.createSuccessResponse("Company updated successfully");
    }

    private Response handleFindEmployeeById(Request request) {
        Long employeeId = RequestDeserializer.deserializeEmployeeId(request);
        Employee employee = employeeService.findEmployeeById(employeeId);
        EmployeeDTO employeeDTO = new EmployeeDTO(employee);
        return responseBuilder.createSuccessResponse("Employee found success", employeeDTO);
    }

    private Response handleUpdateEmployee(Request request) {
        Employee employee = RequestDeserializer.deserializeEmployee(request);
        employeeService.updateEmployee(employee);
        return responseBuilder.createSuccessResponse("Employee updated successfully");
    }
}
