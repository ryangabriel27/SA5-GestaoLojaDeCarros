## `Relatório Situação de Aprendizagem 5 - Loja de Carros`
# Introdução 
  Nesse trabalho foi feito uma aplicação que se consiste em simular um sistema de gestão de uma loja de carros, onde temos uma maneira de cadastrar, remover e editar os carros, também é possível fazer o cadastro, edição e remoção de clientes no sistema e por fim conseguimos registrar vendas de carros cadastrados no sistema para clientes também cadastrados. A aplicação foi feita com a linguagem Java, utilizando POO (Programação Orientada a Objetos), JDBC (Java Database Connection) e o método MVC (Model/ View/ Control).

# Desenvolvimento
  Em primeiro momento foi feita as 3 Classes Principais no package 'Model': Carros, Clientes e Vendas. Essas classes, como o nome do package propriamente diz, servem de modelo para as classes Control então nelas temos os atributos referentes a cada categoria, construtor para cada classe e os métodos Getters and Setters. Após feita a criação das classes 'Model', fomos para as classes 'View', que por sua vez são mais simples de ser feitas, e assim ja definimos a estética, layout para a aplicação.
  Feita as classes 'Model' e 'View', partimos para a parte um pouco mais complexas da aplicação as classes 'Control' e 'Connection'. A classe 'Control' é basicamente um CRUD (Create/ Read/ Update/ Delete), porém é necessário juntar este CRUD ao banco de dados de forma que os dois operem ao mesmo tempo, "Ex: Cadastro um carro na aplicação e no mesmo momento o mesmo carro é cadastrado no banco de dados". Dessa forma foi criada as classes DAO (Data Acess Object) que fazem essa "sincronização" da aplicação visual com o banco de dados, nestas classes temos também um tipo de CRUD e um método diferente que é o 'listarTodos()', que lista todos os dados que estão no banco de dados, esse método é de extrema importância pois é ele quem nos mostra os carros, clientes ou vendas registrados no sistema. As classes DAO são instanciadas nos métodos da classe control, de forma respectiva, por exemplo, o método cadastrarCarro instancia o CarrosDAO().cadastrar.

# Conclusão
  Por fim, a aplicação mesmo que simples tem uma grande importância, pois com ela conseguimos entender e estudar o JDBC, e utiliza-lo em situações comuns no mercado de trabalho, é claro que essa aplicação é uma versão mais básica, com pouca profundidade. Porém, é necessário compreender o básico para que se consiga fazer o avançado.

 # `Manual da Aplicação:`
 
