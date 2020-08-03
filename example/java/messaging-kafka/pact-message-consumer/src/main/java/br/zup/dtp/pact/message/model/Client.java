package br.zup.dtp.pact.message.model;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class Client {

	@NotNull
	private long id;

	@NotNull
	private String name;

	@NotNull
	private String type;

}
