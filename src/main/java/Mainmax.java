import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Mainmax {
    private static List<Future<Integer>> callableList;

    private static int maxLength = 0;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        callableList = new ArrayList<>();

        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < texts.length; i++) {
            MyCallable myCallable = new MyCallable(texts[i]);
            Future<Integer> task = threadPool.submit(myCallable);
            callableList.add(task);
        }

        for (Future<Integer> intFuture : callableList) {
            int result = intFuture.get();
            if (result > maxLength) {
                maxLength = result;
            }
        }

        System.out.println("максимальное колличество последовательных а - " + maxLength);

        threadPool.shutdown();
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