# WhiteBit API Java client
Disclaimer: custom (not-official) implementation

Official API documentation: https://whitebit-exchange.github.io/api-docs/

## Features

- **Best Practices**: We used experience gained working with API clients of the most major exchanges.
- **Core Functionality**: Enables basic operations like fetching account balances, placing orders, retrieving open and closed orders, establishing WebSocket channels etc.
- **Error Handling**: Built-in error handling. Use of <code>WBResponse</code>, <code>WBCloudException</code>, <code>WSResponse</code> and <code>WebSocketException</code> to give the user control over exceptions.
- **Flexibility**: Major configuration settings (HTTP client config, ObjectMapper, etc.) can be modified using <code>WBConfig</code> and <code>ClientConfig</code>. Also, the dependency injection principles has been used to allow user passing custom implementations.
- **Logging**: Logging of HTTP traffic is done using <code>HttpClientLogger</code>. You can disable excessive logging by configuring your logger OR pass custom <code>HttpLogger</code> into <code>HttpClientBuilder</code>.
- **Test Coverage**: Most of the code is covered with tests.

## Installation

To install the library you need to download and build (`mvn clean install -DskipTests`) this repo in your local env first.
After that, you can include it as a dependency into your project:

```xml
<dependency>
    <groupId>trade.wayruha.whitebit</groupId>
    <artifactId>whitebit-client</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## Usage Example
You can find examples in *test/* folder.

The usage is simple:

```
    ...
    // Initialize the client. Note that API keys are not needed to access public endpoints
    final WhiteBitClient config = new WhiteBitClient("your_api_key", "your_api_secret");
    final PublicDataService service = new PublicDataService(config);
    final List<MarketInfo> markets = service.getMarkets();
    System.out.println("Markets available: " + markets);
    ...
```

## Contribution
Co-authored with KushnerykPavel.

If you'd like to contribute to the development of this library, please submit issues or PRs.

## Support

For questions or issues, feel free to reach out at rwdiachuk@gmail.com.