/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SDN;

import java.util.ArrayList;

public class Functions {

    public static int[] pastpath(String paket, int[] paths) {
        ArrayList<Integer> pathArray = new ArrayList<Integer>();
        ArrayList<Integer> ortak = new ArrayList<Integer>();

        for (int eleman : paths) {
            pathArray.add(eleman);
        }

        String[] strings = paket.split("_");

        String str = strings[1];

        char[] c_arr = str.toCharArray();

        if (paths[0] == 3 && paths[1] == 6 && paths[2] == 7) {

            int toplam = 0;
            for (int i = 0; i < c_arr.length; i++) {
                int eleman = Character.getNumericValue(c_arr[i]);
                System.out.print(eleman);
                if (eleman == 2 || eleman == 5) {
                    toplam++;
                }
            }

            if (toplam == 2) {
                ortak.add(3);
            }
        }

        if (paths[0] == 3 && paths[1] == 6 && paths[2] == 8) {

            int toplam2 = 0;
            for (int i = 0; i < c_arr.length; i++) {
                int eleman = Character.getNumericValue(c_arr[i]);

                System.out.print(eleman);

                if (eleman == 1 || eleman == 4) {
                    toplam2++;
                }
            }

            if (toplam2 == 2) {
                ortak.add(3);
            }
        }

        int[] pathes = new int[str.length()];

        for (int i = 0; i < c_arr.length; i++) {
            pathes[i] = Integer.parseInt(String.valueOf(c_arr[i]));
            for (int j = 0; j < paths.length; j++) {

                if (pathes[i] == paths[j]) {
                    ortak.add(paths[j]);
                }

            }
        }

        for (int a = 0; a < paths.length; a++) {

            if (ortak.contains(paths[a])) {
                Object c = paths[a];
                pathArray.remove(c);

            }

        }

        int[] result = new int[pathArray.size()];

        for (int s = 0; s < pathArray.size(); s++) {
            result[s] = pathArray.get(s);

        }

        return result;
    }

}
