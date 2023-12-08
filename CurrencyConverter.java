import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CurrencyConverter {
    private static final String FAVORITE_CURRENCIES_FILE = "favorite_currencies.txt";
    private static List<String> favoriteCurrenciesList = new ArrayList<>();
    private static String apiKey = "d10401e0e2mshc41a94122639781p1b1153jsnf0d2e39418bd";

    public static void main(String[] args) {
        loadFavoriteCurrencies();
        try {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n\nMain Menu:");
                System.out.println("1. Convert Currency");
                System.out.println("2. Manage Favorite Currenciess");
                System.out.println("3. Show Favorite Currencies");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        handleCurrencyConversion(scanner);
                        break;
                    case 2:
                        manageFavoriteCurrencies(scanner);
                        break;
                    case 3:
                        showFavoriteCurrencies(scanner);
                        break;
                    case 4:
                        saveFavoriteCurrencies();
                        System.out.println("Thanks for using Currency Converter.");
                        System.out.println("Exiting the application.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <JSONObject> void handleCurrencyConversion(Scanner scanner) {
        try {
            // System.err.println("Supported currencies
            // ANG,TND,XCD,CAD,MVR,HRK,AUD,MWK,XAG,MAD,PHP,NAD,GNF,KES,MZN,BTN,MGA,AZN,XAU,RON,INR..............etc");
            System.out.print("Enter the source currency (e.g., USD): ");
            String fromCurrency = scanner.nextLine();

            System.out.print("Enter the target currency (e.g., EUR): ");
            String toCurrency = scanner.nextLine();

            System.out.print("Enter the amount to convert: ");
            double amount = scanner.nextDouble();

            // Your provided API key
            String result = convertCurrency(fromCurrency, toCurrency, amount, apiKey);

            printresult(result);
            // System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String convertCurrency(String fromCurrency, String toCurrency, double amount, String apiKey)
            throws Exception {
        String host = "currency-conversion-and-exchange-rates.p.rapidapi.com";
        String endpoint = "convert";

        String url = String.format("https://%s/%s?from=%s&to=%s&amount=%.2f", host, endpoint, fromCurrency, toCurrency,
                amount);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", host)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new Exception("Currency conversion request failed with status code: " + response.statusCode());
        }
    }

    public static void manageFavoriteCurrencies(Scanner scanner) {
        while (true) {
            System.out.println("");
            System.out.println("Favorite Currency Menu:");
            System.out.println("1. Add Favorite Currency");
            System.out.println("2. Update Favorite Currency");
            System.out.println("3. Delete Favorite Currency");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addFavoriteCurrency(scanner);
                    break;
                case 2:
                    updateFavoriteCurrency(scanner);
                    break;
                case 3:
                    deleteFavoriteCurrencies(scanner);
                    break;
                case 4:
                    return; // Return to the main menu
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    public static void addFavoriteCurrency(Scanner scanner) {
        System.out.print("Enter the Favorite Currency to add (e.g., USD): ");
        String favoriteCurrency = scanner.nextLine();
        if (!favoriteCurrenciesList.contains(favoriteCurrency)) {
            favoriteCurrenciesList.add(favoriteCurrency);
            System.out.println("Favorite currency added.");
        } else {
            System.out.println("The currency is already present in the Favorite Currencies");
        }
        // System.out.println("Favorite currency added.");
    }

    private static void deleteFavoriteCurrencies(Scanner scanner) {
        System.out.print("Enter the Currency which you want to delete from Favorite Currencies (e.g., USD): ");
        String deleteCurrency = scanner.nextLine();
        if (favoriteCurrenciesList.contains(deleteCurrency)) {
            favoriteCurrenciesList.remove(deleteCurrency);
            System.out.println(deleteCurrency + " deleted from the Favorite Currencies");
        } else {
            System.out.println("The currency is not present in the Favorite Currencies");
        }

    }

    public static void updateFavoriteCurrency(Scanner scanner) {
        System.out.print("Enter the currency code you want to update: ");
        String currencyCodeToUpdate = scanner.nextLine();

        boolean currencyFound = false;
        for (int i = 0; i < favoriteCurrenciesList.size(); i++) {
            String currency = favoriteCurrenciesList.get(i);
            if (currency.startsWith(currencyCodeToUpdate)) {
                System.out.print("Enter the new Favorite Currency (e.g., USD): ");
                String newCurrency = scanner.nextLine();
                favoriteCurrenciesList.set(i, newCurrency);
                currencyFound = true;
                System.out.println("Favorite currency updated.");
                break; // Exit the loop once updated
            }
        }

        if (!currencyFound) {
            System.out.println("Currency code not found in favorites.");
        }

    }

    private static void loadFavoriteCurrencies() {
        try (Scanner fileScanner = new Scanner(new File(FAVORITE_CURRENCIES_FILE))) {

            while (fileScanner.hasNextLine()) {
                favoriteCurrenciesList.add(fileScanner.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Error loading favorite currencies from file: " + e.getMessage());
        }
    }

    private static void saveFavoriteCurrencies() {
        try (FileWriter writer = new FileWriter(FAVORITE_CURRENCIES_FILE)) {
            for (String favoriteCurrency : favoriteCurrenciesList) {
                writer.write(favoriteCurrency + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving favorite currencies to file: " + e.getMessage());
        }
    }

    private static void showFavoriteCurrencies(Scanner scanner) {
        if (favoriteCurrenciesList.size() == 0) {
            System.out.println("You don't have any Favorite Currencies. Enter 2 to Manage Favorite Currencies");
        } else {
            System.out.println("Favorite Currencies:");
            for (int i = 0; i < favoriteCurrenciesList.size(); i++) {
                System.out.println((i + 1) + ". " + favoriteCurrenciesList.get(i));
            }

            System.out.print("Select a favorite currency for conversion (1-" + favoriteCurrenciesList.size() + "): ");
            int selection = scanner.nextInt();

            if (selection >= 1 && selection <= favoriteCurrenciesList.size()) {
                String selectedCurrency = favoriteCurrenciesList.get(selection - 1);
                System.out.println("Selected currency for conversion: " + selectedCurrency);

                // Perform currency conversion using the selected favorite currency
                handleCurrencyConversion(scanner, selectedCurrency);
            } else {
                System.out.println("Invalid selection. Please choose a number within the range.");
            }
        }
    }

    public static void handleCurrencyConversion(Scanner scanner, String selectedCurrency) {
        try {
            System.out.print("Enter the target currency (e.g., EUR): ");
            scanner.nextLine();
            String toCurrency = scanner.nextLine();

            System.out.print("Enter the amount to convert: ");
            double amount = scanner.nextDouble();

            String result = convertCurrency(selectedCurrency, toCurrency, amount, apiKey);
            printresult(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printresult(String result) {
        System.out.print("The conversion result is ");
        System.out.println(result.substring(result.indexOf("result") + 8, result.length() - 1));
    }

}