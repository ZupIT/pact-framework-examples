package br.com.vinirib.pact.consumer.client.stub;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NameStub {


    private static List<String> firstName = buildFirstNameList();
    private static List<String> lastName = buildLastNameList();

    private static List<String> buildFirstNameList() {
        return firstName =
                Arrays.asList("Fernando", "Valter", "Ricardo", "Igor", "Arthur", "Henrique", "Davi", "Eder", "Adriana", "Giselle", "Rafael", "Lilian",
                        "Filipe",
                        "Carolina", "Bruna", "Juliana", "Rodrigo", "Emmily", "Jonatas", "Larissa", "Gabriela", "Claudio", "Diego", "Fabiano",
                        "Daniel", "Raquel", "Ligia", "Fernanda");
    }

    private static List<String> buildLastNameList() {
        return lastName =
                Arrays.asList("Proença", "Mattos", "Rocha", "Leone", "Garcia", "Fogaça", "Fonseca", "Oliveira", "Pacheco", "Silva", "Souza", "Xavier",
                        "Toledo", "Ribeiro", "Moraes",
                        "Lopes", "Gonzaga", "Alvez", "Nunes", "Pizzani", "Luz", "Machado", "Batista", "Santos", "Domingues", "Costa", "Basso",
                        "Campione", "Bosso", "Aurelio", "Barros");
    }

    public static String getRandomFirstName() {
        Collections.shuffle(firstName);
        return firstName.get(0);
    }

    public static String getRandomLastName() {
        Collections.shuffle(lastName);
        return lastName.get(0);
    }
}
