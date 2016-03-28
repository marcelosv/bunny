# spring-boot-bunny
Este é um componente multitenant para ser usado em aplicações spring-boot.
Com este componente, pode ser configurado múltiplas fontes de dados para manter os dados em diferentes esquemas.
Que está usando o [Hibernate suporte multi-tenancy] (https://docs.jboss.org/hibernate/orm/4.2/devguide/en-US/html/ch16.html) trabalhando com a estratégia de banco de dados separado.

## Compilar e pacote

1) Criar uma entity que implementa a interface DataSourceConfig.
EX:

<code>
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
</code>
