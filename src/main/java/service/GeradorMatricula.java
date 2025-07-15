package service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GeradorMatricula {

    private static final Set<String> matriculasGeradas = new HashSet<>();
    private static final Random random = new Random();

    public static String gerarMatriculaUnica() {
        String matricula;
        do {
            int numero = 10000 + random.nextInt(90000);
            int digito = calcularDigitoVerificador(numero);
            matricula = numero + "-" + digito;
        } while (matriculasGeradas.contains(matricula));

        matriculasGeradas.add(matricula);
        return matricula;
    }

    private static int calcularDigitoVerificador(int numero) {
        int soma = 0;
        int n = numero;
        while (n > 0) {
            soma += n % 10;
            n /= 10;
        }
        return soma % 10;
    }

}