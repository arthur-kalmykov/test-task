###### **Test task**

Requirements: 

-java 11

-maven 3 

To launch application you can use command:  
`mvn spring-boot:run`

After that you can try url: 
http://localhost:8080/api/exchange
POST request with body 

`{"from":"usd", "to":"eur", "amount":100}`

You can use any of these currencies: EUR, AED, ARS, AUD, BGN, BRL, BSD, CAD, CHF, CLP, CNY, COP, CZK, DKK, DOP, EGP, FJD, GBP, GTQ, HKD, HRK, HUF, IDR,
                                        ILS, INR, ISK, JPY, KRW, KZT, MXN, MYR, NOK, NZD, PAB, PEN, PHP, PKR, PLN, PYG, RON, RUB, SAR, SEK, SGD, THB, TRY,
                                        TWD, UAH, USD, UYU, ZAR;

Possible http codes from response: 
200 - OK 

503 - Both apis are unavailable right now

If we are talking about possible auth for extended api, I would use something like that: 

Mono<LoginInformation> login = webClient.post().uri()...;
Mono<String> value = login.flatMap(info -> {
...
getting token header or cookie from login response
...
});

                        
