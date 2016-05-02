import java.util.Arrays;

/**
 * Created by dpacif1 on 4/20/16.
 */
public class LabString {


    public static void main(String[] args) {

        String nodes[] = new String[512];

        String x = nodes['d'];


        String[] nums = new String[]{
                "3164",
                "3363",
                "2251",
                "4452",
                "2662",
                "3413",
                "3364",
                "5643",
                "2153",
                "2142"};

        Quick3string.sort(nums);

        String[] numsMSD = new String[]{
        "3334",
        "1414",
        "1222",
        "1131",
        "4224",
        "2441",
        "4243",
        "2334",
        "4433",
        "3241",
        "1112",
        "3333",
        "1234",
        "1433",
        "4313"};

        MSD.sort(numsMSD);


    }

    public static void mainOld(String[] args) {

        for (int i = 0; i < 20; i++) {
            {
                String[] names = getNames();
                Long now = System.currentTimeMillis();
                Quick3string.sort(names);
                System.out.println("sort Sedgewik! em milis: " + (System.currentTimeMillis() - now));
            }
            {

                String[] names = getNames();
                Long now = System.currentTimeMillis();
                Arrays.sort(names);
                System.out.println("sort java defaul ok! em milis: " + (System.currentTimeMillis() - now));
            }
            {

                String[] names = getNames();
                Long now = System.currentTimeMillis();
                MSD.sort(names);
                System.out.println("sort MSD em milis: " + (System.currentTimeMillis() - now));
            }
            System.out.println();
        }
    }

    private static String[] getNames() {
        final int nums = 1000 * 1000 * 3;

        String[] names = new String[nums];

        for (int i = 0; i < nums; i++) {
            names[i] = nomeAleatorio();
        }
        return names;
    }

    public static String nomeAleatorio() {

        final int tamanhoNome = 4;

        byte[] nome = new byte[tamanhoNome];
        for (int i = 0; i < tamanhoNome; i++) {

            final int rdn = (int) ('a' + (int) (Math.random() * 24));

            nome[i] = (byte) Character.toTitleCase(rdn);

        }

        return new String(nome);
    }

}
