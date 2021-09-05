package ru.otus.spring.loader;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LoaderResource implements Loader {
    @Override
    public String load(String resourceName) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource(resourceName).getInputStream()))
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(String.format("%s\n",line));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return stringBuilder.toString();
    }
}
