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

Electronic invoice example app [here](https://flecharoja.com/blog/2017-11/integracion-factura-digital/)

Hacienda API Info [here](https://tribunet.hacienda.go.cr/docs/esquemas/2016/v4.2/comprobantes-electronicos-api.html#)

Hacienda security info [here](http://www.hacienda.go.cr/contenido/14050-nuevas-funcionalidades-en-atv-para-elaboracion-de-comprobantes-electronicos)

Hacienda XML formatting [here](https://tribunet.hacienda.go.cr/FormatosYEstructurasXML.jsp#)

Hacienda Comprobantes electronicos [here](https://tribunet.hacienda.go.cr/docs/esquemas/2016/v4.2/ResolucionComprobantesElectronicosDGT-R-48-2016_4.2.pdf)

Hacienda Schema [here](https://tribunet.hacienda.go.cr/docs/esquemas/2016/v4.2/MensajeHacienda_V4.2.pdf)

Hacienda manual de ayuda [here](http://www.hacienda.go.cr/docs/5a550c170342c_Manual%20de%20uso%20de%20la%20Herramienta%20Gratuita%20de%20Facturacion.pdf)


#### Cryptography examples

[https://github.com/luisgoncalves/xades4j](https://github.com/luisgoncalves/xades4j)

[https://www.programcreek.com/java-api-examples/index.php?api=javax.xml.crypto.dsig.XMLSignatureFactory](https://www.programcreek.com/java-api-examples/index.php?api=javax.xml.crypto.dsig.XMLSignatureFactory)

[https://github.com/carloswky/pp/blob/master/afirma-crypto-xades/src/test/java/es/gob/afirma/signers/xades/TestFacturaE.java](https://github.com/carloswky/pp/blob/master/afirma-crypto-xades/src/test/java/es/gob/afirma/signers/xades/TestFacturaE.java)

[http://camel.apache.org/xml-security-component.html](http://camel.apache.org/xml-security-component.html)

[http://www.baeldung.com/spring-apache-camel-tutorial](http://www.baeldung.com/spring-apache-camel-tutorial)


For more information about using Java on Heroku, see these Dev Center articles:

- [Java on Heroku](https://devcenter.heroku.com/categories/java)
