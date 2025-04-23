package com.example.demo_rest.config;

import com.example.demo_rest.entity.Advertisement;
import com.example.demo_rest.entity.Role;
import com.example.demo_rest.entity.User;
import com.example.demo_rest.repository.AdvertisementRepository;
import com.example.demo_rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
class LoadDatabase {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AdvertisementRepository advertisementRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Bean
    public CommandLineRunner loadData() {
        return (args) -> {

            List<User> users = new ArrayList<>();
            users.add(new User("Mateusz Rykała","123456789","admin1@pl",passwordEncoder.encode("Hasloha11!"),Role.ADMIN, "", LocalDateTime.now(),true));

            users.add(new User("Anna Kowalska", "111222333", "anna.kowalska@gmail.com", passwordEncoder.encode("Password123!"), Role.ADMIN, "", LocalDateTime.now(),true));
            users.add(new User("Jan Nowak", "444555666", "jan.nowak@gmail.com", passwordEncoder.encode("Qwerty456!"), Role.ADMIN,"", LocalDateTime.now(),true));
            users.add(new User("Katarzyna Wiśniewska", "607456789", "kasia.w@gmail.com", passwordEncoder.encode("SecurePass789!"), Role.USER, "", LocalDateTime.now(),true));
            users.add(new User("Tomasz Zieliński", "+48123456789", "t.zielinski@gmail.com", passwordEncoder.encode("Pass1234!"), Role.USER, "", LocalDateTime.now(),true));
            users.add(new User("Joanna Lewandowska", "444555789", "joanna.lew@gmail.com", passwordEncoder.encode("Joanna2023!"), Role.USER, "", LocalDateTime.now(),true));
            users.add(new User("Michał Nowicki", "888999000", "michal.nowicki@gmail.com", passwordEncoder.encode("Micha12345!"), Role.USER, "", LocalDateTime.now(),true));
            users.add(new User("Agnieszka Kaczmarek", "999000111", "agnieszka.k@gmail.com", passwordEncoder.encode("Agnieszka2024!"), Role.USER, "", LocalDateTime.now(),true));
            users.add(new User("Piotr Wiśniewski", "666777888", "piotr.w@gmail.com", passwordEncoder.encode("PiotrPass!"), Role.USER, "", LocalDateTime.now(),true));
            users.add(new User("Ewa Majewska", "+48123123123", "ewa.majewska@gmail.com", passwordEncoder.encode("EwaMaj12!"), Role.USER, "", LocalDateTime.now(),true));

            users = userRepository.saveAll(users);

            List <Advertisement> advertisements = new ArrayList<>();
            advertisements.add(new Advertisement(users.get(0),"Sprzedam rower", "Mało używany rower w dobrym stanie marki Mentis", "300 zł", "30 dni", "27.01.2025 21:11"));
            advertisements.add(new Advertisement(users.get(0),"Kupię garaż w Rzeszowie", "Kupię garaż lub miejsce parkingowe w okolicach ul. Cichej", "20-35 tys. zł", "Do odwołania", "25.12.2024 14:21"));
            advertisements.add(new Advertisement(users.get(1),"Oddam starą sofę", "Oddam za darmo starą sofę w kolorze bordowym, odbiór osobisty", "Za darmo", "Brak", "03.08.2024 07:21"));
            advertisements.add(new Advertisement(users.get(1),"Sprzedam Hondę Jazz", "Sprzedam bezwypadkową Hondę Jazz, o przebiegu 30 tys. km", "10 tys. zł, cena do negocjacji", "2 miesiące", "06.01.2025 14:14"));
            advertisements.add(new Advertisement(users.get(1),"Oddam małego kota pod opiekę", "Oddam małego, białego, 1-rocznego kota do opieki. Kot jest łagodny w usposobieniu. Kot jest rasy perskie", "Brak", "Do znalezienia opiekuna", "13.07.2024 12:39"));
            advertisements.add(new Advertisement(users.get(2), "Sprzedam używany laptop", "Laptop Dell Inspiron 15, stan bardzo dobry, 8GB RAM, 256GB SSD", "1 200 zł", "14 dni", "01.02.2025 10:00"));
            advertisements.add(new Advertisement(users.get(2), "Kupię używane książki", "Szukam książek science fiction w dobrym stanie", "Do 200 zł", "1 miesiąc", "28.01.2025 18:30"));
            advertisements.add(new Advertisement(users.get(2), "Sprzedam biurko", "Biurko sosnowe, wymiar 120x60 cm, idealne do pracy zdalnej", "300 zł", "7 dni", "26.01.2025 15:45"));
            advertisements.add(new Advertisement(users.get(2), "Oddam akwarium", "Akwarium 50L, bez rybek, w komplecie z filtrem i lampą", "Za darmo", "Brak", "20.01.2025 12:20"));
            advertisements.add(new Advertisement(users.get(3), "Sprzedam kurtkę zimową", "Kurtka zimowa damska, rozmiar M, kolor czarny, bardzo ciepła", "150 zł", "10 dni", "19.01.2025 08:30"));
            advertisements.add(new Advertisement(users.get(3), "Kupię używany aparat fotograficzny", "Poszukuję aparatu marki Canon lub Nikon w dobrym stanie", "Do 2 000 zł", "Do odwołania", "17.01.2025 21:11"));
            advertisements.add(new Advertisement(users.get(4), "Sprzedam pralkę", "Pralka automatyczna Bosch, model z 2020 roku, w pełni sprawna", "800 zł", "14 dni", "15.01.2025 10:00"));
            advertisements.add(new Advertisement(users.get(4), "Oddam regał na książki", "Regał z IKEA, 5 półek, wymiary 200x80x30 cm", "Za darmo", "Brak", "13.01.2025 09:45"));
            advertisements.add(new Advertisement(users.get(4), "Sprzedam fotel biurowy", "Fotel obrotowy, ergonomiczny, w kolorze czarnym, mało używany", "400 zł", "30 dni", "11.01.2025 16:30"));
            advertisements.add(new Advertisement(users.get(4), "Kupię działkę rekreacyjną", "Interesuje mnie działka w okolicach Rzeszowa, powierzchnia 5-10 arów", "Do 50 tys. zł", "Do odwołania", "10.01.2025 11:15"));
            advertisements.add(new Advertisement(users.get(5), "Sprzedam telewizor", "Telewizor LED Samsung, 40 cali, Full HD, model z 2021 roku", "1 500 zł", "21 dni", "08.01.2025 18:00"));
            advertisements.add(new Advertisement(users.get(5), "Oddam książki do nauki języka angielskiego", "Zestaw książek poziom podstawowy i średniozaawansowany", "Za darmo", "Brak", "06.01.2025 08:20"));
            advertisements.add(new Advertisement(users.get(6), "Sprzedam rower miejski", "Rower miejski marki Giant, w kolorze niebieskim, 3-biegowy", "700 zł", "14 dni", "04.01.2025 14:50"));
            advertisements.add(new Advertisement(users.get(6), "Kupię telefon używany", "Szukam telefonu Samsung Galaxy S21 w dobrym stanie", "Do 2 500 zł", "Do odwołania", "02.01.2025 11:30"));
            advertisements.add(new Advertisement(users.get(6), "Sprzedam namiot turystyczny", "Namiot 2-osobowy, wodoodporny, idealny na wędrówki górskie", "250 zł", "7 dni", "31.12.2024 13:00"));
            advertisements.add(new Advertisement(users.get(7), "Oddam stare płyty CD", "Zestaw płyt muzycznych z lat 80. i 90., odbiór osobisty", "Za darmo", "Brak", "29.12.2024 15:40"));
            advertisements.add(new Advertisement(users.get(7), "Sprzedam konsolę do gier", "Konsola PlayStation 4, 500GB, z jednym padem i grą gratis", "900 zł", "14 dni", "27.12.2024 19:20"));
            advertisements.add(new Advertisement(users.get(7), "Kupię stolik kawowy", "Szukam małego stolika kawowego w stylu skandynawskim", "Do 300 zł", "1 miesiąc", "25.12.2024 14:00"));
            advertisements.add(new Advertisement(users.get(8), "Sprzedam zestaw garnków", "Garnki ze stali nierdzewnej, 5 sztuk, stan idealny", "500 zł", "10 dni", "23.12.2024 09:30"));
            advertisements.add(new Advertisement(users.get(8), "Oddam ubrania dla niemowlaka", "Ubranka w rozmiarze 62-68, zarówno dla chłopca, jak i dziewczynki", "Za darmo", "Brak", "21.12.2024 08:15"));
            advertisements.add(new Advertisement(users.get(8), "Sprzedam łóżko piętrowe", "Łóżko piętrowe dla dzieci, z materacami, w kolorze białym", "1 000 zł", "30 dni", "19.12.2024 17:45"));
            advertisements.add(new Advertisement(users.get(9), "Kupię gry planszowe", "Interesują mnie używane gry planszowe w dobrym stanie", "Do 300 zł", "Do odwołania", "17.12.2024 14:50"));
            advertisements.add(new Advertisement(users.get(9), "Oddam stare monitory", "Dwa monitory LCD 19 cali, sprawne, odbiór osobisty", "Za darmo", "Brak", "15.12.2024 12:30"));
            advertisements.add(new Advertisement(users.get(9), "Kupię gitarę w dobrym stanie", "W ramach hobby kupię gitarę", "Do 2000 zł", "Do odwołania", "11.09.2024 13:59"));
            advertisements.add(new Advertisement(users.get(9), "Sprzedam zadbaną gitarę", "Ledwo używana gitara z wzmacniaczem", "1500 zł", "3 tygodnie", "20.01.2025 09:30"));
            advertisementRepository.saveAll(advertisements);

            System.out.println("Baza danych z przykładowymi użytkownikami oraz ogłoszeniami zostały zapisane.");
        };
    }
}