@PETTEST
Feature: API Tests01

  @Prueba3
  Scenario: Create pet
    * def idPet = null
    Given url baseURL + '/v2/pet'
    And request
    """
    { "id": 20,
      "category": { "id": 200, "name": "Flores" },
      "name": "doggie",
      "photoUrls": [ "google.com" ],
      "tags": [
        { "id": 0, "name": "Titi" }
      ],
      "status": "available"
    }
    """
    When method POST
    * print response
    Then status 200
    And match response == { id: '#number', category: { id: '#number', name: '#string' }, name: '#string', photoUrls: ['#string'], tags: [{ id: '#number', name: '#string' }], status: '#string' }
    * def idPet = response.id
    And eval karate.log('New pet created with ID:', idPet)

  @Prueba4
  Scenario: Consult id pet created
    Given url baseURL + '/v2/pet/20'
    When method GET
    * print response
    Then status 200
    And eval karate.log('Get Pet:', response.id)

  @Prueba5
  Scenario: Update name and status pet created
    Given url baseURL
    And path '/v2/pet'
    And request
     """
    { "id": 9223372036854775807,
      "category": { "id": 0, "name": "Update" },
      "name": "Naira",
      "photoUrls": [  "https" ],
      "tags": [
        { "id": 0, "name": "TEST"
        }
      ],
      "status": "available"
    }
    """
    When method PUT
    Then status 200
    And eval karate.log('Put Pet:', response.id)

  @Prueba6
  Scenario: Consult status pet created
    Given url baseURL
    Given url baseURL + '/v2/pet/9223372036854775807'
    When method GET
    * print response
    Then status 200
    And eval karate.log('Get Pet:', response.status)



