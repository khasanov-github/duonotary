package uz.pdp.appg4duonotaryserver.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.appg4duonotaryserver.entity.*;
import uz.pdp.appg4duonotaryserver.entity.enums.OrderStatus;
import uz.pdp.appg4duonotaryserver.entity.enums.RoleName;
import uz.pdp.appg4duonotaryserver.repository.*;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {
    final
    UserRepository userRepository;
    final
    RoleRepository roleRepository;

    final
    PasswordEncoder passwordEncoder;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    CountyRepository countyRepository;

    @Autowired
    ZipCodeRepository zipCodeRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    ServicePriceRepository servicePriceRepository;

    @Autowired
    MainServiceRepository mainServiceRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    TimeTableRepository timeTableRepository;

    @Autowired
    MainServiceWorkTimeRepository mainServiceWorkTimeRepository;

    @Value("${spring.datasource.initialization-mode}")
    private String initMode;

    public DataLoader(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {
            State state = new State();
            state.setName("Boston");
            State savedState = stateRepository.save(state);

            State state2 = new State();
            state2.setName("New-York");
            State savedState2 = stateRepository.save(state2);

            County county = new County();
            county.setName("Dehqon");
            county.setState(savedState);
            County savedCounty = countyRepository.save(county);

            County county2 = new County();
            county2.setName("New-Dehqon");
            county2.setState(savedState2);
            County savedCounty2 = countyRepository.save(county2);

            ZipCode zipCode= new ZipCode();
            zipCode.setName("Xadra");
            zipCode.setActive(true);
            zipCode.setCode("10001");
            zipCode.setCounty(savedCounty2);
            ZipCode zc = zipCodeRepository.save(zipCode);

            ZipCode zipCode1 = new ZipCode();
            zipCode1.setName("Xadra");
            zipCode1.setActive(true);
            zipCode1.setCode("1000");
            zipCode1.setCounty(savedCounty);
            ZipCode zc1 = zipCodeRepository.save(zipCode1);

            ZipCode zipCode2 = new ZipCode();
            zipCode2.setName("qwwe");
            zipCode2.setActive(true);
            zipCode2.setCode("2000");
            zipCode2.setCounty(savedCounty);
            ZipCode zc2 = zipCodeRepository.save(zipCode2);

            ZipCode zipCode3 = new ZipCode();
            zipCode3.setName("afafa");
            zipCode3.setActive(true);
            zipCode3.setCode("3000");
            zipCode3.setCounty(savedCounty);
            ZipCode zc3 = zipCodeRepository.save(zipCode3);

            ZipCode zipCode4 = new ZipCode();
            zipCode4.setName("ofkasf");
            zipCode4.setActive(true);
            zipCode4.setCode("4000");
            zipCode4.setCounty(savedCounty);
            ZipCode zc4 = zipCodeRepository.save(zipCode4);

            MainService mainService = new MainService();
            mainService.setName("Ketmon");
            mainService.setFromTime(LocalTime.of(8,0));
            mainService.setTillTime(LocalTime.of(18,0));
            mainService.setOnline(true);
            mainService.setOrderNumber(1);
            MainService savedMainService = mainServiceRepository.save(mainService);

            MainServiceWorkTime mainServiceWorkTime = new MainServiceWorkTime();
            mainServiceWorkTime.setFromTime(LocalTime.of(19,0));
            mainServiceWorkTime.setTillTime(LocalTime.of(21,0));
            mainServiceWorkTime.setActive(true);
            mainServiceWorkTime.setPercent((double)20);
            mainServiceWorkTime.setMainService(savedMainService);
            mainServiceWorkTime.setZipCode(zc2);
            mainServiceWorkTimeRepository.save(mainServiceWorkTime);

            mainServiceWorkTime = new MainServiceWorkTime();
            mainServiceWorkTime.setFromTime(LocalTime.of(22,0));
            mainServiceWorkTime.setTillTime(LocalTime.of(2,0));
            mainServiceWorkTime.setActive(true);
            mainServiceWorkTime.setPercent((double)50);
            mainServiceWorkTime.setMainService(savedMainService);
            mainServiceWorkTime.setZipCode(zc3);
            mainServiceWorkTimeRepository.save(mainServiceWorkTime);

            mainServiceWorkTime = new MainServiceWorkTime();
            mainServiceWorkTime.setFromTime(LocalTime.of(2,0));
            mainServiceWorkTime.setTillTime(LocalTime.of(4,0));
            mainServiceWorkTime.setActive(true);
            mainServiceWorkTime.setPercent((double)90);
            mainServiceWorkTime.setMainService(savedMainService);
            mainServiceWorkTime.setZipCode(zc4);
            mainServiceWorkTimeRepository.save(mainServiceWorkTime);

            Service service1 = new Service();
            service1.setName("Real");
            service1.setMainService(savedMainService);
            service1.setInitialCount(12);
            service1.setInitialSpendingTime(50);
            service1.setEveryCount(1);
            service1.setEverySpendingTime(5);
            service1.setDynamic(true);
            service1.setActive(true);
            Service savedService1 = serviceRepository.save(service1);

            Service service2 = new Service();
            service2.setName("Inter");
            service2.setMainService(savedMainService);
            service2.setInitialCount(12);
            service2.setInitialSpendingTime(50);
            service2.setEveryCount(1);
            service2.setEverySpendingTime(5);
            service2.setDynamic(true);
            service2.setActive(true);
            Service savedService2 = serviceRepository.save(service2);


            ServicePrice savedServicePrice1 = servicePriceRepository.save(new ServicePrice(
                    savedService1,
                    zc1,
                    20.0,
                    30,
                    25,
                    true
            ));

            ServicePrice savedServicePrice2 = servicePriceRepository.save(new ServicePrice(
                    savedService1,
                    zc2,
                    10.0,
                    10,
                    25,
                    true
            ));

            ServicePrice savedServicePrice3 = servicePriceRepository.save(new ServicePrice(
                    savedService2,
                    zc3,
                    5.0,
                    5,
                    7,
                    true
            ));
            ServicePrice savedServicePrice4 = servicePriceRepository.save(new ServicePrice(
                    savedService2,
                    zc4,
                    8.0,
                    20,
                    15,
                    true
            ));
            ServicePrice savedServicePrice5 = servicePriceRepository.save(new ServicePrice(
                    savedService2,
                    zc,
                    12.0,
                    50,
                    90,
                    true
            ));


            roleRepository.save(new Role(RoleName.ROLE_SUPER_ADMIN));
            roleRepository.save(new Role(RoleName.ROLE_ADMIN));
            roleRepository.save(new Role(RoleName.ROLE_AGENT));
            roleRepository.save(new Role(RoleName.ROLE_USER));
            userRepository.save(
                    new User(
                            "SuperAdmin",
                            "SuperAdminov",
                            "123",
                            "superAdmin@gmail.com",
                            passwordEncoder.encode("123"),
                            new HashSet<>(roleRepository.findAllByName(RoleName.ROLE_SUPER_ADMIN)),
                            true
                    )
            );
            userRepository.save(
                    new User(
                            "RoleAdmin",
                            "RoleAdminov",
                            "456",
                            "roleadminov@gmail.com",
                            passwordEncoder.encode("456"),
                            new HashSet<>(roleRepository.findAllByName(RoleName.ROLE_ADMIN)),
                            true
                    )
            );
            User agentUser =
                    new User(
                            "RoleAgent",
                            "RoleAgentov",
                            "789",
                            "roleagentov@gmail.com",
                            passwordEncoder.encode("789"),
                            new HashSet<>(roleRepository.findAllByName(RoleName.ROLE_AGENT)),
                            true
                    );

            userRepository.save(agentUser);

            User user = new User(
                    "roleUser",
                    "IAmUser",
                    "888",
                    "meuser@gmail.com",
                    passwordEncoder.encode("123"),
                    new HashSet<>(roleRepository.findAllByName(RoleName.ROLE_USER)),
                    true
            );
            userRepository.save(user);


            Order order = orderRepository.save(new Order(
                    "hadra",
                    user,
                    Float.parseFloat("0.3212"),
                    Float.parseFloat("0.0123213"),
                    savedServicePrice1,
                    agentUser,
                    20.0,
                    5.0,
                    "123456789",
                    10,
                    OrderStatus.NEW,
                    4,
                    null,
                    null,
                    "idon'tknow",
                    true));
            TimeTable timeTable = timeTableRepository.save(new TimeTable(
                    agentUser,
                    new Timestamp((new Date()).getTime()),
                    new Timestamp((new Date()).getTime()),
                    order,
                    false,
                    true
            ));

        }
    }

}
