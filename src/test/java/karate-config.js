function fn() {
    var env = karate.env;
    var baseURL = ''
    var usuario = ''
    var password = ''
    var sociantsURL = ''

    karate.log('Se realiza la ejecución en ambiente: ', env);

    if (!env) {
        env = 'dev';
    }
    if (env === 'alpha') {
        baseURL = 'https://petstoredev.swagger.io'
        sociantsURL = 'https://alpha.sociants.com/'
        usuario = ''
        password = ''

    } else if (env === 'beta') {
        baseURL = 'https://petstore.swagger.io'
        sociantsURL = 'https://petstoredev.swagger.io'
        usuario = ''
        password = ''

    }else if (env === 'prod') {
        sociantsURL = 'https://petstoredev.swagger.io'
         baseURL = 'https://petstore.swagger.io'
         usuario = ''
         password = ''
    }

    var config = {
        env: env,
        baseURL: baseURL,
        usuario: usuario,
        password: password,
        sociantsURL:sociantsURL
    }
    return config;
}
