@WebTest
Feature: API Tests01

  @WebTest1
  Scenario: google search
    * def util = Java.type('util.UtilClass')
    * configure driver = { type: 'chrome', addOptions: ["--remote-allow-origins=*"] }
    Given driver 'https://www.google.com/'
    * driver.maximize()
    * retry(10).waitForUrl("/www.google.com/")
    * retry(10).waitFor("#APjFqb")
    * delay(2000)
    And input("#APjFqb","Karate Framerork")
    * script('document.querySelector("form").submit();')
    #* mouse('input-password').click()
    * delay(5000)
   # And util.seleccionarMenu(driver, 'Pagos')