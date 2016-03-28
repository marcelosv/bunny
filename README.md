# spring-boot-bunny
Este é um componente multitenant para ser usado em aplicações spring-boot.
Com este componente, pode ser configurado múltiplas fontes de dados para manter os dados em diferentes esquemas.
Que está usando o [Hibernate suporte multi-tenancy] (https://docs.jboss.org/hibernate/orm/4.2/devguide/en-US/html/ch16.html) trabalhando com a estratégia de banco de dados separado.

## Status
* Em desenvolvimento
* Criar as anotações - OK
* Testar com request - OK
* Integrar com spring-security - em desenvolvimento

## Agradecimentos

Ao github https://github.com/rcandidosilva. 
O projeto spring-boot-multitenant foi usado como base para este componente.


## Configuração
1) Baixar o projeto no git https://github.com/betao22ster/bunny, dar o install do maven.

2) No seu projeto, adicionar a dependencia:

```xml
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>bunny</artifactId>
		<version>0.0.1-SNAPSHOT</version>
	</dependency>
```
		
3) No seu projeto, criar uma entity que implementa a interface DataSourceConfig.
EX:

```java
@Entity
@Table(name="NOME_TABLE")
public class DataSourceTable implements DataSourceConfig, Serializable {

	private static final long serialVersionUID = -5018185835788890513L;
	
	@Id @GeneratedValue
    private Long id;
    private String name;
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private boolean initialize;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public boolean getInitialize() {
        return initialize;
    }
}
```

4) No seu projeto, criar uma respository que implementa a interface DataSourceConfigRepository.

```java

@Repository
public interface DataSourceTableRepository extends DataSourceConfigRepository<DataSourceTable>, JpaRepository<DataSourceTable, Long> {

}

```

5) Na sua classe principal, adicionar a anotação @LoadDataSourceConfig adicionando as duas classes criadas e a anotação @ComponentScan com o pacote do bunny.

```java
@LoadDataSourceConfig(config=DataSourceTable.class, configRepository=DataSourceTableRepository.class)
@ComponentScan(basePackages={"org.springframework.boot.bunny.multitenant"})
```



















