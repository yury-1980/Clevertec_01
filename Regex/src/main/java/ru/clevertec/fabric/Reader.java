package ru.clevertec.fabric;

import ru.clevertec.model.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Reader {
    static List<String> listInvalidData = new ArrayList<>();

    public static Map<Integer, Product> getProduct() {

        Map<Integer, Product> productMap = new HashMap<>();
        String title;
        int id;
        double price;
        boolean discount;
        String regId = "^([1-9]|[0-9]\\d|100)$";
        String regTitle = "^[A-ZА-Я][a-zа-я]{3,30}$";
        String regPrice = "^(([1-9][0-9]\\.[0-9]{2})|100.[0-9]{2})$|^([1-9]\\.[0-9]{2})$";
        String regDiscont = "^true|false$";
        String str;

        String path = new File("").getAbsolutePath();

        try (FileReader reader = new FileReader(path
                + "/src/main/resources/products.txt");
             Scanner scanner = new Scanner(reader)) {
            scanner.useLocale(Locale.ENGLISH);

            while (scanner.hasNextLine()) {
                int flag = 0;

                str = scanner.next();
                if (!str.matches(regId)) {
                    listInvalidData.add(str);
                    flag++;
                }
                id = Integer.parseInt(str);
                str = scanner.next();
                if (!str.matches(regTitle)) {
                    listInvalidData.add(str);
                    flag++;
                }
                title = str;

                str = scanner.next();
                if (!str.matches(regPrice)) {
                    listInvalidData.add(str);
                    flag++;
                }
                price = Double.parseDouble(str);

                str = scanner.next();
                if (!str.matches(regDiscont)) {
                    listInvalidData.add(str);
                    flag++;
                }
                discount = Boolean.parseBoolean(str);

                if (flag != 0) {// Для выбора конкретных не валидных данных
                    continue;
                }

                productMap.put(id, new Product(title, id, price, discount));
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productMap;
    }
}
