package org.commodolink;

import org.mindrot.jbcrypt.BCrypt;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n\n");

        System.out.println("*  Paste password hash here: ");
        String hash = scanner.nextLine().trim();

        System.out.println("** Paste path to wordlist here: ");
        String path = scanner.nextLine().trim();

        long startTime = System.currentTimeMillis();

        List<String> wordlist = null;
        while (true) {
            Path filePath = Paths.get(path);
            if (Files.exists(filePath) && Files.isReadable(filePath)) {
                try {
                    wordlist = Files.readAllLines(filePath);
                    if (!wordlist.isEmpty()) {
                        System.out.println("Wordlist loaded. Number of words: " + wordlist.size());
                        break;
                    } else {
                        System.out.println("Wordlist is empty! Please provide a valid wordlist.");
                    }
                } catch (Exception e) {
                    System.out.println("Error reading the wordlist: " + e.getMessage());
                }
            } else {
                System.out.println("File not found or not readable! Please provide a valid path.");
            }

            System.out.println("** Retype path: ");
            path = scanner.nextLine().trim();
        }

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<CompletableFuture<Boolean>> futures = wordlist.stream()
                .map(word -> CompletableFuture.supplyAsync(() -> {
                    if (BCrypt.checkpw(word, hash)) {
                        long endTime = System.currentTimeMillis();
                        long elapsedTime = endTime - startTime;
                        System.out.println("Password found: " + word);
                        System.out.println("Time taken: " + elapsedTime + " milliseconds");
                        return true;
                    }
                    return false;
                }, executor))
                .collect(Collectors.toList());

        try {
            boolean passwordFound = false;
            for (CompletableFuture<Boolean> future : futures) {
                if (future.get()) {
                    passwordFound = true;
                    break;
                }
            }
            if (!passwordFound) {
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                System.out.println("Password not found in the wordlist.");
                System.out.println("Time taken: " + elapsedTime + " milliseconds");
            }
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error during password cracking: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }
}
