# spring-boot-bunny
Este é um componente multitenant para ser usado em aplicações spring-boot.
Com este componente, pode ser configurado múltiplas fontes de dados para manter os dados em diferentes esquemas.
Que está usando o [Hibernate suporte multi-tenancy] (https://docs.jboss.org/hibernate/orm/4.2/devguide/en-US/html/ch16.html) trabalhando com a estratégia de banco de dados separado.

## Status
* Em desenvolvimento
* Criar as anotações - OK
* Testar com request - OK
* Integrar com spring-security - OK

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

## Configuração do Spring Security
Porque integrar com o Spring Security?<br>
Quando o sistema é integrado ao Spring Security, após o login, os dados do usuário ficam registrados.<br> 
É possível obter os dados do login como está abaixo ou injetando.<br>

```java
	SecurityContextHolder.getContext().getAuthentication().getPrincipal();
```

Desta forma, podemos ter neste objeto, os dados que o bunny precisa.<br>
Na classe TenantIdentifierResolver do bunny ele identifica se o já existe um usuário logado, e já retorna o identificador do tenancy.<br>
Para nos isso é importante. 
<br>
Caso esteja em um projeto de micro-service, os demais serviços, iram já funcionar corretamente, identificando o tenancy pelo usuário logado.<br>

Vamos configurar o spring security:
1) Deve ser usado no maven a seguinte dependencia.

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

2) Crie uma classe que vai servir para manter nosso dados de login.

```java
public class UserDetailsModify extends User implements DataSourceConfigSecurity {

	private static final long serialVersionUID = 3375988537129735478L;
	private DataSourceTable dataSourceTable;

	public UserDetailsModify(String login, String senha, List<GrantedAuthority> auth, DataSourceTable dataSourceTable) {
		super(login, senha, auth);
		this.dataSourceTable = dataSourceTable;
	}

	@Override
	public DataSourceConfig getDataSourceConfig() {
		return dataSourceTable;
	}
	
}
```

O importante nesta classe, para o bunny, é a implementação da classe DataSourceConfigSecurity.

3) Crie um service básico, para consultar os dados do login.

```java
@Service
public class Users implements UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private DataSourceTableRepository repository;
	
	@Override
	public OpsUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		// sua entidade da tabela de usuário normal
		Usuario user = repo.findByEmail(email);
		
		if (user == null) {
			return null;
		}
		
		List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		DataSourceTable dataSourceTable = repository.findOne(user.getId());
		
		return new UserDetailsModify(user.getEmail(), user.getSenha(), auth, dataSourceTable);
	}

}
```

4) Pronto, basta criar agora o bean para configurar o spring security.

```java
	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Autowired
		private Users users;		
		
		@Autowired
		public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
			// @formatter:off	
			
			auth.userDetailsService(users);
			
		}
	}
```








