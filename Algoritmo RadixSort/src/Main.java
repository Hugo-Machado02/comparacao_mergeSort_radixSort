import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        int[] array = lerArquivo("src/numeros_100000.txt");
        if(array != null){
            Long opInicio, opFim;

            opInicio = System.currentTimeMillis();

            int digito = maxDigito(array);
            System.out.println("Maior Dígito: " + digito);

            radixSort(array, 0, array.length - 1, digito);

            System.out.println(Arrays.toString(array));
            opFim = System.currentTimeMillis();
            calcTempo(opFim - opInicio);
        }
        else{
            System.out.println("Array não encontrado");
        }

    }

    public static int[] lerArquivo(String url) throws IOException {
        Path arquivo = Path.of(url);

        if(Files.notExists(arquivo)){
            return null;
        }
        else {
            String valoresString = Files.readString(arquivo);
            String formataValor = valoresString.replace("[", "").replace("]", "").replace(" ", "");

            String[] arrayString = formataValor.split(",");

            int[] arrayInt = new int[arrayString.length];

            for(int i = 0; i < arrayString.length; i++){
                arrayInt[i] = Integer.valueOf(arrayString[i]);
            }

            return arrayInt;
        }
    }

    public static int maxDigito(int[] array) {
        int maiorDigito = 0;
        for (int valor : array) {
            int numero = contaDigitos(valor);
            if (numero > maiorDigito) {
                maiorDigito = numero;
            }
        }
        return maiorDigito;
    }

    public static void radixSort(int[] array, int indexIni, int indexFim, int digito) {
        if (indexIni >= indexFim || digito < 0) {
            return;
        }

        int[] count = new int[19];
        int[] temp = new int[indexFim - indexIni + 1];

        for (int i = indexIni; i <= indexFim; i++) {
            int digitValue = buscaValorDigito(array[i], digito);
            count[digitValue + 9]++;
        }

        for (int i = 1; i < 19; i++) {
            count[i] += count[i - 1];
        }

        for (int i = indexFim; i >= indexIni; i--) {
            int digitoVal = buscaValorDigito(array[i], digito);
            temp[count[digitoVal + 9] - 1] = array[i];
            count[digitoVal + 9]--;
        }

        System.arraycopy(temp, 0, array, indexIni, indexFim - indexIni + 1);

        int atualIndexIni = indexIni;

        for (int i = 0; i < 19; i++) {
            int atualIndexFim = indexIni + count[i] - 1;
            radixSort(array, atualIndexIni, atualIndexFim, digito - 1);
            atualIndexIni = indexIni + count[i];
        }
    }

    public static int buscaValorDigito(int numero, int digito) {
        return (numero / (int) Math.pow(10, digito)) % 10;
    }

    public static int contaDigitos(int numero) {
        if (numero == 0) {
            return 1;
        }
        return (int) Math.log10(Math.abs(numero)) + 1;
    }

    public static void calcTempo(long total){
        long opHr, opMin, opSeg, opMils;
        opMils = total % 1000;
        total /= 1000;
        opHr = total / 3600;
        total %= 3600;
        opMin = total / 60;
        opSeg = total % 60;

        System.out.println("Tempo de execução: " + opHr + ":" + opMin + ":" + opSeg + ":"+opMils);
    }
}
