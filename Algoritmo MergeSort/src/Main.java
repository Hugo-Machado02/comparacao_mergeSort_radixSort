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

            int[] arrayAux = new int[array.length];
            mergeSort(array, arrayAux, 0, array.length-1);

            System.out.println(Arrays.toString(array));
            opFim = System.currentTimeMillis();
            calcTempo(opFim - opInicio);
        }
        else{
            System.out.println("Array não encontrado");
        }
    }

    public static void mergeSort(int[] array, int[] arrayAux, int indexIni, int indexFim) {
        if(indexIni < indexFim){
            int indexMeio = (indexIni + indexFim) / 2;
            mergeSort(array, arrayAux, indexIni, indexMeio);
            mergeSort(array, arrayAux, indexMeio+1, indexFim);
            merge(array, arrayAux, indexIni, indexMeio, indexFim);
        }
    }

    public static void merge(int[] array, int[] arrayAux, int indexIni, int indexMeio, int indexFim) {
        for(int i = indexIni; i <= indexFim; i++) {
            arrayAux[i] = array[i];
        }

        int inicio = indexIni;
        int meio = indexMeio + 1;

        for(int i = indexIni; i <= indexFim; i++){
            if(inicio > indexMeio){
                array[i] = arrayAux[meio++];
            }
            else if(meio > indexFim){
                array[i] = arrayAux[inicio++];
            }
            else if(arrayAux[inicio] < arrayAux[meio]){
                array[i] = arrayAux[inicio++];
            }
            else{
                array[i] = arrayAux[meio++];
            }
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