# Pernix Invoicing


## Running Locally

Make sure you have Java and Maven installed.  Also, install the [Heroku CLI](https://cli.heroku.com/).

```sh
$ git clone https://github.com/tebanhdez/e-invoicing-pernix.git
$ cd e-invoicing-oernix
$ mvn install
$ heroku local:start
```

Your app should now be running on [localhost:5000](http://localhost:5000/).

If you're going to use a database, ensure you have a local `.env` file that reads something like this:

```
DATABASE_URL=postgres://localhost:5432/java_database_name
```

## Deploying to Heroku

```sh
$ heroku create
$ git push heroku master
$ heroku open
```

## Documentation

### Important notes

Electronic invoice example app 
[here](https://flecharoja.com/blog/2017-11/integracion-factura-digital/)

Hacienda API Info 
[here](https://tribunet.hacienda.go.cr/docs/esquemas/2016/v4.2/comprobantes-electronicos-api.html#)

Hacienda security info 
[here](http://www.hacienda.go.cr/contenido/14050-nuevas-funcionalidades-en-atv-para-elaboracion-de-comprobantes-electronicos)

Hacienda XML formatting 
[here](https://tribunet.hacienda.go.cr/FormatosYEstructurasXML.jsp#)

Hacienda Comprobantes electronicos 
[here](https://tribunet.hacienda.go.cr/docs/esquemas/2016/v4.2/ResolucionComprobantesElectronicosDGT-R-48-2016_4.2.pdf)

Hacienda Schema 
[here](https://tribunet.hacienda.go.cr/docs/esquemas/2016/v4.2/MensajeHacienda_V4.2.pdf)

Hacienda manual help 
[here](http://www.hacienda.go.cr/docs/5a550c170342c_Manual%20de%20uso%20de%20la%20Herramienta%20Gratuita%20de%20Facturacion.pdf)

XML formats to Hacienda 
[Here](https://tribunet.hacienda.go.cr/FormatosYEstructurasXML.jsp#)

Informacion sobre XADES4J
[Here](http://luisgoncalves.github.io/xades4j/javadocs/1.3.1/reference/xades4j/package-summary.html)

#### Cryptography examples

XADES4J 
[Here](https://github.com/luisgoncalves/xades4j)

Examples 
[Here](https://www.programcreek.com/java-api-examples/index.php?api=javax.xml.crypto.dsig.XMLSignatureFactory)

Test Invoice 
[Here](https://github.com/carloswky/pp/blob/master/afirma-crypto-xades/src/test/java/es/gob/afirma/signers/xades/TestFacturaE.java)

Apache signature 
[Here](http://camel.apache.org/xml-security-component.html)

Spring Apache Camel Tutorial
[Here](http://www.baeldung.com/spring-apache-camel-tutorial)


For more information about using Java on Heroku, see these Dev Center articles:

Java on Heroku
[Here](https://devcenter.heroku.com/categories/java)

#### Sign XML example using xades4j java library

JohnnyJosep Example 
[Here](https://gist.github.com/JohnnyJosep/29cd545db3d0b7abd23279b56d4db194)

#### Archivos utiles e importantes para la faturacion electronica
CR Libre Hacienda Important Files
[Here](https://github.com/CRLibre/fe-hacienda-cr-misc)

#### Diagrama de flujo para factura electronica 
Diagrama
[Here](https://github.com/CRLibre/fe-hacienda-cr-docs/wiki/Diagrama-de-flujo-Factura-Electr%C3%B3nica-Costa-Rica)

#### Compilado JAR para firmar las facturas
FirmaXadesEpes
[Here](https://github.com/JonhCR/FirmaXadesEpes)

#### Spring MVC JUnit tests 
Unit tests
[Here](https://spring.io/guides/tutorials/bookmarks/)

#### Rest controller test example
[Here](https://blog.zenika.com/2013/01/15/rest-web-services-testing-with-spring-mvc/)

#### Other important stuff
JPA best mapping oneToMany 
[Here](https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/)

Issue with Spring solved 
[Here](https://github.com/spring-projects/spring-boot/issues/6792#issuecomment-243564648)

Deploying in Heroku
[Here](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started-first-application.html)