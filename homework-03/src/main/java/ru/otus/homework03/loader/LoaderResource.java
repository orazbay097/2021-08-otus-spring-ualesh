package ru.otus.homework03.loader;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
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
            throw new RuntimeException();
        }
        return stringBuilder.toString();
    }
}
