import java.util.*;

public class Main {
    private static List<Thread> threads;


    public static void main(String[] args) throws InterruptedException {
        threads = new ArrayList<>();


        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }

        Runnable logic = () -> {
            int index = threads.indexOf(Thread.currentThread());
            String text = texts[index];

            int maxSize = 0;
            for (int i = 0; i < text.length(); i++) {
                for (int j = 0; j < text.length(); j++) {
                    if (i >= j) {
                        continue;
                    }
                    boolean bFound = false;
                    for (int k = i; k < j; k++) {
                        if (text.charAt(k) == 'b') {
                            bFound = true;
                            break;
                        }
                    }
                    if (!bFound && maxSize < j - i) {
                        maxSize = j - i;
                    }
                }
            }
            System.out.println(text.substring(0, 100) + " -> " + maxSize);
        };


        for (int i = 0; i < texts.length; i++) {
            Thread thread = new Thread(logic);
            thread.start();
            threads.add(thread);
        }



        /*
        long startTs = System.currentTimeMillis(); // start time

        long endTs = System.currentTimeMillis(); // end time

        System.out.println("Time: " + (endTs - startTs) + "ms");

         */
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}