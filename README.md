# Servidor Calculadora com Apache Thrift em Java

## Sobre o projeto

Este projeto implementa um servidor de calculadora em **Java** utilizando o **Apache Thrift** para disponibilizar operações matemáticas por chamadas remotas. A aplicação possui uma interface gráfica desenvolvida com **Swing**, que permite iniciar o servidor, informar a porta de comunicação e acompanhar as chamadas recebidas em uma área de
log.

O serviço oferece quatro operações, cada uma recebendo dois valores `double` e retornando um `double`:

-   **Soma:** adiciona dois números.
-   **Subtração:** subtrai o segundo número do primeiro.
-   **Multiplicação:** multiplica dois números.
-   **Divisão:** divide o primeiro número pelo segundo.

## Objetivos didáticos

O projeto demonstra:

-   O conceito de **middleware** em sistemas distribuídos.
-   A separação entre cliente e servidor.
-   Chamadas remotas de procedimentos/métodos (RPC).
-   A definição de contratos de serviço por meio de uma IDL.
-   O papel do processador e do handler no Apache Thrift.
-   A comunicação por sockets TCP e a serialização de dados.
-   A configuração de dependências com Maven.


## Tecnologias utilizadas

-   **Java 8:** versão indicada pelas propriedades `maven.compiler.source` e `maven.compiler.target` do `pom.xml`.
-   **Java Swing** — construção da interface gráfica.
-   **Apache Maven:** para automatizar o processo de compilação.	
-   **Apache Thrift 0.10.0:** biblioteca declarada como dependência Maven e tecnologia usada para comunicação entre aplicações.
-   **TCP:** transporte usado pelo servidor para receber chamadas.
-   **Thrift IDL:** linguagem de definição de interface usada no arquivo `calculadora.thrift`.

> A versão das bibliotecas Apache Thrift do servidor deve ser compatível com a utilizada pelo cliente e com as classes geradas a partir da definição do serviço.

## Estrutura do projeto

Os principais arquivos do servidor são:

| Arquivo | Responsabilidade |
|---|---|
| `pom.xml` | Configura o projeto Maven, identifica o artefato, define Java 8 e declara a dependência `libthrift` na versão `0.10.0`. |
| `calculadora.thrift` | Define o serviço `CalculadoraService` e as quatro operações remotas. |
| `src/main/java/Principal.java` | Ponto de entrada da aplicação, conforme os arquivos Java examinados anteriormente. |
| `src/main/java/CtlServidor.java` | Abre a janela do servidor. |
| `src/main/java/FrmServidor.java` | Interface Swing; configura a porta, inicia o servidor e exibe o log. |
| `src/main/java/FrmServidor.form` | Arquivo de formulário da interface gráfica, normalmente usado pelo editor visual da IDE. |
| `src/main/java/CalculadoraHandler.java` | Implementa as operações matemáticas solicitadas pelos clientes. |
| `src/main/java/CalculadoraService.java` | Código Java gerado pelo compilador Apache Thrift; contém a interface, o cliente e o processador do serviço. |

## Definição do serviço: `calculadora.thrift`

O arquivo fornecido contém esta definição:

``` thrift
service CalculadoraService
{
    double multiplicar(1:double n1, 2:double n2),
    double dividir(1:double n1, 2:double n2),
    double somar(1:double n1, 2:double n2),
    double subtrair(1:double n1, 2:double n2)
}
```

A definição declara o nome do serviço, os métodos disponíveis e seus parâmetros. Os identificadores `1` e `2` identificam os campos dos argumentos na definição Thrift. A ordem em que os métodos aparecem no arquivo não altera a finalidade matemática de cada operação.

O arquivo `CalculadoraService.java` fornecido indica que o código foi gerado pelo compilador Apache Thrift 0.10.0. Caso a definição `.thrift` seja alterada, o código Java gerado deve ser atualizado de forma compatível com essa definição.

## Operações disponíveis

O servidor disponibiliza as quatro operações:

| Operação | Método remoto | Exemplo |
|---|---|---|
| Soma | `somar(n1, n2)` | `3 + 2 = 5` |
| Subtração | `subtrair(n1, n2)` | `3 − 2 = 1` |
| Multiplicação | `multiplicar(n1, n2)` | `3 × 2 = 6` |
| Divisão | `dividir(n1, n2)` | `6 ÷ 2 = 3` |

## Pré-requisitos

-   JDK compatível com Java 8 ou configuração de compilação equivalente.
-   Maven instalado ou suporte Maven disponível na IDE.
-   Acesso ao repositório Maven para baixar `org.apache.thrift:libthrift:0.10.0`.
-   Arquivos-fonte Java do projeto e suas classes geradas pelo Thrift.

## Como executar

### Abrir e executar pela IDE

1.  Abra ou importe o projeto como projeto Maven.
2.  Aguarde a IDE resolver a dependência `libthrift`.
3.  Confira se os arquivos Java estão no diretório de fontes reconhecido pelo Maven e se os pacotes declarados correspondem à estrutura do projeto.
4.  Localize a classe que contém o método `main` e execute-a.
5.  Na janela **Servidor Calculadora**, mantenha a porta `9090` ou informe outra porta disponível.
6.  Clique em **Iniciar**.
7.  Mantenha a janela aberta enquanto o servidor estiver atendendo aos clientes.

### Executar com Maven

O `pom.xml` fornecido não declara explicitamente o plugin Maven Exec. Por isso, não é possível garantir que `mvn exec:java` funcione sem configuração adicional. Antes de usar esse comando, confirme a classe principal e configure o plugin correspondente, se necessário. 

### Gerar novamente o código Java a partir do `.thrift`

O `pom.xml` não configura um plugin para executar automaticamente o compilador Thrift. Se for necessário regenerar
`CalculadoraService.java`, será preciso ter uma versão compatível do compilador Apache Thrift instalada e executar o comando apropriado para a linguagem Java, direcionando a saída ao diretório de fontes do projeto. O comando exato depende da instalação e da estrutura de diretórios adotada.

## Como o servidor funciona

1.  A classe principal inicia o controlador.
2.  O controlador cria e exibe a janela `FrmServidor`.
3.  O usuário informa a porta TCP; o valor inicial configurado é `9090`.
4.  Ao clicar em **Iniciar**, a aplicação cria um `CalculadoraHandler` e associa a ele a área de texto usada para registrar chamadas.
5.  O `CalculadoraService.Processor` encaminha as solicitações aos métodos correspondentes do handler.
6.  O servidor cria um `TServerSocket` na porta informada e inicia um `TSimpleServer`.
7.  Quando um cliente chama uma operação, o handler calcula o resultado e registra a chamada na área de log.

Ao iniciar, o log deverá exibir uma mensagem semelhante a:

``` text
Iniciando servidor da Calculadora na porta=9090
```

Uma chamada pode gerar um registro semelhante a:

``` text
Somar(8.0,2.0)
```

## Conexão de um cliente

-   **Cliente na mesma máquina:** use `localhost` e a porta configurada, normalmente `9090`.
-   **Cliente em outra máquina:** use o endereço IP da máquina que executa o servidor e a mesma porta.
-   **Rede e firewall:** para conexões remotas, confirme a conectividade e permita a porta TCP no firewall, quando apropriado.

O cliente deve usar uma definição de serviço compatível com `calculadora.thrift` e com o protocolo configurado no servidor. A definição `.thrift` fornecida declara os métodos e os argumentos; a configuração concreta do cliente também deve corresponder à configuração de transporte e protocolo usada na aplicação.

## Observações e limitações

-   A porta inicial é `9090`, mas pode ser alterada na interface antes de iniciar o servidor.
-   Escolha uma porta que não esteja sendo utilizada por outro processo.
-   A implementação não impede explicitamente que o usuário clique em **Iniciar** várias vezes; recomenda-se iniciar o servidor apenas uma vez por execução.
-   O botão **Fechar** encerra a aplicação.
-   As operações não incluem validações adicionais para divisão por zero ou valores especiais.
-   `TSimpleServer` é uma implementação simples, adequada a exemplos didáticos. Requisitos maiores de concorrência, disponibilidade ou segurança podem exigir outra configuração.
-   O código apresentado não configura autenticação nem criptografia. Evite expor o servidor a redes não confiáveis sem medidas de segurança apropriadas.
-   O `pom.xml` declara a biblioteca Thrift, mas não configura a geração automática do código Java a partir de `calculadora.thrift`.
-   A classe principal definida em `exec.mainClass` deve ser conferida em relação à classe que realmente contém `main`.

## Conceitos importantes

- **Cliente:** aplicação que solicita um serviço remoto.
- **Servidor:** aplicação que recebe solicitações, executa operações e devolve respostas.
- **Apache Thrift:** framework que permite definir serviços e realizar chamadas remotas.
- **Thrift IDL:** linguagem usada para declarar o contrato do serviço em `calculadora.thrift`.
- **Handler:** classe que implementa as operações disponibilizadas pelo servidor.
- **Processor:** componente que encaminha as chamadas recebidas aos métodos do serviço.
- **Socket TCP:** mecanismo de comunicação entre as aplicações pela rede.

## Conclusão

O projeto demonstra um servidor de calculadora em Java, com interface Swing e comunicação remota por Apache Thrift. O arquivo `calculadora.thrift` define o contrato do serviço, o `pom.xml` declara a dependência da biblioteca Thrift e as classes Java implementam e atendem às operações de soma, subtração, multiplicação e divisão.
