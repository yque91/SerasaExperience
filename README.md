# SerasaExperience
Programa criado para o desafio do programa Serasa e ProWay

Montado em Java, com interface no Java Swing e banco de dados MySQL

Primeiramente monte o banco de dados. Faça o download do dump no seu servidor. O dump se encontra na pasta principal

Então importe o dump. Importante que o banco de dados tenha o nome serasa

Agora você têm duas opções, 1- coloca seu servidor local com essas informações, que é o padrão:
 
No caso aqui a porta é a 3306 (padrão MySQL)

        localhost:3306;
        user = "root";
        password = "";
        
        
Ou 2-  modifique a classe ModuloConexao.java e utilize as configurações do seu servidor. Lembre de utilizar o nome do banco de dados serasa no final.

        String url = "jdbc:mysql://localhost:3306/serasa";
        String user = "root";
        String password = "";
        
   
       
 Agora é só ir dentro da past dist e executar o arquivo SerasaExperience.jar
 
               Enjoy it!
