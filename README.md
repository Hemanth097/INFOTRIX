# Currency Converter Java Application

Welcome to the Currency Converter Java application! This application allows users to perform currency conversions using a third-party API, manage favorite currencies, and persist their preferences across sessions.

## Features

1. **Currency Conversion:** Utilizes the [RapidAPI](https://rapidapi.com/) platform to perform real-time and accurate currency conversions.

2. **Favorite Currencies Management:** Users can easily manage their favorite currencies. The list of favorite currencies is stored in a text file, ensuring persistence.

3. **User-Friendly Interface:** The application features a straightforward menu system, guiding users through currency conversion and favorite currency management.

4. **File I/O Operations:** The favorite currencies are stored in a text file, enabling users to reuse their preferred currencies each time they open the application.

## How to Use

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/CurrencyConverterJava.git
   ```

2. Compile and run the application:

   ```bash
   cd CurrencyConverterJava
   javac CurrencyConverter.java
   java CurrencyConverter
   ```

3. Follow the on-screen prompts to perform currency conversions and manage favorite currencies.

## Dependencies

- [Java](https://www.oracle.com/java/technologies/javase-downloads.html) (JDK 8 or later)

## API Integration

This application integrates with the [RapidAPI](https://rapidapi.com/) platform for currency conversion. Make sure to obtain your API key and replace the placeholder in the `convertCurrency` method in the `CurrencyConverter.java` file.

```java
String apiKey = "your-rapidapi-key";
```

## Contributing

Contributions are welcome! Feel free to open issues or submit pull requests for any enhancements or bug fixes.

## License

This project is licensed under the [MIT License](LICENSE).
