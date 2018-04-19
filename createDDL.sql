CREATE TABLE SERVICE (ID INTEGER NOT NULL, amount VARCHAR(255) NOT NULL, code VARCHAR(255) NOT NULL, codeType VARCHAR(255) NOT NULL, comercialUnitOfMeasurement VARCHAR(255) NOT NULL, detail VARCHAR(255) NOT NULL, discount VARCHAR(255) NOT NULL, lineNumber VARCHAR(255) NOT NULL, priceByUnit VARCHAR(255) NOT NULL, subTotal VARCHAR(255) NOT NULL, total VARCHAR(255) NOT NULL, totalAmount VARCHAR(255) NOT NULL, unitOfMeasurementName VARCHAR(255) NOT NULL, unitOfMeasurementType VARCHAR(255) NOT NULL, PRIMARY KEY (ID))
CREATE TABLE USEREMITTERRECEIVER (ID INTEGER NOT NULL, OtherSignals VARCHAR(255) NOT NULL, comercialName VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, faxCountryCode VARCHAR(255) NOT NULL, faxNumber VARCHAR(255) NOT NULL, identificationNumber VARCHAR(255) NOT NULL, identificationType VARCHAR(255) NOT NULL, locationCantonName VARCHAR(255) NOT NULL, locationCantonType VARCHAR(255) NOT NULL, locationDistrictName VARCHAR(255) NOT NULL, locationDistrictType VARCHAR(255) NOT NULL, locationNeighborhoodName VARCHAR(255) NOT NULL, locationNeighborhoodType VARCHAR(255) NOT NULL, locationProvinceName VARCHAR(255) NOT NULL, locationProvinceType VARCHAR(255) NOT NULL, name VARCHAR(255) NOT NULL, phoneCountryCode VARCHAR(255) NOT NULL, phoneNumber VARCHAR(255) NOT NULL, userType VARCHAR(255) NOT NULL, PRIMARY KEY (ID))
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50
