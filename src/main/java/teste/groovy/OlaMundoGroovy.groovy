package teste.groovy

class OlaMundoGroovy {
    static main(args){
        println "Ola mundo goovy"
        String  nome = "Udinei"
        int idade = 50

        // interpolação(concatenação) de string usando $
        String frase = " O $nome tem $idade anos"
        println frase
    }
}
